<%@page import="java.io.BufferedReader"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.demo.*"%>
<%@page import="java.io.*"%>
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
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<script type="text/javascript">

	function updateTestData() {			
	<%
		String functionName = request.getParameter("CheckOutFromSVN");	 
	 	if(functionName != null){
	 		if (functionName.equals("CheckOutLatestFromSVN")) {
				Common common1 = new Common();
				String path = common1.getProperty("AdactinMobileWorkspace");	 
				String currDir1 = common1.getProjectPath();			 
				String[] parms = {"cmd.exe", "/c","start"+ currDir1+"/assets/vbs/adactin.bat"};
				String arg = "cmd /c "+ currDir1+"/assets/vbs/adactin.bat";
				System.out.println(arg);
				Process pr = Runtime.getRuntime().exec("cmd /c "+ currDir1+"\\assets\\vbs\\svn.bat "+ path);	
				BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));			 
			    String line=null;
			    while((line=input.readLine()) != null) {
			        System.out.println(line);
			    }            
			System.out.println("Extracted the latest code from SVN");

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
				<figure class="site-logo"> <a
					href="../../Homepage.jsp"> <img
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
				<li class="active"><a href="#">Android</a></li>
				<li class="dropdown">
			        <a class="dropdown-toggle" data-toggle="dropdown" href="../../mobile/android/TestExecution.jsp">Test Case Execution
			        </a>
			        <ul class="dropdown-menu">
			        <li><a href="../../mobile/android/TestExecution.jsp">Execution By Test Cases</a></li>
			          <li><a href="#">Execution By Category</a></li>			          			         
			        </ul>
			      </li>
			<!-- 	<li><a href="../../mobile/android/TestExecution.jsp">Test Case Execution</a></li> -->
			<li><a href="../../mobile/android/configurations.jsp">App Configurations</a></li>
				<li><a href="../../mobile/android/TestData.jsp">Test Data</a></li>
				<li><a href="../../mobile/android/report.jsp">Results</a></li>
			</ul>
		</div>
		</nav>
		
		
		<nav aria-label="breadcrumb">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="">Home</a></li>
			<li class="breadcrumb-item"><a href="#">Mobile Application</a></li>
			<li class="breadcrumb-item"><a
				href="../../mobile/android/default.jsp" active>Android</a></li>
			<li class="breadcrumb-item" aria-current="page">Android Home Page</li>
		</ol>
		</nav>
		
		<section class="d-flex justify-content-center" id="svncode">
		<main class="main-area">
		<div class="area-section">
			<section class="d-flex"> <!-- <div class="col"> -->
			<div class="content-area">
				<header class="headline"> <b>Check Out the Latest Code from SVN
				</b></header>
				<form method="post" id="checkout" role="form">
					<input type="hidden" name="CheckOutFromSVN"
						value="CheckOutLatestFromSVN" />
						
				<table border="1" class="table">					
					<thead class="p-3 mb-2 bg-secondary text-white">					
					<tbody>						
						<tr>
							<td>SVN URL</td>							
							<td style="padding: 0"> <input class="form-control" name="svnurl" value="https://Praskash-PC/svn/SeleniumScripts/trunk/android/trunk"></input>								
							</td>					
						</tr>					
					</thead>					
					</tbody>					
				</table>
				
					<nav class="navbar fixed-bottom navbar-light bg-faded">
					<div class="pl-4">
						<button type="submit" class="btn btn-info">
							<span class="glyphicon"></span> CheckOut Latest Code
						</button>						
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