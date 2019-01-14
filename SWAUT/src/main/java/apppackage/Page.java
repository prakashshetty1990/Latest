package apppackage;




import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.Common;
import Utilities.GlobalKeys;
import Utilities.GlobalKeys;

/**
 * Base class for all the pages.
 *
 */
public abstract class Page {
	protected WebDriver browser;	
	protected final int defaultElementLoadWaitTime = 20;
	protected abstract boolean isValidPage();

	protected abstract void waitForPageLoad();

	/**
	 * Constructor for Page class 
	 * @param browser
	 * @param report
	 */
	protected Page(WebDriver browser) {
		this.browser=browser;		
		PageFactory.initElements(browser, this);
		waitForPageLoad();
		verifyApplicationInCorrectPage();
	}

	/**
	 * Verify Application in Correct Page. 
	 * @param Nil 
	 * @return Nil
	 */	
	private void verifyApplicationInCorrectPage() {
		if (!isValidPage()) {
			String stepName="Navigation to Page";
			String message="The application is not in the expected page , current page: " + 
					browser.getTitle() +" Page.";			
		}
	}

	/**
	 * Get the browser object specified in the property
	 * @param browserName
	 * @return
	 */
	public static WebDriver getDriver(String browserName) {
		WebDriver driver = null;
		if(GlobalKeys.execEnv.toLowerCase().trim().equals("server")){		
			if (browserName.equalsIgnoreCase("firefox")) {			
				System.setProperty("webdriver.gecko.driver", 
						GlobalKeys.configPath+Common.getConfigProperty("gekodriver"));								
				driver = new FirefoxDriver();
				System.out.println("Executing on FireFox on Server");
			}
			if(browserName.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver", 
						GlobalKeys.configPath+Common.getConfigProperty("chromedriver"));									
				driver=new ChromeDriver();
				System.out.println("Executing on CHROME on Server");					
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			}
			if(browserName.equalsIgnoreCase("iexplore") || browserName.equalsIgnoreCase("ie")){
				System.setProperty("webdriver.ie.driver",
						GlobalKeys.configPath+Common.getConfigProperty("iedriver"));
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
				capabilities.setCapability("ACCEPT_SSL_CERTS", true);
				
				driver=new InternetExplorerDriver(capabilities);		
				System.out.println("Executing on Internet Explorer on Server");
			}		
		}else if(GlobalKeys.execEnv.toLowerCase().trim().equals("desktop")){
			DesiredCapabilities cap = null;
			if(browserName.equalsIgnoreCase("chrome")){				
				System.out.println(" Executing on CHROME");
				cap = DesiredCapabilities.chrome();
			}
			if (browserName.equalsIgnoreCase("firefox")) {
				cap = DesiredCapabilities.firefox();
			}
			if(browserName.equalsIgnoreCase("iexplore") || browserName.equalsIgnoreCase("ie")){
				cap = DesiredCapabilities.internetExplorer();
			}
			String Node = "http://" + GlobalKeys.host + ":" + GlobalKeys.port + "/wd/hub";			
			try {
				driver = new RemoteWebDriver(new URL(Node), cap);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}			
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		}else if(GlobalKeys.execEnv.toLowerCase().trim().equals("cloud")){
			DesiredCapabilities cap = null;
			System.out.println(" Executing on Cloud");
			if(browserName.equalsIgnoreCase("chrome")){				
				System.out.println(" Executing on CHROME");
				cap = DesiredCapabilities.chrome();
			}
			if (browserName.equalsIgnoreCase("firefox")) {
				cap = DesiredCapabilities.firefox();
			}
			if(browserName.equalsIgnoreCase("iexplore") || browserName.equalsIgnoreCase("ie")){
				cap = DesiredCapabilities.internetExplorer();
			}
			cap.setCapability("platform", "WINDOWS");
			cap.setCapability("version", "69.0");
			try {
				driver = new RemoteWebDriver(
						new URL("http://" + GlobalKeys.host + ":" + GlobalKeys.port + "@hub-cloud.browserstack.com/wd/hub"), cap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return driver;
	}



	/**
	 * Check if the element is present in the page
	 * @param element WebElement need to check
	 * @return True if present
	 */
	protected boolean isElementPresent(WebElement element){
		try{
			return element.isDisplayed();
		}catch(NoSuchElementException ex){
			return false;
		}catch(Exception ex2){
			return false;
		}
	}

	/**
	 * To generate Random Number
	 * @param int min and max numbers
	 * @return random Number in Integer
	 */

	public static int randInt(int min, int max) {
		// Usually this can be a field rather than a method variable
		Random rand = new Random();
		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	/**
	 * Check if the element is displayed in the page
	 * @param element WebElement need to check
	 * @return True if present
	 */
	protected boolean isElementDisplayed(WebElement element, String str){
		try{			
			boolean blnDisplay = element.isDisplayed();
			Common.testStepPassed(str+" is Displayed");
			return blnDisplay;
		}catch(NoSuchElementException ex){
			Common.testStepFailed(str+" is Not Displayed");
			return false;
		}
	}

	/**
	 * Check if the element is present in the page
	 * @param xpath of WebElement need to check
	 * @return True if present
	 */
	public boolean isElementPresent(By by){
		try{
			return browser.findElement(by).isDisplayed();
		}catch(NoSuchElementException ex){
			return false;
		}catch(Exception ex2){
			return false;
		}
	}

	/***
	 * Method to select recentPopupSelect_without_window_nameWebdriver
	 * @return      : Null 
	 ***/
	public void recentPopupSelect_without_window_nameWebdriver() {
		String mainwindow;
		mainwindow = browser.getWindowHandle();
		sleep(2000);
		Iterator<String> popwindow = browser.getWindowHandles().iterator();
		while (popwindow.hasNext()) {
			String window = popwindow.next();
			browser.switchTo().window(window);

		}

	}

	/***
	 * Method to click on a link(WebElement button)
	 * @param : we WebElement to be clicked
	 * @param : elem Name of the WebElement
	 ***/
	public void clickOn(WebElement we,String elem) {		
		try{
			waitForElement(we);
			if (isElementPresent(we)){
				moveToElement(we);
				we.click();
				Common.testStepPassed("Clicked on WebElement-"+ elem );	
			}else{
				Common.testStepFailed(elem+" Element is not displayed");
			}				
		}
		catch (Exception ex) {
			Common.testStepFailed("Unable to click on Element-"+ elem);
		} 
	}


	/**
	 * Method to jsclick on a link(WebElement link)
	 * @param : we WebElement to be Clicked 
	 * @param : elem Name of the webElement
	 */
	protected void jsClick(WebElement we,String elem) {		
		try{	
			scrollPageDown(we);
			((JavascriptExecutor) browser).executeScript("arguments[0].click();",we);
			Common.testStepPassed("Clicked on -"+ elem +"- Element");			
		}catch (RuntimeException ex) {
			Common.testStepFailed("Uanble to click on -"+ elem +"- Element");
		} 
	}


	/***
	 * Method to enter text in a textbox
	 * @param : Webelement 
	 * @param : Name of the Webelement
	 * @param : text to be entered
	 * @return : true if entered
	 ***/
	public boolean enterText(WebElement we,String elemName,String text){
		boolean blnFlag;
		blnFlag = false;
		try{
			waitForElement(we);
			if(isElementPresent(we)){
				we.clear();
				we.sendKeys(text);	
				Common.testStepPassed("Entered value - "+text+" in the text field- "+ elemName);
				blnFlag = true;
			}else{
				Common.testStepFailed(elemName+" element is not present");
			}
		}
		catch (Exception ex) {			
			Common.testStepFailed("Unable to enter text in the text field->"+ elemName);
		} 
		return blnFlag;
	}


	/***
	 * Method to enter text in a textbox
	 * @param : Webelement 
	 * @param : Name of the Webelement
	 * @param : text to be entered
	 * @return : true if entered
	 ***/
	public boolean enterText(WebElement we,String elemName,String text,int waitTime){
		boolean blnFlag;
		blnFlag = false;
		try{
			waitForElement(we,waitTime);
			if(isElementPresent(we)){
				we.clear();
				we.sendKeys(text);	
				Common.testStepPassed("Entered value - "+text+" in the text field- "+ elemName);
				blnFlag = true;
			}else{
				Common.testStepFailed(elemName+" element is not displayed");
			}
		}
		catch (Exception ex) {			
			Common.testStepFailed("Unable to enter text in the text field->"+ elemName);
		} 
		return blnFlag;
	}


	/***
	 * Method to clear text in a textbox
	 * @param : Element Name
	 * @return :
	 ***/
	public void clearText(WebElement we){
		try{			
			if(isElementPresent(we)){
				we.clear();				
			}
		}catch(Exception ex){
			Common.testStepFailed("Unable to clear text in the text field");
		}
	}


	/***
	 * Method to switch to child window
	 * @param : pageTitle Title of the Child window
	 * @return : true if navigation is success
	 ***/
	public boolean navigateToNewWindow(String pageTitle) {
		boolean loopstatus = false;
		int timeout = GlobalKeys.pageLoadWaitTime;
		for (int i = 1; i <= timeout; i++)
		{
			loopstatus = false;
			if (i == timeout)
			{
				Common.testStepFailed("Unable to Navigate to the page -"+pageTitle);
			}

			Set<String> AllHandle = browser.getWindowHandles();
			for (String han : AllHandle)
			{
				browser.switchTo().window(han);
				String getTitle = browser.getTitle();
				if (getTitle.trim().equalsIgnoreCase(pageTitle))
				{
					loopstatus = true;
					break;
				}
			}
			if (loopstatus) {
				Common.testStepPassed("Navigated to the page -"+pageTitle+"- successfully");
				break;
			}	      
			sleep(1000);
		}				
		return loopstatus;
	}


	/***
	 * Method to switch to child window
	 * @param : pageTitle Title of the Child window
	 * @return : true if navigation is success
	 ***/
	public boolean navigateToSecondWindow() {
		boolean loopstatus = false;
		String getTitle = null;
		int timeout = GlobalKeys.pageLoadWaitTime;
		String strParentTitle = browser.getTitle();
		for (int i = 1; i <= timeout; i++)
		{
			loopstatus = false;
			if (i == timeout)
			{
				Common.testStepFailed("Unable to Navigate to the new window-");
			}

			Set<String> AllHandle = browser.getWindowHandles();
			for (String han : AllHandle)
			{
				browser.switchTo().window(han);
				getTitle = browser.getTitle();

				if (!getTitle.trim().equalsIgnoreCase(strParentTitle))
				{
					loopstatus = true;

					break;
				}
			}
			if (loopstatus) {
				Common.testStepPassed("Navigated to the page -"+getTitle+"- successfully");
				browser.manage().window().maximize();
				break;
			}	      
			sleep(1000);
		}				
		return loopstatus;
	}


	/***
	 * Method to switch to parent window
	 * @param : parentWindow Window handle of the parent window
	 ***/
	public void navigatoToParentWindow(String parentWindow) {
		browser.switchTo().window(parentWindow);
	}


	/***
	 * Javascript to hover over WebElement
	 * @param elem Webelement to hover over
	 */
	public void jsmoveToElement(WebElement elem){

		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		JavascriptExecutor js = (JavascriptExecutor) browser;
		js.executeScript(mouseOverScript, elem);

	}

	/***
	 * Method to close a webpage
	 * @return      : Null 
	 ***/
	public void closeCurrentPage(){
		String str=browser.getTitle();
		try {
			browser.close();
			Set<String> windows=browser.getWindowHandles();
			for(String window:windows){    
				browser.switchTo().window(window);
				String getTitle = browser.getTitle();
				if (!getTitle.trim().equalsIgnoreCase(str))
				{     
					break;
				}
			}
			sleep(5000);
			Common.testStepPassed("Closed the current page with title->"+str);
		} catch (Exception e) {
			Common.testStepFailed("Unable to Close the current page with title->"+str);
		}
	}


	//*****************************************************************************************************************//
	//Alert pop ups
	//*****************************************************************************************************************//
	/***
	 * Method to accept and close alert and return the text within the alert
	 * @param : 
	 * @return :
	 ***/
	public String closeAlertAndReturnText(){
		String alertMessage=null;
		try{		
			WebDriverWait wait = new WebDriverWait(browser, GlobalKeys.elementLoadWaitTime);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = browser.switchTo().alert();
			alertMessage=alert.getText();
			Common.testStepPassed("alertMessage displayed is->"+alertMessage);
			alert.accept();
			Common.testStepPassed("Alert is closed successfully");
		}catch(Exception Ex){
			Common.testStepFailed("Unable to Close Alert, Error Message is->"+Ex.getMessage());
		}
		return alertMessage;
	}


	/***
	 * Method to Cancel the alert
	 * @param : 
	 * @return :
	 ***/
	public void alertCancel(){
		try{
			WebDriverWait wait = new WebDriverWait(browser, GlobalKeys.elementLoadWaitTime);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = browser.switchTo().alert();
			alert.dismiss();
			Common.testStepPassed("Clicked on Alert Cancel successfully");
		}catch(Exception ex){
			Common.testStepFailed("Unable to Cancel Alert, Error Message is->"+ex.getMessage());
		}
	}


	/***
	 * Method to verify if alert is Present
	 * @param : 
	 * @return :
	 ***/
	public boolean isAlertWindowPresent()
	{
		try
		{
			browser.switchTo().alert();
			return true;
		}
		catch (Exception E) {

		}
		return false;
	}


	//*****************************************************************************************************************//
	//waits
	//*****************************************************************************************************************//

	/**
	 * Method to wait for element to load in the page
	 * @param WebElement
	 * @return true or false
	 */
	protected Boolean waitForElement(By by) {
		try {			
			new WebDriverWait(browser,GlobalKeys.elementLoadWaitTime).
			until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception ex) {
			return false;
		}
		return true;
	}


	protected Boolean waitForElementInvisible(By by) {
		try {			
			new WebDriverWait(browser,GlobalKeys.elementLoadWaitTime).
			until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Method to wait for element to load in the page for default time specified in framework configuration
	 * @param WebElement
	 * @return true or false
	 */

	protected Boolean waitForElement(WebElement we) {
		try {
			new WebDriverWait(browser, GlobalKeys.elementLoadWaitTime).until(ExpectedConditions
					.elementToBeClickable(we));
			return true;
		} catch (RuntimeException ex) {
			return false;
		}    	
	}


	/**
	 * Method to wait for element to load in the page for particular time specified
	 * @param WebElement
	 * @return true or false
	 */

	protected Boolean waitForElement(WebElement we,int waitTime) {
		try {
			new WebDriverWait(browser, waitTime).until(ExpectedConditions
					.elementToBeClickable(we));
			return true;
		} catch (RuntimeException ex) {
			return false;
		}    	
	}

	/**
	 * Method to wait for Alert present in the page
	 * @param 
	 * @return true or false
	 */
	protected Boolean waitForAlert(){
		try{
			new WebDriverWait(browser, GlobalKeys.elementLoadWaitTime).until(ExpectedConditions.alertIsPresent());
			return true;
		}catch(Exception Ex){
			return false;
		}
	}


	/***
	 * Method to get current time in minutes
	 * @param : Element Name
	 * @return :
	 * Modified By :
	 ***/
	public int getTimeInMin(String time) {
		//String time=new SimpleDateFormat("HH:mm").format(new Date());
		String[] splitTime=time.split(":");
		int hr=Integer.parseInt(splitTime[0]);
		int mn=Integer.parseInt(splitTime[1].substring(0,2));
		if(hr>12){
			hr=hr-12;
		}
		int timStamp=(hr*60)+mn;
		return timStamp;
	}


	/***
	 * Method to check for an alert for 5 seconds
	 * @param       : Element Name
	 * @return      : 
	 * Modified By  :  
	 ***/
	public boolean isAlertPresent(){
		try{
			WebDriverWait wait = new WebDriverWait(browser, GlobalKeys.elementLoadWaitTime);
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		}catch(Exception e){			
			return false;
		}
	}


	/***
	 * Method to wait for the any of 2 elements to be displayed
	 * @param       : we1,we2
	 * @return      : 
	 * @author      : Prakash Shetty
	 * Modified By  :  
	 ***/
	public boolean waitForAnyElement(WebElement we1,WebElement we2){
		try{			
			for(int i=0;i<GlobalKeys.elementLoadWaitTime;i++){
				if(isElementPresent(we1)||isElementPresent(we2)){
					break;
				}else{
					sleep(1000);
				}
			}
			return true;
		}catch(Exception Ex){
			return false;
		}
	}
	/***
	 * Method to wait for the any of 2 elements to be displayed
	 * @param       : we1,we2
	 * @return      : 
	 * @author      : Prakash Shetty
	 * Modified By  :  
	 ***/
	public boolean waitForTwoElements(WebElement we1,WebElement we2){
		try{			
			for(int i=0;i<GlobalKeys.elementLoadWaitTime;i++){
				if(isElementPresent(we1)||isElementPresent(we2)){
					break;
				}else{
					sleep(1000);
				}
			}
			return true;
		}catch(Exception Ex){
			return false;
		}
	}
	/***
	 * Method to wait for the any of 2 elements to be displayed
	 * @param       : we1,we2,we3
	 * @return      : 
	 * @author      : Prakash Shetty
	 * Modified By  :  
	 ***/
	public boolean waitForThreeElements(WebElement we1,WebElement we2,WebElement we3){
		try{			
			for(int i=0;i<GlobalKeys.elementLoadWaitTime;i++){
				if(isElementPresent(we1)||isElementPresent(we2)||isElementPresent(we3)){
					break;
				}else{
					sleep(1000);
				}
			}
			return true;
		}catch(Exception Ex){
			return false;
		}
	}
	/**
	 * method to make a thread sleep for customized time in milliseconds
	 * @param milliseconds
	 */
	protected void sleep(int milliseconds){
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void selectRadioButton(By by,String value)
	{
		boolean blnSelect;
		blnSelect = false;
		try{
			List<WebElement> radioList=browser.findElements(by);
			for(WebElement element : radioList){
				String strActValue = element.getAttribute("value");
				if(strActValue.equalsIgnoreCase(value)){
					element.click(); 
					Common.testStepPassed("Radio button selected is->"+value);
					blnSelect = true;
					break;			
				}
			}
			if(!blnSelect){
				Common.testStepFailed("Radio button with specified value does not exist->"+value);
			}
		}catch(Exception ex){
			Common.testStepFailed("Exception Caught,Message is->"+ex.getMessage());
		}
	}

	/***
	 * Method to wait for the any of 2 elements to be displayed
	 * @param       : By,By
	 * @return      : 
	 * @author      : Prakash Shetty
	 * Modified By  :  
	 ***/
	public boolean waitForAnyElement(By we1,By we2){
		try{
			for(int i=0;i<GlobalKeys.elementLoadWaitTime;i++){
				if(isElementPresent(we1)||isElementPresent(we2)){
					break;
				}else{
					sleep(1000);
				}
			}
			return true;
		}catch(Exception Ex){
			return false;
		}
	}


	/***
	 * Method to hover over an element
	 * @param       : weMainMenuElement,weSubMenuElement
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void clickOnSubMenu(WebElement weMain,WebElement weSub ){
		String strElem=null;
		try{
			Actions action = new Actions(browser);
			action.moveToElement(weMain).click().perform();
			Common.testStepPassed("Hover over the Main menu item successfully");
		}catch(Exception Ex){
			Common.testStepFailed("Unable to hover Over main menu Item");
		}
		try{
			waitForElement(weSub);
			strElem = weSub.getText();
			weSub.click();
			Common.testStepPassed("Clicked on the Sub menu item successfully");
		}catch(Exception ex){
			Common.testStepFailed("Unable to Click on the sub menu item");
		}		
	}


	/***
	 * Method to hover over an element
	 * @param       : WebElement we
	 * @return      : 
	 * Modified By  :  
	 ***/


	public void moveToElement(WebElement we){
		try {
			/*Actions action = new Actions(browser);
			action.moveToElement(we).build().perform();*/
			scrollPageDown(we);
		} catch (Exception e) {
			Common.testStepFailed("Error Occurred while Move to Element --> "+e.getMessage());
		}
	}

	public void moveToElement(By by){
		try {
			/*Actions action = new Actions(browser);
			action.moveToElement(we).build().perform();*/
			scrollPageDown(by);
		} catch (Exception e) {
			Common.testStepFailed("Error Occurred while Move to Element --> "+e.getMessage());
		}
	}


	/***
	 * Method to drag and drop from source element to destination element
	 * @param       : weSource,weDestination
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void dragAndDrop(WebElement weSource, WebElement weDestination)
	{		
		try
		{	     
			new Actions(browser).dragAndDrop(weSource, weDestination).perform();
			Common.writeToLogFile("Info", "Drag and drop successful");
			Common.testReporter("Green", "Drag Source element and drop on Destination Element");
		}
		catch (Exception e)
		{
			Common.writeToLogFile("Error", "Error during drag and drop");
			Common.testStepFailed("Error : Drag Source element and drop on Destination Element");
		}
	}

	/***
	 * Method to Select value from dropdown by visible text
	 * @param       : we,strElemName,strVisibleText
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void selectByVisisbleText(WebElement we,String strElemName,String strVisibleText){
		try{
			Select sel = new Select( we);
			sel.selectByVisibleText(strVisibleText);

			Common.testStepPassed("selected value -"+strVisibleText +" from dropdown->"+strElemName);
		}catch(Exception Ex){
			Common.testStepFailed("Unable to select value from the dropdown "+Ex.getMessage());
		}
	}


	/***
	 * Method to Select value from dropdown by index
	 * @param       : we,strElemName,index
	 * @return      : 
	 * Modified By  :  
	 ***/

	public void selectByIndex(WebElement we,String strElemName,int index){
		try{
			Select sel = new Select( we);
			sel.selectByIndex(index);
			Common.testStepPassed("Selected "+index +"option from dropdown->"+strElemName);
		}catch(Exception Ex){
			Common.testStepFailed("Unable to select value from the dropdown "+Ex.getMessage());
		}
	}


	/***
	 * Method to Select value from dropdown by index
	 * @param       : we,strElemName,strValue
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void selectByValue(WebElement we,String strElemName,String strValue){
		try{
			Select sel = new Select( we);
			sel.selectByValue(strValue);
			Common.testStepPassed("Selected "+strValue +"option from dropdown->"+strElemName);
		}catch(Exception Ex){
			Common.testStepFailed("Unable to select value from the dropdown "+Ex.getMessage());
		}
	}


	/***
	 * Method to get the Selected value from dropdown
	 * @param       : weDropdown
	 * @return      : selectText
	 * Modified By  :  
	 ***/
	public String getTextSelectedOption(WebElement weDropDown){
		waitForElement(weDropDown);
		String selectText="";
		try {
			Select select = new Select(weDropDown);
			selectText = select.getFirstSelectedOption().getText().toString();	          

		}catch(Exception ex){
			Common.testStepFailed("Unable to get the selected value from dropdown->"+ex.getMessage());
		}
		return selectText;
	}



	/***
	 * Method to verify if the WebElement has the expected text
	 * @param       : we,expectedText
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void verifyElementText(WebElement we, String expectedText)
	{
		waitForElement(we);
		if (isElementPresent(we)){
			for (int i = 1; i <= GlobalKeys.elementLoadWaitTime; i++){
				try {
					if (we.getText().trim().equalsIgnoreCase(expectedText.trim())){	            
						Common.testStepPassed("Element contains the Expected Text->" + expectedText);

					}else{
						Common.testStepFailed("Element does not contain the expected text" + expectedText);
					}	          
				}catch (Exception e){
					sleep(1000);
				}	        
				if (i == GlobalKeys.elementLoadWaitTime){
					Common.testStepFailed("Element not found within " + GlobalKeys.elementLoadWaitTime + " timeouts");
				}
			}
		}
	}


	/***
	 * Method to get the ElementLoadWaitTime
	 * @param       : 
	 * @return      : ElementLoadWaitTime
	 * Modified By  :  
	 ***/
	public int getElementLoadWaitTime(){
		try{
			String waitTime = Common.getConfigProperty("ElementLoadWaitTime");
			int i = Integer.parseInt(waitTime);			
			if(i<1){
				return defaultElementLoadWaitTime;
			}else{
				return i;					
			}
		}catch(Exception ex){
			return defaultElementLoadWaitTime;
		}

	}


	/***
	 * Method to wait till the page contains expected text
	 * @param       : txt
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void waitForText(String txt)
	{
		waitForText(txt, GlobalKeys.textLoadWaitTime);
	}


	/***
	 * Method to wait till the page contains expected text
	 * @param       : txt,timeout
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void waitForText(String txt, int timeout){
		for (int second = 0; second < timeout; second++){
			if (second == timeout - 1) {
				Common.testStepFailed("The text '" + txt + "' is not found within " + GlobalKeys.textLoadWaitTime + " seconds timeout");
				break;
			}
			try{
				if (browser.getPageSource().contains(txt)) {
					Common.writeToLogFile("INFO", "Text: '" + txt + "' is present");
				}
			}
			catch (Exception localException){
				try {
					Thread.sleep(1000L);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}


	/***
	 * Method to close the browser with title provided
	 * @param       : windowTitle
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void closeChildBrowser(String windowTitle)
	{
		try {
			for (String winHandle : browser.getWindowHandles()){
				browser.switchTo().window(winHandle);
				if (browser.getTitle().equalsIgnoreCase(windowTitle)){
					browser.close();	          
					Common.testStepPassed("Closed the browser with page title->"+windowTitle);
					break;
				}
			}
		}catch (Exception e){	      
			Common.testStepFailed("Unable to Close Browser");
		}
	}
	/***
	 * Method to wait until element is invisible
	 * @param : 
	 * @return : void
	 * @author : suntechUser(userId) Modified By :
	 ***/
	protected Boolean waitUntilElementInvisible(String path) {
		try {
			new WebDriverWait(browser, GlobalKeys.elementLoadWaitTime).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(path)));  
			return true;
		} catch (RuntimeException ex) {
			return false;
		}    	
	}
	/***
	 * Method to Switch to Frame
	 * @param : 
	 * @return : void
	 * @author : suntechUser(userId) Modified By :
	 ***/
	public void selectFrame(WebElement we){
		try {
			UnSelectFrame();
			waitForElement(we);
			if (isElementPresent(we)){
				browser.switchTo().frame(we);
				Common.testStepPassed("Switched to Frame-"+ we );	
			}
			else{
				Common.testStepFailed(we+" frame not found");
			}			
		} 
		catch(Exception e)
		{
			Common.testStepFailed("Exception Caught, Message is->"+e.getMessage());
		}
	}
	/***
	 * Method to switch to default content
	 * @param       : 
	 * @return      : 
	 * Modified By  : 
	 ***/
	public void UnSelectFrame()
	{
		try {
			Common.writeToLogFile("Info", "Switching to default content frame ");
			browser.switchTo().defaultContent();
		}
		catch (Exception e)
		{
			Common.testStepFailed("Error in swiching to default content frame");
		}
	}


	/***
	 * Method to select the checkbox
	 * @param       : cbElement
	 * @return      : 
	 * Modified By  : 
	 ***/
	public void selectCheckBox(WebElement cbElement)
	{
		waitForElement(cbElement);
		if (isElementPresent(cbElement)) {
			try
			{
				if (!cbElement.isSelected())
				{
					cbElement.click();
				}
				Common.testStepPassed("Checked on the checkbox");
			}
			catch (Exception e)
			{
				Common.testStepFailed("Unable to check the checkbox->"+e.getMessage());
			}
		}
	}


	/***
	 * Method to UnSelect the checkbox
	 * @param       : cbElement
	 * @return      : 
	 * Modified By  : 
	 ***/
	public void unSelectCheckBox(WebElement cbElement)
	{
		waitForElement(cbElement);
		if (isElementPresent(cbElement)) {
			try
			{
				if (cbElement.isSelected())
				{
					cbElement.click();
				}
				Common.testStepPassed("Unchecked the checkbox");
			}
			catch (Exception e)
			{
				Common.testStepFailed("Unable to check the checkbox->"+e.getMessage());
			}
		}
	}


	/***
	 * Method to verify the checkbox if checked
	 * @param       : cbElement
	 * @return      : 
	 * Modified By  : 
	 ***/
	public boolean verifyCheckBoxIsChecked(WebElement cbElement)
	{
		waitForElement(cbElement);
		if (isElementPresent(cbElement)) {
			try{
				if (cbElement.isSelected()){
					return true;
				}else{
					return false;
				}

			}catch (Exception e){
				Common.testStepFailed("Unable to verify the checkbox->"+e.getMessage());
				return false;
			}
		}else{
			Common.testStepFailed("Unable to verify the checkbox");
			return false;
		}
	}


	public void hoverMenu(WebElement menu,String subMenuId)
	{
		try
		{
			Actions actions =new Actions(browser);
			actions.moveToElement(menu).perform();
			By locator=By.id(subMenuId);
			browser.findElement(locator).click();
			Common.testStepPassed("Clicked on Sub menu successfully");
		}
		catch(Exception ex)
		{
			Common.testStepFailed("Hover Failed!!");
		}
	}
	public boolean verifyPage(String pageTitle)
	{
		if(browser.getTitle().contains(pageTitle))
		{
			Common.testStepPassed("Successfully Navigated to "+pageTitle);
			return true;
		}
		else
		{
			Common.testStepFailed("Unexpected Navigation!! Expected Navigation to "+pageTitle);
			return false;
		}
	}

	public void scrollPageDown(By by)
	{
		try {
			WebElement we= browser.findElement(by);			 
			((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView(true);", we);
			sleep(2000); 
		} catch (Exception e) {
			Common.testStepFailed("Exception caught while scrolling Page down "+e.getMessage());
		}
	}

	public void scrollPageDown(WebElement we)
	{
		try {
			((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView(true);", we);
			sleep(2000); 
		} catch (Exception e) {
			Common.testStepFailed("Exception caught while scrolling Page down "+e.getMessage());
		}
	}

	/***
	 * Method to double click on a web element
	 * @param       : we,strElemName,strVisibleText
	 * @return      : 
	 * Modified By  :  
	 ***/
	public void doubleClick(WebElement we, String elem){
		try{
			Actions action = new Actions(browser);
			waitForElement(we);
			if (isElementPresent(we)){
				action.moveToElement(we).doubleClick().perform();
				Common.testStepPassed("Double Clicked on WebElement-"+ elem );	
			}else{
				Common.testStepFailed(elem+" Element is not displayed");
			}	
		} catch (Exception e) {
			Common.testStepFailed("Unable to douoble click - "+e.getMessage());
		}
	}

	/***
	 * Method to check for an alert for 5 seconds
	 * @param       : Element Name
	 * @return      : 
	 * Modified By  :  
	 ***/
	public boolean AlertPresent(){
		try{
			WebDriverWait wait = new WebDriverWait(browser, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		}catch(Exception e){			
			return false;
		}
	}

	/***
	 * Method to retrieve Sale number
	 * @param       : Element Name
	 * @return      : 
	 * Modified By  :  
	 ***/

	public String saleNumber;
	public String  getSaleNumber(String str){
		String s1 = str;
		String[] ref=s1.split("-");
		String s2 = ref[1].trim().substring(0, 6);
		//s1.substring(10,17);
		s2.trim();
		System.out.println("here:"+ s2); 
		return s2;
	}

	/***
	 * Method to Select value from dropdown by partial visible text
	 * @param       : we,strElemName,strVisibleText
	 * @return      : 
	 * Modified By  :  
	 ***/
	public boolean selectByPartialText(WebElement we,String strElemName,String strVisibleText){
		Select elSel = new Select(we);
		List<WebElement> opts = elSel.getOptions();
		for(WebElement elOpt:opts)
		{
			if(elOpt.getText().contains(strVisibleText))
			{
				elOpt.click();
				Common.testStepPassed("selected value -"+elOpt.getText() +" from dropdown->"+strElemName);
				return true;
			}
		}
		return false;
	}
	/***
	 * Method to retrieve Allowance number
	 * @param       : Element Name
	 * @return      : 
	 * Modified By  :  
	 ***/
	public static String allowanceNumber;
	public String  getAllowanceNumber(String str){
		String s1 = str;
		String s2 = s1.substring(6);
		s2.trim();
		System.out.println("here:"+ s2); 
		allowanceNumber=s2;
		return s2;
	}


	public void navigatetobackscreen(String Pagename)
	{
		try 
		{
			browser.navigate().back();
			Common.testStepPassed("Navigated to the back page"+Pagename);
		} catch (Exception e) {
			// TODO: handle exception
			Common.testStepFailed("Could not navigate to new page");
		}

	}

	public void navigatetoforwardscreen(String Pagename)
	{
		try 
		{
			browser.navigate().forward();
			Common.testStepPassed("Navigated to the forwared page"+Pagename);
		} catch (Exception e) {
			// TODO: handle exception
			Common.testStepFailed("Could not navigate to new forward page");
		}

	}

	public static String selectedDate;
	/***
	 * Method to select future delivery date
	 * @param : 
	 * @return : void
	 * @author : suntechUser(userId) Modified By :
	 * @Testcases: TC_10
	 ***/



	/***
	 * Method to get selected delivery date
	 * @param : 
	 * @return : void
	 * @author : suntechUser(userId) Modified By :
	 ***/

	public String getSelectedDate(String selDate){
		try{

			String[]sdate = selDate.split(",");
			int k=1,l=2;
			if(sdate.length<=2){
				k=0;
				l=1;
			}
			String s1=sdate[k].trim();
			String[] mmdd = s1.split(" ");
			String mm=monthToInt(mmdd[0]);
			String dd ;
			int idate = Integer.parseInt(mmdd[1]);
			if(idate<10){
				dd="0"+idate;
			}else{
				dd=mmdd[1];
			}
			String s2 = sdate[l].trim();
			String yy=s2.substring(2,4);
			selectedDate = mm+"/"+dd+"/"+yy; 
			Common.testStepInfo("Selected date is : "+selectedDate);
		}catch (Exception e) {

			Common.testStepFailed("Exception Caught, Message is->"+e.getMessage());
		}
		return selectedDate;
	}

	/***
	 * Method to converted month name to month value
	 * @param : 
	 * @return : void
	 * @author : suntechUser(userId) Modified By :
	 ***/

	public String monthToInt(String month){
		int i;
		String mm="";
		String months[] = {"January", "February", "March", "April",
				"May", "June", "July", "August", "September",
				"October", "November", "December"};

		for ( i = 0; i < 12; i++) {
			if (months[i].equalsIgnoreCase(month.trim())) {
				i=i+1;
				System.out.println("month is "+i) ; 
				break;
			}
		}
		if(i<10){
			mm="0"+i;
		}else
		{
			mm=Integer.toString(i);
		}
		return mm;
	}
	/**** Method to compare Two Strings
	 * @param : 
	 * @return : void
	 * @author : suntechUser(userId) 
	 * @Modified By :
	 ****/ 

	public boolean compareTwoStrings(String str1, String str2){
		try {
			if(str1.equalsIgnoreCase(str2)){
				return true;
			}else{
			}
		} catch (Exception e) {
			Common.testStepFailed("Error occurred while comparing Two Strings --> "+e.getMessage());
		}
		return false;
	}

	/**** Method to wait for element not present
	 * @param : 
	 * @return : void
	 * @author : suntechUser(userId) 
	 * @Modified By :
	 ****/ 
	public void waitForElementNotPresent(WebElement element)throws Exception{
		for (int second = 0;;second ++) {
			try {// try catch block is used handle 'Permission Denied Error' when waiting for element
				if (second>=120) {
					break;
					//Common.testStepPassed("Element was found after waiting for 2 Minute");
				}
				if (!isElementPresent(element)) {
					break;
				}
			} catch (Exception e) {

			}

		}
	}

	/**** Method to generate random string 
	 * @param : length of string to be generated
	 * @return : void
	 * @author : suntechUser(userId) 
	 * @Modified By :
	 ****/ 
	public  String generateRandomString(int len, String type){
		Random rng = new Random();
		String characters=null;
		if(type.equalsIgnoreCase("numeric")){
			characters="1237890456";
		}
		else if(type.equalsIgnoreCase("alpha")){
			characters="abcdefghijklmnoNOPQRSTUVWXYZpqrstuvwxyzABCDEFGHIJKLM";
		}
		else if(type.equalsIgnoreCase("alphanumeric")){
			characters="abc1238974560defghijklmno1238974560NOPQRSTUVWXYZpqrst1238974560uvwxyz1238974560ABCDEFGHIJ1238974560KLM";
		}
		char[] text = new char[len];
		for (int i = 0; i < len; i++)
		{
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}


	public WebElement webElement(String object) {

		String elementLocator = object;// "xpath>>//input[@name='username']";
		By byLocator = null;
		WebElement element = null;
		try {
			String locatorType = elementLocator.split(">>")[0];
			String locator = elementLocator.split(">>")[1];
			if (locatorType.toLowerCase().contains("xpath")) {
				byLocator = By.xpath(locator);
			} else if (locatorType.toLowerCase().contains("css")) {
				byLocator = By.cssSelector(locator);
			} else if (locatorType.toLowerCase().contains("id")) {
				byLocator = By.id(locator);
			} else if (locatorType.toLowerCase().contains("class")) {
				byLocator = By.className(locator);
			}
			element = browser.findElement(byLocator);
		} catch (Exception ex) {
			Common.testStepFailed("Uanble to locate element-" + ex);
		}
		return element;
	}

	public List<WebElement> webElements(String object) {

		String elementLocator = object;// "xpath>>//input[@name='username']";
		By byLocator = null;
		List<WebElement> elements = null;
		try {
			String locatorType = elementLocator.split(">>")[0];
			String locator = elementLocator.split(">>")[1];
			if (locatorType.toLowerCase().contains("xpath")) {
				byLocator = By.xpath(locator);
			} else if (locatorType.toLowerCase().contains("css")) {
				byLocator = By.cssSelector(locator);
			} else if (locatorType.toLowerCase().contains("id")) {
				byLocator = By.id(locator);
			} else if (locatorType.toLowerCase().contains("class")) {
				byLocator = By.className(locator);
			}
			elements = browser.findElements(byLocator);
		} catch (Exception ex) {
			Common.testStepFailed("Uanble to locate element-" + ex);
		}
		return elements;
	}

	public void waitForPageElementLoad(String object) {

		try {
			String elementLocator = object;// "xpath>>//input[@name='username']";
			By byLocator = null;
			String locatorType = elementLocator.split(">>")[0];
			String locator = elementLocator.split(">>")[1];
			if (locatorType.toLowerCase().contains("xpath")) {
				byLocator = By.xpath(locator);
			} else if (locatorType.toLowerCase().contains("css")) {
				byLocator = By.cssSelector(locator);
			} else if (locatorType.toLowerCase().contains("id")) {
				byLocator = By.id(locator);
			} else if (locatorType.toLowerCase().contains("class")) {
				byLocator = By.className(locator);
			}
			new WebDriverWait(browser, 30).until(ExpectedConditions.presenceOfElementLocated(byLocator));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void alertAccept() {
		try {
			WebDriverWait wait = new WebDriverWait(browser, GlobalKeys.elementLoadWaitTime);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = browser.switchTo().alert();
			alert.accept();
			Common.testStepPassed("Clicked on Alert Ok/Accept successfully");
		} catch (Exception ex) {
			Common.testStepFailed("Unable to Acdept Alert, Error Message is->" + ex.getMessage());
		}
	}

	public String getText(String object) {
		String elementLocator = object;// "xpath>>//input[@name='username']";
		String elementText = null;
		By byLocator = null;
		try {
			String locatorType = elementLocator.split(">>")[0];
			String locator = elementLocator.split(">>")[1];
			if (locatorType.toLowerCase().contains("xpath")) {
				byLocator = By.xpath(locator);
			} else if (locatorType.toLowerCase().contains("css")) {
				byLocator = By.cssSelector(locator);
			} else if (locatorType.toLowerCase().contains("id")) {
				byLocator = By.id(locator);
			} else if (locatorType.toLowerCase().contains("class")) {
				byLocator = By.className(locator);
			}
			elementText = browser.findElement(byLocator).getText().trim();

		} catch (Exception e) {
				Common.testStepFailed(e.toString());
		}
		return elementText;

	}
	
	
	
	public void clickElementWithEnterKey(WebElement we, String clickedElement) {
		try {
			waitForElement(we);
			if (isElementPresent(we)) {
				Actions actions = new Actions(browser);
				actions.moveToElement(we).build().perform();
				we.sendKeys(Keys.TAB);
				we.sendKeys(Keys.ENTER);
				Common.testStepPassed("Clicked on WebElement-" + clickedElement);
			} else {
				Common.testStepFailed(clickedElement + " Element is not displayed");
			}
		} catch (Exception e) {
			// Common.testStepFailed("Error Occurred While Clicking
			// "+clickedElement+" in clickElementWithEnterKey -->
			// "+e.getMessage());
		}
	}
	
	
	
}
