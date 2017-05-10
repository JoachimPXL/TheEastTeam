package Services;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * Created by 11500555 on 28/03/2017.
 */
//Bouncy Castle nakijken.
public class Sign {

    public static byte[] signData(byte[] data, PrivateKey key) throws Exception {
        Signature signer = Signature.getInstance("SHA1withRSA");
        signer.initSign(key);
        signer.update(data);
        return (signer.sign());
    }

    public static boolean verifySig(byte[] data, PublicKey key, byte[] sig) throws Exception {
        Signature signer = Signature.getInstance("SHA1withRSA");
        signer.initVerify(key);
        signer.update(data);
        return (signer.verify(sig));
    }
}
