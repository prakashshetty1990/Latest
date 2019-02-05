<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.io.File"%>
<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.demo.*" import="java.net.URLDecoder.*"
	import="java.util.ResourceBundle" import="java.io.File.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
<meta charset="utf-8">
<link rel="shortcut icon" type="image/png"
	href="assets/images/logo/logo_icon.png" />
<meta name="viewport" content="width=device-width">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="cssjs/css/bootstrap.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="cssjs/css/bootstrap.min.js"></script>
</head>

<script type="text/javascript">
function updateAppConfig()  {
	
	 <%		
	 String functionName = request.getParameter("UpdateConfig");	 
	 	if(functionName != null){
	 		if (functionName.equals("updateAppConfiguration")) {	
		String testdatapath = "C:/Apps/config/FrameworkConfiguration.xls";		
		ReadExcel objReadXls = new ReadExcel(testdatapath);
		Object testdata2[][] = objReadXls.retrieveTestData(0);		
		for (int i = 1; i < testdata2.length; i++) {						
			for (int j = 1; j < testdata2[i].length; j++) {
				String TestDataName2 = request.getParameter("data" + i + "_" + j);
				if(TestDataName2 != null){
					objReadXls.writeData(0, j, i, TestDataName2);							
				}							
			}
		}	
	 	}
	 	}
			%> 
		
	}
	
</script>

<body>
	<div class="wrapper">
		<!-- Top header -->
		<div class="header-container">
			<header class="top-header shadow-2dp"> <section
				class="d-flex justify-content-between align-items-center">
			<div class="col-sm-6 col-lg-6">
				<section class="d-flex align-items-center"> <!--Site logo-->
				<figure class="site-logo"> <a href="./Homepage.jsp"> <img
					src="cssjs/css/suntechlogo.jpg" class="img-fluid"
					alt="SWAUT User Interface">
				</a> </figure> <!-- Header Nav / Single Nav Item --> <nav
					class="col header-nav border-left ml-4">
				<ul class="d-flex align-items-center list-unstyled mb-2 py-2">
					<li><h4>SWAUT Automation Dashboard</h4></li>
				</ul>
				</nav> </section>
			</div>

			</section> </header>
		</div>

		<nav class="navbar navbar-expand-lg navbar-inverse">
		<div class="container-fluid">
			<!-- <div class="navbar-header">
				<a class="navbar-brand" href="#">WebSiteName</a>
			</div> -->
			<ul class="nav navbar-nav">
				<li><a href="./Homepage.jsp">Home Page</a></li>
				<li><a href="./WebApplications.jsp">Hybrid Web Applications</a></li>
				 <li><a href="./CucumberWebApplications.jsp">BDD Web Applications</a></li>
				<li><a href="./UnderConstruction.jsp">Mobile Applications</a></li>
				<li><a href="./UnderConstruction.jsp">Windows Applications</a></li>
				<li class="active"><a href="./FrameworkConfig.jsp">Framework
						Configurations</a></li>
			</ul>
		</div>
		</nav>

		<section class="d-flex justify-content-center" id="TestExecution">
		<main class="main-area">
		<div class="area-section">
			<section class="d-flex"> <!-- <div class="col"> -->
			<div class="content-area">
				<header class="headline"> <b>Application Configurations
				</b></header>
				<form method="post" id="Update" role="form">
					<input type="hidden" name="UpdateConfig"
						value="updateAppConfiguration" />
					<table class="table table-bordered table-striped">
						<%String filepathEditACStoGL1 = "C:/Apps/config/FrameworkConfiguration.xls";
							ReadExcel objReadXls3 = new ReadExcel(filepathEditACStoGL1);
								Object testdata3[][] = objReadXls3.retrieveTestData(0);
								int col3 = 0;
								for (int i = 0; i < testdata3.length; i++) {
									if (i == 0) {%>
						<thead class="p-3 mb-2 bg-secondary text-white">
							<%}
										if (i == 1) {%>
						
						<tbody>
							<%}%>
							<tr>
								<%boolean color = false;
								boolean color1 = false;
								for (int j = 0; j < testdata3[i].length; j++) {									
												//col3 = testdata3[i].length;												
												if(j==1)
													color = true;
												else
													color = false;																								
												if(j==0){
													if(!testdata3[i][j].toString().trim().equals("")){
														color1 = true;
													}else{
														color1 = false;
													}
												}
												if (i == 0) {%>
								<td><%=testdata3[i][j]%></td>
								<%} else {								
								if((i==1 && j==2)){%>
								<td style="padding: 0">
								<select name="<%="data" + i + "_" + j%>" class='form-control' onChange="changetextbox();">
									<% if(testdata3[i][j].toString().toLowerCase().contains("cloud")) {%>
										<option value='Server'>Server</option>
										<option value='Cloud' selected>Cloud</option>
										<option value='Desktop'>Desktop</option>
									<% }else if(testdata3[i][j].toString().toLowerCase().contains("desktop")) {%>
										<option value='Server'>Server</option>
										<option value='Cloud'>Cloud</option>
										<option value='Desktop' selected>Desktop</option>
									<% }else {%>
										<option value='Server' selected>Server</option>
										<option value='Cloud'>Cloud</option>
										<option value='Desktop'>Desktop</option>
										</select></td>
								<%}}else{%>
								
								<td style="padding: 0">
									<%if(j==2){%> <input class="form-control"
									name="<%="data" + i + "_" + j%>" value="<%=testdata3[i][j]%>"></input>
									<%}else{ 
									if(!testdata3[i][j].toString().trim().equals("")){ %>
									<div class="p-3 mb-2 bg-secondary text-white"
										name="<%="data" + i + "_" + j%>"><%=testdata3[i][j]%></div> <%}}%>
								</td>
								<%}}}%>
							</tr>
							<%if (i == 0) {%>
						
						</thead>
						<%}
									if (i == 1) {%>
						</tbody>
						<%}  
								}%>

					</table>
					<nav class="navbar fixed-bottom navbar-light bg-faded">
					<div class="pl-4">
						<button class="btn btn-primary" onclick="updateAppConfig()">Update</button>
						<button class="btn btn-primary">Back</button>
					</div>
					</nav>
				</form>
				<div class="col">
					<div class="content-area col no-padding"></div>
				</div>
				<div class="col">
					<div class="content-area col no-padding"></div>
				</div>
				<div class="tbl-layout"></div>
			</div>
			</section>
		</div>
		</main> </section>
	</div>
</body>

</html>