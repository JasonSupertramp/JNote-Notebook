package util;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.UserDao;

public class CheckLogin {
	private static ResultSet rs = null;
	private static boolean flag = false;

	public static boolean isLogin(String iuser, String ipass) throws IOException {
		rs = UserDao.select();
		try {
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");

				if ((username.equals(iuser)) & (password.equals(ipass))) {
					flag = true;
					rs.close();
					return flag;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}
}
