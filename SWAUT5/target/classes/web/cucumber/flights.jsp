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
<link rel="stylesheet" href="../../cssjs/css/bootstrap.min.css">
<link rel="stylesheet" href="../../cssjs/css/bootstrap-grid.min.css">
<link rel="stylesheet" href="../../cssjs/css/bootstrap-grid.css">
<link rel="stylesheet" href="../../cssjs/css/flexboxgrid.css">
<link rel="stylesheet" href="../../cssjs/css/navbar.less">
<link rel="stylesheet" href="../../cssjs/css/app.css">
<link rel="stylesheet" href="../../cssjs/css/flexboxgrid.min.css">
<script src="../../cssjs/js/bootstrap.min.js"></script>
<script src="../../cssjs/js/anchor.min.js"></script>
<script src="../../cssjs/js/popper.min.js"></script>
<script src="../../cssjs/js/jquery-slim.min.js"></script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="../../cssjs/js/jquery-1.9.1.min.js"></script> 
</head>

<body>

	<script type="text/javascript">
	function showHide(self, formId){
    var list = document.getElementsByTagName("form");    
    for(var i=0;i<list.length; i++){   		
 		if(self.parentElement.children[i] == self){
 			self.parentElement.children[i].classList.add('active');
 		} else {
 			self.parentElement.children[i].classList.remove('active');
 		} 		
	    if(list[i].id === formId){
	     document.getElementById(formId).classList.remove('d-none');
	     document.getElementById(formId).classList.add('d-block');
	    } else {
	        list[i].classList.remove('d-block');
	        list[i].classList.add('d-none');
   		}
    }
}
	
	function openExcel(el){
		window.open(el.href);
	}

 	function _toggleCollapse(el){
		var id = el.getAttribute('data-target').split('#')[1];
		var el = document.getElementById(id);
		if(el.classList.contains('show')){
			el.classList.remove('show')
		} else {
			el.classList.add('show')
		}
	} 
 	function _saveData(){
		<%DisplayScenarioOnNewTours objDisplayScenario = new DisplayScenarioOnNewTours("F2_FlightFinder");
		String baseUrl = objDisplayScenario.readBaseUrl();%>
		window.location.href = "../../web/cucumber/flights.jsp?updatedTextData=" + ValueToBeSaved;
		ValueToBeSaved=[];
		<%String UpdatedData = request.getParameter("updatedTextData");
		System.out.println("Values are" + UpdatedData);
		if (UpdatedData != null)
    	{
			DisplayScenarioOnNewTours objUpdateS = new DisplayScenarioOnNewTours();
			objUpdateS.updateData(UpdatedData);
    	}%>
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
			<header class="top-header shadow-2dp">
				<section class="d-flex justify-content-between align-items-center">
					<div class="col-sm-6 col-lg-6">
						<section class="d-flex align-items-center">
							<!--Site logo-->
							<figure class="site-logo">
								<a href="../../Homepage.jsp"> <img
									src="../../cssjs/css/suntechlogo.jpg" class="img-fluid"
									alt="SWAUT User Interface">
								</a>
							</figure>
							<!-- Header Nav / Single Nav Item -->
							<nav class="col header-nav border-left ml-4">
								<ul class="d-flex align-items-center list-unstyled mb-2 py-2">
									<li><h4>SWAUT Automation Dashboard</h4></li>
								</ul>
							</nav>
						</section>
					</div>

				</section>
			</header>
		</div>

		<nav class="navbar navbar-expand-lg navbar-inverse">
			<div class="container-fluid">
				<!-- <div class="navbar-header">
				<a class="navbar-brand" href="#">WebSiteName</a>
			</div> -->
				<ul class="nav navbar-nav">
					<li><a href="../../web/cucumber/homepage.jsp">New Tours</a></li>
					<li class="active" class="dropdown"><a class="dropdown-toggle"
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
				<li class="breadcrumb-item"><a
					href="../../CucumberWebApplications.jsp">BDD Web Applications</a></li>
				<li class="breadcrumb-item"><a
					href="../../web/cucumber/homepage.jsp">New Tours</a></li>
				<li class="breadcrumb-item active" aria-current="page">Test
					Execution By Test Cases</li>
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
						<form name="modules" id="modules" onsubmit="checkBoxValidation()">
							<!-- Area Header  -->
							<h6>New Tours</h6>
							<hr />
							<div class="data-form-wrapper shadow-2dp p-3 bg-white">
								<div class="d-flex align-items-center justify-content-between">
									<h6>Select Test Scenarios</h6>
									<div data-guide="6" data-guide-title="Select All Deselect All"
										data-guide-desc="user can select scenario for execution."
										data-guide-parent="mein-menu"
										data-guide-placement="bottom right"
										class="d-flex justify-content-end align-items-center mt-3  mb-3 text-blue">
										<a class="btn btn-link" onClick="_checkAll()">Select all</a> <a
											style="margin-top: -5px;"> | </a> <a class="btn btn-link"
											onClick="_uncheckAll()">Deselect all</a>
									</div>
								</div>

								<div class="data-form-wrapper shadow-2dp p-3 bg-white">
									<%								DisplayScenarioOnNewTours objDisplayScenario1 = new DisplayScenarioOnNewTours("F2_FlightFinder");
									   objDisplayScenario1.removeRunTag();
									   objDisplayScenario1.addRunTagTestRunner();
																	String scenario[] = new String[objDisplayScenario1.count + 1];
																	for (int i = 1; i < objDisplayScenario1.count + 1; i++) {
																		scenario[i] = objDisplayScenario1.DisplayScenario(i);
									%>
									<div class="form-check">
										<!-- <div class="form-check-input"> -->
										<input name="1" type="checkbox" class="form-check-input"
											id="chk<%=scenario[i]%>" data-target="#<%=scenario[i]%>"
											onclick="_toggleCollapse(this)" data-toggle="collapse"
											value="<%=scenario[i]%>"> <label
											for="chk<%=scenario[i]%>" class="form-check-label"											
											aria-controls="<%="     "+scenario[i]%>"
											for="<%=scenario[i]%>"> <%="     "+scenario[i]%></label>
									</div>
									<div class="child-el pl-4 collapse" id="<%=scenario[i]%>"
										aria-labelledby="headingOne" data-parent="#accordion">
										<div class="form-check">
											<%String steps = objDisplayScenario1.scenarioSteps.get(i - 1).replace("[", "");
																String innerTable = "";
																String TableStruct = "";
																String stepsWithoutTable = "";
																steps = steps.replace("]", "");
																steps = steps.replace("<", "");
																steps = steps.replace(">", "");
																steps = steps.replace(",", "</br>");
																steps = steps.replace("@Run", "");
																if (steps.contains("Examples:")) {
																	stepsWithoutTable = "<div>" + steps.split("Examples:")[0] + "</div><b>Example Data :</b> ";
																	innerTable = steps.split("Examples:")[1];
																} else {
																	stepsWithoutTable = "<div>" + steps + "</div>";
																}
																stepsWithoutTable += "Click on data grid to update test data";%>
											<label class="form-check-label labelStyle"
												for="Add_child-add1"> <%=stepsWithoutTable%>
											</label>
											<div class="scrollbar-container">
												<div class="inner">
													<%innerTable = innerTable.trim().replace("  ", "");
																		if (innerTable.length() > 0) {
																			String thValue = innerTable.replace("|", "<th>").toString().split("</br>")[1].toString();
																			TableStruct = "<br/><table class='innerTableData tablewidth thfont' border='1' cellpadding='10'><thead><tr align='center' class='innerTableTh'>"
																					+ thValue + "</tr></thead>";

																			for (int j = 0; j <= innerTable.split("</br>").length - 1; j++) {
																				if (j > 1) {
																					String replaceValue = "</span></td><td style='text-align: center;'><input type='text' class='displayNone wordbrkall' value='' /><span class='lblValue wordbrkall'>";
																					String tdValue = innerTable.replace("|", replaceValue).toString().split("</br>")[j]
																							.toString();
																					String oldValue = tdValue.replace(
																							"</span></td><td><input type='text' class='displayNone' value='' /> <span class='lblValue'>",
																							" | ");
																					TableStruct += "<tr class='innerTableTr'>" + tdValue;
																				}
																				TableStruct += "</tr>";
																			}
																		}
																		TableStruct += "</table>";%>
													<div><%=TableStruct%></div>
												</div>
											</div>
										</div>
									</div>
									<br />
									<%
										}
									%>
									<div class="d-flex justify-content-end mt-3 px-3">
										<button id="btnStartExecution" data-guide="7"
											data-guide-title="Start Execution button"
											data-guide-desc="The start execution button has incorporated the functionality of getting started with the application scenarios."
											data-guide-parent="mein-menu"
											data-guide-placement="bottom right"
											class="btn btn-primary mr-3" type="submit" value="submit"
											onClick="_startExecution()">Start Execution</button>

									</div>
								</div>
							</div>
							<script>
								
							<%for (int k = 1; k < objDisplayScenario1.count + 1; k++) {
				String selectedTCList[] = request.getParameterValues(String.valueOf(k));
				if (selectedTCList != null) {
					for (int j = 0; j < selectedTCList.length; j++) {
						objDisplayScenario1.UpdateFile(selectedTCList[j]);
					}
				}
			}%>
				function _startExecution() {
				<%for (int k = 1; k < objDisplayScenario1.count + 1; k++) {
				String selectedTCList[] = request.getParameterValues(String.valueOf(k));
				if (selectedTCList != null) {
					/* if(objDisplayScenario1.readRunFlag().equals("Y"))
					{ */
						Common common1 = new Common();
						 String path = common1.getProperty("CucumberWorksapce");
						 String currDir1 = common1.getProjectPath();			 
						 /* String[] parms = {"cmd.exe", "/c","start"+ currDir1+"/assets/vbs/adactin.bat"};
						 String arg = "cmd /c "+ currDir1+"/assets/vbs/adactin.bat";
						 System.out.println(arg); */
						 Process pr = Runtime.getRuntime().exec("cmd /c "+ currDir1+"\\assets\\vbs\\cucumber.bat");	
						BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));			 
			            String line=null;
			            while((line=input.readLine()) != null) {
			                System.out.println(line);
			            }            
						System.out.println("Execution Completed");					
				}
			}%>
					}
							</script>
						</form>
						<div id="dvLoader"
							class="col-xs-12 col-md-12 col-lg-12 text-center">
							<i style="margin: 25%" class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>
						</div>
						<!-- <div class="tbl-layout"></div> -->
					</div>
				</div>
			</div>
			</main>
		</section>
	</div>
</body>
</html>