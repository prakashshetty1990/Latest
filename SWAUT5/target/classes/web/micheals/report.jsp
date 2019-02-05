<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.demo.*"%>
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
<body>
	<div class="wrapper1">
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
				<li><a href="../../web/micheals/default.jsp">Michaels</a></li>
				<li class="dropdown">
			        <a class="dropdown-toggle" data-toggle="dropdown" href="../../web/micheals/TestExecution.jsp">Test Case Execution
			        </a>
			        <ul class="dropdown-menu">
			        <li><a href="../../web/micheals/TestExecution.jsp">Execution By Test Cases</a></li>
			          <li><a href="#">Execution By Category</a></li>			          			         
			        </ul>
			      </li>
			<!-- 	<li><a href="../../web/micheals/TestExecution.jsp">Test Case Execution</a></li> -->
			<li><a href="../../web/micheals/configurations.jsp">App Configurations</a></li>
				<li><a href="../../web/micheals/TestData.jsp">Test Data</a></li>
				<li class="active"><a href="../../web/micheals/report.jsp">Results</a></li>
			</ul>
		</div>
		</nav>

<nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="">Home</a></li>
    <li class="breadcrumb-item"><a href="#">Web Application</a></li>
    <li class="breadcrumb-item"><a href="../../web/micheals/default.jsp">Michaels</a></li>
    <li class="breadcrumb-item active" aria-current="page">Results</li>
  </ol>
</nav>



<div class="p-container content-area mb-2 col">
        <h4>Click on 'View Reports' button to view Automation execution results</h4>       
            <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#demo">View Reports</button>
         		<div id="demo" class="collapse mb-2">  
         		<% Displayreports.Tree= "";
         		Common common = new Common();
         		String str = common.getProjectPath();
         		%>        											
					<%=
					Displayreports.listDirectory("C:/apache-tomcat-8.5.32/webapps/AutomationResults/Web/Micheals/TestResults/",0,"Micheals")
					%>								
						<div class="report-viewer"></div> 
 				</div>
</div>

	</div>
</body>
</html>