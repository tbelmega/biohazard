package de.belmega.biohazard.selenium;

import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Singleton.
 * Contains the test configuration.
 */
public class TestConfiguration {

    private static TestConfiguration INSTANCE = null;
    private final String driverPath;
    private final String serverUrl;


    /**
     * Singleton: Private constructor to prevent instances.
     * Reads configuration from file.
     */
    private TestConfiguration() {
        Properties config = new Properties();
        try {
            InputStream resourceAsStream = TestConfiguration.class.getClassLoader().getResourceAsStream("config.xml");
            config.loadFromXML(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driverPath = (String) config.get("driver path");
        serverUrl = (String) config.get("application url");

        //make Gecko webdriver available
        System.setProperty("webdriver.gecko.driver", driverPath);
    }

    public static String baseUrl() {
        return config().serverUrl;
    }

    public static TestConfiguration config() {
        if (INSTANCE == null) {
            INSTANCE = new TestConfiguration();
        }
        return INSTANCE;
    }

    public FirefoxDriver createFirefoxDriver() {
        FirefoxDriver fox1 = new FirefoxDriver();

        fox1.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

        return fox1;
    }
}
