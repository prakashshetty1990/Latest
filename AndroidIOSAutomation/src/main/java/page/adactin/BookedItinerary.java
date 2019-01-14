package page.adactin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Common;
import apppackage.MobilePage;
import io.appium.java_client.AppiumDriver;

public class BookedItinerary extends MobilePage {
	

	@FindBy(xpath="//*[@class='input_search']")
	private WebElement txtOrderNumber;

	@FindBy(xpath="//*[@class='input_search']")
	private WebElement txtSearchOrderId;
	
	@FindBy(xpath="//*[@class='button_search']")
	private WebElement btnGo;
	
	@FindBy(id="search_result_error")
	private WebElement weSearchError;
	
	
	protected static String HOME_PAGE_TITLE = "AdactIn.com - Select Hotel";
	
	
	public BookedItinerary(AppiumDriver browser) {
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
		waitForElement(By.id("my_itinerary"));
	}

	
	
	public void deleteOrder(String orderNumber) {
		try {			
			
			enterText(txtSearchOrderId, "Search Order Id", orderNumber);
			clickOn(btnGo, "Search Go Button");
			WebElement weOrderNumber = browser.findElement(By.xpath("input[value='Cancel " + orderNumber + "']"));
			clickOn(weOrderNumber, "Cancel Button of " + orderNumber);
			alertAccept();
			if (getText(weSearchError).contains("The booking has been cancelled.")) {
				Common.testStepPassed(orderNumber + " - The booking has been cancelled.");
			} else {
				Common.testStepFailed(orderNumber + " - The booking is not cancelled.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
