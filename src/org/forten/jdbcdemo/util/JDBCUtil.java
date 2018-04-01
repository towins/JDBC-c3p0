package org.forten.jdbcdemo.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtil {
	// 数据源
	private static final DataSource dataSourceForMySQL = new ComboPooledDataSource("mysql");
	private static final DataSource dataSourceForOracle = new ComboPooledDataSource("oracle");

	private JDBCUtil() {

	}

	public static Connection getConnectionForMySQL(boolean autoCommit) {
		Connection conn = null;
		try {
			conn = dataSourceForMySQL.getConnection();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Connection getConnectionForMySQL() {
		return getConnectionForMySQL(false);
	}

	public static Connection getConnectionForOracle(boolean autoCommit) {
		Connection conn = null;
		try {
			conn = dataSourceForOracle.getConnection();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Connection getConnectionForOracle() {
		return getConnectionForOracle(false);
	}

	public static void close(Connection conn, Statement stat) {
		try {
			if (stat != null) {
				stat.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection conn, Statement stat, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stat != null) {
				stat.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
