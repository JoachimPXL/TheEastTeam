package Servlets;

import JavaBeans.Message;
import Services.*;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by 11500555 on 9/05/2017.
 */
@WebServlet("/DownloadFile")
public class EncryptAndDecryptServlet extends HttpServlet {

    private String dbURL = "jdbc:mysql://213.136.26.180/u5162p3748_joa?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String dbUser = "u5162p3748_jojo";
    private String dbPass = "test123";
    private SecretKeySpec secretKeySpec = null;
    private PublicKey publicKeySender = null;
    private PrivateKey privateKeySender = null;
    private PublicKey publicKeyReceiver = null;
    private PrivateKey privateKeyReceiver = null;
    private Message messageSend = null;
    private boolean validToDownloadOrNot = false;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int messageId = Integer.parseInt(req.getParameter("messageId"));
        //Message die meekomt met de ID

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM messages WHERE id = ?");
            pst.setInt(1, messageId);
            ResultSet rs = pst.executeQuery();
            //parse all data into Message Object FROM the Database (ip 213.136.26.180)
            while (rs.next()) {
                messageSend = new Message(rs.getBytes(5), rs.getString(4), rs.getInt(2), rs.getInt(3), rs.getInt(1), rs.getBytes(6));
            }
            //Get the keypairs from Receiver from Users

            PreparedStatement pstReceiver = conn.prepareStatement("SELECT * FROM users WHERE id=?");
            pstReceiver.setInt(1, messageSend.getReceiverId());
            rs = pstReceiver.executeQuery();
            while (rs.next()) {
                publicKeyReceiver = getPublic(rs.getBytes(5),"RSA");
                privateKeyReceiver = getPrivateKeyInBytes(rs.getBytes(6),"RSA");
            }

            PreparedStatement pstSender = conn.prepareStatement("SELECT * FROM users WHERE id=?");
            pstSender.setInt(1, messageSend.getSenderId());
            rs = pstSender.executeQuery();
            while (rs.next()) {
                publicKeySender = getPublic(rs.getBytes(5),"RSA");
                privateKeySender = getPrivateKeyInBytes(rs.getBytes(6),"RSA");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn = null;
        }
        //creates a secretKey

        try {
            generateSecretKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        messageSend.setSecretKeySpec(secretKeySpec);
        //Aanmaken van keys indien deze niet bestaan of invalid zijn.
        if(privateKeyReceiver == null && publicKeyReceiver == null) {
            // Get the public/private key pair
            KeyPairGenerator keyGen = null;
            try {
                keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(1024);
                KeyPair keyPair = keyGen.genKeyPair();
                privateKeyReceiver = keyPair.getPrivate();
                publicKeyReceiver = keyPair.getPublic();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        //Aanmaken van keys indien deze niet bestaan of invalid zijn.
        if(privateKeySender == null && publicKeySender == null) {
            KeyPairGenerator keyGen = null;

            try {
                keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(1024);
                KeyPair keyPair = keyGen.genKeyPair();
                privateKeySender = keyPair.getPrivate();
                publicKeySender = keyPair.getPublic();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        try {
            Encrypt();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error bij encrypteren");
        }
        if(validToDownloadOrNot) {
            ServletOutputStream outStream = resp.getOutputStream();
            resp.setContentType("");
            resp.setHeader("Content-Disposition", "attachment; filename=" + "decryptedMessage.txt");
            outStream.write(messageSend.getFileInBytes());
            outStream.flush();
        } else {
            resp.sendRedirect("index.jsp");
        }
    }

    public void Encrypt() throws Exception {
        //message encrypted with secretkey ( symmetric )
        EncryptData d = new EncryptData("AES" );

        byte[] encryptedMessageWithSecret = d.encryptFile(messageSend.getFileInBytes(),secretKeySpec); //File 1

        //symmetric key encrypted with publickey from receiver
        EncryptKey k = new EncryptKey( "RSA");
        System.out.println(getSecretKey(secretKeySpec.getEncoded(),"AES"));
        byte[] encryptedKeyWithPublic = k.encryptFile(secretKeySpec.getEncoded(),publicKeyReceiver); // File 2

        //Sign data with private key of sender
        byte[] hashedsign = Sign.signData(hash(messageSend.getFileInBytes()),privateKeySender); //File 3 // nog hashen
        Decrypt(encryptedKeyWithPublic, encryptedMessageWithSecret, hashedsign);
    }

    public void Decrypt( byte[] encryptedKeyWithPublic, byte[] encryptedMessageWithSecret, byte[] signed) throws Exception {
        DecryptKey k = new DecryptKey("RSA");
        byte[] decryptedSecret = k.decryptFile(encryptedKeyWithPublic, privateKeyReceiver);
        //file 2 = decrypted and secretkeys are equal.

        DecryptData d = new DecryptData("AES");
        byte[] decryptedMessage = d.decryptFile(encryptedMessageWithSecret,getSecretKey(decryptedSecret,"AES"));
        //sendmessage.bytes are equal to decryptedMessage (= decrypted)
        //System.out.println(Arrays.equals(messageSend.getFileInBytes(), decryptedMessage));

        validToDownloadOrNot = Sign.verifySig(hash(decryptedMessage),publicKeySender,signed);
    }

    public static byte[] hash(byte[] hashThis) { // source: http://www.java2s.com/Code/Android/Security/ComputetheSHA1hashofthegivenbytearray.htm
        try {
            byte[] hash = new byte[128];
            MessageDigest md = MessageDigest.getInstance("MD5");

            hash = md.digest(hashThis);
            return hash;
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("SHA-1 algorithm is not available...");
            System.exit(2);
        }
        return null;
    }

    public static PublicKey getPublic(byte[] bytes, String algorithm) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(spec);
    }

    public static PrivateKey getPrivateKeyInBytes(byte[] privateKey, String algorithm) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        return kf.generatePrivate(spec);
    }

    public static SecretKeySpec getSecretKey(byte[] bytes, String algorithm) throws IOException {
        return new SecretKeySpec(bytes, algorithm);
    }

    public void generateSecretKey() throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
        SecureRandom secureRandom = new SecureRandom();
        byte [] key = new byte [16];
        secureRandom.nextBytes(key);
        this.secretKeySpec = new SecretKeySpec(key, "AES");
    }

}