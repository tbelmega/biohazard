package de.belmega.biohazard.auth;

import de.belmega.biohazard.selenium.TestConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static de.belmega.biohazard.selenium.TestConfiguration.config;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class LogIn_LogOut_Test {

    private WebDriver driver1;

    private static void login(WebDriver driver) {
        driver.get(TestConfiguration.baseUrl());

        driver.findElement(By.name("form_login:emailadress")).sendKeys("kenn@ich.net");
        driver.findElement(By.name("form_login:password")).sendKeys("1234");

        driver.findElement(By.id("form_login:btnLogin")).click();

        WebDriverWait webDriver1Wait = new WebDriverWait(driver, 5);
        webDriver1Wait.until(elementToBeClickable(By.partialLinkText("Logout")));
    }

    @BeforeTest
    public void openBrowser() {
        driver1 = config().createFirefoxDriver();
    }

    @Test
    public void testLogin() throws Exception {
        //arrange

        //act
        login(driver1);

        //assert
        assertThat(driver1.findElement(By.id("loggedInBar:lblGreeting")).getText(), containsString("Hello, kenn."));
    }

    @AfterTest
    public void closeBrowser() {
        driver1.quit();
    }

}
