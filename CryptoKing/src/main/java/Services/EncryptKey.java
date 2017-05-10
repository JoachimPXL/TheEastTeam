package Services;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;

/**
 * Created by 11500555 on 23/03/2017.
 */

public class EncryptKey {
	private Cipher cipher;
	
	public EncryptKey(String cipherAlgorithm) throws IOException, GeneralSecurityException{
		this.cipher = Cipher.getInstance(cipherAlgorithm);
		//encryptFile(toEncrypt, key);
	}
	
	public byte[] encryptFile(byte[] input, PublicKey key) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.ENCRYPT_MODE, key);
		return this.cipher.doFinal(input);
    }
}
