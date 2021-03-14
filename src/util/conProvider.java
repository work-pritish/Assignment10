package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class conProvider {
	static Connection con;
	public static Connection createCon() {
		try {
			Class.forName("com.mysql.jdbc.Driver");	
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Movies", "root", "pritish1@");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}


