package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bean.Category;
import bean.CategoryProperty;
import db.SqliteConnect;

public class CategoryDao {
	private static String sql = null;
	private static PreparedStatement ps = null;
	private static Connection con = null;
	private static ResultSet rs = null;

	public static void insert(Category cat) {
		sql = "INSERT INTO Category (categoryName,categoryCreateTime) " + "VALUES('" + cat.getCategoryName() + "'" + ","
				+ "'" + cat.getCategoryCreateTime() + "')";
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
		sql = "SELECT id,categoryName,categoryCreateTime FROM Category";
		con = SqliteConnect.getConnector();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static void delete(CategoryProperty cat) {
		sql = "DELETE FROM Category WHERE ID=" + cat.getCatId();
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

	public static void update(CategoryProperty cat) {
		sql = "UPDATE Category SET categoryName='" + cat.getCatName() + "'" + "," + "categoryCreateTime='"
				+ cat.getCatCreateTime() + "'" + "WHERE ID=" + cat.getCatId();
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
}
