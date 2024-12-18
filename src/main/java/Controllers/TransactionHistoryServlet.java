package Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Data;
import model.service;


@WebServlet("/TransactionHistoryServlet")
public class TransactionHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public TransactionHistoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Data userData = new Data();
		userData.setEmail(request.getParameter("email"));
		 service s=new service(userData);
		userData= s.fetchUserDataWithTransactions();
		
		 // Check if userData is not null
        if (userData != null) {
            // Set userData as an attribute in the request object
            request.setAttribute("ud", userData);
            request.setAttribute("transactionData", userData.getTransactions());

            // Forward the request to history.jsp page
            request.getRequestDispatcher("history.jsp").forward(request, response);
        } else {
            // If userData is null, handle the case appropriately (e.g., display an error message)
            response.getWriter().println("No user data found for the specified email.");
        }
	}

	

}
