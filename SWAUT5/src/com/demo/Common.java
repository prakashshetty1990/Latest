package com.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Common {



	public String getProperty(String key){
		String keyValue = null;
		try {
			String path = this.getClass().getClassLoader().getResource("").getPath();
			String fullPath = URLDecoder.decode(path, "UTF-8");
			//String basePath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
			FileInputStream file = new FileInputStream(fullPath+"com/property/configuration.properties");
			Properties prop = new Properties(System.getProperties());
			prop.load(file);
			keyValue = prop.getProperty(key);		

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return keyValue;
	}

	
	public void setProperty(String File,String Key, String value){
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(File);
			config.setProperty(Key, value);		
			config.save();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String getProjectPath() {
		String reponsePath = null;
		try{
			String path = this.getClass().getClassLoader().getResource("").getPath();
			String fullPath = URLDecoder.decode(path, "UTF-8");
			String pathArr[] = fullPath.split("/WEB-INF/classes/");
			System.out.println(fullPath);
			System.out.println(pathArr[0]);
			fullPath = pathArr[0];		
			reponsePath = new File(fullPath).getPath();		
			System.out.println(reponsePath);
			return reponsePath;
		}catch(Exception ex){

		}
		return reponsePath;
	}
	
	
	public void executeTestCases()  {				
		try {
			System.out.println("Started");
			String currDir1 = getProjectPath();
					 String[] parms = {"wscript", currDir1+"/assets/vbs/adactin.vbs" ,"C:/NewWebWorkspace/SWAUT2/WebContent/assets/vbs/adactin.bat"};
					Runtime.getRuntime().exec(parms);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 										
			
		}
	
	public void executeAndroidCases()  {				
		try {
			System.out.println("Started");
			String currDir1 = getProjectPath();
					 String[] parms = {"wscript", currDir1+"/assets/vbs/adactin.vbs" ,"C:/NewWebWorkspace/SWAUT2/WebContent/assets/vbs/android.bat"};
					Runtime.getRuntime().exec(parms);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 										
			
		}
}

