package com.overwatchleague.client;

public class DatabaseConnection {
	private DatabaseConnectionService dbService = null;
	
	public DatabaseConnection() {
		// TODO: Remove hard-coded info
		this.dbService = new DatabaseConnectionService("golem.csse.rose-hulman.edu", "OverwatchLeague");
		
		String username = "test";
		String password = "password";
		
		if(!this.dbService.connect(username, password)) {
			System.out.println("Connection failed");
		}
	}
	
	public DatabaseConnectionService getDbService() {
		return dbService;
	}
}
