package Controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Data;
import model.service;

@WebServlet("/paymentCtrl")
public class paymentCtrl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the values from the request
        String email = request.getParameter("email");
        String coinsParam = request.getParameter("coins");

        // Check if coinsParam is not null and not empty
        if (coinsParam != null && !coinsParam.isEmpty()) {
            try {
                int coins = Integer.parseInt(coinsParam);

                // Create a Data object with the retrieved values
                Data data = new Data();
                data.setEmail(email);
                data.setCoins(coins);

                // Update the balance using the service
                service s = new service(data);
                int result;
                if (request.getParameter("name") == null) {
                    result = s.newCoinsAfterDeducted();
                } else {
                    result = s.update_balance();
                }

                // Check if the balance was updated successfully
                if (result > 0) {
                    // Fetch updated data
                    List<Data> dataList = s.fetch_data();
                    // Set the fetched data as an attribute in the request object
                    request.setAttribute("userDataList", dataList);
                    // Set balance update success attribute
                    request.setAttribute("balanceUpdateSuccess", true);
                }
            } catch (Exception e) {
                // Handle the case where the coins parameter is not a valid integer
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid value for coins parameter");
                return;
            }
        } else {
            // Handle the case where the coins parameter is null or empty
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Coins parameter is missing or empty");
            return;
        }

        // Forward the request to the JSP page
        forwardToLiveStreamPage(request, response);
    }

    private void forwardToLiveStreamPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("liveStream.jsp");
        dispatcher.forward(request, response);
    }
}





