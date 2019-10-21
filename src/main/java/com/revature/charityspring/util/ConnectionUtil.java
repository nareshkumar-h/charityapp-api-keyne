package com.revature.charityspring.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.revature.charityspring.exception.DBException;



public class ConnectionUtil {

	private static final String DRIVERCLASSNAME = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://trainingdb.ck1ayq0lncmp.ap-south-1.rds.amazonaws.com:3306/keyne_db";
	private static final String USERNAME = "keyne";
	private static final String PASSWORD = "keyne";

	public static Connection getConnection() throws DBException {
		
		Connection con = null;

		try {
			Class.forName(DRIVERCLASSNAME);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		} catch (ClassNotFoundException e) {

			throw new DBException(MessageConstant.DRIVER_CLASS , e);
		} catch (SQLException e) {

			throw new DBException(MessageConstant.SQL_CONNECTION, e);
		}

		return con;

	}


	public static void close(Connection con, PreparedStatement pst) throws DBException {

		try {
			if (pst != null)
				pst.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			throw new DBException(MessageConstant.CLOSE_CONNECTION,e);

		}

	}

	public static void close(Connection con, PreparedStatement pst, ResultSet rs) throws DBException {
		try {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			if (con != null)
				con.close();

		} catch (Exception e) {
			throw new DBException(MessageConstant.CLOSE_CONNECTION,e);

		}

	}

	public static void close(Connection con, Scanner scn) throws DBException {
		try {
			if (scn != null)
				scn.close();
			if (con != null)
				con.close();

		} catch (Exception e) {
			throw new DBException(MessageConstant.CLOSE_CONNECTION,e);

		}

	}
}
