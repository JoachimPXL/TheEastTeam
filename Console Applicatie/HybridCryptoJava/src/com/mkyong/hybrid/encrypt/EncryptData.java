package com.mkyong.hybrid.encrypt;

import com.mkyong.writers.InputOutputController;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

/**
 * Created by 11500555 on 23/03/2017.
 */
public class EncryptData {
	private Cipher cipher;

	public EncryptData(File originalFile, File encrypted, SecretKeySpec secretKey, String cipherAlgorithm) throws IOException, GeneralSecurityException {
		this.cipher = Cipher.getInstance(cipherAlgorithm);
		encryptFile(InputOutputController.getFileInBytes(originalFile), encrypted, secretKey);
	}

	public EncryptData(File originalFile, File encrypted, PrivateKey privateKey, String cipherAlgorithm) throws IOException, GeneralSecurityException {
		this.cipher = Cipher.getInstance(cipherAlgorithm);
		encryptFile(InputOutputController.getFileInBytes(originalFile), encrypted, privateKey);
	}

	public void encryptFile(byte[] input, File output, SecretKeySpec key) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.ENCRYPT_MODE, key);
		InputOutputController.writeToFile(output, this.cipher.doFinal(input));
	}

	public void encryptFile(byte[] input, File output, PrivateKey key) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.ENCRYPT_MODE, key);
		InputOutputController.writeToFile(output, this.cipher.doFinal(input));
	}

	/*
	public static byte[] hashStringWithSalt(final char[] stringToHash, final byte[] salt, final int iterations, final int keyLength) {
		try {
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("test");
			PBEKeySpec spec = new PBEKeySpec(stringToHash, salt, iterations, keyLength);
			SecretKey key = secretKeyFactory.generateSecret(spec);
			byte[] res = key.getEncoded();
			return res;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] hashStringWithoutSalt(String stringToHash) {
		byte[] result = null;
		try {
			byte[] bytesOfMessage = stringToHash.getBytes("UTF-8");

			MessageDigest md = MessageDigest.getInstance("MD5");
			result = md.digest(bytesOfMessage);
		} catch (Exception e) {

		}
		return result;
	}
	*/
}

