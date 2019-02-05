package com.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class runbycategory
 */
@WebServlet("/auditconfirmations/runbycategory")
public class runbycategory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String[] chkbxValues = request.getParameterValues("chbox");
		//System.out.println("++++++++++++++++"+request.getParameterValues("chbox"));
		/*for (int i=0; i<chkbxValues.length; i++){
			out.println(chkbxValues[i]);
		}*/
		String resultMessage = "";
		try 
        {
			DisplayScenarioOnNewTours displayScenarioOnAuditconfirmation = new DisplayScenarioOnNewTours();
			displayScenarioOnAuditconfirmation.removeRunTag();
			//displayScenarioOnAuditconfirmation.addTag(chkbxValues);
			displayScenarioOnAuditconfirmation.StartCucumberExecution();
            resultMessage = "Execution is in progress, you will be able to see reports when execution completed.";
        }
        catch (Exception ex)  {
            ex.printStackTrace();
            resultMessage = "There were an error with execution: " + ex.getMessage();
        }
        finally{    
        	request.setAttribute("Message", resultMessage);
            getServletContext().getRequestDispatcher("/runbycategoryresult.jsp").forward(request, response);
        }
		
		
	}
}