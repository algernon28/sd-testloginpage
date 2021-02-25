package com.sysdig.tests.login;

import static com.sysdig.tests.pages.LoginConstants.regionDomain;
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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sysdig.tests.BaseTest;
import com.sysdig.tests.pages.LoginPage;

public class TestRegions extends BaseTest {
	private LoginPage loginPage;
	

	@Parameters({ "lang", "country", "browser" })
	public TestRegions(String lang, String country, String browser) {
		super(Optional.ofNullable(lang), Optional.ofNullable(country), browser);
	}

	
	@BeforeClass
	public void beforeClass() {
		loginPage = new LoginPage(config);
		loginPage.navigate();
		WebElement logo = loginPage.getLogo();
		waitUntilVisible(logo);
	}

	@Test
	public void testRegionEU() throws MalformedURLException {
		WebElement el = loginPage.selectRegion(EU);
		Reporter.log("SELECTOR: " + el.getText(), true);
		el.click();
		waitUntilVisible(loginPage.getLogo());
		URL newURL = new URL(driver.getCurrentUrl());
		Reporter.log("URL: " + driver.getCurrentUrl(), true);
		assertThat(newURL.getHost()).isEqualTo(regionDomain(EU));
	}

	@Test
	public void testRegionWS_EAST() throws MalformedURLException {
		WebElement el = loginPage.selectRegion(WS_EAST);
		Reporter.log("SELECTOR: " + el.getText(), true);
		el.click();
		waitUntilVisible(loginPage.getLogo());
		URL newURL = new URL(driver.getCurrentUrl());
		Reporter.log("URL: " + driver.getCurrentUrl(), true);
		assertThat(newURL.getHost()).isEqualTo(regionDomain(WS_EAST));
	}

	@Test
	public void testRegionWS_WEST() throws MalformedURLException {
		WebElement el = loginPage.selectRegion(WS_WEST);
		Reporter.log("SELECTOR: " + el.getText(), true);
		el.click();
		waitUntilVisible(loginPage.getLogo());
		URL newURL = new URL(driver.getCurrentUrl());
		Reporter.log("URL: " + driver.getCurrentUrl(), true);
		assertThat(newURL.getHost()).isEqualTo(regionDomain(WS_WEST));
	}
}
