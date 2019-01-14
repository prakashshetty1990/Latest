package Utilities;


import io.appium.java_client.AppiumDriver;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class GlobalKeys
{
  public  RemoteWebDriver browser = null;
  //public  AppiumDriver mdriver= null;
  public  ExtentReports extent= null;
  public  ExtentTest parent= null;
  public  ExtentTest child= null;
  public  String outputDirectory= null;
  public  String tempOutputDirectory= null;
  public  String currentExcelBook= null;
  public  Logger logger= null;
  public  int currentTestCaseNumber= 0;
  public  int currentExcelSheet= 0;
  public  int currentStep= 0;
  public  int failureNo=0;
  public  int screenshotNo=0; public  int rowCount=0; public  int colCount=0;  public  boolean testFailure = false;
  public  boolean loadFailure = false;
  public  int temp = 1;
  public  String testStatus = "";
  public  int testCaseDataRow=0; 
  public  int textLoadWaitTime=0; public  int elementLoadWaitTime=0; public  int implicitlyWaitTime=0; public  int pageLoadWaitTime = 0;
  public  int testCaseRow=0;
  final public  ArrayList<String> testCaseNames = new ArrayList<String>();
  public  ArrayList<String> testCaseDataSets = new ArrayList<String>();
  public  boolean windowreadyStateStatus = true;
  public  int testSuccessCount = 0;
  public  int testFailureCount = 0;
  public  int testSkippedCount = 0;
  public  String timeStamp = "";
  public  boolean testCaseExecutionStatus = false;
  public  String testCaseName= null;;
  public  String strProjectPath= null;;
  public  String configPath = "C:/Apps/";
  public  String host= null;;
  public  String port= null;;
  public  String execEnv= null;;
  public ArrayList<String> testCases = new ArrayList();
	public HashMap<String, String> category = new HashMap<String, String>();
	public String categoryvalue;

}
