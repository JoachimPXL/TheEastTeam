package mkyong.hybrid.encrypt;

import mkyong.hybrid.writers.InputOutputController;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;

/**
 * Created by 11500555 on 23/03/2017.
 */

public class EncryptKey {
	private Cipher cipher;
	
	public EncryptKey(PublicKey key, File originalKeyFile, File encryptedKeyFile, String cipherAlgorithm) throws IOException, GeneralSecurityException{
		this.cipher = Cipher.getInstance(cipherAlgorithm);
		
		encryptFile(InputOutputController.getFileInBytes(originalKeyFile), encryptedKeyFile, key);
	}
	
	public void encryptFile(byte[] input, File output, PublicKey key) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.ENCRYPT_MODE, key);
		writeToFile(output, this.cipher.doFinal(input));
    }
	
	private void writeToFile(File output, byte[] toWrite) throws IllegalBlockSizeException, BadPaddingException, IOException {
		output.getParentFile().mkdirs();
		FileOutputStream fileOutputStream = new FileOutputStream(output);
		fileOutputStream.write(toWrite);
		fileOutputStream.flush();
		fileOutputStream.close();
		System.out.println(output.getPath());
	}
}
