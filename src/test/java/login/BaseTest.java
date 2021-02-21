package login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import config.Config;

public abstract class BaseTest {
	protected Config config;
	protected WebDriver driver;
	protected WebDriverWait wait;

	protected BaseTest() {
	}

	@BeforeTest
	protected void beforeTest() {
		wait = new WebDriverWait(driver, 10);
	}
	
	@AfterTest
	protected void afterTest() {
		driver.close();
	}

	@AfterClass
	protected void shutDown() {
		driver.quit();
	}

	public WebElement waitUntilVisible(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

}
