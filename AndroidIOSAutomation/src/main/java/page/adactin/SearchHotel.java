package page.adactin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import apppackage.MobilePage;
import Utilities.Common;
import io.appium.java_client.AppiumDriver;

public class SearchHotel extends MobilePage {
	
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
	
	@FindBy(xpath="//input[@name='datepick_in']")
	private WebElement txtCheckindate;
	
	@FindBy(xpath="//input[@name='datepick_out']")
	private WebElement txtCheckoutdate;
	
	
	//***********dropdown**********//
	
	@FindBy(xpath="//*[@id='location']")
	private WebElement lstLocation;

	@FindBy(xpath="//*[@id='hotels']")
	private WebElement lstHotels;

	@FindBy(xpath="//*[@id='room_type']")
	private WebElement lstRoomtype;
	
	@FindBy(xpath="//*[@id='room_nos']")
	private WebElement lstRoomnos;
	
	@FindBy(xpath="//*[@id='adult_room']")
	private WebElement lstAdults;
	
	@FindBy(xpath="//*[@id='child_room']")
	private WebElement lstChildren;
	
	//***********Buttons**********//
	
	@FindBy(xpath="//input[@name='Submit']")
	private WebElement btnSearch;
	
	@FindBy(xpath="//input[@name='Reset']")
	private WebElement btnReset;
	
	
	
	
	protected static String HOME_PAGE_TITLE = "AdactIn.com - Search Hotel";
	
	protected SearchHotel(AppiumDriver browser) {
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
		try {
			waitForElement(By.xpath("//input[@name='Submit']"));			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	public boolean verifyLoginpage() {
		if (isElementPresent(btnReset)) {
			Common.testStepPassed("Successfully logged in to the application" );
			Common.takeScreenshot();
			return true;
		}else{			
			Common.testStepFailed("Unable to login to application, Check Username and password" );
			return false;
		}		
	}
	
	
	public LoginPage Logout() {
		try {
			clickOn(lnkLogout, "logout");
			Common.takeScreenshot();			
			waitForElement(lnkLoginAgain);
			if (isElementPresent(lnkLoginAgain)) {
				Common.testStepInfo("Logged out Successfully");
				Common.takeScreenshot();
			}
			clickOn(lnkLoginAgain, "Login Again");			
		} catch (Exception e) {
			Common.testStepFailed("Exception Caught:" + e.getMessage());
		}
		return new LoginPage(browser);
	}
	
	
	

	public SelectHotel searchHotel() {
		try {
			selectByIndex(lstLocation, "location", 1);
			selectByIndex(lstRoomnos, "room_nos", 2);
			selectByIndex(lstAdults, "adult_room", 2);
			Common.takeScreenshot();
			clickOn(btnSearch, "Search button");					
		} catch (Exception e) {
			Common.testStepFailed("Exception Caught:" + e.getMessage());
		}
		return new SelectHotel(browser);
	}
	

}
