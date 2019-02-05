//Use below template for all the Classes/Files which are currently added in Automation Dashboard Project.
// ****************************************************************************************************//
// Author : 1Rivet
// Version : 0.1
// Summary : This class has been created to validate all the fetching the file with Scenario's and on demand Execution with the vbscript with edit functionalities for Automation Dashboard
// ****************************************************************************************************//


package com.demo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class DisplayScenarioOnNewTours {
	
	public static String projectPath = "C:/DemoWorkspace/FlightBDD/";
	public static String fileName = "C:/DemoWorkspace/FlightBDD/src/test/resources/Features/";
	public static String locationName = "C:/DemoWorkspace/FlightBDD/src/test/java/LaunchScript/LaunchScript.java";	
	public int count=0,currentlineNumber;
	public List<Integer> scenarioLineNumber = new ArrayList<Integer>();
	public List<String> scenarioSteps = new ArrayList<String>();
	public int flag=0;
	public Properties config;
	//public static String propfileName = "D://AutomationFHLBNY//Sun_SQE//PrakashWorkspace//WebInterface//src//com//property//configuration.properties";
	public static String propfileName = "C:/SwautUiWorkspace/SWAUT5/src/com/property/configuration.properties";
	//public static String propfileName = "D://QATOOLS//apache-tomcat-8.0.50//configurationProd.properties";
	public static String startExecutionFlag, dirPath=fileName;
	public static String strWorkspace;
		
	/**
	 * Parameterized constructor for Display Scenario on web page
	 * @param fName
	 * @throws IOException
	 */
	public DisplayScenarioOnNewTours(String fName) throws IOException
	{
		int pos=0;
		String file;
		if (fileName.contains(".feature")) 
		{
			pos=(fileName.indexOf("Features"));
			pos=pos+9;
			file=fileName.substring(0, pos);
			fileName=file;
		}
		
		fileName=fileName.concat(fName).concat(".feature");
		System.out.println("FilePath = " + fileName);
		
		String line = null;
		try {
			System.out.println("inside try"+fileName);
			FileReader fileReader = new FileReader(fileName);
			System.out.println("Read "+fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				currentlineNumber++;
				if (line.contains("Scenario"))
				{
					count++;
					scenarioLineNumber.add(currentlineNumber);
					System.out.println("Added"+currentlineNumber);
				}
			}
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println(
					"Unable to open file '" + 
							fileName + "'");                
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" 
							+ fileName + "'");
		}
		finally {
		}
		
		for(int j = 0; j< scenarioLineNumber.size(); j++)
		{
			List<String> scenarioData = new ArrayList<String>();
			if(j!=scenarioLineNumber.size()-1)
			{
			for(int i = scenarioLineNumber.get(j); i<scenarioLineNumber.get(j+1)-1; i++)
				{
						scenarioData.add(Files.readAllLines(Paths.get(fileName)).get(i));
				}
			}
			else
			{
				for(int i = scenarioLineNumber.get(j);i<currentlineNumber;i++)
				{
						scenarioData.add(Files.readAllLines(Paths.get(fileName)).get(i));
				}
			}
			scenarioSteps.add(scenarioData.toString());
			scenarioData = Collections.<String> emptyList();
		}
		return ;
	}
	
	
	public static void updateThePropertyFile(String url, String DataBase,String Browser){
		try {
			String strConfigpath = projectPath+"src/test/resources/local.properties";
			FileInputStream propFile = new FileInputStream(strConfigpath);
			Properties config = new Properties(System.getProperties());
			config.load(propFile);
			config.setProperty("web.Url", url);		
			config.setProperty("DataBase", DataBase);
			config.setProperty("Browser", Browser);
			config.store(new FileOutputStream(strConfigpath), "");
			System.out.println("Changed ");
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage());
		}
	}
	
	
	public static String readThePropertyFile(String key){
		try {
			String strConfigpath = projectPath+"src/test/resources/local.properties";
			FileInputStream propFile = new FileInputStream(strConfigpath);
			Properties config = new Properties(System.getProperties());
			config.load(propFile);
			return config.getProperty(key);								
		} catch (Exception e) {			
			System.out.println("Exception "+e.getMessage());
			return "";
		}
	}
	
	/**
	 * Read run flag from properties file
	 * @return
	 * @throws IOException
	 */
	public String readRunFlag() throws IOException{
		FileInputStream propFile = new FileInputStream(propfileName);
		config = new Properties(System.getProperties());
		config.load(propFile);
		startExecutionFlag = config.getProperty("runtoFlagQRM");
		return startExecutionFlag;
	}
	
	/**
	 * Read Base URL from properties file
	 * @return
	 * @throws IOException
	 */
	public String readBaseUrl() throws IOException{
		FileInputStream propFile = new FileInputStream(propfileName);
		config = new Properties(System.getProperties());
		config.load(propFile);
		startExecutionFlag = config.getProperty("baseUrl");
		return startExecutionFlag;
	}
	
	/**
	 * Read Base URL from properties file
	 * @return
	 * @throws IOException
	 */
	public String readWorkspacePath() throws IOException{
		FileInputStream propFile = new FileInputStream(propfileName);
		config = new Properties(System.getProperties());
		config.load(propFile);
		strWorkspace = config.getProperty("workspacePath");
		return strWorkspace;
	}
	
	/**
	 * Constructor without parameter for Display Scenario on web page
	 * @throws IOException
	 */
	public DisplayScenarioOnNewTours() throws IOException
	{
		String line = null;
		try {
			System.out.println("fileName in another method"+fileName);
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				currentlineNumber++;
				if (line.contains("Scenario"))
				{
					count++;
					scenarioLineNumber.add(currentlineNumber);
				}
			}
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println(
					"Unable to open file '" + 
							fileName + "'");                
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" 
							+ fileName + "'");
		}
		finally {
		}
		
		for(int j = 0; j< scenarioLineNumber.size(); j++)
		{
			List<String> scenarioData = new ArrayList<String>();
			if(j!=scenarioLineNumber.size()-1)
			{
			for(int i = scenarioLineNumber.get(j); i<scenarioLineNumber.get(j+1)-1; i++)
				{
						scenarioData.add(Files.readAllLines(Paths.get(fileName)).get(i));
				}
			}
			else
			{
				for(int i = scenarioLineNumber.get(j);i<currentlineNumber;i++)
				{
						scenarioData.add(Files.readAllLines(Paths.get(fileName)).get(i));
				}
			}
			scenarioSteps.add(scenarioData.toString());
			scenarioData = Collections.<String> emptyList();
		}
		return ;
	}
	
	/**
	 * count number of scenario in feature file
	 * @param countScenario
	 * @return
	 */
	public String DisplayScenario(int countScenario)
	{
		String lineData = "";
		int countScenarios=0;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((lineData = bufferedReader.readLine()) != "") {
				if (lineData.contains("Scenario"))
				{
					countScenarios++;
				}
				if (countScenarios == countScenario)
				{
					break;
				}
			}
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");                
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");                  
		}
		finally {
		}
		return lineData;
	}
	
	/**
	 * add @Run tag in feature file for selected scenario
	 * @param scenarioname
	 * @throws IOException
	 */
	public void UpdateFile(String scenarioname) throws IOException
	{
		String line = null,featureContent = "";
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				currentlineNumber++;
				if (line.contains(scenarioname))
				{
					featureContent = featureContent.concat("@Run\n");
					featureContent = featureContent.concat(line);
					featureContent = featureContent.concat("\n");
				}
				else
				{
					featureContent = featureContent.concat(line);
					featureContent = featureContent.concat("\n");
				}
			}
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");                
		}
		finally {
		}
		FileWriter writer = new FileWriter(fileName);
		writer.write(featureContent);
		writer.close();
	}
	
	/**
	 * Remove @run tag from feature file
	 * @throws IOException
	 */
	public void removeRunTag() throws IOException
	{
		String featureFileName="", filepath= dirPath;
		File f = new File(dirPath);
	    File[] files = f.listFiles();
	    if (files != null)
	    {
		    for (int i = 0; i < files.length; i++) 
		    {
		    	int pos=0;
				String file;
				if (filepath.contains(".feature")) 
				{
					pos=(filepath.indexOf("Features"));
					pos=pos+9;
					file=filepath.substring(0, pos);
					filepath=file;
				}
				
		    	featureFileName = files[i].getName();
		    	filepath=filepath.concat(featureFileName);
				String line = null,featureContent = "";		
				try {
					FileReader fileReader = new FileReader(filepath);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					while((line = bufferedReader.readLine()) != null) {
						if (!line.contains("@Run"))
						{
							featureContent = featureContent.concat(line);
							featureContent = featureContent.concat("\n");
						}
					}
					bufferedReader.close();         
				}
				catch(FileNotFoundException ex) {
					System.out.println("Unable to open file '" + filepath + "'");                
				}
				finally {
				}
				FileWriter writer = new FileWriter(filepath);
				writer.write(featureContent);
				writer.close();
		    }
	    }
	}
	
	/**
	 * Update example data in feature file
	 * @param newData
	 * @throws IOException
	 */
	public void updateData(String newData) throws IOException
	{
		String testCaseId= newData.split(",")[0].toString();  
		System.out.println("Inside Method test case id are"+testCaseId);
		String lineData="", data="",addPipe= "       |";
		addPipe=addPipe.concat(testCaseId);
		String replacementStr = newData.replace(","," | ");
		replacementStr=replacementStr.concat(" | ");
		replacementStr = replacementStr.replace(testCaseId,addPipe);
		System.out.println("Inside Method values are"+replacementStr);
		try {
			FileReader filereader = new FileReader(fileName);
		    BufferedReader bufferedReader = new BufferedReader(filereader);
		    while ((lineData = bufferedReader.readLine()) != null){
		    	if (lineData.contains(testCaseId))
		    	{
		    		data=data.concat(replacementStr);
		    		data = data.concat("\n");
		    	}
		    	else
		    	{
		    		data=data.concat(lineData);
		    		data = data.concat("\n");
		    	}
		    }
		    FileWriter writer = new FileWriter(fileName);
			writer.write(data);
			writer.close();
			bufferedReader.close();
			filereader.close();
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");                
		}
		finally {
		}
	}
	/**
	 * Add @Run tag to test runner file
	 * @param chkbxValues
	 * @throws IOException
	 */
	public void addRunTagTestRunner() throws IOException
	{
		String line = "",featureContent = "";
		try {
			FileReader fileReader = new FileReader(locationName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				if (line.contains("tags={")){
					String texttoadd = "tags={\"@Run\"}";
					featureContent = featureContent + texttoadd + "\n";
				}
				else{
					featureContent = featureContent + line + "\n";
				}
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + locationName + "'");
		}
		finally {
		}
		FileWriter writer = new FileWriter(locationName);
		writer.write(featureContent);
		writer.close();
	}

	/**
	 * Start cucumber execution for selected scenarios 
	 */
	public void StartCucumberExecution()
	{
		try {
			Process p = Runtime.getRuntime().exec("cmd /c start D:\\AutomationFHLBNY\\Sun_SQE\\PrakashWorkspace\\WebInterface\\blotter.vbs");
			//p.waitFor();
			BufferedReader input= new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while((line = input.readLine())!=null){
				System.out.println("CMD "+line);
			}
			/*ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int c = -1;
			while((c = in.read()) != -1)
			{
				baos.write(c);
			}
			System.out.println(in);*/
		} catch (Exception e) {
			System.out.println("Exception caught"+e.getMessage());
			e.printStackTrace();
		}
		finally {
			System.out.println("Something going wrong 6" + fileName + "'");
		}
	}
}
