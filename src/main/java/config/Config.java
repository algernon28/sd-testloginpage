package config;

import java.util.Optional;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.Architecture;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public class Config {
	private WebDriver driver;
	private WebDriverManager wdm;
	private static final String DEFAULT_LANG = "en";

	public Config(DriverManagerType type, String version, Architecture arch, String language)
			throws InvalidArgumentException {
		if (type == null) {
			throw new InvalidArgumentException("No Browser type defined");
		}
		wdm = WebDriverManager.getInstance(type);
		Optional.ofNullable(version).ifPresent(wdm::driverVersion);
		Optional.ofNullable(arch).ifPresent(wdm::architecture);
		wdm.setup();
		try {
			switch (type) {
			case CHROME:
				setConfigChrome(language);
				break;
			default:
				driver = (WebDriver) Class.forName(type.browserClass()).getConstructor().newInstance();
				wdm = WebDriverManager.getInstance(type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setConfigChrome(String language) throws Exception {
		ChromeOptions options = new ChromeOptions();
		String arg = String.format("--lang=%s", language);
		options.addArguments(arg);
		driver = new ChromeDriver(options);
	}

	public Config(DriverManagerType type, Architecture arch, String language) {
		this(type, null, arch, language);
	}

	public Config(DriverManagerType type) {
		this(type, null, null, DEFAULT_LANG);
	}

	public Config(DriverManagerType type, String version) {
		this(type, version, null, DEFAULT_LANG);
	}

	public Config(DriverManagerType type, Architecture arch) {
		this(type, null, arch, DEFAULT_LANG);
	}

	public final WebDriverManager getWebDriverManager() {
		return wdm;
	}

	public final WebDriver getWebDriver() {
		return driver;
	}
}
