package com.demo;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Runtime;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class UpdateRunModeFlag {

	//public static String filePath = "D:\\AutomationBackup\\Ketan_Workspace\\cnTest\\src\\test\\resources\\excelfiles\\ACSToGL.xls";
	public static String testDataFolder = "D:\\AutomationBackup\\Ketan_Workspace\\cnTest\\src\\test\\resources\\excelfiles";
	public static int executionCount;

	/**
	 * Update run mode flag
	 * @param selTestCaseId
	 * @param sheetName
	 * @param fileName
	 * @param colNum
	 * @throws IOException
	 */
	public void UpdateRunFlag(String selTestCaseId, String fileName, int sheetNum) throws IOException
	{
		if(executionCount<1)
		{
			executionCount++;	
			//RevertTCChanges(fileName);
		}
		FileInputStream input=new FileInputStream(testDataFolder+"\\"+fileName+".xls");	 
		HSSFWorkbook wb=new HSSFWorkbook(input);
		HSSFSheet sh=wb.getSheetAt(sheetNum);
		HSSFRow Suiterow = sh.getRow(0);
		int lastRowNum=sh.getLastRowNum();
		int LastCol = sh.getRow(0).getPhysicalNumberOfCells();
		int colNum = 0;		
		for (int i = 0; i < LastCol; i++) {

			if (Suiterow.getCell(i).getStringCellValue().equals("CaseToRun".trim())||Suiterow.getCell(i).getStringCellValue().equals("DataToRun".trim())) {
				colNum = i;
			}
		}
		for (int count=1;count <=lastRowNum;count++) {
			String testCaseId = (sh.getRow(count).getCell(0).getStringCellValue()).toString();		
			if (testCaseId.equals(selTestCaseId))
			{
				sh.getRow(count).getCell(colNum).setCellValue("Y");
			}
		}
		FileOutputStream outputStream = new FileOutputStream(testDataFolder+"\\"+fileName+".xls");
		wb.write(outputStream);
		wb.close();
		outputStream.close();
	}



	/*
	 * Run Testng.xml file using .Bat file
	 */
	
	public void StarExecution() throws IOException
	{			
		System.out.println("Execution Started");
		try {	            
			Process p = Runtime.getRuntime().exec("cmd /c start D:\\AutomationBackup\\Ketan_Workspace\\WebInterface\\execute.vbs");
			p.waitFor();
			InputStream in = p.getInputStream(); 
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int c = -1;
			while((c = in.read()) != -1)
			{
				baos.write(c);
			}
			String response = new String(baos.toByteArray());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void RevertTCChanges(String fileName) throws IOException
	{
		FileInputStream input=new FileInputStream(testDataFolder+"\\"+fileName+".xls");
		HSSFWorkbook wb=new HSSFWorkbook(input);
		for (int CntSh=0; CntSh<wb.getNumberOfSheets(); CntSh++) {
			HSSFSheet sh=wb.getSheetAt(CntSh);
			int lastRowNum = sh.getLastRowNum();
			int LastCol = sh.getRow(0).getPhysicalNumberOfCells();
			outerLoop:
				for (int countrow=0; countrow<lastRowNum; countrow++) {
					for (int countcol=0;countcol<LastCol;countcol++) {

						String testName = (sh.getRow(countrow).getCell(countcol).getStringCellValue()).toString();
						if (testName.equals("CaseToRun") || (testName.equals("DataToRun")))
						{			   
							for (int count=1; count<=lastRowNum; count++) {
								sh.getRow(count).getCell(countcol).setCellValue("N");
							}break outerLoop;
						}
					}
				}	   
		}
		FileOutputStream outputStream = new FileOutputStream(testDataFolder+"\\"+fileName+".xls");
		wb.write(outputStream);
		wb.close();
		outputStream.close();	  
	}
	
	public void editData(String fileName,String functionName,String TestDataName2[])
	{
		String filePath = testDataFolder+"\\"+fileName+".xls";
		try {
			if (functionName.equals("updateAddACStoGLData")) {
				ReadExcel objReadXls = new ReadExcel(filePath);
				Object testdata2[][] = objReadXls.retrieveTestData(1);
				for (int i = 1; i < testdata2.length; i++) {
					for (int j = 1; j < testdata2[i].length; j++) {
						if (TestDataName2 != null) {
							for (int k = 0; k < TestDataName2.length; k++) {
								objReadXls.writeData(0, j, i, TestDataName2[k]);
							}
						}
					}
				}
			}
		}
		catch(Exception ex) {
			
		}
	}
		
}
