package com.demo;

import java.io.File;
import java.io.IOException;

public class Displayreports {
	public static String Tree="";

	public static String listDirectory(String dirPath, int level,String appFolderName){
		
		try {
			File dir = new File(dirPath);
			File[] firstLevelFiles = dir.listFiles();			

			if (firstLevelFiles != null && firstLevelFiles.length > 0) {
				for (File aFile : firstLevelFiles) {
					if(!(aFile.getCanonicalPath().toLowerCase().contains("js"))){
						if(!(aFile.getCanonicalPath().toLowerCase().contains("extentreports"))){
							if(!(aFile.getCanonicalPath().contains("Screenshots"))){
								if(!(aFile.getCanonicalPath().contains("screenshot"))){
								if(!(aFile.getCanonicalPath().contains("TestDocuments"))){
									if(!(aFile.getCanonicalPath().contains("Response"))){
									String fPath = aFile.getAbsolutePath();
									String splitPath[] =  fPath.split("TestResults");
									String fpath1 = splitPath[0];								
									String fpath2 = splitPath[1];								
									fpath2 = fpath2.substring(1);
									fpath2 =fpath2.replaceAll("\\\\", "/");
									String strPath = "../../../AutomationResults/Web/"+appFolderName+"/TestResults/" + fpath2;								
									if (level == 0) {									
										Tree += "<ul class='level-1'><li><label class='content-one ' >" + aFile.getName() +"</label></li></ul>";
									}else if (level == 2) {									
										Tree += " <ul class='level-2 child-node'><li><label class='content-one'><a class='hyperLink' href='"+strPath+"'target='_blank'>" + aFile.getName() + "</a><label>";
									}else if (level == 4)  {							
										Tree += " <ul class='level-3 child-node'><li><label class='link-view-report content-one'><a href='#'> " + aFile.getName() + "</a></label>";
										Tree+=" <ul class='level-3 child-node'><li><label class='link-view-report content-one'><iframe class='collapsed card-link' data-toggle = 'collapse' width='1000' height='700' src="+"./reports/"+fpath2+"><label>"+aFile.getName()+"</label></iframe></li></ul>";
									}else if (level == 6)	{								
										Tree+=" <ul class='level-4 child-node'><li><label class='link-view-report content-one'><a href='./reports/"+fpath2+"'target='iframe_b'>" + aFile.getName() + "</a></label>";
									}	
								}
							}
							}
						}				
						listDirectory(aFile.getAbsolutePath(), level + 2,appFolderName).toString();
					}	 
				}
			}
			}
			Tree+="</li></ul></li></ul></li></ul>";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Tree;			
	}	


	public static String listAndroidDirectory(String dirPath, int level,String appFolderName){
		
		
		try {
			File dir = new File(dirPath);
			File[] firstLevelFiles = dir.listFiles();						
			if (firstLevelFiles != null && firstLevelFiles.length > 0) {
				for (File aFile : firstLevelFiles) {
					if(!(aFile.getCanonicalPath().toLowerCase().contains("js"))){
						if(!(aFile.getCanonicalPath().toLowerCase().contains("extentreports"))){
							if(!(aFile.getCanonicalPath().contains("Screenshots"))){
								if(!(aFile.getCanonicalPath().contains("testng"))){
									if(!(aFile.getCanonicalPath().contains("TestDocuments"))){
										String fPath = aFile.getAbsolutePath();
										String splitPath[] =  fPath.split("TestResults");
										String fpath1 = splitPath[0];								
										String fpath2 = splitPath[1];								
										fpath2 = fpath2.substring(1);
										fpath2 =fpath2.replaceAll("\\\\", "/");
										String strPath = "../../../AutomationResults/Mobile/"+appFolderName+"/TestResults/" + fpath2;												
										if (level == 0) {														
											Tree += "<ul class='level-1'><li><label class='content-one ' >" + aFile.getName() +"</label></li></ul>";
										}else if (level == 2) {											
											Tree += " <ul class='level-2 child-node'><li><label class='content-one'><a class='hyperLink' href='"+strPath+"'target='_blank'>" + aFile.getName() + "</a><label>";											
										}else if (level == 4)  {		
											
											Tree += " <ul class='level-3 child-node'><li><label class='link-view-report content-one'><a href='#'> " + aFile.getName() + "</a></label>";
											Tree+=" <ul class='level-3 child-node'><li><label class='link-view-report content-one'><iframe class='collapsed card-link' data-toggle = 'collapse' width='1000' height='700' src="+"./reports/"+fpath2+"><label>"+aFile.getName()+"</label></iframe></li></ul>";
										}else if (level == 6)	{													
											Tree+=" <ul class='level-4 child-node'><li><label class='link-view-report content-one'><a href='./reports/"+fpath2+"'target='iframe_b'>" + aFile.getName() + "</a></label>";
										}	
									}
								}
							}
						}				
						listAndroidDirectory(aFile.getAbsolutePath(), level + 2,appFolderName).toString();
					}	 
				}
			}
			Tree+="</li></ul></li></ul></li></ul>";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Tree;			
	}	





	public static String listIosDirectory(String dirPath, int level) throws IOException {

		File dir = new File(dirPath);
		File[] firstLevelFiles = dir.listFiles();			

		if (firstLevelFiles != null && firstLevelFiles.length > 0) {
			for (File aFile : firstLevelFiles) {
				if(!(aFile.getCanonicalPath().toLowerCase().contains("js"))){
					if(!(aFile.getCanonicalPath().toLowerCase().contains("extentreports"))){
						if(!(aFile.getCanonicalPath().contains("Screenshots"))){
							if(!(aFile.getCanonicalPath().contains("TestDocuments"))){
								String fPath = aFile.getAbsolutePath();
								String splitPath[] =  fPath.split("TestResults");
								String fpath1 = splitPath[0];								
								String fpath2 = splitPath[1];								
								fpath2 = fpath2.substring(1);
								fpath2 =fpath2.replaceAll("\\\\", "/");
								String strPath = "../../IOS/TestResults/" + fpath2;										
								if (level == 0) {												
									Tree += "<ul class='level-1'><li><label class='content-one ' >" + aFile.getName() +"</label></li></ul>";
								}else if (level == 2) {									
									Tree += " <ul class='level-2 child-node'><li><label class='content-one'><a class='hyperLink' href='"+strPath+"'target='_blank'>" + aFile.getName() + "</a><label>";									
								}else if (level == 4)  {											
									Tree += " <ul class='level-3 child-node'><li><label class='link-view-report content-one'><a href='#'> " + aFile.getName() + "</a></label>";
									Tree+=" <ul class='level-3 child-node'><li><label class='link-view-report content-one'><iframe class='collapsed card-link' data-toggle = 'collapse' width='1000' height='700' src="+"./reports/"+fpath2+"><label>"+aFile.getName()+"</label></iframe></li></ul>";
								}else if (level == 6)	{											
									Tree+=" <ul class='level-4 child-node'><li><label class='link-view-report content-one'><a href='./reports/"+fpath2+"'target='iframe_b'>" + aFile.getName() + "</a></label>";
								}	
							}
						}
					}				
					listIosDirectory(aFile.getAbsolutePath(), level + 2).toString();
				}	 
			}
		}
		Tree+="</li></ul></li></ul></li></ul>";

		return Tree;			
	}	
}


