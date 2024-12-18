package dbConnections;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class Connections {
	public static String drivermanager = "oracle.jdbc.driver.OracleDriver";
	public static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	public static String user = "system";
	public static String password = "Gani143";

	public static Connection getCon() throws Exception {

		// build connection
		Class.forName(drivermanager);

		return DriverManager.getConnection(url, user, password);

	}

	public static JdbcRowSet getJdbc() throws Exception {

		RowSetFactory rs = RowSetProvider.newFactory();

		JdbcRowSet crs = rs.createJdbcRowSet();

		Class.forName(drivermanager);

		crs.setUrl(url);
		crs.setUsername(user);
		crs.setPassword(password);

		return crs;

	}

}
