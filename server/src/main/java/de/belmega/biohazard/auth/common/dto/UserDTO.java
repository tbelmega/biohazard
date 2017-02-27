package de.belmega.biohazard.auth.common.dto;

import de.belmega.biohazard.auth.common.EncryptionUtil;
import de.belmega.biohazard.auth.persistence.entities.ApplicationRole;

import java.util.Set;

public class UserDTO {

    private String mailAddress;
    private String password;
    private Set<ApplicationRole> roles;

    public UserDTO(String mailAddress, String password) {
        this.mailAddress = mailAddress;
        this.password = password;
    }

    public UserDTO(String mailAddress, Set<ApplicationRole> roles) {
        this.mailAddress = mailAddress;
        this.roles = roles;
    }

    public UserDTO() {

    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getEncryptedPassword(byte[] salt) {
        return EncryptionUtil.hash(password.toCharArray(), salt);
    }

    public String getName() {
        return mailAddress.substring(0, getMailAddress().indexOf('@'));
    }

    public void addRole(ApplicationRole role) {
        this.roles.add(role);
    }

    public Set<ApplicationRole> getRoles() {
        return roles;
    }

}
