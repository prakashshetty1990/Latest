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
<script src="cssjs/css/bootstrap.min.js"></script>
</head>
<body>
	<div class="wrapper">
		<!-- Top header -->
		<div class="header-container">
			<header class="top-header shadow-2dp"> <section
				class="d-flex justify-content-between align-items-center">
			<div class="col-sm-6 col-lg-6">
				<%
					Common common = new Common();
					String baseurl = common.getProperty("baseurl");
				%>
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
		<section class="d-flex"> 
			<main class="main-area col p-3">
				<div class="area-section">
					<section class="d-flex">
						<div class="col-12">
							<section class="cards-wrapper"> 
								<header	class="headline pb-3 mb-4 border-bottom"> 
									<section class="d-flex flex-wrap align-items-center mt-4">
										<section class="  p-0 d-flex align-items-center animate mr-2 mb-2 rounded shadow-2dp db-card handhover"					 
					 							onclick="window.location.href='./web/adactin/default.jsp'">
												<div class="image align-items-center">
													<figure class="card-icon m-0 "> 
														<img src="assets/images/adactinlogo.png" alt="" /> 
													</figure>
												</div>				
												<div class="card-title">Web - Adactin</div>
										</section>
									</section>
								</header>
							</section>
						</div>
					</section>				
				</div>
			</main>
		</section>			
	</div>
</body>
</html>