package mkyong.hybrid.encrypt;

import mkyong.hybrid.writers.InputOutputController;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by 11500555 on 23/03/2017.
 */
public class EncryptData {
	private Cipher cipher;

	public EncryptData(File originalFile, File encrypted, SecretKeySpec secretKey, String cipherAlgorithm) throws IOException, GeneralSecurityException {
		this.cipher = Cipher.getInstance(cipherAlgorithm);
		encryptFile(InputOutputController.getFileInBytes(originalFile), encrypted, secretKey);
	}

	public void encryptFile(byte[] input, File output, SecretKeySpec key) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.ENCRYPT_MODE, key);
		InputOutputController.writeToFile(output, this.cipher.doFinal(input));
	}

}

