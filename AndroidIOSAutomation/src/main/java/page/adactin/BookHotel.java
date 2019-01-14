package page.adactin;
import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import apppackage.MobilePage;
import apppackage.Page;
import Utilities.Common;
import Utilities.GlobalKeys;


public class BookHotel  extends MobilePage{

	
	//***********Link**********//
	
	@FindBy(xpath="//a[text()='Logout']")
	private WebElement lnkLogout;

	@FindBy(xpath="//a[text()='Click here to login again']")
	private WebElement lnkLoginAgain;
	
	@FindBy(xpath="//a[text()='Search Hotel']")
	private WebElement lnkSearchHotel;
	
	@FindBy(xpath="//a[text()='Booked Itinerary']")
	private WebElement lnkBookedItinerary;
	
	@FindBy(xpath="//a[text()='Change Password']")
	private WebElement lnkChangePassword;
	
	//***********Text Box**********//
	
	@FindBy(xpath="//input[@id='first_name']")
	private WebElement txtFirstName;

	@FindBy(xpath="//input[@id='last_name']")
	private WebElement txtLastName;
	
	@FindBy(xpath="//textarea[@id='address']")
	private WebElement txtAddress;
	
	@FindBy(xpath="//input[@id='cc_num']")
	private WebElement txtCreditcardnumber;
	
	@FindBy(xpath="//input[@id='cc_cvv']")
	private WebElement txtccvNumber;
	
	@FindBy(xpath="//td[contains(text(),'Booking Confirmation')]")
	private WebElement txtBookinconfirm;
	
	
	//***********Buttons**********//
	
	@FindBy(xpath="//input[@id='book_now']")
	private WebElement btnBooknow;
	
	
	//***********Dropdowns**********//
	
	@FindBy(xpath="//select[@id='cc_type']")
	private WebElement lstCardType;
		
	@FindBy(xpath="//select[@id='cc_exp_month']")
	private WebElement lstExpMonth;
	
	@FindBy(xpath="//select[@id='cc_exp_year']")
	private WebElement lstExpYear;
	
	@FindBy(id="my_itinerary")
	private WebElement btnItinerary;
	
	@FindBy(xpath="//*[@class='login_title']")
	private WebElement weWebPageTitle;
	
	@FindBy(id="order_no")
	private WebElement txtOrderNo;
	
		
	protected static String HOME_PAGE_TITLE = "AdactIn.com - Book A Hotel";
	
		
	protected BookHotel(AppiumDriver browser) {
		super(browser);	
	}
	
	
	
	@Override
	protected boolean isValidPage() {
		if (browser.getTitle().trim().contains(HOME_PAGE_TITLE)) {
			return true;
		}
		return false;
	}

	@Override
	protected void waitForPageLoad() {
		try{
			waitForElement(By.xpath("//input[@id='first_name']"));				
		}catch(Exception e){
			System.out.println(e.getMessage());			
		}
	}
	

	public String bookHotel() {
		try {
			waitForElement(txtFirstName);
			
			enterText(txtFirstName, "FirstName", Common.retrieve("FirstName"));
			
			enterText(txtLastName, "LastName", Common.retrieve("LastName"));
			
			enterText(txtAddress, "BillingAddress", Common.retrieve("BillingAddress"));
			
			enterText(txtCreditcardnumber, "Creditcardno", Common.retrieve("creditcardnumber"));
			
			selectByIndex(lstCardType, "Card Type", 3);
			
			selectByIndex(lstExpMonth, "Exp Month", 3);
			
			selectByIndex(lstExpYear, "Exp Year", 3);
			
			enterText(txtccvNumber, "Cvv",  Common.retrieve("Cvv"));
								
			clickElementWithEnterKey(btnBooknow, "Book Now Button");
						
			waitForElement(txtOrderNo);
			
			if(isElementPresent(txtOrderNo)){
				String orderNumber =  getAttribute(txtOrderNo,"value");
				Common.testStepPassed("Order Created Successfully, Order Number is->"+orderNumber);
				Common.takeScreenshot();
				return orderNumber;
			}else{
				Common.testStepFailed("Order is not Created ");
				return null;
			}			
		} catch (Exception e) {
			Common.testStepFailed("Exception Caught:" + e.getMessage());
			return null;
		}
	}
	
	
	public BookedItinerary goToBookedItinerary(){
		if(isElementPresent(btnItinerary)){
			clickOn(btnItinerary, "My Itinerary");
			if(isElementPresent(weWebPageTitle)){
				if(weWebPageTitle.getText().contains("Booked Itinerary")){
					Common.testStepPassed("Booked Itinerary Page is loaded as expected");
				}else{
					Common.testStepFailed("Booked Itinerary Page is not loaded");
				}
			}else{
				Common.testStepFailed("Page Title element is not displayed");
			}
		}else{
			Common.testStepFailed("My Itinerary Button is not displayed");
		}
		return new BookedItinerary(browser);
	}


}
