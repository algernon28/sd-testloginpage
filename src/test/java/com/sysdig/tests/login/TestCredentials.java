package com.sysdig.tests.login;

import static org.assertj.core.api.Assertions.assertThat;
import static pages.LoginConstants.FIELD_REQUIRED;
import static pages.LoginConstants.MISSING_AT_SIGN;
import static pages.LoginConstants.MISSING_DOMAIN;

import java.util.Optional;

import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
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
		loginPage = new LoginPage(config);
		txtUsername = loginPage.getUsername();
		txtPassword = loginPage.getPassword();
		btnLogin = loginPage.getLoginButton();
	}

	@BeforeTest
	public void beforeTest() {
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
		expectedMsg = "";
		String username = "mickey.mouse@waldisney.com";
		waitUntilVisible(txtUsername);
		txtUsername.sendKeys(username);
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
		WebElement error = loginPage.getLoginErrorDisplay();
		waitUntilVisible(error);
		String validationMsg = error.getText();
		Reporter.log("Message: " + validationMsg, true);
		assertThat(error.isDisplayed()).as("Error message should be displayed").isTrue();
	}
	
}
