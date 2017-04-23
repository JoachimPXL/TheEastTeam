package mkyong.hybrid.writers;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;

/**
 * Created by 11500555 on 23/03/2017.
 */
public class InputOutputController {

    public static void writeToFile(File output, byte[] toWrite) throws IllegalBlockSizeException, BadPaddingException, IOException {
        output.getParentFile().mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(output);
        fileOutputStream.write(toWrite);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static byte[] getFileInBytes(File f) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(f);
        byte[] fbytes = new byte[(int) f.length()];
        fileInputStream.read(fbytes);
        fileInputStream.close();
        return fbytes;
    }

    public static void writeToFileWithoutParent(File output, byte[] toWrite) throws IllegalBlockSizeException, BadPaddingException, IOException {
        //output.getParentFile().mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(output);
        fileOutputStream.write(toWrite);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static String readFile(String filename) throws IOException {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader !=null){reader.close();}
        }
        return content;
    }
}
