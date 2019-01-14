package Utilities;


import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import jxl.Sheet;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.relevantcodes.extentreports.LogStatus;




public class Common extends GlobalKeys
{
	public static String testName;
	public static int testCaseDataNo;

	public Common() {}

	public static int testCaseexecutionNo = 0;

	public  String getConfigProperty(String keyword) {
		Properties properties = new Properties();
		try
		{
			properties.load(new FileInputStream("./config/TestConfiguration.properties"));
		}
		catch (FileNotFoundException e)
		{
			writeToLogFile("ERROR", "File Not Found Exception thrown while getting value of " + keyword + " from Test Configuration file");
		}
		catch (IOException e) {
			writeToLogFile("ERROR", "IO Exception thrown while getting value of " + keyword + " from Test Configuration file");
		}
		writeToLogFile("INFO", "Getting value of " + keyword + " from Test Configuration file : " + properties.getProperty(keyword));

		return properties.getProperty(keyword);
	}
	
	public  String getReportPath(String keyword) {
		Properties properties = new Properties();
		try
		{
			properties.load(new FileInputStream("./config/TestConfiguration.properties"));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}		
		return properties.getProperty(keyword);
	}

	public  void setConfigProperty(String keyword,String value) {
		PropertiesConfiguration config;
		try {
			config = new PropertiesConfiguration("./config/TestConfiguration.properties");			
			config.setProperty(keyword, value);
			config.save();
		} catch (ConfigurationException e1) {

		}	

	}




	public void writeToLogFile(String type, String message)
	{
		String t = type.toUpperCase();
		if (t.equalsIgnoreCase("DEBUG"))
		{
			logger.debug(message);
		}
		else if (t.equalsIgnoreCase("INFO"))
		{
			logger.info(message);
		}
		else if (t.equalsIgnoreCase("WARN"))
		{
			logger.warn(message);
		}
		else if (t.equalsIgnoreCase("ERROR"))
		{
			logger.error(message);
		}
		else if (t.equalsIgnoreCase("FATAL"))
		{
			logger.fatal(message);
		}
		else if (t.equalsIgnoreCase("PASS"))
		{
			logger.info(message);
		}
		else {
			logger.error("Invalid log Type :" + type + ". Unable to log the message.");
		}
	}

	public  void startup()
	{

		try
		{	
			PropertiesFile PropertiesFile = new PropertiesFile();			
			PropertiesFile.createConfigFile();
			OutputAndLog OutputAndLog = new OutputAndLog();
			OutputAndLog.createOutputDirectory();
			PropertiesFile.properties();			
			loadTestCaseData();			
		}
		catch (Exception e)
		{
			writeToLogFile("INFO", "Execptio caught in the Startup activity..."+e.getMessage());
		}
	}


