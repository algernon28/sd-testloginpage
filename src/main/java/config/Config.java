package config;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.tinylog.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.Architecture;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public class Config {
	private WebDriver driver;
	private WebDriverManager wdm;

	public Config(DriverManagerType type, String version, Architecture arch) throws InvalidArgumentException{
		if(type == null) {
			throw new InvalidArgumentException("No Browser type defined");
		}
		wdm = WebDriverManager.getInstance(type);
		Optional.ofNullable(version).ifPresent(wdm::driverVersion);
		Optional.ofNullable(arch).ifPresent(wdm::architecture);
		wdm.setup();
		try {
			driver = (WebDriver) Class.forName(type.browserClass()).getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			Logger.error(e);
		}

	}

	public Config(DriverManagerType type) {
		this(type, null, null);
	}

	public Config(DriverManagerType type, String version) {
		this(type, version, null);
	}

	public Config(DriverManagerType type, Architecture arch) {
		this(type, null, arch);
	}

	public final WebDriverManager getWebDriverManager() {
		return wdm;
	}

	public final WebDriver getWebDriver() {
		return driver;
	}
}
