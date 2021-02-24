package pages;

import static pages.LoginConstants.DEFAULT_REGION;

import java.text.MessageFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import config.Config;
import pages.LoginConstants.REGION;

public class LoginPage extends BasePage{
	public static final String PATH = "/#/login";
	public LoginPage(Config config) {
		super(config);
	}

	@FindBy(css = ".login__logo")
	private WebElement imgLogo;

	@FindBy(xpath = "//*[contains(@href, '#/samlAuthentication')]")
	private WebElement lnkGoogle;

	@FindBy(xpath = "//*[contains(@href, '#/samlAuthentication')]")
	private WebElement lnkSaml;

	@FindBy(xpath = "//*[contains(@href, '#/openIdAuthentication')]")
	private WebElement lnkOpenid;

	@FindBy(xpath = "//*[@name = 'txtUsername' and @type = 'email']")
	private WebElement txtUsername;

	@FindBy(xpath = "//*[@name = 'txtPassword' and @type = 'txtPassword']")
	private WebElement txtPassword;

	@FindBy(xpath = "//*[@type='submit']")
	private WebElement btnLogin;

	@FindBy(css = "*[data-test='link-forgot-password']")
	private WebElement lnkForgotPassword;

	@FindBy(css = ".login__error-display")
	private WebElement msgLoginErrorDisplay;

	@FindBy(css = ".reactsel__value-container")
	private WebElement cboRegionSelector;

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
		WebElement sel = driver.findElement(By.id(id));
		return sel;

	}
	
	public void typeUsername(String username) {
		txtUsername.clear();
		txtUsername.sendKeys(username);
	}
	
	public void typePassword(String password) {
		txtPassword.clear();
		txtPassword.sendKeys(password);
	}

	public void clearFields() {
		this.txtUsername.clear();
		this.txtPassword.clear();
	}

	@Override
	public void navigate() {
		navigateTo(DEFAULT_REGION, PATH);
	}

	public void navigate(REGION region) {
		navigateTo(region, PATH);
	}
}
