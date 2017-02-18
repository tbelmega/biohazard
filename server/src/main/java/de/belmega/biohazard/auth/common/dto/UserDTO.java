package de.belmega.biohazard.auth.common.dto;

/**
 * @author tbelmega on 18.02.2017.
 */
public class UserDTO {

    private String mailAddress;
    private String password;

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return password;
    }
}
