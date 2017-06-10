package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.db.TempDB;
import com.model.Employee;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String mobile = request.getParameter("mobile");
			String password = request.getParameter("password");
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			
			if(name==""||email==""||mobile==""||password=="")
			{
				out.print("<h3>*All field are mandotory</h3>");
				RequestDispatcher dispatcher = request.getRequestDispatcher("registration.html");
				dispatcher.include(request, response);
			}
			else
			{
				//Password encoding
				String passwordToHash = password;
				// Create MessageDigest instance for MD5
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            //Add password bytes to digest
	            md.update(passwordToHash.getBytes());
	            //Get the hash's bytes 
	            byte[] bytes = md.digest();
	            //This bytes[] has bytes in decimal format;
	            //Convert it to hexadecimal format
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            //Get complete hashed password in hex format
	            String generatedPassword = sb.toString();
	            
	            
	            System.out.println("Password:>>>" + generatedPassword);
				
				
				
				Employee emp = new Employee(name,email,mobile,password);
				TempDB.saveData(emp);
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", emp);
				
				out.print("<h2>You have succesfully Registered! <br> Welcome Home Page</h2>");
				RequestDispatcher dispatcher = request.getRequestDispatcher("HomeServlet");
				dispatcher.include(request, response);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		
		
		
	}

}
