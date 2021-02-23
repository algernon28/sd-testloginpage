package com.sysdig.tests.login;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sysdig.tests.BaseTest;

import pages.LoginPage;
import pages.LoginPage.REGION;

public class TestRegions extends BaseTest {
	private static final String EU_URL = "eu1.app.sysdig.com";
	private static final String WS_EAST_URL = "app.sysdigcloud.com";
	private static final String WS_WEST_URL = "us2.app.sysdig.com";
	private LoginPage login;
	

	@Parameters({ "lang", "country", "browser" })
	public TestRegions(String lang, String country, String browser) {
		super(Optional.ofNullable(lang), Optional.ofNullable(country), browser);
		login = new LoginPage(config);
	}

	@BeforeTest
	public void beforeTest() {
		login.navigate();
		WebElement logo = login.getLogo();
		waitUntilVisible(logo);
	}

	@Test
	public void testRegionEU() throws MalformedURLException {
		WebElement el = login.selectRegion(REGION.EU_CENTRAL);
		Reporter.log("SELECTOR: " + el.getText(), true);
		el.click();
		waitUntilVisible(login.getLogo());
		URL newURL = new URL(driver.getCurrentUrl());
		Reporter.log("URL: " + driver.getCurrentUrl(), true);
		assertThat(newURL.getHost()).isEqualTo(EU_URL);
	}

	@Test
	public void testRegionWS_EAST() throws MalformedURLException {
		WebElement el = login.selectRegion(REGION.WS_EAST);
		Reporter.log("SELECTOR: " + el.getText(), true);
		el.click();
		waitUntilVisible(login.getLogo());
		URL newURL = new URL(driver.getCurrentUrl());
		Reporter.log("URL: " + driver.getCurrentUrl(), true);
		assertThat(newURL.getHost()).isEqualTo(WS_EAST_URL);
	}

	@Test
	public void testRegionWS_WEST() throws MalformedURLException {
		WebElement el = login.selectRegion(REGION.WS_WEST);
		Reporter.log("SELECTOR: " + el.getText(), true);
		el.click();
		waitUntilVisible(login.getLogo());
		URL newURL = new URL(driver.getCurrentUrl());
		Reporter.log("URL: " + driver.getCurrentUrl(), true);
		assertThat(newURL.getHost()).isEqualTo(WS_WEST_URL);
	}
}
