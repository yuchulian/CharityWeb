package com.admin.common;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.*;



public class BaseDao {
	
	QueryRunner  qr = new QueryRunner();
	
	public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
		Connection con = JdbcUtil.getConnetion();
		T result = qr.query(con, sql, rsh);
		JdbcUtil.close(con);
		return result;
	}

	public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
		Connection con = JdbcUtil.getConnetion();
		T result = qr.query(con, sql, rsh, params);
		JdbcUtil.close(con);
		return result;
	}
	
	public int update(String sql) throws SQLException {
		Connection con = JdbcUtil.getConnetion();
		int result = qr.update(con, sql);
		JdbcUtil.close(con);
		return result;
	}

	public int update(String sql, Object... params) throws SQLException {
		Connection con = JdbcUtil.getConnetion();
		int result = qr.update(con, sql, params);
		JdbcUtil.close(con);
		return result;
	}

	public int[] batch(String sql, Object[][] params) throws SQLException {
		Connection con = JdbcUtil.getConnetion();
		int[] result = qr.batch(con, sql, params);
		JdbcUtil.close(con);
		return result;
	}
}
