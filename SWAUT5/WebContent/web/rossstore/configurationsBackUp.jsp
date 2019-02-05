<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.io.File"%>
<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.demo.*" import="java.net.URLDecoder.*"
	import="java.io.File.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
<meta charset="utf-8">
<link rel="shortcut icon" type="image/png"
	href="../../assets/images/logo/logo_icon.png" />
<link rel="stylesheet" href="../../cssjs/css/bootstrap.css">
<script src="../../cssjs/css/bootstrap.min.js"></script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<script type="text/javascript">
function executeTestCases()  {
	
	 <%		
	 String functionName = request.getParameter("executeTestCasebyCategory");	 
	 	if(functionName != null){
	 		if (functionName.equals("updateTestExecution")) {
	 String testdatapath = "C:/FHLBNYWorkspace/Adactin/src/test/resources/TestCaseSettings.xls";				
		boolean strComplete= false;
		try {			
				ReadExcel objReadXls = new ReadExcel(testdatapath);
				Object testdata2[][] = objReadXls.retrieveTestData(1);
				for (int i = 1; i < testdata2.length; i++) {						
					for (int j = 1; j < testdata2[i].length; j++) {						
						String TestDataName2 = request.getParameter("data" + i + "_" + j);						
						if(TestDataName2 != null){
							objReadXls.writeData(1, j, i, TestDataName2);							
						}else{
							strComplete= true;
							break;
						}
					}						
			}				
		}
		catch (Exception ex) {
			
		}			 		
			Common common1 = new Common();
			 String currDir1 = common1.getProjectPath();
			 String[] parms = {"wscript", currDir1+"/assets/vbs/adactin.vbs" ,"C:/NewWebWorkspace/SWAUT2/WebContent/assets/vbs/adactin.bat"};
			Runtime.getRuntime().exec(parms);	 	
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
				<%
					Common common = new Common();					
					String currDir = common.getProjectPath();
				%>
				<section class="d-flex align-items-center"> <!--Site logo-->
				<figure class="site-logo"> <a					
					src="../../cssjs/css/suntechlogo.jpg" class="img-fluid"
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
				<li><a href="../../web/adactin/default.jsp">Adactin</a></li>
				<li  class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="../../web/adactin/TestExecution.jsp">Test
						Case Execution </a>
					<ul class="dropdown-menu">
						<li><a href="../../web/adactin/TestExecution.jsp">Execution
								By Test Cases</a></li>
						<li><a href="#">Execution By Category</a></li>
					</ul></li>
				<!-- 	<li><a href="../../web/adactin/TestExecution.jsp">Test Case Execution</a></li> -->
				<li class="active"><a href="../../web/adactin/configurations.jsp">App Configurations</a></li>
				<li><a href="../../web/adactin/TestData.jsp">Test Data</a></li>
				<li><a href="../../web/adactin/TestResults.jsp">Results</a></li>
			</ul>
		</div>
		</nav>
		<nav aria-label="breadcrumb">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="">Home</a></li>
			<li class="breadcrumb-item"><a href="#">Web Application</a></li>
			<li class="breadcrumb-item"><a
				href="../../web/adactin/default.jsp">Adactin</a></li>
			<li class="breadcrumb-item active" aria-current="page">Test
				Execution By Test Cases</li>
		</ol>
		</nav>

		<section class="d-flex justify-content-center" id="TestExecution">
		<main class="main-area">
		<div class="area-section">
			<section class="d-flex"> <!-- <div class="col"> -->
			<div class="content-area">
				<header class="headline"> <b>Test Execution Controller
				</b></header>
				<table border="1" class="table">
					<%String filepathEditACStoGL1 = "C:/FHLBNYWorkspace/Adactin/src/test/resources/TestCaseSettings.xls";
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
							<%for (int j = 0; j < testdata3[i].length; j++) {
										col3 = testdata3[i].length;	
										if(!testdata3[i][j].toString().equals("")){
											if (i == 0) {%>
							<td><%=testdata3[i][j]%></td>
							<%} else {%>
							<td style="padding: 0">								
								<input class="form-control" id="<%="data" + i + "_" + j%>" name="<%="data" + i + "_" + j%>" value="<%=testdata3[i][j]%>"></input> 
							</td>
							<%}
										}else{
											//break;
										}
									}%>
						</tr>
						<%if (i == 0) {%>
					
					</thead>
					<%}
									if (i == 1) {%>
					</tbody>
					<%}  
								}%>

				</table>
				<form method="post" id="Execute" role="form">
					<input type="hidden" name="executeTestCasebyCategory"
						value="updateTestExecution" />
					<nav class="navbar fixed-bottom navbar-light bg-faded">
					<div class="pl-4">
						<button type="submit" class="btn btn-info">
							<span class="glyphicon glyphicon-search"></span> Update
						</button>
						<!-- <button class="btn btn-primary" type="button"
							onclick="javascript:executeTestCases()">Execute</button> -->
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