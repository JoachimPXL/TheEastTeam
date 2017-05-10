package JavaBeans;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by 11500555 on 28/04/2017.
 */
public class Message implements Serializable {
    private int messageId;
    private byte[] fileInBytes;
    private String message;
    private int senderId;
    private int receiverId;
    private String senderName;
    private String receiverName;
    private SecretKeySpec secretKeySpec;


    public Message(byte[] fileInBytes, String message, int senderId, int receiver, String senderName, String receiverName, int messageId, byte[] secretKey)
            throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException {
        this.fileInBytes = fileInBytes;
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiver;
        this.senderName = senderName;
        this.receiverName=receiverName;
        this.messageId = messageId;
        this.secretKeySpec = new SecretKeySpec(secretKey, "AES"); //source: http://stackoverflow.com/questions/14204437/convert-byte-array-to-secret-key
    }
    public Message(byte[] fileInBytes, String message, int senderId, int receiver, int messageId, byte[] secretKey)
            throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException {
        this.fileInBytes = fileInBytes;
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiver;
        this.messageId = messageId;
        this.secretKeySpec = new SecretKeySpec(secretKey, "AES"); //source: http://stackoverflow.com/questions/14204437/convert-byte-array-to-secret-key
    }

    public SecretKeySpec getSecretKeySpec() {
        return secretKeySpec;
    }

    public void setSecretKeySpec(SecretKeySpec secretKeySpec) {
        this.secretKeySpec = secretKeySpec;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        messageId = messageId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public byte[] getFileInBytes() {
        return fileInBytes;
    }

    public void setFileInBytes(byte[] fileInBytes) {
        this.fileInBytes = fileInBytes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiver() {
        return receiverId;
    }

    public void setReceiver(int receiver) {
        this.receiverId = receiver;
    }

    @Override
    public String toString() {
        return "Message{" +
                "fileInBytes=" + Arrays.toString(fileInBytes) +
                ", message='" + message + '\'' +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                '}';
    }


}
