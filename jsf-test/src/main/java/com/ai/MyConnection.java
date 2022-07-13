package com.ai;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
	static Connection con= null;
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jsf","root","hein");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Class not found");
		} catch (SQLException e){
			System.out.println("Database Error");
		}
		
		return con;
	}
}
