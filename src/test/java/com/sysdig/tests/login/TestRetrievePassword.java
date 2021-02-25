package com.sysdig.tests.login;

import static org.assertj.core.api.Assertions.assertThat;
import static pages.LoginConstants.DEFAULT_REGION;
import static pages.LoginConstants.regionDomain;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sysdig.tests.BaseTest;

import pages.LoginConstants.REGION;
import pages.LoginPage;
import pages.RetrievePasswordPage;

public class TestRetrievePassword extends BaseTest {
	private LoginPage loginPage;
	private RetrievePasswordPage retrievePasswordPage;
	private WebElement btnSubmit;
	private WebElement lnkBackToLogin;
	private WebElement lnkForgotPassword;

	@Parameters({ "lang", "country", "browser" })
	public TestRetrievePassword(String lang, String country, String browser) {
		super(Optional.ofNullable(lang), Optional.ofNullable(country), browser);
	}

	@BeforeClass
	public void beforeClass() {
		retrievePasswordPage = new RetrievePasswordPage(config);
		loginPage = new LoginPage(config);
		btnSubmit = retrievePasswordPage.getBtnSubmit();
		lnkBackToLogin = retrievePasswordPage.getLnkBackToLogin();
		lnkForgotPassword = loginPage.getForgotPassword();

	}

	@Test
	public void testForgotLink() throws MalformedURLException {
		loginPage.navigate();
		waitUntilVisible(lnkForgotPassword);
		lnkForgotPassword.click();
		waitUntilVisible(btnSubmit);
		URL url = new URL(driver.getCurrentUrl());
		Reporter.log("URL: " + url, true);
		assertThat(url.getHost()).isEqualTo(regionDomain(DEFAULT_REGION));
		assertThat(url.toString()).endsWith(RetrievePasswordPage.PATH);
	}

	@Test
	public void testRetrievePassword() {
		retrievePasswordPage.navigate();
		retrievePasswordPage.typeEmail("mickey.mouse@disney.com");
		btnSubmit.click();
		WebElement message = retrievePasswordPage.getLblFeedbackMessage();
		Reporter.log("MESSAGE: " + Optional.ofNullable(message).get().getText(), true);
		assertThat(message).as("Missing Feedback Message").matches(msg -> msg.isDisplayed());
	}

	private URL backToLogin(REGION region) throws MalformedURLException {
		retrievePasswordPage.navigate(region);
		lnkBackToLogin.click();
		URL url = new URL(driver.getCurrentUrl());
		return url;
	}

	@Test
	public void testBackToLoginLinkEU() throws MalformedURLException {
		REGION region = REGION.EU;
		URL url = backToLogin(region);
		Reporter.log("Clicked back to Login: " + url, true);
		assertThat(url).hasHost(regionDomain(region));
	}

	@Test
	public void testBackToLoginLinkWS_WEST() throws MalformedURLException {
		REGION region = REGION.WS_WEST;
		URL url = backToLogin(region);
		Reporter.log("Clicked back to Login: " + url, true);
		assertThat(url).hasHost(regionDomain(region));
	}

	@Test
	public void testBackToLoginLinkWS_EAST() throws MalformedURLException {
		REGION region = REGION.WS_EAST;
		URL url = backToLogin(region);
		Reporter.log("Clicked back to Login: " + url, true);
		assertThat(url).hasHost(regionDomain(region));
	}

}
