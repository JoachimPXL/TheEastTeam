package com.mkyong.hybrid.decrypt;

import com.mkyong.hybrid.encrypt.Sign;
import com.mkyong.writers.Hash;
import com.mkyong.writers.InputOutputController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by 11500555 on 23/03/2017.
 */
public class StartDecryption {

	public PrivateKey getPrivate(String filename, String algorithm) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		return kf.generatePrivate(spec);
	}

	public PublicKey getPublic(String filename, String algorithm) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		return keyFactory.generatePublic(spec);
	}

	public SecretKeySpec getSecretKey(String filename, String algorithm) throws IOException {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		return new SecretKeySpec(keyBytes, algorithm);
	}

	public static void main(String[] args) throws IOException, GeneralSecurityException, Exception {
		StartDecryption startEnc = new StartDecryption();

		File encryptedKeyReceived = new File("EncryptedFiles/File_2");
		File decreptedKeyFile = new File("DecryptedFiles/SecretKey");
		new DecryptKey(startEnc.getPrivate("KeyPair/private_B", "RSA"), encryptedKeyReceived, decreptedKeyFile, "RSA");

		File encryptedFileReceived = new File("EncryptedFiles/File_1");
		File decryptedFile = new File("DecryptedFiles/decryptedFile");
		new DecryptData(encryptedFileReceived, decryptedFile, startEnc.getSecretKey("DecryptedFiles/SecretKey", "AES"), "AES");

		byte[] hashByte = Hash.getMD5ByteArray("DecryptedFiles/decryptedFile");
		File encryptedHashFile = new File("EncryptedHashedData/File_3");
		byte[] hashToCheck = InputOutputController.getFileInBytes(encryptedHashFile);

		byte[] b = new byte[20];
		new Random().nextBytes(b);

		boolean decryptedHash = Sign.verifySig(hashByte, startEnc.getPublic("KeyPair/public_A", "RSA"), hashToCheck ) ;
		//InputOutputController.writeToFile(encryptedHashFile,encryptedHash);

		System.out.println(decryptedHash + " TEST ");
	}
}

