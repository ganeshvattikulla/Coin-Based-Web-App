<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Data"%>
<%@ page import="model.Transaction"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Data and Transaction History</title>
</head>
<body>
	<h1>User Data and Transaction History</h1>
	<table border="1">
		<tr>
			<th>Name</th>
			<th>Email</th>
			<th>Password</th>
			<th>Coins</th>
		</tr>
		<%
		Data userData = (Data) request.getAttribute("ud");

		if (userData != null) {
			out.println("<tr>");
			out.println("<td>" + userData.getName() + "</td>");
			out.println("<td>" + userData.getEmail() + "</td>");
			out.println("<td>" + userData.getPassword() + "</td>");
			out.println("<td>" + userData.getCoins() + "</td>");
			out.println("</tr>");
		} else {
			out.println("<tr><td colspan='4'>No user data found for the specified email.</td></tr>");
		}
		%>
	</table>
	<br>
	<h2>Transaction History</h2>
	<table border="1">
		<tr>
			<!-- <th>Transaction ID</th> -->
			<th>Transaction Date</th>
			<th>Transaction Amount</th>
		</tr>
		<%
		List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactionData");
		if (transactions != null && !transactions.isEmpty()) {
			for (Transaction transaction : transactions) {
				out.println("<tr>");
				//out.println("<td>" + transaction.getTransactionId() + "</td>");
				out.println("<td>" + transaction.getTransactionDate() + "</td>");
				out.println("<td>" + transaction.getTransactionAmount() + "</td>");
				out.println("</tr>");
			}
		} else {
			out.println("<tr><td colspan='3'>No transaction history found for the user.</td></tr>");
		}
		%>
	</table>
</body>
</html>
