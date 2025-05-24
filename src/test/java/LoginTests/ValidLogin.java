package LoginTests;

import org.testng.annotations.Test;
import org.testng.Assert;
import PageObjects.Login;
import BaseConfTests.BaseTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidLogin extends BaseTest {
    Login loginPage;
    WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(ValidLogin.class);

    @Test
    public void validLogin() {
        logger.info("Navigating to https://www.saucedemo.com/");
        driver.get("https://www.saucedemo.com/");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger.info("Waiting for page title to be 'Swag Labs'");
        wait.until(ExpectedConditions.titleIs("Swag Labs"));

        loginPage = new Login(driver);
        loginPage.waitForPageLogo();

        logger.info("Entering username and password");
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("secret_sauce");

        logger.info("Clicking login button");
        loginPage.clickLogin();

        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        logger.info("Waiting for URL to be '{}'", expectedUrl);
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed or incorrect navigation.");
        logger.info("Login test passed - navigated to inventory page");
    }
}
