package com.mincom.rest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconnnection {

	private static Connection connection= null;
	private static String server = "localhost";
	private static String user = "root";
	private static String pwd = "123456";
	
	private static Dbconnnection dbconnnection = null;
	
	private Dbconnnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://"+server+"/whatif", user, pwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static Dbconnnection getInstance(){
		if (dbconnnection == null){
			dbconnnection  = new Dbconnnection();
		}
		return dbconnnection;
	}
	
	public Connection getConnection(){
		return this.connection;
	}
}
