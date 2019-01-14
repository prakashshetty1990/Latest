package page.adactin;

import java.net.URL;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;

import apppackage.MobilePage;
import Utilities.Common;
import Utilities.GlobalKeys;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Application {
	public static int count1 = 1, count2 = 1;
	private AppiumDriver browser;
	private String url;	

	public Application() {
		String strbrowser = Common.getConfigProperty("Browser");
		this.browser = MobilePage.getAppiumDriver(strbrowser);		
		this.url = Common.getConfigProperty("URL");		
		GlobalKeys.mdriver = browser;			
	}

	public LoginPage openApplication() {
		try {
			browser.get(url);
			//browser.manage().window().maximize();		
		} catch (Exception e) {			
			System.out.println("Problem Opening the URL"+e.getMessage());
		}		
		return new LoginPage(browser);
	}

	
	public void closeApp() {
		try {
			browser.quit();
		} catch (Exception e) {
			Common.testStepFailed(e.toString());
		}
	}
}
