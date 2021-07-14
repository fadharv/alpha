package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RegistrationDAO;

public class FormReader extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String age = request.getParameter("age");

		boolean check = true;
		String errors = "";

		// Validate email address
		if (!email.contains("@") || !email.contains(".")) {
			check = false;
			errors += "Invalid Email Address<br><br>";
		}

		// Validate phone number
		if (phone.length() != 10) {
			check = false;
			errors += "Invalid Phone Number<br><br>";
		}

		int ageValue = 0;
		
		// Validate age (if its a number)
		try {

			ageValue = Integer.parseInt(age);

		} catch (NumberFormatException e) {
			check = false;
			errors += "Invalid Age<br><br>";
		}

		// ONLY SHOW THANK YOU MESSAGE IF THE FORM FIELDS ARE VALID
		if (check == true) {
			
			//INSERT data into the database
			RegistrationDAO dao = new RegistrationDAO();
			String fullName = fname + " " + lname;
			dao.insertData(fullName, email, phone, ageValue);
			
			response.getWriter().println(
					"<html><h1><font color = blue>Thank you " + fname + " "
							+ lname + " for registering </font></h1></html>");
			
		} else {
			response.getWriter()
					.println(
							"<html><h1><font color = red>" + errors + "</font></h1></html>");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Call the doGet method and this will help us hide the form fields
		doGet(request, response);
	}

}
