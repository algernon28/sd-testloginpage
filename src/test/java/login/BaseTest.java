package login;

import java.time.Duration;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeSuite;

import config.Config;
import io.github.bonigarcia.wdm.config.Architecture;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public abstract class BaseTest {
	protected Config config;
	protected WebDriver driver;
	protected Wait<WebDriver> wait;
	protected Locale locale;
	protected ResourceBundle bundle;

	protected BaseTest(Optional<String> lang, Optional<String> country, String browser) {
		locale = new Locale.Builder().setLanguage(lang.orElse("en")).setRegion(country.orElse("")).build();
		if (lang.isEmpty()) {
			bundle = ResourceBundle.getBundle("messages");
		} else {
			bundle = ResourceBundle.getBundle("messages", locale);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(lang.orElse("en").toLowerCase());
		if (country.isPresent()) {
			sb.append("-").append(country.get().toUpperCase());
		}
		String browserLanguage = sb.toString();
		config = new Config(DriverManagerType.valueOf(browser.toUpperCase()), Architecture.X64, browserLanguage);
		driver = config.getWebDriver();
		wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(5))
				.ignoring(NoSuchElementException.class);

	}

	@BeforeSuite
	protected void startUp() {
	}


	public WebElement waitUntilVisible(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public Wait<WebDriver> webDriverWait() {
		return wait;
	}

}
