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




public class Common
{
	public static String testName;
	public static int testCaseDataNo;

	public Common() {}

	public static int testCaseexecutionNo = 0;

	public static String getConfigProperty(String keyword) {
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
	
	public static String getReportPath(String keyword) {
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

	public static void setConfigProperty(String keyword,String value) {
		PropertiesConfiguration config;
		try {
			config = new PropertiesConfiguration("./config/TestConfiguration.properties");			
			config.setProperty(keyword, value);
			config.save();
		} catch (ConfigurationException e1) {

		}	

	}




	public static void writeToLogFile(String type, String message)
	{
		String t = type.toUpperCase();
		if (t.equalsIgnoreCase("DEBUG"))
		{
			GlobalKeys.logger.debug(message);
		}
		else if (t.equalsIgnoreCase("INFO"))
		{
			GlobalKeys.logger.info(message);
		}
		else if (t.equalsIgnoreCase("WARN"))
		{
			GlobalKeys.logger.warn(message);
		}
		else if (t.equalsIgnoreCase("ERROR"))
		{
			GlobalKeys.logger.error(message);
		}
		else if (t.equalsIgnoreCase("FATAL"))
		{
			GlobalKeys.logger.fatal(message);
		}
		else if (t.equalsIgnoreCase("PASS"))
		{
			GlobalKeys.logger.info(message);
		}
		else {
			GlobalKeys.logger.error("Invalid log Type :" + type + ". Unable to log the message.");
		}
	}

	public static void startup()
	{

		try
		{	PropertiesFile.createConfigFile();
			OutputAndLog.createOutputDirectory();
			PropertiesFile.properties();			
			loadTestCaseData();			
		}
		catch (Exception e)
		{
			writeToLogFile("INFO", "Execptio caught in the Startup activity..."+e.getMessage());
		}
	}


	public static void cleanup()
	{
		try
		{     	
			if (Common.getConfigProperty("SendMail").trim().equalsIgnoreCase("Always"))
			{ 
				ZipReportFile.zipReport();            
				writeToLogFile("INFO", "<<<<<<<<<<<<< Sending mail...>>>>>>>>>>>>>>>>>");
				Mailing.sendMail();
			}else if (Common.getConfigProperty("SendMail").trim().equalsIgnoreCase("OnlyWhenFailed"))
			{ 
				if(GlobalKeys.testFailureCount>0){
					ZipReportFile.zipReport();            
					writeToLogFile("INFO", "<<<<<<<<<<<<<Sending mail...>>>>>>>>>>>>>>>>>");
					Mailing.sendMail();
				}
			}
			if (Common.getConfigProperty("SendMsg(Yes/No)").trim().equalsIgnoreCase("yes"))
			{
				Texting.sendMsg();
			}			
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}


	public static void testReporter(String color, String report)
	{
		colorTypes ct = colorTypes.valueOf(color.toLowerCase());
		if (!color.contains("white"))
		{
			GlobalKeys.currentStep += 1;
		}

		switch (ct)
		{
		case green: 
			GlobalKeys.child.log(LogStatus.PASS,"<font color=green>" + GlobalKeys.currentStep + ". " + report + "</font><br/>");writeToLogFile("PASS", "Report step generation success : " + report);System.out.println("green" + GlobalKeys.currentStep); break;
		case blue:  GlobalKeys.child.log(LogStatus.INFO,"<font color=blue>" + GlobalKeys.currentStep + ". " + report + "</font><br/>");writeToLogFile("INFO", "Report step generation success : " + report);System.out.println("blue" + GlobalKeys.currentStep); break;
		case orange:  GlobalKeys.child.log(LogStatus.WARNING,"<font color=orange>" + GlobalKeys.currentStep + ". " + report + "</font><br/>");writeToLogFile("WARN", "Report step generation success : " + report); break;
		case red:  GlobalKeys.child.log(LogStatus.FAIL,"<font color=red>" + GlobalKeys.currentStep + ". " + report + "</font><br/>");writeToLogFile("ERROR", "Report step generation success : " + report);System.out.println("red" + GlobalKeys.currentStep); break;
		case white:  GlobalKeys.child.log(LogStatus.INFO,report);writeToLogFile("WARN", "Report step generation success : " + report);
		}

	}

	public static enum colorTypes
	{
		green, 
		red, 
		blue, 
		orange,  white;
	}

	

	public static void screenShot(String filename)
	{
		String scrPath = GlobalKeys.outputDirectory + "\\Screenshots";
		File file = new File(scrPath);
		file.mkdir();
		try {
			GlobalKeys.driver.manage().window().maximize();
			Robot robot = new Robot();
			Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage bufferedImage = robot.createScreenCapture(captureSize);
			File outputfile = new File(scrPath + "\\" + filename + ".png");
			ImageIO.write(bufferedImage, "png", outputfile);
			Common.writeToLogFile("INFO", "Taken screenshot of failing screen");
		}
		catch (AWTException e) {
			Common.writeToLogFile("ERROR", "AWT Exception : While taking screenshot of the failing test case");
		} catch (IOException e) {
			Common.writeToLogFile("ERROR", "IO Exception : While taking screenshot of the failing test case");
		}
	}

	
	public static void captureScreenShot(String filename)
	{
		File scrFile = null;
		String scrPath = GlobalKeys.outputDirectory + "\\Screenshots";
		File file = new File(scrPath);
		file.mkdir();

		try
		{
			if(Common.getConfigProperty("PlatFormName").contains("Windows")){
				scrFile = (File)((RemoteWebDriver) GlobalKeys.driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(scrPath + "\\" + filename + ".png"));
			}else{
				scrFile = (File)((RemoteWebDriver) GlobalKeys.mdriver).getScreenshotAs(OutputType.FILE);
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



	public static void testStepFailed(String errMessage)
	{
		GlobalKeys.testFailure = true;
		GlobalKeys.failureNo += 1;
		writeToLogFile("Error", errMessage);
		testReporter("Red", errMessage);
		try{
			if(GlobalKeys.driver.findElement(By.xpath("//div[@id='footer']/div[contains(text(),'qasales')]")).isDisplayed()){
				testReporter("blue", "The Build is-> "+GlobalKeys.driver.findElement(By.xpath("//div[@id='footer']/div")).getText());
			}
		}catch(Exception e){
		}
		try {
			if(GlobalKeys.driver.findElement(By.id("serverdisplay")).isDisplayed()){
				testReporter("blue", "The Build is-> "+GlobalKeys.driver.findElement(By.id("serverdisplay")).getText());  
			}
		} catch (Exception e) {
		}
		if (!GlobalKeys.windowreadyStateStatus)
		{
			screenShot("TestFailure" + GlobalKeys.failureNo);
			GlobalKeys.windowreadyStateStatus = true;
		}
		else
		{
			captureScreenShot("TestFailure" + GlobalKeys.failureNo);
		}	  
		String pathAndFile = "Screenshots\\TestFailure" + GlobalKeys.failureNo+ ".png";
		GlobalKeys.child.log(LogStatus.INFO, "Check ScreenShot Below: " + GlobalKeys.child.addScreenCapture(pathAndFile));	  
		if (getConfigProperty("ExecuteRemainingStepsOnFailure(Yes/No)").toUpperCase().contains("YES"))
		{

			GlobalKeys.testCaseExecutionStatus = true;
			GlobalKeys.elementLoadWaitTime = Integer.parseInt(getConfigProperty("OverideTimeoutOnFailure"));
			GlobalKeys.textLoadWaitTime = Integer.parseInt(getConfigProperty("OverideTimeoutOnFailure"));
			GlobalKeys.pageLoadWaitTime = Integer.parseInt(getConfigProperty("OverideTimeoutOnFailure"));
			GlobalKeys.implicitlyWaitTime = Integer.parseInt(getConfigProperty("OverideTimeoutOnFailure"));
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

	public static void testStepPassed(String errMessage)
	{
		writeToLogFile("Info", errMessage);
		testReporter("Green", errMessage);
	}

	public static void testStepInfo(String errMessage) {
		writeToLogFile("Info", errMessage);
		testReporter("Blue", errMessage);
	}

	public static void testStepInfoStart(String testDataSet) {
		String strCategory = "";
		try{			
			if(PropertiesFile.category.get(GlobalKeys.testCaseName).trim().equals("")){
				strCategory = "Regression";
			}else{
				strCategory = PropertiesFile.category.get(GlobalKeys.testCaseName).trim();
			}
		}catch(Exception ex){
			strCategory = "Regression";
		}
		GlobalKeys.child = GlobalKeys.extent.startTest(testDataSet);
		GlobalKeys.child.assignCategory(strCategory);		
		GlobalKeys.child.log(LogStatus.INFO, "########### Start of Test Case Data Set: "+testDataSet + " ###########");	    
	}


	public static void testStepInfoEnd(String testDataSet) {
		GlobalKeys.child.log(LogStatus.INFO, "########### End of Test Case Data Set: "+testDataSet + " ###########");
		GlobalKeys.parent
		.appendChild(GlobalKeys.child);
	}

	public static void reportStart(String strName,String testCaseDescription) {
		try{
			boolean blnFound = false;
			GlobalKeys.testCaseExecutionStatus = false;			
			GlobalKeys.execEnv = getConfigProperty("ExecutionHost").toString().trim();
			GlobalKeys.host = getConfigProperty("IPAddressOrCloudUserName").toString().trim();
			GlobalKeys.port = getConfigProperty("PortOrCloudKey").toString().trim();
			GlobalKeys.elementLoadWaitTime = Integer.parseInt(getConfigProperty("ElementLoadWaitTime").toString().trim());
			GlobalKeys.textLoadWaitTime = Integer.parseInt(getConfigProperty("TextLoadWaitTime").toString().trim());
			GlobalKeys.pageLoadWaitTime = Integer.parseInt(getConfigProperty("PageLoadWaitTime").toString().trim());
			GlobalKeys.implicitlyWaitTime = Integer.parseInt(getConfigProperty("ImplicitlyWaitTime").toString().trim());
			writeToLogFile("INFO", "Element time out set");
			Common.writeToLogFile("INFO", "##### Start of Test Case : " + testCaseDescription + " #####");
			for(int i=0;i<PropertiesFile.testCases.size();i++){
				for (String testCaseName : GlobalKeys.testCaseNames)
				{    	
					if (testCaseName.equals(((String)PropertiesFile.testCases.get(testCaseexecutionNo)).trim()))
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
			GlobalKeys.parent = GlobalKeys.extent.startTest(strName,"<font size=4 color=black>" +testCaseDescription+ "</font><br/>");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}



	public static void updateTestDataSet(String testCaseName)
	{
		useExcelSheet(getConfigProperty("TestDataFile"), 1);
		Sheet readsheet = DataDriver.w.getSheet(0);
		String testCaseDataSet = null;
		String executionFlag = null;
		Boolean flag = Boolean.valueOf(false);
		for (int caseRow = 0; caseRow < readsheet.getRows(); caseRow++) {
			GlobalKeys.testCaseDataSets.clear();
			if (testCaseName.equals(readsheet.getCell(1, caseRow).getContents()))
			{
				for (int DataRow = caseRow; DataRow < readsheet.getRows(); DataRow++)
				{
					GlobalKeys.testCaseRow = caseRow + 1;
					testCaseDataSet = readsheet.getCell(1, DataRow).getContents();
					writeToLogFile("INFO", "Test Data Set Name: " + testCaseDataSet);
					executionFlag = readsheet.getCell(2, DataRow).getContents();
					writeToLogFile("INFO", "Execution Flag: " + executionFlag);
					if ((testCaseDataSet.startsWith(testCaseName)) && (executionFlag.toUpperCase().equals("YES")))
					{
						GlobalKeys.testCaseDataSets.add(testCaseDataSet);
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


	public static void embedScreenshot(String colour, String pathAndFile)
	{   
		GlobalKeys.child.log(LogStatus.INFO, "Manual Verification Point: " + GlobalKeys.child.addScreenCapture(pathAndFile+ ".png"));    
	}


	public static void takeScreenshot()
	{
		GlobalKeys.screenshotNo += 1;
		if (!GlobalKeys.windowreadyStateStatus){
			screenShot("Screenshot" + GlobalKeys.screenshotNo);
			GlobalKeys.windowreadyStateStatus = true;
		}else{
			captureScreenShot("Screenshot" + GlobalKeys.screenshotNo);
		}
		String strbrowser = Common.getConfigProperty("Browser");
		if(strbrowser.contains("InternetExplorer")){
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		embedScreenshot("orange", "Screenshots" + "\\Screenshot" + GlobalKeys.screenshotNo);
	}





	public static void takeFullScreenshot(String comment)
	{
		GlobalKeys.screenshotNo += 1;
		if (!GlobalKeys.windowreadyStateStatus){
			screenShot("Screenshot" + GlobalKeys.screenshotNo);
			GlobalKeys.windowreadyStateStatus = true;
		}else{
			screenShotFull("Screenshot" + GlobalKeys.screenshotNo);			
		}			
		embedScreenshot("orange", "Screenshots" + "\\Screenshot" + GlobalKeys.screenshotNo);
	}


	public static void useExcelSheet(String pathOfExcel, int sheetNumber)
	{
		DataDriver.useExcelSheet(pathOfExcel, sheetNumber);
	}



	public static void closeExcelSheet() {}


	public static String retrieve(String Label)
	{
		return DataDriver.retrieve(GlobalKeys.testCaseRow, GlobalKeys.testCaseDataRow, Label);
	}

	public static int returnRowNumber(String Label)
	{
		return DataDriver.returnRowNo(2, Label);
	}

	public static void loadTestCaseData() {
		useExcelSheet(getConfigProperty("TestDataFile"), 1);

		Sheet readsheet = DataDriver.w.getSheet(0);
		for (int i = 0; i < readsheet.getRows(); i++) {
			String testCaseName = readsheet.getCell(1, i).getContents();
			GlobalKeys.testCaseNames.add(testCaseName);
		}
	}


	public static String retrieveExceptionMessage(Integer exceptionNumber)
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

	public static void screenShotFull(String filename) {		
		File scrFile = null;
		String scrPath = GlobalKeys.outputDirectory + "\\Screenshots";
		File file = new File(scrPath);
		file.mkdir();
		try {
			if(Common.getConfigProperty("PlatFormName").contains("Windows")){
			
				Shutterbug.shootPage(GlobalKeys.driver,ScrollStrategy.BOTH_DIRECTIONS)	                       
				.withName(filename)
				.save(scrPath);
				((JavascriptExecutor) GlobalKeys.driver).executeScript("scroll(0,0)");
			}else{
				Shutterbug.shootPage(GlobalKeys.mdriver,ScrollStrategy.BOTH_DIRECTIONS)	                       
				.withName(filename)
				.save(scrPath);
				((JavascriptExecutor) GlobalKeys.mdriver).executeScript("scroll(0,0)");
			}

		} catch (Exception e) {
			testReporter("Red", e.toString());
			return;
		} finally {
			((JavascriptExecutor) GlobalKeys.mdriver).executeScript("scroll(0,0)");
		}

	}
	
	public static String getPageProperty(String fileName,String keyword) {
    	Properties properties = new Properties();
    	try {
    		properties.load(new FileInputStream(".\\src\\main\\java\\ObjectRepository\\"+fileName+".properties"));
    	}
    	catch (FileNotFoundException e) {
    		Common.writeToLogFile("ERROR", "File Not Found Exception thrown while getting value of " + keyword + " from "+fileName+" properties file");
    	}
    	catch (IOException e) {
    		Common.writeToLogFile("ERROR", "IO Exception thrown while getting value of " + keyword + " from "+fileName+" properties file");
    	}
    	Common.writeToLogFile("INFO", "Getting value of " + keyword + " from "+fileName+" properties file : " + properties.getProperty(keyword));
    	return properties.getProperty(keyword);
    }



}
