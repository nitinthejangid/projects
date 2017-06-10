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

import com.model.Employee;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	
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
		HttpSession session =	 request.getSession(false);
		if(session!=null)
		{
			Employee emp = (Employee)session.getAttribute("currentUser");
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("Name: " + emp.getName() + "<br>");
			out.println("Email: " + emp.getEmail() + "<br>");
			out.println("mobile: " + emp.getMobile() + "<br>");
			out.println("password: " + emp.getPassword() + "<br>");
			
			//out.println("<a href='/LogoutServlet'>Logout</a>");
			//out.println("<form action='LogoutServlet'>");
			out.println("<form action='LogoutServlet' method='post'>");
            out.println("<input type='submit'  value='Logout'>");  
            out.println("</form>");
            
            session.setMaxInactiveInterval(60);
			
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

}