	public  void cleanup()
	{
		try
		{     	
			if (getConfigProperty("SendMail").trim().equalsIgnoreCase("Always"))
			{ 
				ZipReportFile ZipReportFile = new ZipReportFile();
				ZipReportFile.zipReport();            
				writeToLogFile("INFO", "<<<<<<<<<<<<< Sending mail...>>>>>>>>>>>>>>>>>");
				Mailing Mailing = new Mailing();
				Mailing.sendMail();
			}else if (getConfigProperty("SendMail").trim().equalsIgnoreCase("OnlyWhenFailed"))
			{ 
				if(testFailureCount>0){
					ZipReportFile ZipReportFile = new ZipReportFile();
					ZipReportFile.zipReport();            
					writeToLogFile("INFO", "<<<<<<<<<<<<<Sending mail...>>>>>>>>>>>>>>>>>");
					Mailing Mailing = new Mailing();
					Mailing.sendMail();
				}
			}					
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}


	public  void testReporter(String color, String report)
	{
		colorTypes ct = colorTypes.valueOf(color.toLowerCase());
		if (!color.contains("white"))
		{
			currentStep += 1;
		}

		switch (ct)
		{
		case green: 
			child.log(LogStatus.PASS,"<font color=green>" + currentStep + ". " + report + "</font><br/>");writeToLogFile("PASS", "Report step generation success : " + report);System.out.println("green" + currentStep); break;
		case blue:  child.log(LogStatus.INFO,"<font color=blue>" + currentStep + ". " + report + "</font><br/>");writeToLogFile("INFO", "Report step generation success : " + report);System.out.println("blue" + currentStep); break;
		case orange:  child.log(LogStatus.WARNING,"<font color=orange>" + currentStep + ". " + report + "</font><br/>");writeToLogFile("WARN", "Report step generation success : " + report); break;
		case red:  child.log(LogStatus.FAIL,"<font color=red>" + currentStep + ". " + report + "</font><br/>");writeToLogFile("ERROR", "Report step generation success : " + report);System.out.println("red" + currentStep); break;
		case white:  child.log(LogStatus.INFO,report);writeToLogFile("WARN", "Report step generation success : " + report);
		}

	}

	public  enum colorTypes
	{
		green, 
		red, 
		blue, 
		orange,  white;
	}

	

	public  void screenShot(String filename)
	{
		String scrPath = outputDirectory + "\\Screenshots";
		File file = new File(scrPath);
		file.mkdir();
		try {
			browser.manage().window().maximize();
			Robot robot = new Robot();
			Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage bufferedImage = robot.createScreenCapture(captureSize);
			File outputfile = new File(scrPath + "\\" + filename + ".png");
			ImageIO.write(bufferedImage, "png", outputfile);
			writeToLogFile("INFO", "Taken screenshot of failing screen");
		}
		catch (AWTException e) {
			writeToLogFile("ERROR", "AWT Exception : While taking screenshot of the failing test case");
		} catch (IOException e) {
			writeToLogFile("ERROR", "IO Exception : While taking screenshot of the failing test case");
		}
	}

	
	public  void captureScreenShot(String filename)
	{
		File scrFile = null;
		String scrPath = outputDirectory + "\\Screenshots";
		File file = new File(scrPath);
		file.mkdir();

		try
		{
			if(getConfigProperty("PlatFormName").contains("Windows")){
				scrFile = (File)((RemoteWebDriver) browser).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(scrPath + "\\" + filename + ".png"));
			}else{
				scrFile = (File)((RemoteWebDriver) browser).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(scrPath + "\\" + filename + ".png"));
			}
		}
		catch (Exception e)
		{
			testReporter("Red", e.toString()); return;

		}
		finally
		{

			if (scrFile == null) {
				System.out.println("This WebDriver does not support screenshots");
				return;
			}
		}
	}



	public  void testStepFailed(String errMessage)
	{
		testFailure = true;
		failureNo += 1;
		writeToLogFile("Error", errMessage);
		testReporter("Red", errMessage);
		try{
			if(browser.findElement(By.xpath("//div[@id='footer']/div[contains(text(),'qasales')]")).isDisplayed()){
				testReporter("blue", "The Build is-> "+browser.findElement(By.xpath("//div[@id='footer']/div")).getText());
			}
		}catch(Exception e){
		}
		try {
			if(browser.findElement(By.id("serverdisplay")).isDisplayed()){
				testReporter("blue", "The Build is-> "+browser.findElement(By.id("serverdisplay")).getText());  
			}
		} catch (Exception e) {
		}
		if (!windowreadyStateStatus)
		{
			screenShot("TestFailure" + failureNo);
			windowreadyStateStatus = true;
		}
		else
		{
			captureScreenShot("TestFailure" + failureNo);
		}	  
		String pathAndFile = "Screenshots\\TestFailure" + failureNo+ ".png";
		child.log(LogStatus.INFO, "Check ScreenShot Below: " + child.addScreenCapture(pathAndFile));	  
		if (getConfigProperty("ExecuteRemainingStepsOnFailure(Yes/No)").toUpperCase().contains("YES"))
		{

			testCaseExecutionStatus = true;
			elementLoadWaitTime = Integer.parseInt(getConfigProperty("OverideTimeoutOnFailure"));
			textLoadWaitTime = Integer.parseInt(getConfigProperty("OverideTimeoutOnFailure"));
			pageLoadWaitTime = Integer.parseInt(getConfigProperty("OverideTimeoutOnFailure"));
			implicitlyWaitTime = Integer.parseInt(getConfigProperty("OverideTimeoutOnFailure"));
		}
		else if (getConfigProperty("ExecuteRemainingStepsOnFailure(Yes/No)").toUpperCase().contains("NO"))
		{
			Assert.fail("Error Descripton: " + errMessage);
		}
		else
		{
			testReporter("Red", "Invalid option 'ExecuteRemainingStepsOnFailure(Yes/No)--'" + getConfigProperty("ExecuteRemainingStepsOnFailure(Yes/No)"));
			Assert.fail("Error Descripton: " + errMessage);
		}	  
		// Assert.fail("Error Descripton: " + errMessage); 
	}

	public  void testStepPassed(String errMessage)
	{
		writeToLogFile("Info", errMessage);
		testReporter("Green", errMessage);
	}

	public  void testStepInfo(String errMessage) {
		writeToLogFile("Info", errMessage);
		testReporter("Blue", errMessage);
	}

	public  void testStepInfoStart(String testDataSet) {
		String strCategory = "";
		try{			
			/*if(PropertiesFile.category.get(testCaseName).trim().equals("")){
				strCategory = "Regression";
			}else{
				strCategory = PropertiesFile.category.get(testCaseName).trim();
			}*/
		}catch(Exception ex){
			strCategory = "Regression";
		}
		child = extent.startTest(testDataSet);
		child.assignCategory(strCategory);		
		child.log(LogStatus.INFO, "########### Start of Test Case Data Set: "+testDataSet + " ###########");	    
	}


	public  void testStepInfoEnd(String testDataSet) {
		child.log(LogStatus.INFO, "########### End of Test Case Data Set: "+testDataSet + " ###########");
		parent
		.appendChild(child);
	}

	public void reportStart(String strName,String testCaseDescription) {
		try{
			boolean blnFound = false;
			testCaseExecutionStatus = false;				
			execEnv = getConfigProperty("ExecutionHost").toString().trim();
			host = getConfigProperty("IPAddressOrCloudUserName").toString().trim();
			port = getConfigProperty("PortOrCloudKey").toString().trim();
			elementLoadWaitTime = Integer.parseInt(getConfigProperty("ElementLoadWaitTime").toString().trim());
			textLoadWaitTime = Integer.parseInt(getConfigProperty("TextLoadWaitTime").toString().trim());
			pageLoadWaitTime = Integer.parseInt(getConfigProperty("PageLoadWaitTime").toString().trim());
			implicitlyWaitTime = Integer.parseInt(getConfigProperty("ImplicitlyWaitTime").toString().trim());
			writeToLogFile("INFO", "Element time out set");
			writeToLogFile("INFO", "##### Start of Test Case : " + testCaseDescription + " #####");
			for(int i=0;i<testCases.size();i++){
				for (String testCaseName : testCaseNames)
				{    	
					if (testCaseName.equals(((String)testCases.get(testCaseexecutionNo)).trim()))
					{
						writeToLogFile("INFO", "Test Case Name: " + testCaseName);
						updateTestDataSet(testCaseName);
						testCaseexecutionNo += 1;
						blnFound = true;
						break;
					}
					testCaseDataNo += 1;	      
				}
				if(blnFound){
					break;
				}else{
					testCaseexecutionNo += 1;
				}
			}
			parent = extent.startTest(strName,"<font size=4 color=black>" +testCaseDescription+ "</font><br/>");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}



	public void updateTestDataSet(String testCaseName)
	{
		useExcelSheet(getConfigProperty("TestDataFile"), 1);
		Sheet readsheet = DataDriver.w.getSheet(0);
		String testCaseDataSet = null;
		String executionFlag = null;
		Boolean flag = Boolean.valueOf(false);
		for (int caseRow = 0; caseRow < readsheet.getRows(); caseRow++) {
			testCaseDataSets.clear();
			if (testCaseName.equals(readsheet.getCell(1, caseRow).getContents()))
			{
				for (int DataRow = caseRow; DataRow < readsheet.getRows(); DataRow++)
				{
					testCaseRow = caseRow + 1;
					testCaseDataSet = readsheet.getCell(1, DataRow).getContents();
					writeToLogFile("INFO", "Test Data Set Name: " + testCaseDataSet);
					executionFlag = readsheet.getCell(2, DataRow).getContents();
					writeToLogFile("INFO", "Execution Flag: " + executionFlag);
					if ((testCaseDataSet.startsWith(testCaseName)) && (executionFlag.toUpperCase().equals("YES")))
					{
						testCaseDataSets.add(testCaseDataSet);
					} else if (testCaseDataSet.isEmpty())
					{
						flag = Boolean.valueOf(true);
						break;
					}
				}
				if (flag.booleanValue()) {
					break;
				}
			}
		}
	}


	public  void embedScreenshot(String colour, String pathAndFile)
	{   
		child.log(LogStatus.INFO, "Manual Verification Point: " + child.addScreenCapture(pathAndFile+ ".png"));    
	}


	public void takeScreenshot()
	{
		screenshotNo += 1;
		if (!windowreadyStateStatus){
			screenShot("Screenshot" + screenshotNo);
			windowreadyStateStatus = true;
		}else{
			captureScreenShot("Screenshot" + screenshotNo);
		}
		String strbrowser = getConfigProperty("Browser");
		if(strbrowser.contains("InternetExplorer")){
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		embedScreenshot("orange", "Screenshots" + "\\Screenshot" + screenshotNo);
	}





	public  void takeFullScreenshot(String comment)
	{
		screenshotNo += 1;
		if (!windowreadyStateStatus){
			screenShot("Screenshot" + screenshotNo);
			windowreadyStateStatus = true;
		}else{
			screenShotFull("Screenshot" + screenshotNo);			
		}			
		embedScreenshot("orange", "Screenshots" + "\\Screenshot" + screenshotNo);
	}


	public  void useExcelSheet(String pathOfExcel, int sheetNumber)
	{
		DataDriver DataDriver = new DataDriver();
		DataDriver.useExcelSheet(pathOfExcel, sheetNumber);
	}


	public void loadTestCaseData() {
		useExcelSheet(getConfigProperty("TestDataFile"), 1);

		Sheet readsheet = DataDriver.w.getSheet(0);
		for (int i = 0; i < readsheet.getRows(); i++) {
			String testCaseName = readsheet.getCell(1, i).getContents();
			testCaseNames.add(testCaseName);
		}
	}


	public String retrieveExceptionMessage(Integer exceptionNumber)
	{
		String exceptionMessage = null;
		String exceptionNo = exceptionNumber.toString();
		Sheet readsheet = DataDriver.w.getSheet(1);
		for (int i = 0; i < readsheet.getRows(); i++) {
			String testCaseName = readsheet.getCell(0, i).getContents();
			if (testCaseName.equals(exceptionNo))
			{
				exceptionMessage = readsheet.getCell(4, i).getContents();
				writeToLogFile("INFO", "Exception Message: " + exceptionMessage);
				break;
			}
		}
		return exceptionMessage;
	}

	public void screenShotFull(String filename) {		
		File scrFile = null;
		String scrPath = outputDirectory + "\\Screenshots";
		File file = new File(scrPath);
		file.mkdir();
		try {
			if(getConfigProperty("PlatFormName").contains("Windows")){
			
				Shutterbug.shootPage(browser,ScrollStrategy.BOTH_DIRECTIONS)	                       
				.withName(filename)
				.save(scrPath);
				((JavascriptExecutor) browser).executeScript("scroll(0,0)");
			}else{
				Shutterbug.shootPage(browser,ScrollStrategy.BOTH_DIRECTIONS)	                       
				.withName(filename)
				.save(scrPath);
				((JavascriptExecutor) browser).executeScript("scroll(0,0)");
			}

		} catch (Exception e) {
			testReporter("Red", e.toString());
			return;
		} finally {
			((JavascriptExecutor) browser).executeScript("scroll(0,0)");
		}

	}
	
	public String getPageProperty(String fileName,String keyword) {
    	Properties properties = new Properties();
    	try {
    		properties.load(new FileInputStream(".\\src\\main\\java\\ObjectRepository\\"+fileName+".properties"));
    	}
    	catch (FileNotFoundException e) {
    		writeToLogFile("ERROR", "File Not Found Exception thrown while getting value of " + keyword + " from "+fileName+" properties file");
    	}
    	catch (IOException e) {
    		writeToLogFile("ERROR", "IO Exception thrown while getting value of " + keyword + " from "+fileName+" properties file");
    	}
    	writeToLogFile("INFO", "Getting value of " + keyword + " from "+fileName+" properties file : " + properties.getProperty(keyword));
    	return properties.getProperty(keyword);
    }



}
