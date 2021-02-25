package com.sysdig.tests.login;

import static org.assertj.core.api.Assertions.assertThat;
import static pages.LoginConstants.FIELD_REQUIRED;
import static pages.LoginConstants.MISSING_AT_SIGN;
import static pages.LoginConstants.MISSING_DOMAIN;

import java.util.Optional;

import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sysdig.tests.BaseTest;

import pages.LoginPage;

public class TestCredentials extends BaseTest {

	private LoginPage loginPage;
	private WebElement txtUsername;
	private WebElement txtPassword;
	private WebElement btnLogin;
	private String expectedMsg;

	@Parameters({ "lang", "country", "browser" })
	public TestCredentials(String lang, String country, String browser) {
		super(Optional.ofNullable(lang), Optional.ofNullable(country), browser);

	}

	@BeforeClass
	public void beforeClass() {
		loginPage = new LoginPage(config);
		txtUsername = loginPage.getUsername();
		txtPassword = loginPage.getPassword();
		btnLogin = loginPage.getLoginButton();
		loginPage.navigate();
		WebElement logo = loginPage.getLogo();
		waitUntilVisible(logo);
		waitUntilVisible(btnLogin);
	}
	
	@BeforeMethod
	public void beforeMethod() {
		loginPage.clearFields();
	}

	@Test
	public void testCorrectUsername() {
		String username = "mickey.mouse@waldisney.com";
		waitUntilVisible(txtUsername);
		loginPage.typeUsername(username);
		loginPage.submit();
		Optional<String> validationMsg = Optional.ofNullable(txtUsername.getAttribute("validationMessage"));
		Reporter.log("Message: " + validationMsg.orElse("<NO VALIDATION MESSAGE>"), true);
		assertThat(validationMsg.orElse("")).as("Wrong message").isEmpty();
		assertThat(checkElement.test(txtUsername)).as("Username should be").isTrue();
	}

	@Test
	public void testEmptyUsername() {
		expectedMsg = bundle.getString(FIELD_REQUIRED);
		loginPage.submit();
		String validationMsg = txtUsername.getAttribute("validationMessage");
		Reporter.log("Message: " + validationMsg, true);
		assertThat(validationMsg).as("Wrong message").isEqualTo(expectedMsg);
		assertThat(checkElement.test(txtUsername)).as("Username should not be valid").isFalse();
	}

	@Test
	public void testUsernameWithout_At() {
		String username = "mickey.mouse";
		expectedMsg = String.format(bundle.getString(MISSING_AT_SIGN), username);
		loginPage.typeUsername(username);
		loginPage.submit();
		String validationMsg = txtUsername.getAttribute("validationMessage");
		Reporter.log("Message: " + validationMsg, true);
		assertThat(validationMsg).as("Wrong message").isEqualTo(expectedMsg);
		assertThat(checkElement.test(txtUsername)).as("Username should not be valid").isFalse();
	}

	@Test
	public void testUsernameWithMissingDomain() {
		String username = "mickey.mouse@";
		expectedMsg = String.format(bundle.getString(MISSING_DOMAIN), username);
		loginPage.typeUsername(username);
		loginPage.submit();
		String validationMsg = txtUsername.getAttribute("validationMessage");
		Reporter.log("Message: " + validationMsg, true);
		assertThat(validationMsg).as("Wrong message").isEqualTo(expectedMsg);
		assertThat(checkElement.test(txtUsername)).as("Username should not be valid").isFalse();
	}

	@Test
	public void testEmptyPassword() {
		expectedMsg = bundle.getString(FIELD_REQUIRED);
		loginPage.submit();
		String validationMsg = txtPassword.getAttribute("validationMessage");
		Reporter.log("Message: " + validationMsg, true);
		assertThat(validationMsg).as("Wrong message").isEqualTo(expectedMsg);
		assertThat(checkElement.test(txtPassword)).as("Username should not be valid").isFalse();
	}
	
	@Test
	public void testWrongLogin() {
		String username = "mickey.mouse@waldisney.com";
		String pwd = "justAPassword";
		loginPage.typeUsername(username);
		loginPage.typePassword(pwd);
		loginPage.submit();
		WebElement error = loginPage.getLoginErrorDisplay();
		waitUntilVisible(error);
		String validationMsg = error.getText();
		Reporter.log("Message: " + validationMsg, true);
		assertThat(error.isDisplayed()).as("Error message should be displayed").isTrue();
	}
	
}
