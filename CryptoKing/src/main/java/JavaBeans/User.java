package JavaBeans;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by 11500555 on 26/04/2017.
 */
public class User implements Serializable{
    private int id;
    private String username;
    private String password;
    private String email;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public User() {
    }

    public User(String username, String password, String email, PublicKey publicKey, PrivateKey privateKey) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
