package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.User;
import db.SqliteConnect;

public class UserDao {
	private static String sql = null;
	private static PreparedStatement ps = null;
	private static Connection con = null;
	private static ResultSet rs = null;

	public static void insert(User user) {
		sql = "INSERT INTO User (username,password) " + "VALUES('" + user.getUsername() + "'" + "," + "'"
				+ user.getPassword() + "'" + ")";
		con = SqliteConnect.getConnector();
		try {
			ps = con.prepareStatement(sql);
			ps.execute();
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet select() {
		sql = "SELECT username,password FROM User";
		con = SqliteConnect.getConnector();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
