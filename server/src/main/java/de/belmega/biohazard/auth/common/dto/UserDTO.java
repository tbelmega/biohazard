package de.belmega.biohazard.auth.common.dto;

import de.belmega.biohazard.auth.common.EncryptionUtil;

public class UserDTO {

    private String mailAddress;
    private String password;

    public UserDTO(String mailAddress, String password) {
        this.mailAddress = mailAddress;
        this.password = password;
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
}
