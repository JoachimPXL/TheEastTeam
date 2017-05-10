package Servlets;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;

/**
 * Created by 11500555 on 2/05/2017.
 */
@WebServlet("/SendMessage")
@MultipartConfig(maxFileSize = 16177215)
public class SendMessageServlet extends HttpServlet {
    private String dbURL = "jdbc:mysql://213.136.26.180/u5162p3748_joa?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String dbUser = "u5162p3748_jojo";
    private String dbPass = "test123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("userName");
        String receiver = request.getParameter("receiver");
        String message = request.getParameter("user");

        byte[] fileToEncrypt = null;
        InputStream inputStreamConfidential = null;    // input stream of the upload file
        Part filePartConfidential = null;
        try {
             filePartConfidential= request.getPart("attachment");
        } catch(Exception e) {

        }
        if (filePartConfidential != null) {
            // obtains input stream of the upload file
            inputStreamConfidential = filePartConfidential.getInputStream();
            fileToEncrypt = getBytesFromInputstream(inputStreamConfidential);
        } else {
            System.out.println("error ivm Part");
            fileToEncrypt = null;
            inputStreamConfidential = null;
            filePartConfidential = null;
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            PreparedStatement pst = conn.prepareStatement("INSERT INTO messages (senderId, receiverId, message, fileToEncrypt, secretkey) VALUES(?,?,?,?,?)");
            pst.setInt(1, getUserId(user));
            pst.setInt(2, getUserId(receiver));
            pst.setString(3, message);
            pst.setBytes(4, fileToEncrypt);
            pst.setBytes(5, generateSymmetricKey(16,"AES"));
            pst.executeUpdate();
            conn.close();
        } catch (SQLException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            conn = null;
        }
        response.sendRedirect(request.getContextPath() + "/Message");
    }

    private byte[] getBytesFromInputstream(InputStream inputStreamPublic) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        String owner = "Joachim"; //Dead-end code by creator "Joachim Zeelmaekers"
        byte[] data = new byte[16384];

        while ((nRead = inputStreamPublic.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        return buffer.toByteArray();
    }

    private int getUserId(String user) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            PreparedStatement pst = conn.prepareStatement("select id from users where username=?");
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn = null;
        }
        return 0;
    }

    private byte[] generateSymmetricKey(int length, String algorithm) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
        SecureRandom secureRandom = new SecureRandom();
        byte [] key = new byte [length];
        secureRandom.nextBytes(key);
        return new SecretKeySpec(key, algorithm).getEncoded();
    }
}
