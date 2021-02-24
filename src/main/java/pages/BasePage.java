package pages;

import static pages.LoginConstants.regionDomain;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import config.Config;
import pages.LoginConstants.REGION;

public abstract class BasePage {

	protected Config config;
	protected WebDriver driver;

	public BasePage(Config config) {
		this.config = config;
		this.driver = config.getWebDriver();
		PageFactory.initElements(driver, this);
	}
	
	public void navigateTo(URL url) {
		driver.navigate().to(url);
	}

	public void navigateTo(String url) {
		driver.navigate().to(url);
	}
	
	public void navigateTo(REGION region, String path) {
		String url = "https://".concat(regionDomain(region)).concat(path);
		navigateTo(url);
	}
	
	public abstract void navigate();

}
