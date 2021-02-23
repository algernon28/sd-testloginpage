package login;

import static login.LoginConstants.FIELD_REQUIRED;
import static login.LoginConstants.MISSING_AT_SIGN;
import static login.LoginConstants.MISSING_DOMAIN;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.function.Predicate;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.LoginPage;

public class TestCredentials extends BaseTest {

	private LoginPage login;
	private WebElement txtUsername;
	private WebElement txtPassword;
	private WebElement lnkForgotPassword;
	private WebElement btnLogin;
	private String expectedMsg;
	private Predicate<WebElement> checkElement = (element) -> (Boolean) ((JavascriptExecutor) driver)
			.executeScript("return arguments[0].validity.valid;", element);

	@Parameters({ "lang", "country", "browser" })
	public TestCredentials(String lang, String country, String browser) {
		super(Optional.ofNullable(lang), Optional.ofNullable(country), browser);
		login = new LoginPage(config);
		txtUsername = login.getUsername();
		txtPassword = login.getPassword();
		lnkForgotPassword = login.getForgotPassword();
		btnLogin = login.getLoginButton();
	}

	@BeforeTest
	public void beforeTest() {
		login.navigate();
		WebElement logo = login.getLogo();
		waitUntilVisible(logo);
		waitUntilVisible(btnLogin);
	}

	@AfterTest
	public void close() {
		if (driver != null) {
			driver.close();
		}
	}
	
	@BeforeMethod
	public void beforeMethod() {
		login.clearFields();
	}

	@Test
	public void testCorrectUsername() {
		expectedMsg = "";
		String username = "mickey.mouse@waldisney.com";
		waitUntilVisible(txtUsername);
		txtUsername.sendKeys(username);
		//wait.until(ExpectedConditions.textToBePresentInElement(txtUsername, username));
		btnLogin.click();
		String validationMsg = txtUsername.getAttribute("validationMessage");
		Reporter.log("Message: " + validationMsg, true);
		assertThat(validationMsg).as("Wrong message").isEqualTo(expectedMsg);
		assertThat(checkElement.test(txtUsername)).as("Username should be").isTrue();
	}

	@Test
	public void testEmptyUsername() {
		expectedMsg = bundle.getString(FIELD_REQUIRED);
		waitUntilVisible(txtUsername);
		btnLogin.click();
		String validationMsg = txtUsername.getAttribute("validationMessage");
		Reporter.log("Message: " + validationMsg, true);
		assertThat(validationMsg).as("Wrong message").isEqualTo(expectedMsg);
		assertThat(checkElement.test(txtUsername)).as("Username should not be valid").isFalse();
	}

	@Test
	public void testUsernameWithout_At() {
		String username = "mickey.mouse";
		expectedMsg = String.format(bundle.getString(MISSING_AT_SIGN), username);
		waitUntilVisible(txtUsername);
		txtUsername.sendKeys(username);
		//wait.until(ExpectedConditions.textToBePresentInElement(txtUsername, username));
		btnLogin.click();
		String validationMsg = txtUsername.getAttribute("validationMessage");
		Reporter.log("Message: " + validationMsg, true);
		assertThat(validationMsg).as("Wrong message").isEqualTo(expectedMsg);
		assertThat(checkElement.test(txtUsername)).as("Username should not be valid").isFalse();
	}

	@Test
	public void testUsernameWithMissingDomain() {
		String username = "mickey.mouse@";
		expectedMsg = String.format(bundle.getString(MISSING_DOMAIN), username);
		waitUntilVisible(txtUsername);
		txtUsername.sendKeys(username);
		//wait.until(ExpectedConditions.textToBePresentInElement(txtUsername, username));
		btnLogin.click();
		String validationMsg = txtUsername.getAttribute("validationMessage");
		Reporter.log("Message: " + validationMsg, true);
		assertThat(validationMsg).as("Wrong message").isEqualTo(expectedMsg);
		assertThat(checkElement.test(txtUsername)).as("Username should not be valid").isFalse();
	}

	@Test
	public void testEmptyPassword() {
		expectedMsg = bundle.getString(FIELD_REQUIRED);
		waitUntilVisible(txtPassword);
		btnLogin.click();
		String validationMsg = txtPassword.getAttribute("validationMessage");
		Reporter.log("Message: " + validationMsg, true);
		assertThat(validationMsg).as("Wrong message").isEqualTo(expectedMsg);
		assertThat(checkElement.test(txtPassword)).as("Username should not be valid").isFalse();
	}
	
	@Test
	public void testWrongLogin() {
		String username = "mickey.mouse@waldisney.com";
		String pwd = "justAPassword";
		txtUsername.sendKeys(username);
		txtPassword.sendKeys(pwd);
		btnLogin.click();
		WebElement error = login.getLoginErrorDisplay();
		waitUntilVisible(error);
		String validationMsg = error.getText();
		Reporter.log("Message: " + validationMsg, true);
		assertThat(error.isDisplayed()).as("Error message should be displayed").isTrue();
	}
}
