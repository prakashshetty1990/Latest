<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.demo.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="cssjs/css/bootstrap.min.js"></script>
</head>
<body>
	<div class="wrapper" style="margin-left:10px;">
		<!-- Top header -->
		<div class="header-container">
			<header class="top-header shadow-2dp"> <section
				class="d-flex justify-content-between align-items-center">
			<div class="col-sm-6 col-lg-6">				
				<section class="d-flex align-items-center"> <!--Site logo-->
				<figure class="site-logo"> <a
					href="./Homepage.jsp"> <img
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
				<li class="active"><a href="./Homepage.jsp">Home Page</a></li>
				<li><a href="./WebApplications.jsp">Hybrid Web Applications</a></li>
				 <li><a href="./CucumberWebApplications.jsp">BDD Web Applications</a></li>		
				<li><a href="./MobileApplications.jsp">Mobile Applications</a></li>
				<li><a href="./WindowsApplications.jsp">Windows Applications</a></li>		
				<li><a href="./FrameworkConfig.jsp">Framework Configurations</a></li>			
			</ul>
		</div>
		</nav>
		
		<div class="container">
  <h2>SWAUT Architecture</h2>
  <div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>      
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner">

      <div class="item active">
        <img class="thumbnail img-responsive" src="assets/images/arch.png" alt="SWAUT Architecture">
        <div class="carousel-caption d-none d-md-block">
          <h3>SWAUT Architecture</h3>          
        </div>
      </div>

      <div class="item">
        <img class="thumbnail img-responsive" src="assets/images/features.png" alt="SWAUT feature">
        <div class="carousel-caption d-none d-md-block">
          <h3>Features of SWAUT</h3>          
        </div>
      </div>  
    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
</div>
</body>
</html>