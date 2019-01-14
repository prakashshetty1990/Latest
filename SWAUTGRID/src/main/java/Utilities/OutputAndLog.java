package Utilities;


import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

public class OutputAndLog extends Common
{
  public static String am_pm;
  public static String min;
  public static String hr;
  public static String sec;
  public static int yr;
  public static String mon;
  public static String day;
  
  public OutputAndLog() {}
  
  public void createOutputDirectory()
  {
    java.io.File curdir = new java.io.File(".");
    
    Calendar calendar = new java.util.GregorianCalendar();
    

    hr = "0" + calendar.get(10);
    hr = hr.substring(hr.length() - 2);
    
    min = "0" + calendar.get(12);
    min = min.substring(min.length() - 2);
    

    sec = "0" + calendar.get(13);
    sec = sec.substring(sec.length() - 2);
    
    yr = calendar.get(1);
    

    mon = "0" + (calendar.get(2) + 1);
    mon = mon.substring(mon.length() - 2);
    
    day = "0" + calendar.get(5);
    day = day.substring(day.length() - 2);
    
    if (calendar.get(9) == 0) {
      am_pm = "AM";
    } else {
      am_pm = "PM";
    }    
    timeStamp = yr + "_" + mon + "_" + day + "_" + hr + "_" + min + "_" + sec + "_" + am_pm;    
    try
    {
    	strProjectPath = curdir.getCanonicalPath();
    	if(getReportPath("ReportPath").trim().equals("")){
    		outputDirectory = curdir.getCanonicalPath() + "\\TestResults\\" + yr + "_" + mon + "_" + day + "_" + hr + "_" + min + "_" + sec + "_" + am_pm;
    	}else{
    		String basepath = null;
    		if(!(getReportPath("ReportPath").trim().endsWith("\\")||getReportPath("ReportPath").trim().endsWith("/"))){
    			basepath = getReportPath("ReportPath").trim() + "\\";
    		}
    		outputDirectory = basepath + yr + "_" + mon + "_" + day + "_" + hr + "_" + min + "_" + sec + "_" + am_pm;
    	} 
      tempOutputDirectory = outputDirectory;

    }
    catch (IOException e)
    {
      System.out.println("IO Error while creating Output Directory : " + outputDirectory);
    }
    
    createLogFile();
  }
  

  public void createLogFile()
  {
    Properties props = new Properties();
    String propsFileName = configPath+"config/log4j.properties";
    
    try
    {
      java.io.FileInputStream configStream = new java.io.FileInputStream(propsFileName);
      props.load(configStream);
      


      String myStr = outputDirectory.substring(outputDirectory.length() - 22);
      
      //myStr = "./TestResults/" + myStr + "/LogFile.log";
      myStr = outputDirectory+"/LogFile.log";
      
      
      props.setProperty("log4j.appender.FA.File", myStr);
      java.io.FileOutputStream output = new java.io.FileOutputStream(propsFileName);
      props.store(output, "Output Directory updated : " + outputDirectory);
      

      output.close();
      configStream.close();
      

      org.apache.log4j.PropertyConfigurator.configure(propsFileName);
      
      logger = org.apache.log4j.Logger.getLogger(myStr);
      writeToLogFile("INFO", "Startup activites...");
      
      writeToLogFile("INFO", "Test Output Directory creation successful :" + outputDirectory);
      writeToLogFile("INFO", "Log File creation successful : LogFile.log");
    }
    catch (IOException ex)
    {
      System.out.println("There was an error creating the log file");
    }
  }
}
