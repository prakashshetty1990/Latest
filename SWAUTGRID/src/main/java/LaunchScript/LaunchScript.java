package LaunchScript;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.annotations.Test;

import Utilities.Common;
import Utilities.GlobalKeys;

import com.relevantcodes.extentreports.ExtentReports;

public class LaunchScript extends Common {

	
	@Test
	public void Launch(){
	//public static void main(String []args){
	
		try {			
			startup();			
			TestNG testng = new TestNG();
			List<String> suites = new ArrayList<String>();
			suites.add("./config/testng.xml");			
			testng.setTestSuites(suites);			
			extent = new ExtentReports(outputDirectory+"/Results.html", true);
			extent.loadConfig(new File("./config/extent-config.xml"));
			testng.run();		

		} catch (Exception e) {
			writeToLogFile("error", e.toString());
		} finally {
			try {								
				cleanup();
				//createZipFileOfReport(GenericKeywords.outputDirectory);				

			} catch (Exception e) {
				writeToLogFile("error", e.toString());
			} finally {
				writeToLogFile("INFO", "###################################");
				writeToLogFile("INFO", "Script Execution Complete");
				writeToLogFile("INFO", "####################################");

			}

		}
	}


}
