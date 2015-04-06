package models.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcCon {

	public final JdbcCon instance = new JdbcCon();

	private JdbcCon() {

	}

	public static Connection getConnection(String url, String userName,
			String password) {
		try {
			return DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
