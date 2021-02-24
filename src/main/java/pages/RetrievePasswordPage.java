package pages;

import static pages.LoginConstants.DEFAULT_REGION;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import config.Config;
import pages.LoginConstants.REGION;

public class RetrievePasswordPage extends BasePage {
	public static final String PATH = "/#/forgotPassword";
	
	@FindBy(css = "*[data-test='forgot-password-username']")
	private WebElement txtEmail;
	
	@FindBy(xpath = "//*[@type='submit']")
	private WebElement btnSubmit;
	
	@FindBy(className = "login__link")
	private WebElement lnkBackToLogin;
	
	@FindBy(css = ".login__feedback-message")
	private WebElement lblFeedbackMessage;
	
	public RetrievePasswordPage(Config config) {
		super(config);
	}
	
	public void typeEmail(String email) {
		txtEmail.clear();
		txtEmail.sendKeys(email);
	}

	public final WebElement getTxtEmail() {
		return this.txtEmail;
	}

	public final WebElement getBtnSubmit() {
		return this.btnSubmit;
	}

	public final WebElement getLnkBackToLogin() {
		return this.lnkBackToLogin;
	}

	public final WebElement getLblFeedbackMessage() {
		return this.lblFeedbackMessage;
	}
	
	@Override
	public void navigate() {
		navigateTo(DEFAULT_REGION, PATH);
	}

	public void navigate(REGION region) {
		navigateTo(region, PATH);
	}
}
