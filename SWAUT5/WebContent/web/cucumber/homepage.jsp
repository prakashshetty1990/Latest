<%@page language="java"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.demo.*"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Automation Module</title>
<meta charset="utf-8">
<link rel="shortcut icon" type="image/png"
	href="../../assets/images/logo/logo_icon.png" />
<link rel="stylesheet" href="../../cssjs/css/bootstrap.css">
<link rel="stylesheet" href="../../cssjs/css/flexboxgrid.css">
<link rel="stylesheet" href="../../cssjs/css/flexboxgrid.min.css">
<script src="../../cssjs/js/bootstrap.min.js"></script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
	<script>
		function showHide(self, formId) {
			var list = document.getElementsByTagName("form");
			for (var i = 0; i < list.length; i++) {
				if (self.parentElement.children[i] == self) {
					self.parentElement.children[i].classList.add('active');
				} else {
					self.parentElement.children[i].classList.remove('active');
				}
				if (list[i].id === formId) {
					document.getElementById(formId).classList.remove('d-none');
					document.getElementById(formId).classList.add('d-block');
				} else {
					list[i].classList.remove('d-block');
					list[i].classList.add('d-none');
				}
			}
		}

		function openExcel(el) {
			window.open(el.href);
		}

		function _toggleCollapse(el) {
			var id = el.getAttribute('data-target').split('#')[1];
			var el = document.getElementById(id);
			if (el.classList.contains('show')) {
				el.classList.remove('show')
			} else {
				el.classList.add('show')
			}
		}

		$(document).ready(
				function() {
					$("div#dvLoader").hide();
					$("body").on("click",".btn-Save",
							function() {
								ValueToBeSaved = [];
								$(this).closest("tr").find("td").find("input[type=text]").each(
										function(value) {
											ValueToBeSaved.push($(this).val());
										});
								_saveData();
							});

					$("#btnStartExecution").click(function() {
						$("div#dvLoader").show();
						setTimeout(function() {
							$("div#dvLoader").hide();
						}, 12000);
					});

					$("body").on("click",".innerTableTr",
									function() {
										var currentRow = $(this);
										currentRow.each(function(key) {
													$(this).find("td").each(
																	function(key,value) {
																		$(this).find(".btn-Save").removeAttr('disabled');
																		if ($(this).find("input[type=text]").hasClass('displayNone')) {
																			$(this).find("input[type=text]").removeClass('displayNone');
																			var CurrentValue = $(this).find("span.lblValue").html().trim();
																			$(this).find("input[type=text]").val(CurrentValue);
																			$(this).find(".btn-Save").removeAttr("disabled");
																			$(this).find("span.lblValue").addClass('displayNone');
																		}
																	});
												});
									});
					$(".innerTableData").find('th:last-child').html("Action");
					$(".innerTableData").find('td:last-child').html(
									"<input  type='button' disabled class='btn btn-primary btn-Save' name='btnsave' value='Save'>");
				});	
	</script>

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
				<li  class="active"><a href="../../web/cucumber/homepage.jsp">New Tours</a></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="../../web/cucumber/flights.jsp">Test
						Case Execution </a>
					<ul class="dropdown-menu">
						<li><a href="../../web/cucumber/flights.jsp">Execution
								By Test Cases</a></li>
						<li><a href="#">Execution By Category</a></li>
					</ul></li>				
				<li><a href="../../web/cucumber/report.jsp">Results</a></li>
			</ul>
		</div>
		</nav>
		<nav aria-label="breadcrumb">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="">Home</a></li>
			<li class="breadcrumb-item"><a href="../../CucumberWebApplications.jsp">BDD Web Applications</a></li>
			<li class="breadcrumb-item active"><a
				href="../../web/cucumber/homepage.jsp">New Tours</a></li>			
		</ol>
		</nav>
		<!-- /Top header -->
		<section class="d-flex body-wrapper fhlb-bg-light">
			<main class="main-area col p-0">
			<div class="area-section h-100 d-flex">
				<script>
					$(document).ready(function(e) {
						$('.item-module').click(function(e) {
							$('.list-unstyled > li').removeClass('active');
							$(this).parent().addClass('active');
							event.stopPropagation();
							$('.sub-menu').toggle();
						})

						$(document).click(function() {
							$('.sub-menu').hide();
						});
					})
					function _checkAll() {
						$('.form-check-input').prop("checked", true);
						$('.collapse').addClass("show");
					}
					function _uncheckAll() {
						$('.form-check-input').prop("checked", false);
						$('.collapse').removeClass("show");
					}
				</script>

				<div class="col">
					<div class="content-area no-padding">
						<form name="homepage" id="homepage"
							onsubmit="checkBoxValidation()">
						</form>

						<section
							class="area-header row align-items-center justify-content-between">
							<header class="no-padding col h5 mb-4 pb-3 border-bottom">Application Overview </header>
						</section>												
						</div>					
					</div>
				</div>
			</main>
		</section>
	</div>
</body>
</html>
