package com.overwatchleague.client.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public abstract class CachedDBObject {
	Connection connection;
		
	long lastUpdated;
	
	public CachedDBObject(Connection connection) {
		this.connection = connection;
				
		updateData();
	}
	
	public void updateData() {
		lastUpdated = System.currentTimeMillis();
	}
	
	public PreparedStatement createPreparedStatement(String query) {
		try {
			return this.connection.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean queryServerPrepared(PreparedStatement statement) {
		try {
			ResultSet rs = statement.executeQuery();
			
			parseResponse(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR: ServerPrepared failed with unknown error");
			return false;
		}
		return true;
	}
	
	public boolean queryServerCallable(CallableStatement statement) {
		try {
			ResultSet rs = statement.executeQuery();
			
			int returnValue = statement.getInt(1);
			
			handleError(returnValue);
			
			if(returnValue != 0) {
				return false;
			}
			
			parseResponse(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR: ServerCallable failed with unknown error");
			return false;
		}
		return true;
	}
	
	public abstract void handleError(int errorCode);
	
	public abstract void parseResponse(ResultSet resultSet);
	
	public boolean updateIfNecessary(boolean forceUpdate) {
		boolean didUpdate = false;
		// If 5 minutes have passed since data was last retrieved, update data
		if(forceUpdate || System.currentTimeMillis() - lastUpdated > 1000*60*5) {
			didUpdate = true;
			updateData();
		}
		
		return didUpdate;
	}
}
