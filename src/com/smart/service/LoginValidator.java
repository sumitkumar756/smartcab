package com.smart.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smart.dao.DatabaseUtility;
import com.smart.entity.Driver;
import com.smart.entity.Person;
import com.smart.entity.Traveler;

public class LoginValidator extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Person person = null;
		if(req.getParameter("type").equals("driver")){
			person = new Driver();
		}else{
			person = new Traveler();
		}
		person.setMobileNumber(req.getParameter("number"));
		person.setType(req.getParameter("type"));
		DatabaseUtility utility = new DatabaseUtility();
		person = utility.checkPerson(person);
		if (person != null) {
			HttpSession session = req.getSession(true);
			session.setAttribute("person", person);
			req.getRequestDispatcher("profile.jsp").forward(req, resp);

		} else {
			PrintWriter out = resp.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('user is not register with us!! Join us');");
			out.println("location='login.jsp';");
			out.println("</script>");
		}

	}
}
