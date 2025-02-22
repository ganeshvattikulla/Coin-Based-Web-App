package Controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Data;
import model.service;

/**
 * Servlet implementation class regCtrl
 */
@WebServlet("/regCtrl")
public class regCtrl extends HttpServlet {

	public regCtrl() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Data d = new Data();
		d.setName(request.getParameter("Username"));
		d.setEmail(request.getParameter("email"));
		d.setPassword(request.getParameter("password"));
		service s = new service(d);
		try {
			int a = s.saveData();
			if (a > 0) {
				System.out.println("user Registered..");
				RequestDispatcher dispatcher = request.getRequestDispatcher("LoginPage.html");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
