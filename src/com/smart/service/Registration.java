package com.smart.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.dao.DatabaseUtility;
import com.smart.entity.Driver;
import com.smart.entity.Person;
import com.smart.entity.Traveler;

public class Registration extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		DatabaseUtility utility = new DatabaseUtility();

		Person person = null;
		if (req.getParameter("type").equals("driver")) {
			person = new Driver();
		} else {
			person = new Traveler();
		}
		person.setName(req.getParameter("name"));
		person.setMobileNumber(req.getParameter("number"));
		person.setType(req.getParameter("type"));
		boolean resgitered = utility.register(person);
		PrintWriter out = resp.getWriter();
		out.println("<script type=\"text/javascript\">");
		if (resgitered) {
			out.println("alert('Registeration successfull as " + person.getType() + " Please login!!');");
			out.println("location='login.jsp';");
			out.println("</script>");

		} else {
			out.println("alert('incorrect data please try again');");
			out.println("location='register.jsp';");
			out.println("</script>");
		}
	}

}
