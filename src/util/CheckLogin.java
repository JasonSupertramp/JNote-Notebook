package util;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.UserDao;

public class CheckLogin {
	private static ResultSet rs = null;
	private static int userId = -1;

	public static int isLogin(String iuser, String ipass) throws IOException {
		rs = UserDao.select();
		try {
			while (rs.next()) {
				int userId = rs.getInt("userId");
				String username = rs.getString("username");
				String password = rs.getString("password");

				if ((username.equals(iuser)) & (password.equals(ipass))) {
					rs.close();
					return userId;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
	}
}
