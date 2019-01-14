package page.adactin;
import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import apppackage.MobilePage;
import apppackage.Page;
import Utilities.Common;


public class SelectHotel  extends MobilePage{

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
	
	//***********Radio Button**********//
	
	@FindBy(xpath="//*[@id='radiobutton_1']")
	private WebElement rbSelect;
	

	//***********Buttons**********//	
	
	@FindBy(xpath="//input[@name='continue']")
	private WebElement btnContinue;
	
	@FindBy(xpath="//input[@name='cancel']")
	private WebElement btnCancel;
	
	@FindBy(xpath="//form[@name='select_form']//tr[2]//table")
	private WebElement tblSelectHotel;
	
	
	
	
	protected static String HOME_PAGE_TITLE = "AdactIn.com - Select Hotel";
	
	
	protected SelectHotel(AppiumDriver browser) {
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
			waitForElement(By.xpath("//input[@name='continue']"));				
		}catch(Exception e){
			System.out.println(e.getMessage());			
		}
	}

	public BookHotel Selecthotel()
	{
		//clickOnTableItem(tblSelectHotel,2,"Hotel Cornice",1);
		clickOn(rbSelect,"Selected");		
		clickOn(btnContinue,"Continue");
		return new BookHotel(browser);
	}
	
	



}
