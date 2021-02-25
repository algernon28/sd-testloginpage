package com.sysdig.tests.login;

import static com.sysdig.tests.pages.LoginConstants.AUTH.GOOGLE;
import static com.sysdig.tests.pages.LoginConstants.AUTH.OPENID;
import static com.sysdig.tests.pages.LoginConstants.AUTH.SAML;
import static com.sysdig.tests.pages.LoginConstants.REGION.EU;
import static com.sysdig.tests.pages.LoginConstants.REGION.WS_EAST;
import static com.sysdig.tests.pages.LoginConstants.REGION.WS_WEST;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sysdig.tests.BaseTest;
import com.sysdig.tests.pages.LoginPage;
public class TestLinks extends BaseTest {
	private LoginPage loginPage;
	
	

	@Parameters({ "lang", "country", "browser" })
	public TestLinks(String lang, String country, String browser) {
		super(Optional.ofNullable(lang), Optional.ofNullable(country), browser);
	}

	
	@BeforeClass
	public void beforeClass() {
		loginPage = new LoginPage(config);
	}
	
	@BeforeMethod
	public void beforeMethod() {
		loginPage.navigate();
		WebElement logo = loginPage.getLogo();
		waitUntilVisible(logo);

	}

	@Test
	public void testFreeTrialEU() throws MalformedURLException {
		WebElement el = loginPage.selectRegion(EU);
		Reporter.log("SELECTOR: " + el.getText(), true);
		el.click();
		URL newURL = loginPage.signupForFreeTrial();
		Reporter.log("URL: " + driver.getCurrentUrl(), true);
		assertThat(newURL.getHost()).isEqualTo(LoginPage.FREETRIAL_HOST);
		assertThat(newURL.getPath()).contains(LoginPage.PATH_FREETRIAL);
	}

	@Test
	public void testFreeTrialWS_EAST() throws MalformedURLException {
		WebElement el = loginPage.selectRegion(WS_EAST);
		Reporter.log("SELECTOR: " + el.getText(), true);
		el.click();
		URL newURL = loginPage.signupForFreeTrial();
		Reporter.log("URL: " + driver.getCurrentUrl(), true);
		assertThat(newURL.getHost()).isEqualTo(LoginPage.FREETRIAL_HOST);
		assertThat(newURL.getPath()).contains(LoginPage.PATH_FREETRIAL);
	}

	@Test
	public void testFreeTrialWS_WEST() throws MalformedURLException {
		WebElement el = loginPage.selectRegion(WS_WEST);
		Reporter.log("SELECTOR: " + el.getText(), true);
		el.click();
		URL newURL = loginPage.signupForFreeTrial();
		Reporter.log("URL: " + driver.getCurrentUrl(), true);
		assertThat(newURL.getHost()).isEqualTo(LoginPage.FREETRIAL_HOST);
		assertThat(newURL.getPath()).contains(LoginPage.PATH_FREETRIAL);
	}
	
	@Test
	public void testGoogleAuth() throws MalformedURLException {
		URL newURL = loginPage.authenticate(GOOGLE);
		assertThat(newURL.getPath()).contains(GOOGLE.path);
	}
	
	@Test
	public void testOpenIdAuth() throws MalformedURLException {
		URL newURL = loginPage.authenticate(OPENID);
		assertThat(newURL.toString()).contains(OPENID.path);
	}
	
	@Test
	public void testSAMLAuth() throws MalformedURLException {
		URL newURL = loginPage.authenticate(SAML);
		assertThat(newURL.toString()).contains(SAML.path);
	}
}
