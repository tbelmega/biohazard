package de.belmega.biohazard.auth;

import de.belmega.biohazard.selenium.TestUser;
import de.belmega.biohazard.selenium.pages.auth.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static de.belmega.biohazard.selenium.TestConfiguration.config;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogIn_LogOut_Test {

    private WebDriver driver1;
    private LoginPage loginPage = new LoginPage();


    @BeforeClass
    public void createDriver() {
        driver1 = config().createFirefoxDriver();
    }

    @Test
    public void testLogin() throws Exception {
        //arrange

        //act
        loginPage.login(driver1, TestUser.ADMIN);

        //assert
        assertThat(driver1.findElement(By.id("loggedInBar:lblGreeting")).getText(), containsString("Hello, kenn."));
    }

    @Test
    public void testLogout() throws Exception {
        //arrange
        loginPage.login(driver1, TestUser.ADMIN);

        //act
        loginPage.logout(driver1);

        //assert
        String navigation = driver1.findElement(By.id("navigation")).getText();
        assertThat(navigation, not(containsString("Logout")));
    }


    @AfterTest
    public void closeBrowser() {
        driver1.quit();
    }

}
