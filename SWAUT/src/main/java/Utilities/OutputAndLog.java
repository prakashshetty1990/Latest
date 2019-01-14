package Utilities;


import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

public class OutputAndLog
{
  public static String am_pm;
  public static String min;
  public static String hr;
  public static String sec;
  public static int yr;
  public static String mon;
  public static String day;
  
  public OutputAndLog() {}
  
  public static void createOutputDirectory()
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
    GlobalKeys.timeStamp = yr + "_" + mon + "_" + day + "_" + hr + "_" + min + "_" + sec + "_" + am_pm;    
    try
    {
    	GlobalKeys.strProjectPath = curdir.getCanonicalPath();
    	if(Common.getReportPath("ReportPath").trim().equals("")){
    		GlobalKeys.outputDirectory = curdir.getCanonicalPath() + "\\TestResults\\" + yr + "_" + mon + "_" + day + "_" + hr + "_" + min + "_" + sec + "_" + am_pm;
    	}else{
    		String basepath = null;
    		if(!(Common.getReportPath("ReportPath").trim().endsWith("\\")||Common.getReportPath("ReportPath").trim().endsWith("/"))){
    			basepath = Common.getReportPath("ReportPath").trim() + "\\";
    		}
    		GlobalKeys.outputDirectory = basepath + yr + "_" + mon + "_" + day + "_" + hr + "_" + min + "_" + sec + "_" + am_pm;
    	} 
      GlobalKeys.tempOutputDirectory = GlobalKeys.outputDirectory;

    }
    catch (IOException e)
    {
      System.out.println("IO Error while creating Output Directory : " + GlobalKeys.outputDirectory);
    }
    
    createLogFile();
  }
  

  public static void createLogFile()
  {
    Properties props = new Properties();
    String propsFileName = GlobalKeys.configPath+"config/log4j.properties";
    
    try
    {
      java.io.FileInputStream configStream = new java.io.FileInputStream(propsFileName);
      props.load(configStream);
      


      String myStr = GlobalKeys.outputDirectory.substring(GlobalKeys.outputDirectory.length() - 22);
      
      //myStr = "./TestResults/" + myStr + "/LogFile.log";
      myStr = GlobalKeys.outputDirectory+"/LogFile.log";
      
      
      props.setProperty("log4j.appender.FA.File", myStr);
      java.io.FileOutputStream output = new java.io.FileOutputStream(propsFileName);
      props.store(output, "Output Directory updated : " + GlobalKeys.outputDirectory);
      

      output.close();
      configStream.close();
      

      org.apache.log4j.PropertyConfigurator.configure(propsFileName);
      
      GlobalKeys.logger = org.apache.log4j.Logger.getLogger(myStr);
      Common.writeToLogFile("INFO", "Startup activites...");
      
      Common.writeToLogFile("INFO", "Test Output Directory creation successful :" + GlobalKeys.outputDirectory);
      Common.writeToLogFile("INFO", "Log File creation successful : LogFile.log");
    }
    catch (IOException ex)
    {
      System.out.println("There was an error creating the log file");
    }
  }
}
