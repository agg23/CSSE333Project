package com.overwatchleague.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionService {

	private String url = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public DatabaseConnectionService(String serverName, String databaseName) {
		try {
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String user, String pass) {
		if(this.connection != null) {
			return false;
		}
		
		String connectionString = url.replace("${dbServer}", serverName);
		connectionString = connectionString.replace("${dbName}", databaseName);
		connectionString = connectionString.replace("${user}", user);
		connectionString = connectionString.replace("${pass}", pass);
		
		try {
			this.connection = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		try {
			this.connection.close();
			this.connection = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
