package mkyong.hybrid.writers;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Created by 11500555 on 26/03/2017.
 */
public class Hash {

    public static byte[] createChecksum(String filename) throws Exception {
            InputStream inputStream =  new FileInputStream(filename);

            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("MD5");
            int numRead;

            do {
                numRead = inputStream.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);

            inputStream.close();
            return complete.digest();
        }

    public static String getMD5Checksum(String filename) throws Exception {
            byte[] byteArray = createChecksum(filename);
            String result = "";

            for (int i=0; i < byteArray.length; i++) {
                result += Integer.toString( ( byteArray[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            return result;
        }

    public static byte[] getMD5ByteArray(String filename) throws Exception {
        return createChecksum(filename);
    }

    public static void main(String args[]) {
            try {
                System.out.println(getMD5Checksum("confidential.txt"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

