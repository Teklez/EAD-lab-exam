package com.itsc;

import com.itsc.Connection;
import com.itsc.Exception;

import com.itsc.String;
// Zemenu Mekuria Embiale

public class DBConnectionManager {
	public static void main(String[] args) {
		
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mariadb://localhost:3306/BookstoreDB";
		String user = "zemen";
		String password = "justme";
		
		try {
			
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println("Established Connection");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
