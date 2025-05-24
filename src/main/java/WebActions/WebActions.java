package WebActions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class WebActions {
	protected static WebDriver driver;
	protected JavascriptExecutor jsExecutor;
	protected Actions actions;
	private By dropDownOption = By.xpath("(//*[@role='option'])[1]");
	public WebActions(WebDriver driver) {
		WebActions.driver = driver;
		this.jsExecutor = (JavascriptExecutor) driver;
		this.actions = new Actions(driver);
	}

	public void fieldInput(WebElement field, String fieldValue) {
		actions.moveToElement(field).click().perform();
		jsExecutor.executeScript("arguments[0].value='" + fieldValue + "';", field);
		jsExecutor.executeScript("arguments[0].dispatchEvent(new Event('input'));", field);
		jsExecutor.executeScript("arguments[0].dispatchEvent(new Event('change'));", field);
	}

	public void dropDownFieldInput(String elementName, String searchKey) {
		WebElement dropDownField = driver.findElement(By.name(elementName));
		WebElement inputField = dropDownField.findElement(By.xpath(".//input"));
		inputField.click();
		inputField.sendKeys(searchKey);
	}

	public void selectdropdown(By element, String key) {
		WebElement elementname = driver.findElement(element);
		WebElement elementinput = elementname.findElement(By.xpath(".//input"));
		elementinput.click();
		elementinput.sendKeys(key);
		driver.findElement(dropDownOption).click();
	}

	private void selectNgOptionByIndex(String ngSelectxPath, By dropDownPath) {

		WebElement ngSelectElement = driver.findElement(By.name(ngSelectxPath));
		ngSelectElement.click();
		driver.findElement(dropDownPath).click();
	}

	public void dropDownSearchSelect(String fieldName, String searchKey) {
		
		dropDownFieldInput(fieldName, searchKey);
		selectNgOptionByIndex(fieldName, dropDownOption);
	}

	public void setDate(By element, String date) {
		WebElement dateField = driver.findElement(element);
		jsExecutor.executeScript("arguments[0].value=arguments[1];", dateField, date);
		jsExecutor.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", dateField);
		jsExecutor.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", dateField);
	}

	public void waitForLoaderToDisappear() {
		try {
			driver.findElement(By.xpath("(//div[@class='loader-hidden'])[1]"));
		} catch (TimeoutException e) {
			System.out.println("Request Time Out");
		}
	}
}