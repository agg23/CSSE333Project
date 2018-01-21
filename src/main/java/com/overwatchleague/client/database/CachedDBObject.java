package com.overwatchleague.client.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;


public class CachedDBObject {
	Connection connection;
	
	HashMap<String, String> cachedObjects;
	
	long lastUpdated;
	
	public CachedDBObject(Connection connection) {
		this.connection = connection;
		
		this.cachedObjects = new HashMap<>();
		
		updateData();
	}
	
	public void updateData() {
		lastUpdated = System.currentTimeMillis();
	}
	
	public boolean queryServerPrepared(PreparedStatement statement) {
		try {
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR: ServerPrepared failed with unknown error");
			return false;
		}
		return true;
	}
	
	public boolean queryServerCallable(CallableStatement statement) {
		try {
			statement.execute();
			
			int returnValue = statement.getInt(1);
			
			handleError(returnValue);
			
			if(returnValue != 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR: ServerCallable failed with unknown error");
			return false;
		}
		return true;
	}
	
	public void handleError(int errorCode) {
		System.err.println("Unimplemented error handling");
	}
	
	public void parseResponse() {
		System.err.println("Unimplemented response parsing");
	}
	
	public HashMap<String, String> getCachedObjects(boolean forceUpdate) {
		// If 5 minutes have passed since data was last retrieved, update data 
		if(forceUpdate || System.currentTimeMillis() - lastUpdated > 1000*60*5) {
			updateData();
		}
		
		return cachedObjects;
	}
}
