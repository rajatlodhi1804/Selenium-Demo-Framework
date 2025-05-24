package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;
import org.openqa.selenium.WebElement;

public class Inventory {
    WebDriver driver;

    By itemNames = By.className("inventory_item_name");
    By itemPrices = By.className("inventory_item_price");
    By addToCartButtons = By.xpath("//button[text()='Add to cart']");
    By cartBadge = By.className("shopping_cart_badge");

    public Inventory(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getItemNames() {
        return driver.findElements(itemNames);
    }

    public List<WebElement> getItemPrices() {
        return driver.findElements(itemPrices);
    }

    public List<WebElement> getAddToCartButtons() {
        return driver.findElements(addToCartButtons);
    }

    public void clickAddToCart(int index) {
        getAddToCartButtons().get(index).click();
    }

    public String getCartCount() {
        return driver.findElement(cartBadge).getText();
    }

    public boolean isCartVisible() {
        return driver.findElements(cartBadge).size() > 0;
    }
}
