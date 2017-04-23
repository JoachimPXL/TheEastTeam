package mkyong.hybrid.encrypt;

import mkyong.writers.Hash;
import mkyong.writers.InputOutputController;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by 11500555 on 23/03/2017.
 */
public class StartEncryption {

	public PrivateKey getPrivateKey(String filename, String algorithm) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		return kf.generatePrivate(spec);
	}

	public PublicKey getPublicKey(String filename, String algorithm) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		return kf.generatePublic(spec);
	}
	
	public SecretKeySpec getSecretKey(String filename, String algorithm) throws IOException{
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		return new SecretKeySpec(keyBytes, algorithm);
	}
	
	public static void Encrypt(String path) throws IOException, GeneralSecurityException, Exception {
		StartEncryption startEnc = new StartEncryption();
		/*
		Scanner scanner = new Scanner(System.in);
		System.out.println("Geef de boodschap in die u wilt encrypteren.");
		String boodschap = scanner.nextLine();
		*/

		File messageFile = new File("confidential.txt");
		String boodschap = InputOutputController.readFile(path);

		InputOutputController.writeToFileWithoutParent(messageFile, boodschap.getBytes());

		File originalKeyFile = new File("src/main/Files/OneKey/secretKey");
		File encryptedKeyFile = new File("src/main/Files/EncryptedFiles/File_2");
		new EncryptKey(startEnc.getPublicKey("src/main/Files/KeyPair/public_B", "RSA"), originalKeyFile, encryptedKeyFile, "RSA");

		File originalFile = new File("confidential.txt");
		File encryptedFile = new File("src/main/Files/EncryptedFiles/File_1");
		new EncryptData(originalFile, encryptedFile, startEnc.getSecretKey("src/main/Files/OneKey/secretKey", "AES"), "AES");

		byte[] hashByte = Hash.getMD5ByteArray("confidential.txt");
		File encryptedHashFile = new File("src/main/Files/DecryptedFiles/EncryptedHashedData/File_3");
		byte[] encryptedHash = Sign.signData(hashByte,startEnc.getPrivateKey("src/main/Files/KeyPair/private_A", "RSA"));
		InputOutputController.writeToFile(encryptedHashFile,encryptedHash);
	}
}
