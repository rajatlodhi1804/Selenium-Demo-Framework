package InventoryTests;

import org.testng.annotations.Test;
import org.testng.Assert;
import PageObjects.Login;
import PageObjects.Inventory;
import BaseConfTests.BaseTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import Utilities.JsonDataReader;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(InventoryTests.class);

    Login loginPage;
    Inventory inventoryPage;
    WebDriverWait wait;

    public void loginToApp() {
        logger.info("Starting login process...");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
        logger.info("Navigated to https://www.saucedemo.com/");
        
        loginPage = new Login(driver);

        JSONObject loginData = JsonDataReader.readJson("src/test/resources/testData.json");
        String username = (String) loginData.get("username");
        String password = (String) loginData.get("password");
        logger.info("Read credentials from JSON: username = {}", username);

        wait.until(ExpectedConditions.titleIs("Swag Labs"));
        loginPage.waitForPageLogo();
        logger.info("Page loaded successfully with title 'Swag Labs'");

        loginPage.enterUserName(username);
        logger.info("Entered username");

        loginPage.enterPassword(password);
        logger.info("Entered password");

        loginPage.clickLogin();
        logger.info("Clicked login button");

        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/inventory.html"));
        logger.info("Login successful, inventory page loaded");

        inventoryPage = new Inventory(driver);
    }

    @Test
    public void verifyInventoryItemsDisplayed() {
        logger.info("Test: verifyInventoryItemsDisplayed started");
        loginToApp();
        Assert.assertTrue(inventoryPage.getItemNames().size() > 0, "No inventory items found.");
        logger.info("Inventory items are displayed");
    }

    @Test
    public void verifyInventoryPricesDisplayed() {
        logger.info("Test: verifyInventoryPricesDisplayed started");
        loginToApp();
        Assert.assertTrue(inventoryPage.getItemPrices().size() > 0, "No prices displayed for inventory items.");
        logger.info("Inventory prices are displayed");
    }

    @Test
    public void verifyAddToCartIncreasesCartCount() {
        logger.info("Test: verifyAddToCartIncreasesCartCount started");
        loginToApp();
        inventoryPage.clickAddToCart(0);
        Assert.assertTrue(inventoryPage.isCartVisible(), "Cart badge not visible after adding item.");
        Assert.assertEquals(inventoryPage.getCartCount(), "1", "Cart count mismatch after adding one item.");
        logger.info("Add to cart increases cart count as expected");
    }

    @Test
    public void verifyAddMultipleItemsToCart() {
        logger.info("Test: verifyAddMultipleItemsToCart started");
        loginToApp();
        inventoryPage.clickAddToCart(0);
        inventoryPage.clickAddToCart(1);
        inventoryPage.clickAddToCart(2);
        Assert.assertEquals(inventoryPage.getCartCount(), "3", "Cart count should be 3 after adding 3 items.");
        logger.info("Adding multiple items to cart updates cart count correctly");
    }
}
