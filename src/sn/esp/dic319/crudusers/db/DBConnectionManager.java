package sn.esp.dic319.crudusers.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
	private static Connection conn;
	
	private DBConnectionManager() {
		
	}
	
	public static Connection getInstance() throws SQLException, ClassNotFoundException {
		if (conn == null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/crudusers", "root", "root");
		}
		return conn;
	}
}
