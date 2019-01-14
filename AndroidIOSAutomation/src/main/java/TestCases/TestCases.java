package TestCases;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import page.adactin.*;
import Utilities.Common;
import Utilities.GlobalKeys;

@Listeners({ Utilities.TestListener.class })
public class TestCases extends Common {

	Application adactinApplication;
	public LoginPage loginPage;
	public SearchHotel searchHotel;
	public SelectHotel selectHotel;
	public BookHotel bookHotel;
	public BookedItinerary bookedItineraryPage;
	public static int count1 = 1, count2 = 1;

	public static Process p;


	public void testStart(String testCaseDescription){

		reportStart(GlobalKeys.testCaseName, testCaseDescription);		
		adactinApplication = new Application();
		loginPage = adactinApplication.openApplication();			
		GlobalKeys.testFailure = false;
		GlobalKeys.currentStep = 0;

	}
	
	public void testEnd() throws IOException {
		try {			
			adactinApplication.closeApp();
		} finally {
			GlobalKeys.extent.endTest(GlobalKeys.parent);
			GlobalKeys.extent.flush();
		}

	}

	@Test(alwaysRun = true)
	public void TC_001() throws IOException {		
			GlobalKeys.testCaseName = new Exception().getStackTrace()[0].getMethodName();
			testStart("Login to Adactin Application");

			for (String testDataSet : GlobalKeys.testCaseDataSets) {
				GlobalKeys.testCaseDataRow = returnRowNumber(testDataSet);
				testStepInfoStart(testDataSet);

				searchHotel = loginPage.loginToAdactin();
				if(searchHotel.verifyLoginpage()){
					loginPage = searchHotel.Logout();
				}

				testStepInfoEnd(testDataSet);
			}		
			testEnd();		
	}

	@Test(alwaysRun = true)
	public void TC_002() throws IOException {		
		GlobalKeys.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("Search For Hotel");

		for (String testDataSet : GlobalKeys.testCaseDataSets) {
			GlobalKeys.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);

			searchHotel = loginPage.loginToAdactin();
			if(searchHotel.verifyLoginpage()){
				searchHotel.searchHotel();
				loginPage = searchHotel.Logout();
			}
			
			testStepInfoEnd(testDataSet);
		}		
		testEnd();	
	}

	@Test(alwaysRun = true)
	public void TC_003() throws IOException {
		GlobalKeys.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("Book Hotel");

		for (String testDataSet : GlobalKeys.testCaseDataSets) {
			GlobalKeys.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);
			
			
			searchHotel = loginPage.loginToAdactin();
			if(searchHotel.verifyLoginpage()){
				selectHotel = searchHotel.searchHotel();				
				bookHotel = selectHotel.Selecthotel();
				bookHotel.bookHotel();
				loginPage = searchHotel.Logout();
			}
			
			testStepInfoEnd(testDataSet);
		}		
		testEnd();
		}

	@Test(alwaysRun = true)
	public void TC_004() throws IOException {
		GlobalKeys.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("Delete the Order");

		for (String testDataSet : GlobalKeys.testCaseDataSets) {

			GlobalKeys.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);

			searchHotel = loginPage.loginToAdactin();
			if(searchHotel.verifyLoginpage()){
				selectHotel = searchHotel.searchHotel();				
				bookHotel = selectHotel.Selecthotel();
				String orderNumber = bookHotel.bookHotel();
				bookedItineraryPage =bookHotel.goToBookedItinerary();
				bookedItineraryPage.deleteOrder(orderNumber);
				loginPage = searchHotel.Logout();
			}
			
			testStepInfoEnd(testDataSet);
		}		
		testEnd();
	}
	
	
	


}
