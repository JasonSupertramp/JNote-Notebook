package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Article;
import bean.ArticleProperty;
import db.SqliteConnect;

public class ArticleDao {
	private static String sql = null;
	private static PreparedStatement ps = null;
	private static Connection con = null;
	private static ResultSet rs = null;

	public static void insert(Article art) {
		sql = "INSERT INTO Article (categoryId,titleName,articleContent,createTime) " + "VALUES('" + art.getCategoryId()
				+ "'" + "," + "'" + art.getTitleName() + "'" + "," + "'" + art.getArticleContent() + "'" + "," + "'"
				+ art.getCreateTime() + "')";
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
		sql = "SELECT articleId,categoryId,titleName,articleContent,createTime FROM Article";
		con = SqliteConnect.getConnector();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static void delete(ArticleProperty art) {
		sql = "DELETE FROM Article WHERE articleId=" + art.getArtId();
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

	public static void update(Article art) {
		sql = "UPDATE Article SET categoryId='" + art.getCategoryId() + "'" + "," + "titleName='" + art.getTitleName()
				+ "'" + "," + "articleContent='" + art.getArticleContent() + "'" + "," + "createTime='"
				+ art.getCreateTime() + "'" + "WHERE articleId=" + art.getArticleId();
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
