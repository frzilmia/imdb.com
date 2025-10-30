package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {

    @BeforeMethod
    public void setUp() {
        // Check if running in CI environment
        boolean isCI = System.getenv("CI") != null || System.getProperty("selenide.headless") != null;
        
        // Selenide configuration
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = isCI || Boolean.parseBoolean(System.getProperty("selenide.headless", "false"));
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 30000;
        
        // Additional Chrome options for CI
        if (isCI) {
            Configuration.browserCapabilities.setCapability("chrome.switches", new String[]{
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--disable-extensions",
                "--disable-plugins",
                "--disable-images",
                "--disable-javascript-harmony-shipping",
                "--disable-background-timer-throttling",
                "--disable-renderer-backgrounding",
                "--disable-backgrounding-occluded-windows"
            });
        }
        
        // Add Allure listener to Selenide
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    @AfterMethod
    public void tearDown() {
        closeWebDriver();
    }
}
