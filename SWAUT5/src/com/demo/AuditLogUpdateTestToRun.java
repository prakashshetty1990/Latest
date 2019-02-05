package com.demo;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class AuditLogUpdateTestToRun {
	
	public String FilePath ="D:\\AutomationBackup\\Ketan_Workspace\\cnTest\\src\\test\\resources\\excelfiles\\AuditLog.xls";
	
	/*
	 * Update Test Case for Main TestCaseList sheet
	 */
	public void UpdateTCList(String tcname) throws IOException
	{
		System.out.println("Method Started for " + tcname);		
		FileInputStream input=new FileInputStream(FilePath);		 
		HSSFWorkbook wb1=new HSSFWorkbook(input);
		HSSFSheet sh1=wb1.getSheet("TestCasesList");
		int Lastrow=sh1.getLastRowNum();
		
		for (int count=1;count <=Lastrow;count++) {
						
			String STCName = (sh1.getRow(count).getCell(0).getStringCellValue()).toString();			
			if (STCName.equals(tcname))
			{
				sh1.getRow(count).getCell(1).setCellValue("Y");
			}
		}
		
		FileOutputStream outputStream = new FileOutputStream(FilePath);
		wb1.write(outputStream);
		wb1.close();
		outputStream.close();
		//RunXML();
	}
	
	/*
	 * Update Test Case for AddACSToGL
	 */
	public void UpdateTCExportAuditLogTranInquiryReport(String tcname) throws IOException
	{
		System.out.println("Method Started for " + tcname);
		FileInputStream input=new FileInputStream(FilePath);		 
		HSSFWorkbook wb1=new HSSFWorkbook(input);
		HSSFSheet sh1=wb1.getSheet("ExportAuditLogTranInquiryReport");
		int Lastrow=sh1.getLastRowNum();
		for (int count=1;count <=Lastrow;count++) {
						
			String STCName = (sh1.getRow(count).getCell(1).getStringCellValue()).toString();		
			
			if (STCName.equals(tcname))
			{
				sh1.getRow(count).getCell(8).setCellValue("Y");
			}
		}
		
		FileOutputStream outputStream = new FileOutputStream(FilePath);
		wb1.write(outputStream);
		wb1.close();
		outputStream.close();
	}	
	
	/*
	 * Update Test Case for EditACSToGL
	 */
	public void UpdateTCExportAuditLogTranDetailsReport(String tcname) throws IOException
	{
		System.out.println("Method Started for " + tcname);	
		FileInputStream input=new FileInputStream(FilePath);		 
		HSSFWorkbook wb1=new HSSFWorkbook(input);
		HSSFSheet sh1=wb1.getSheet("ExportAuditLogTranDetailsReport");
		int Lastrow=sh1.getLastRowNum();
		for (int count=1;count <=Lastrow;count++) {
						
			String STCName = (sh1.getRow(count).getCell(1).getStringCellValue()).toString();		
			
			if (STCName.equals(tcname))
			{
				sh1.getRow(count).getCell(9).setCellValue("Y");
			}
		}
		
		FileOutputStream outputStream = new FileOutputStream(FilePath);
		wb1.write(outputStream);
		wb1.close();
		outputStream.close();
	}
	
	/*
	 * Update Test Case for DeleteACStoGL
	 */
	public void UpdateTCMultiAuditLogTranInquiryReport(String tcname) throws IOException
	{
		System.out.println("Method Started for " + tcname);
		FileInputStream input=new FileInputStream(FilePath);		 
		HSSFWorkbook wb1=new HSSFWorkbook(input);
		HSSFSheet sh1=wb1.getSheet("MultiAuditLogTranInquiryReport");
		int Lastrow=sh1.getLastRowNum();
		for (int count=1;count <=Lastrow;count++) {
						
			String STCName = (sh1.getRow(count).getCell(1).getStringCellValue()).toString();		
			
			if (STCName.equals(tcname))
			{
				sh1.getRow(count).getCell(8).setCellValue("Y");
			}
		}
		
		FileOutputStream outputStream = new FileOutputStream(FilePath);
		wb1.write(outputStream);
		wb1.close();
		outputStream.close();
	}
	
	
	/*
	 * Update Test Case for ExportACSToGLReport
	 */
	public void UpdateTCMultiAuditLogTranDetailsReport(String tcname) throws IOException
	{
		System.out.println("Method Started for " + tcname);
		FileInputStream input=new FileInputStream(FilePath);		 
		HSSFWorkbook wb1=new HSSFWorkbook(input);
		HSSFSheet sh1=wb1.getSheet("MultiAuditLogTranDetailsReport");
		int Lastrow=sh1.getLastRowNum();
		for (int count=1;count <=Lastrow;count++) {

			String STCName = (sh1.getRow(count).getCell(1).getStringCellValue()).toString();
			
			if (STCName.equals(tcname))
			{
				sh1.getRow(count).getCell(9).setCellValue("Y");
			}
		}
		
		FileOutputStream outputStream = new FileOutputStream(FilePath);
		wb1.write(outputStream);
		wb1.close();
		outputStream.close();
	}
	
	
		/*
		 * Run Testng.xml file using .Bat file
		 */
		public void RunXML() throws IOException
		{			
			try {	            
				
	            Process p = Runtime.getRuntime().exec("cmd /c start D:\\AutomationBackup\\Ketan_Workspace\\WebInterface\\pom.bat");
	            p.waitFor();
	            InputStream in = p.getInputStream(); 
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            int c = -1;
	            while((c = in.read()) != -1)
	            {
	                baos.write(c);
	            }
	             
	            String response = new String(baos.toByteArray());
	            System.out.println("Response From Exe : "+response);
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}

}
