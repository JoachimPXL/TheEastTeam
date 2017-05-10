package Services;

import mkyong.hybrid.writers.InputOutputController;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

/**
 * Created by 11500555 on 23/03/2017.
 */
public class DecryptKey {
	private Cipher cipher;

	public DecryptKey(String algorithm) throws IOException, GeneralSecurityException {
		this.cipher = Cipher.getInstance(algorithm);
	}

	public byte[] decryptFile(byte[] input, PrivateKey key) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.DECRYPT_MODE, key);
		return this.cipher.doFinal(input);
	}
}
