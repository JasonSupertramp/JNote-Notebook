package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnect {
	private static Connection con = null;

	public static Connection getConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:./src/resources/notebook.db");
			con.setAutoCommit(true);
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
