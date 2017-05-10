package Services;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by 11500555 on 23/03/2017.
 */
public class DecryptData {
	private Cipher cipher;

	public DecryptData(String algorithm) throws IOException, GeneralSecurityException {
		this.cipher = Cipher.getInstance(algorithm);
	}
	
	public byte [] decryptFile(byte[] input, SecretKeySpec key) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.DECRYPT_MODE, key);
		return this.cipher.doFinal(input);
    }
}
