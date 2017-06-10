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

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		HttpSession session = request.getSession(false);
		if(session!=null)
		{
			doPost(request, response);
			System.out.println("get if.......>>>>>>>>");
		}
		else
		{
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<h2>Please login here!</h2>");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
			dispatcher.include(request, response);
			System.out.println("get else.......>>>>>>>>");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session!=null)
		{
			System.out.println("not null---->");
			session.invalidate();
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
			dispatcher.forward(request, response);
			System.out.println("post if.......>>>>>>>>");
			
		}
		else
		{
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
			dispatcher.forward(request, response);
			System.out.println("post else.......>>>>>>>>");
		}
		
	}

}
