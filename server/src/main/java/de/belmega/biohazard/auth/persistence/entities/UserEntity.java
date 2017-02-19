package de.belmega.biohazard.auth.persistence.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "mailAddress", nullable = false, unique = true)
    private String mailAddress;

    @NotNull
    private byte[] hashedPassword;

    @NotNull
    private byte[] salt;

    @ManyToMany
    @JoinTable(
            name = "User_ApplicationRole",
            joinColumns = {@JoinColumn(name = "roles")},
            inverseJoinColumns = {@JoinColumn(name = "users")}
    )
    private Set<ApplicationRoleEntity> roles;


    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String userName) {
        this.mailAddress = userName;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] password) {
        this.hashedPassword = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
