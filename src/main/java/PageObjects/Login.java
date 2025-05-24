package PageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WebActions.WebActions;

public class Login extends WebActions {
	public Login(WebDriver driver) {
		super(driver);
	}

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

//	Login Form Elements & Form Control
	private By pageLogo = By.xpath("//div[text()='Swag Labs']");
	private By userNameField = By.name("user-name");
	private By passwordField = By.name("password");
	private By loginBtn = By.id("login-button");

	public void waitForPageLogo() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(pageLogo));

	}

	public void enterUserName(String userName) {
		driver.findElement(userNameField).sendKeys(userName);
	}

	public void enterPassword(String password) {
		driver.findElement(passwordField).sendKeys(password);

	}

	public void clickLogin() {
		driver.findElement(loginBtn).click();

	}

}
