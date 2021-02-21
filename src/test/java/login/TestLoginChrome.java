package login;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.tinylog.Logger;

import config.Config;
import io.github.bonigarcia.wdm.config.Architecture;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import pages.LoginPage;

public class TestLoginChrome extends BaseTest {
	public static final String FIELD_REQUIRED = "Compila questo campo.";
	private Config config;
	private WebDriver driver;
	private LoginPage login;
	private WebDriverWait wait;
	private WebElement txtUsername;
	private WebElement txtPassword;
	private WebElement lnkForgotPassword;
	private WebElement btnLogin;
	private Predicate<WebElement> checkElement = (element) -> (Boolean) ((JavascriptExecutor) driver)
			.executeScript("return arguments[0].validity.valid;", element);

	public TestLoginChrome() {
		super();
		config = new Config(DriverManagerType.CHROME, Architecture.X64);
		driver = config.getWebDriver();
		login = new LoginPage(config);
		txtUsername = login.getUsername();
		txtPassword = login.getPassword();
		lnkForgotPassword = login.getForgotPassword();
		btnLogin = login.getLoginButton();

	}

	@Override
	@BeforeTest
	public void beforeTest() {
		login.navigate();
		WebElement logo = login.getLogo();
		waitUntilVisible(logo);
	}

	@Test
	public void testUsername() {
		String msg = "Aggiungi un simbolo \"@\" nell'indirizzo email. In \"{}\" manca un simbolo \"@\"";
		waitUntilVisible(btnLogin);
		login.clearFields();
		btnLogin.click();
		String validationMsg = txtUsername.getAttribute("validationMessage");
		Logger.info("Message: {}", validationMsg);
		assertThat(checkElement).as("Username should not be valid").accepts(txtUsername);
	}

	@Test
	public void testPassword() {
	}

}
