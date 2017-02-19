package de.belmega.biohazard.auth.common.dto;

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

    public String getEncryptedPassword() {
        return password;
    }
}
