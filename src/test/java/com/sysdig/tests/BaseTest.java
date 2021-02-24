package com.sysdig.tests;

import java.time.Duration;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterTest;

import config.Config;
import io.github.bonigarcia.wdm.config.Architecture;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public abstract class BaseTest {
	protected Config config;
	protected WebDriver driver;
	protected Wait<WebDriver> wait;
	protected Locale locale;
	protected ResourceBundle bundle;
	protected Predicate<WebElement> checkElement = (element) -> (Boolean) ((JavascriptExecutor) driver)
			.executeScript("return arguments[0].validity.valid;", element);

	protected BaseTest(Optional<String> lang, Optional<String> country, String browser) {
		loadBundle(browser, lang, country);
		String browserLanguage = getBrowserLanguage(lang, country, browser);
		config = new Config(DriverManagerType.valueOf(browser.toUpperCase()), Architecture.X64, browserLanguage);
		driver = config.getWebDriver();
		wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(5))
				.ignoring(NoSuchElementException.class);
	}

	private void loadBundle(String baseName, Optional<String> lang, Optional<String> country) {
		Locale.setDefault(new Locale("en"));
		locale = new Locale.Builder().setLanguage(lang.orElse("en")).setRegion(country.orElse("")).build();
		bundle = ResourceBundle.getBundle("messages", locale);
	}
	
	private String getBrowserLanguage(Optional<String> lang, Optional<String> country, String browser) {
		StringBuilder sb = new StringBuilder();
		sb.append(lang.orElse("en").toLowerCase());
		if (country.isPresent()) {
			sb.append("-").append(country.get().toUpperCase());
		}
		return sb.toString();
	}


	@AfterTest
	public void close() {
		if (driver != null) {
			driver.close();
		}
	}
	
	public WebElement waitUntilVisible(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public Wait<WebDriver> webDriverWait() {
		return wait;
	}

}
