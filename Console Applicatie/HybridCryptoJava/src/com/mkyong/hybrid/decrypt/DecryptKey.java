package com.mkyong.hybrid.decrypt;

import com.mkyong.writers.InputOutputController;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import javax.crypto.Cipher;

/**
 * Created by 11500555 on 23/03/2017.
 */
public class DecryptKey {
	private Cipher cipher;

	public DecryptKey(PrivateKey privateKey, File encryptedKeyReceived, File decreptedKeyFile, String algorithm) throws IOException, GeneralSecurityException {
		this.cipher = Cipher.getInstance(algorithm);
		decryptFile(InputOutputController.getFileInBytes(encryptedKeyReceived), decreptedKeyFile, privateKey);
	}
	
	public void decryptFile(byte[] input, File output, PrivateKey key) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.DECRYPT_MODE, key);
		InputOutputController.writeToFile(output, this.cipher.doFinal(input));
    }
}
