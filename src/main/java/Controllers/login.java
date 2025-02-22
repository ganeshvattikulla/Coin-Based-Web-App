package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Data;
import model.service;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {

	public login() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter o = response.getWriter();
		Data d = new Data();
		String Email = request.getParameter("email");
		d.setEmail(Email);
		d.setPassword(request.getParameter("password"));

		PrintWriter out = response.getWriter();

		service s = new service(d);

		boolean b = s.check_login();

		if (b) {

			// Set email as a session attribute
			HttpSession session = request.getSession();
			session.setAttribute("email", Email);

			// Fetch data from the database
			List<Data> dataList = s.fetch_data();

			// Set the fetched data as an attribute in the request object
			request.setAttribute("userDataList", dataList);
			// Forward the request to the JSP page
			RequestDispatcher dispatcher = request.getRequestDispatcher("liveStream.jsp");
			dispatcher.forward(request, response);

		} else {
			out.println("<script>alert('Invalid email or password. Please try again.');</script>");
			RequestDispatcher r = request.getRequestDispatcher("LoginPage.html");
			r.include(request, response);

		}

	}

}
