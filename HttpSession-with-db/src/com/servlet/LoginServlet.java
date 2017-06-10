package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.db.TempDB;
import com.model.Employee;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session!=null)
		{
			doPost(request, response);
		}
		else
		{
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<h2>Please login here!</h2>");
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
			dispatcher.include(request, response);
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(username=="" || password=="")
		{
			out.print("<h3>Username and Password is incorrect!</h3>");
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
			dispatcher.include(request, response);
		}
		else
		{
			JSONObject emp = TempDB.getData(username,password);

			if(emp!=null)
			{
				Employee ee=new Employee(emp.getString("name"), emp.getString("email"), emp.getString("mobile"), emp.getString("password"));
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", ee);
				
				out.print("<h2>You have succesfully Login! <br> Welcome Home Page</h2>");
				RequestDispatcher dispatcher = request.getRequestDispatcher("HomeServlet");
				dispatcher.include(request, response);
			}
			else
			{
				out.print("<h3>Username and Password is incorrect!</h3>");
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
				dispatcher.include(request, response);
			}
			
		}
		

	}

}
