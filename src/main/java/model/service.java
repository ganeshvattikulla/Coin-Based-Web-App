package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.sql.rowset.JdbcRowSet;

import dbConnections.Connections;

public class service {
	static Connection con;
	private Data d;

	public service() {

	}

	public service(Data d) {
		this.d = d;
	}

	public int saveData() throws Exception {

		// TODO Auto-generated method stub
		con = Connections.getCon();
		PreparedStatement pst = con.prepareStatement("insert into user_Data values(?,?,?,?)");
		pst.setString(1, d.getName());
		pst.setString(2, d.getEmail());
		pst.setString(3, d.getPassword());
		pst.setInt(4, 0);
		return pst.executeUpdate();

	}

	public boolean check_login() {

		boolean b = false;

		try {
			JdbcRowSet jr = Connections.getJdbc();

			jr.setCommand("select * from user_Data where email=? ");

			jr.setString(1, d.getEmail());

			jr.execute();

			while (jr.next()) {
				String storedPassword = jr.getString("password");
				if (d.getPassword().equals(storedPassword)) {
					return true; // Login successful

				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return b;

	}

	public List<Data> fetch_data() {
		List<Data> dataList = new ArrayList<>();

		JdbcRowSet jr;
		try {
			jr = Connections.getJdbc();
			jr.setCommand("select * from user_Data where email=?");
			jr.setString(1, d.getEmail());
			jr.execute();

			while (jr.next()) {
				Data data = new Data();
				data.setName(jr.getString("name"));
				data.setEmail(jr.getString("email"));
				data.setPassword(jr.getString("password"));
				data.setCoins(jr.getInt("coinblc"));
				dataList.add(data);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataList;

	}

	public int update_balance() {
		try {
			con = Connections.getCon();
			PreparedStatement pst = con.prepareStatement("UPDATE user_Data SET coinblc = coinblc + ? WHERE email = ?");
			pst.setInt(1, d.getCoins()); // Index starts from 1
			pst.setString(2, d.getEmail()); // Index starts from 1
			int rowsUpdated = pst.executeUpdate();
			// Insert transaction record into transactions table
			if (rowsUpdated > 0) {
				PreparedStatement insertStatement = con
						.prepareStatement("INSERT INTO transactions (email, transaction_amount) VALUES (?, ?)");
				insertStatement.setString(1, d.getEmail());
				insertStatement.setInt(2, d.getCoins());
				insertStatement.executeUpdate();
			}

			return rowsUpdated;
		} catch (Exception e) {
			e.printStackTrace(); // Always log or handle exceptions
		}
		return 0;
	}

	public int newCoinsAfterDeducted() throws Exception {

		con = Connections.getCon();
		PreparedStatement pst = con.prepareStatement("UPDATE user_Data SET coinblc = ? WHERE email = ?");
		pst.setInt(1, d.getCoins()); // Index starts from 1
		pst.setString(2, d.getEmail()); // Index starts from 1
		int rowsUpdated = pst.executeUpdate();
		return rowsUpdated;

	}

	public Data fetchUserDataWithTransactions() {

		try {
			JdbcRowSet jdbcRowSet = Connections.getJdbc();

			// Prepare SQL query to fetch user data based on email
			String userSql = "SELECT * FROM user_data WHERE email=?";
			jdbcRowSet.setCommand(userSql);
			jdbcRowSet.setString(1, d.getEmail());
			jdbcRowSet.execute();

			// If user data is found, populate the d object
			if (jdbcRowSet.next()) {
				d.setName(jdbcRowSet.getString("NAME"));
				d.setEmail(jdbcRowSet.getString("EMAIL"));
				d.setPassword(jdbcRowSet.getString("PASSWORD"));
				d.setCoins(jdbcRowSet.getInt("COINBLC"));
			}

			// Prepare SQL query to fetch transaction history based on email
			String transactionSql = "SELECT * FROM transactions WHERE email=?";
			jdbcRowSet.setCommand(transactionSql);
			jdbcRowSet.setString(1, d.getEmail());
			jdbcRowSet.execute();

			// If transaction history is found, populate the d object with
			// transactions
			if (d != null) {
				List<Transaction> transactions = new ArrayList<>();
				try {
					while (jdbcRowSet.next()) {
						Transaction transaction = new Transaction();
						int transactionId = jdbcRowSet.getInt("TRANSACTION_ID");
						if (jdbcRowSet.wasNull()) {
							// Handle NULL value for TRANSACTION_ID if necessary
						}
						transaction.setTransactionId(transactionId);

						Timestamp transactionDate = jdbcRowSet.getTimestamp("TRANSACTION_DATE");
						if (jdbcRowSet.wasNull()) {
							// Handle NULL value for TRANSACTION_DATE if necessary
						}
						transaction.setTransactionDate(transactionDate);

						double transactionAmount = jdbcRowSet.getDouble("TRANSACTION_AMOUNT");
						if (jdbcRowSet.wasNull()) {
							// Handle NULL value for TRANSACTION_AMOUNT if necessary
						}
						transaction.setTransactionAmount(transactionAmount);

						transactions.add(transaction);
					}
					d.setTransactions(transactions);
				} catch (SQLException e) {
					// Handle SQL exception
					e.printStackTrace(); // Log the exception for debugging
					// You may want to throw or handle the exception appropriately
				} finally {
					try {
						if (jdbcRowSet != null) {
							jdbcRowSet.close();
						}
					} catch (SQLException e) {
						e.printStackTrace(); // Log the exception for debugging
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}

}
