package de.belmega.biohazard.selenium.pages.auth;

import de.belmega.biohazard.selenium.TestConfiguration;
import de.belmega.biohazard.selenium.TestUser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class LoginPage {


    public void login(WebDriver driver, TestUser user) {
        driver.get(TestConfiguration.baseUrl());

        WebElement inputEmailaddress = driver.findElement(By.name("form_login:emailaddress"));
        inputEmailaddress.clear();
        inputEmailaddress.sendKeys(user.getUsername());

        WebElement inputPassword = driver.findElement(By.name("form_login:password"));
        inputPassword.clear();
        inputPassword.sendKeys(user.getPassword());

        driver.findElement(By.id("form_login:btnLogin")).click();

        WebDriverWait webDriver1Wait = new WebDriverWait(driver, 5);
        webDriver1Wait.until(elementToBeClickable(By.partialLinkText("Logout")));
    }


    public void logout(WebDriver driver) {
        driver.findElement(By.partialLinkText("Logout")).click();

        WebDriverWait webDriver1Wait = new WebDriverWait(driver, 5);
        webDriver1Wait.until(stalenessOf(driver.findElement(By.partialLinkText("Logout"))));
    }

}
