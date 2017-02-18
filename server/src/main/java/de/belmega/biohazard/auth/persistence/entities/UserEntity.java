package de.belmega.biohazard.auth.persistence.entities;

import javax.persistence.*;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "mailAddress", nullable = false, unique = true)
    private String mailAddress;

    private String password;
    private String salt;


    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String userName) {
        this.mailAddress = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
