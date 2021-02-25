package pages;

import static pages.LoginConstants.DEFAULT_REGION;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import config.Config;
import pages.LoginConstants.AUTH;
import pages.LoginConstants.REGION;
public class LoginPage extends BasePage{
	public static final String PATH = "/#/login";
	public static final String PATH_FREETRIAL = "/company/free-trial";
	public static final String FREETRIAL_HOST = "sysdig.com";
	public LoginPage(Config config) {
		super(config);
	}

	@FindBy(css = ".login__logo")
	private WebElement imgLogo;

	@FindBy(css = ".block-login__third-party-button--google-logo")
	private WebElement lnkGoogle;

	@FindBy(xpath = "//*[contains(@href, '#/samlAuthentication')]")
	private WebElement lnkSaml;

	@FindBy(xpath = "//*[contains(@href, '#/openIdAuthentication')]")
	private WebElement lnkOpenid;

	@FindBy(css = "*[data-test='login-username']")
	private WebElement txtUsername;

	@FindBy(css = "*[data-test='login-password']")
	private WebElement txtPassword;

	@FindBy(xpath = "//*[@type='submit']")
	private WebElement btnLogin;

	@FindBy(css = "*[data-test='link-forgot-password']")
	private WebElement lnkForgotPassword;

	@FindBy(css = ".login__error-display")
	private WebElement msgLoginErrorDisplay;

	@FindBy(css = ".reactsel__value-container")
	private WebElement cboRegionSelector;
	
	@FindBy(xpath = "//*[contains(@href, 'sysdig.com/sign-up')]")
	private WebElement lnkFreeTrial;

	public final WebElement getRegionSelector() {
		return this.cboRegionSelector;
	}

	public final String getUrl() {
		return driver.getCurrentUrl();
	}

	public final WebElement getLoginErrorDisplay() {
		return this.msgLoginErrorDisplay;
	}

	public final WebElement getForgotPassword() {
		return this.lnkForgotPassword;
	}

	public final WebElement getLoginButton() {
		return this.btnLogin;
	}

	public final Config getConfig() {
		return this.config;
	}

	public final WebElement getLogo() {
		return this.imgLogo;
	}

	public final WebElement getGoogle() {
		return this.lnkGoogle;
	}

	public final WebElement getSaml() {
		return this.lnkSaml;
	}

	public final WebElement getOpenid() {
		return this.lnkOpenid;
	}

	public final WebElement getUsername() {
		return this.txtUsername;
	}

	public final WebElement getPassword() {
		return this.txtPassword;
	}

	public WebElement selectRegion(REGION region) {
		String id = MessageFormat.format("react-select-2-option-{0}", region.label);
		cboRegionSelector.click();
		return driver.findElement(By.id(id));

	}
	
	public void typeUsername(String username) {
		txtUsername.clear();
		txtUsername.sendKeys(username);
	}
	
	public void typePassword(String password) {
		txtPassword.clear();
		txtPassword.sendKeys(password);
	}
	
	public void submit() {
		btnLogin.click();
	}
	
	public URL authenticate(AUTH authProvider) throws MalformedURLException {
		switch(authProvider) {
		case GOOGLE:
			lnkGoogle.click();
			break;
		case OPENID:
			lnkOpenid.click();
			break;
		case SAML:
			lnkSaml.click();
			break;
		default:
			//do nothing
			break;
		}
		return new URL(driver.getCurrentUrl());
	}

	public void clearFields() {
		this.txtUsername.clear();
		this.txtPassword.clear();
	}
	
	public URL signupForFreeTrial() throws MalformedURLException {
		lnkFreeTrial.click();
		return new URL(driver.getCurrentUrl());
	}

	@Override
	public void navigate() {
		navigateTo(DEFAULT_REGION, PATH);
	}

	public void navigate(REGION region) {
		navigateTo(region, PATH);
	}
}
