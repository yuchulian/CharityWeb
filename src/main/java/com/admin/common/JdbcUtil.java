package com.admin.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {
	static String driverClass;
	static String url;
	static String username;
	static String password;
	static {
		loadConfig();
	}
	//加载配置文件
	private static void loadConfig() {
		InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("prop_kit.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
			driverClass = prop.getProperty("driverClass");
			url = prop.getProperty("jdbcUrl");
			username = prop.getProperty("user");
			password = prop.getProperty("password");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public static Connection getConnetion() {
		Connection conn = null;

		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}


	public static  void close(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void close(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

		public static void close(Connection conn,Statement stmt) {		
			close(stmt);
			close(conn);
		}

	public static void close(Connection conn,Statement stmt,ResultSet rs) {
		close(rs);
		close(stmt);
		close(conn);
	}
}
