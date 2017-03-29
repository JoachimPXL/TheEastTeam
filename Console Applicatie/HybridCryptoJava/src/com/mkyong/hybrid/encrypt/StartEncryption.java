package com.mkyong.hybrid.encrypt;

import com.mkyong.onekey.GenerateSymmetricKey;
import com.mkyong.writers.Hash;
import com.mkyong.writers.InputOutputController;
import jdk.internal.util.xml.impl.Input;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by 11500555 on 23/03/2017.
 */
public class StartEncryption {
	// If private key needs to be pulled -> probably not needed.

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
	
	public static void main(String[] args) throws IOException, GeneralSecurityException, Exception{
		StartEncryption startEnc = new StartEncryption();
		
		File originalKeyFile = new File("OneKey/secretKey");
		File encryptedKeyFile = new File("EncryptedFiles/File_2");
		new EncryptKey(startEnc.getPublicKey("KeyPair/public_B", "RSA"), originalKeyFile, encryptedKeyFile, "RSA");

		File originalFile = new File("confidential.txt");
		File encryptedFile = new File("EncryptedFiles/File_1");
		new EncryptData(originalFile, encryptedFile, startEnc.getSecretKey("OneKey/secretKey", "AES"), "AES");

		//String hash = Hash.getMD5Checksum("confidential.txt");
		byte[] hashByte = Hash.getMD5ByteArray("confidential.txt");
		//File hashFile = new File("HashedData/hashOfOriginalMessage.txt");

		File encryptedHashFile = new File("EncryptedHashedData/File_3");
		byte[] encryptedHash = Sign.signData(hashByte,startEnc.getPrivateKey("KeyPair/private_A", "RSA"));
		InputOutputController.writeToFile(encryptedHashFile,encryptedHash);
	}
}
