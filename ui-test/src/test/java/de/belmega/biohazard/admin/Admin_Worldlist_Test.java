package de.belmega.biohazard.admin;

import de.belmega.biohazard.selenium.TestUser;
import de.belmega.biohazard.selenium.pages.player.WorldlistPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static de.belmega.biohazard.selenium.TestConfiguration.config;

public class Admin_Worldlist_Test {

    private WebDriver driver1;
    private WorldlistPage worldlistPage = new WorldlistPage();

    @BeforeClass
    public void createDriver() {
        driver1 = config().createFirefoxDriver();
    }

    @BeforeTest
    public void openWorldlistPage() {
        worldlistPage.openAs(TestUser.ADMIN);
    }

    @Test
    public void testThat() throws Exception {
        //arrange


        //act


        //assert
    }

    @AfterTest
    public void closeBrowser() {
        driver1.quit();
    }
}
