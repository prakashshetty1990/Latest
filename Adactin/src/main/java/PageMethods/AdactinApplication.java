package PageMethods;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;

import apppackage.Page;
import Utilities.Common;
import Utilities.GlobalKeys;



public class AdactinApplication {

	private static WebDriver browser;
	private String url;			
	private static final String TASKLIST = "tasklist";
	private static final String KILL = "taskkill /IM ";

	public AdactinApplication() {	
		String strbrowser = Common.getConfigProperty("Browser");
		browser=Page.getDriver(strbrowser);
		this.browser = browser;
		this.url = Common.getConfigProperty("URL");
		GlobalKeys.driver = browser;

	}

	public AdactinHomePage openAdactinApplication() {
		try {
			browser.get(url);
			browser.manage().window().maximize();		
		} catch (Exception e) {			
			System.out.println("Problem Opening the URL"+e.getMessage());
		}
		return new  AdactinHomePage(browser);

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

	private void deleteAllCookies() {
		try{
			browser.manage().deleteAllCookies();
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}


	
	/**
	 * Get relative path of the framework
	 * @return
	 */
	public static String getRelativePath() {
		String relativePath = new File(System.getProperty("user.dir"))
		.getAbsolutePath();
		if (relativePath.endsWith("bin")) {
			relativePath = new File(System.getProperty("user.dir")).getParent();
		}
		return relativePath;
	}

	
	
	public void close() throws Exception {
		try{
			deleteAllCookies();
			browser.quit();
			if(GlobalKeys.testFailure){
				Assert.fail("Testcasefailed");
			}
		}catch(Exception Ex){
			System.out.println("Unable to Close Application");
		}		
	}

	public static boolean isProcessRunging(String serviceName) throws Exception {

		Process p = Runtime.getRuntime().exec(TASKLIST);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains(serviceName)) {
				return true;
			}
		}
		return false;
	}

	
	public static void killProcess(String serviceName) throws Exception {
		Runtime.getRuntime().exec(KILL + serviceName);
	}
	
	
	//*********************************ReUsable Methods***************************************************//
	
	
	
	
	
}
