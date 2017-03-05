package de.belmega.biohazard.selenium;

public class TestUser {

    public static final TestUser ADMIN = new TestUser("kenn@ich.net", "1234");
    private String username;
    private String password;

    public TestUser(String username, String password) {
        this.username = username;
        this.password = password;
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
}
