package JavaBeans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 11500555 on 30/04/2017.
 */
public class Chat implements Serializable{
    public String receiver;
    public String sender;
    public ArrayList<Message> received;
    public ArrayList<Message> send;

    public Chat(String receiver, String sender) {
        this.receiver = receiver;
        this.sender = sender;
        this.received = new ArrayList<Message>();
        this.send  = new ArrayList<Message>();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public ArrayList<Message> getReceivedList() {
        return received;
    }

    public void setReceived(ArrayList<Message> received) {
        this.received = received;
    }

    public ArrayList<Message> getSendList() {
        return send;
    }

    public void setSend(ArrayList<Message> send) {
        this.send = send;
    }

    public void addSendMessage(Message m) {
        this.send.add(m);
    }

    public void addReceivedMessage(Message m) {
        this.received.add(m);
    }
}
