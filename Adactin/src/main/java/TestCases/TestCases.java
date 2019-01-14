package TestCases;


import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import PageMethods.AdactinApplication;
import PageMethods.AdactinHomePage;
import PageMethods.ConfirmationPage;
import PageMethods.ForgetPassword;
import PageMethods.NewUserRegistration;
import PageMethods.SearchHotel;
import PageMethods.SelectHotel;
import Utilities.Common;
import Utilities.GlobalKeys;

@Listeners({ Utilities.TestListener.class })
public class TestCases extends Common {

	public static int count = 1;
	private AdactinApplication adactinApplication;
	private AdactinHomePage adactinHomePage;
	private SearchHotel searchHotel;
	private SelectHotel selectHotel;
	private ForgetPassword ForgetPswd;
	private ConfirmationPage confirmpage;
	private NewUserRegistration Registrationpage;

	public void testStart(String testCaseDescription) {
		GlobalKeys.testFailure = false;
		GlobalKeys.currentStep = 0;
		reportStart(GlobalKeys.testCaseName,testCaseDescription);
		adactinApplication=new AdactinApplication();
		adactinHomePage=adactinApplication.openAdactinApplication();

	}

	public void testEnd() {
		try {
			adactinApplication.close();			
		} catch (Exception e) {
			System.out.println("Expception : " + e.getMessage());
		}finally{
			GlobalKeys.extent.endTest(GlobalKeys.parent);
			GlobalKeys.extent.flush();				
		}
	}


	@Test(alwaysRun = true)
	public void TC_001() {
		GlobalKeys.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("Login to Adactin Application");


		for (String testDataSet : GlobalKeys.testCaseDataSets) {
			GlobalKeys.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);

			searchHotel = adactinHomePage.Login();			
			if(searchHotel.verifyLoginpage())
				searchHotel.logoutFromApp();

			testStepInfoEnd(testDataSet);
		}
		testEnd();

	}


	@Test(alwaysRun = true)
	public void TC_002() {
		GlobalKeys.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("Search a hotel");


		for (String testDataSet : GlobalKeys.testCaseDataSets) {
			GlobalKeys.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);

			searchHotel = adactinHomePage.Login();			
			selectHotel=searchHotel.BookHotel();

			testStepInfoEnd(testDataSet);
		}
		testEnd();

	}


	@Test(alwaysRun = true)
	public void TC_003() {
		GlobalKeys.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("Book a hotel");


		for (String testDataSet : GlobalKeys.testCaseDataSets) {
			GlobalKeys.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet );

			searchHotel = adactinHomePage.Login();			
			selectHotel=searchHotel.BookHotel();
			selectHotel.Selecthotel();

			testStepInfoEnd(testDataSet);
		}
		testEnd();

	}


	@Test(alwaysRun = true)
	public void TC_004() {
		GlobalKeys.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("Forget Password functionality");


		for (String testDataSet : GlobalKeys.testCaseDataSets) {
			GlobalKeys.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);

			ForgetPswd=adactinHomePage.forgetpassword();

			testStepInfoEnd(testDataSet );
		}
		testEnd();

	}


	@Test(alwaysRun = true)
	public void TC_005() {
		GlobalKeys.testCaseName = new Exception().getStackTrace()[0].getMethodName();
		testStart("New User Register functionality");


		for (String testDataSet : GlobalKeys.testCaseDataSets) {
			GlobalKeys.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);

			Registrationpage=adactinHomePage.UserRegistration();

			testStepInfoEnd(testDataSet );
		}
		testEnd();

	}

}
