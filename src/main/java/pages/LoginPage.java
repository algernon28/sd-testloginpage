package pages;

import java.text.MessageFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import config.Config;

public class LoginPage {
	public static final String URL = "https://app.sysdigcloud.com/";

	private Config config;

	private enum REGION {
		EU_CENTRAL("0"), WS_EAST("1"), WS_WEST("2");

		public final String label;

		private REGION(String label) {
			this.label = label;
		}
	}

	public LoginPage(Config config) {
		this.config = config;
		PageFactory.initElements(config.getWebDriver(), this);
	}

	@FindBy(css = ".login__logo")
	private WebElement logo;

	@FindBy(xpath = "//*[contains(@href, '#/samlAuthentication')]")
	private WebElement google;

	@FindBy(xpath = "//*[contains(@href, '#/samlAuthentication')]")
	private WebElement saml;

	@FindBy(xpath = "//*[contains(@href, '#/openIdAuthentication')]")
	private WebElement openid;

	@FindBy(xpath = "//*[@name = 'username' and @type = 'email']")
	private WebElement username;

	@FindBy(xpath = "//*[@name = 'password' and @type = 'password']")
	private WebElement password;

	@FindBy(xpath = "//*[@type='submit']")
	private WebElement loginButton;

	@FindBy(xpath = "//*[(contains(text(), 'Forgot your password?') or contains(., 'Forgot your password?'))]")
	private WebElement forgotPassword;
	
	@FindBy(css = ".login__error-display")
	private WebElement loginErrorDisplay;

	public static final String getUrl() {
		return URL;
	}

	public final WebElement getLoginErrorDisplay() {
		return this.loginErrorDisplay;
	}

	public final WebElement getForgotPassword() {
		return this.forgotPassword;
	}

	public final WebElement getLoginButton() {
		return this.loginButton;
	}

	public final Config getConfig() {
		return this.config;
	}

	public final WebElement getLogo() {
		return this.logo;
	}

	public final WebElement getGoogle() {
		return this.google;
	}

	public final WebElement getSaml() {
		return this.saml;
	}

	public final WebElement getOpenid() {
		return this.openid;
	}

	public final WebElement getUsername() {
		return this.username;
	}

	public final WebElement getPassword() {
		return this.password;
	}

	public WebElement selectRegion(REGION region) {
		String id = MessageFormat.format("react-select-2-option-{0}", region.label);
		Select sel = new Select(config.getWebDriver().findElement(By.id(id)));
		sel.selectByIndex(Integer.parseInt(region.label));
		return sel.getWrappedElement();
		
	}
	
	public void clearFields() {
		this.username.clear();
		this.password.clear();
	}
	
	public void navigate() {
		this.config.getWebDriver().navigate().to(URL);
	}

}
