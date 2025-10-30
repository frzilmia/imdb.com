package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.chrome.ChromeOptions;
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
        Configuration.timeout = 15000; // Increased timeout for CI
        Configuration.pageLoadTimeout = 45000; // Increased page load timeout
        Configuration.pollingInterval = 300; // Faster polling
        
        // Set Chrome options for CI using proper W3C capabilities
        if (isCI) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--disable-extensions",
                "--disable-plugins",
                "--disable-images",
                "--disable-background-timer-throttling",
                "--disable-renderer-backgrounding",
                "--disable-backgrounding-occluded-windows",
                "--disable-web-security",
                "--allow-running-insecure-content",
                "--disable-features=VizDisplayCompositor",
                "--remote-debugging-port=9222",
                "--disable-logging",
                "--log-level=3",
                "--silent"
            );
            Configuration.browserCapabilities = chromeOptions;
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
