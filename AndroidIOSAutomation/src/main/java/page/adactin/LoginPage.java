package page.adactin;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import apppackage.MobilePage;
import apppackage.Page;
import Utilities.Common;
import io.appium.java_client.AppiumDriver;

public class LoginPage extends MobilePage {
	
	
	//***********Text Box**********//
	
	@FindBy(xpath="//input[@name='username']")
	private WebElement txtUsername;
	
	@FindBy(xpath="//input[@name='password']")
	private WebElement txtPassword;
	
	//***********Buttons**********//
	
	@FindBy(xpath="//input[@name='login']")
	private WebElement btnLogin;
	
	//***********Link**********//
	
	@FindBy(xpath="//div[@class='login_forgot']/a")
	private WebElement lnkForgotPassword;
	
		
	protected LoginPage(AppiumDriver browser) {		
		super(browser);		
	}
	
	protected static String HOME_PAGE_TITLE = "AdactIn.com - Hotel Reservation System";

	@Override
	protected boolean isValidPage() {
		if (browser.getTitle().trim().contains(HOME_PAGE_TITLE)) {
			return true;
		}
		return false;
	}

	@Override
	protected void waitForPageLoad() {
		waitForElement(By.xpath("//input[@name='login']"));
	}

	public SearchHotel loginToAdactin() {
		try {
			waitForElement(txtUsername);
			if (isElementPresent(txtUsername)) {				
				enterText(txtUsername, "Username", Common.retrieve("Username"));				
				enterText(txtPassword, "Password", Common.retrieve("Password"));
				Common.takeScreenshot();				
				clickElementWithEnterKey(btnLogin, "Loggedin");				
			}
		} catch (Exception e) {
			Common.testStepFailed("Exception Caught in Login Method "+ e.getMessage());
		}
		return new SearchHotel(browser);
	}
}
