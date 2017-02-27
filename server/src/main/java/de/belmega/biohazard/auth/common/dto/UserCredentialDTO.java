package de.belmega.biohazard.auth.common.dto;

import de.belmega.biohazard.auth.common.EncryptionUtil;

/**
 * DTO for credentials entered by the user.
 */
public class UserCredentialDTO {

    private String mailAddress;
    private String password;

    public UserCredentialDTO(String emailaddress, String password) {
        this.mailAddress = emailaddress;
        this.password = password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getEncryptedPassword(byte[] salt) {
        return EncryptionUtil.hash(password.toCharArray(), salt);
    }
}
