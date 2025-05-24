package BaseConfTests;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {
    public static String browser = "Chrome"; // Change this to "Firefox" or "Edge" as needed
    public static WebDriver driver;

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeClass
    public void setUpEnv() {
        logger.info("Starting setup for browser: {}", browser);

        try {
            switch (browser.toLowerCase()) {
                case "chrome": {
                	Map<String, Object> prefs = new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.default_content_setting_values.notifications", 2);

                    ChromeOptions options = new ChromeOptions();
                    options.setExperimentalOption("prefs", prefs);
                    options.addArguments("--disable-password-manager-reauthentication");
                    options.addArguments("--disable-save-password-bubble");
                    options.addArguments("--incognito");  // Optional: run browser in incognito mode

                    driver = new ChromeDriver(options);
                    driver.manage().window().maximize();
                    logger.info("Chrome browser started successfully");
                    break;
                }
                case "firefox":
                    driver = new FirefoxDriver();
                    driver.manage().window().maximize();
                    logger.info("Firefox browser started successfully");
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    driver.manage().window().maximize();
                    logger.info("Edge browser started successfully");
                    break;
                default:
                    logger.error("Invalid browser input: {}", browser);
                    return;
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            logger.info("Implicit wait set to 20 seconds");
        } catch (Exception e) {
            logger.error("Could not start the browser session: {}", e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing the browser");
            driver.quit();
            driver = null;
            logger.info("Browser closed successfully");
        } else {
            logger.warn("Driver is null. Nothing to close.");
        }
    }
}