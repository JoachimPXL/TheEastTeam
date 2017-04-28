package JavaBeans;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by 11500555 on 28/04/2017.
 */
public class Message implements Serializable {
    private byte[] fileInBytes;
    private String message;
    private int senderId;
    private int receiverId;


    public Message(byte[] fileInBytes, String message, int senderId, int receiver) {
        this.fileInBytes = fileInBytes;
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiver;
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
