package com.overwatchleague.client.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CharacterData extends CachedDBObject {
	ArrayList<OWCharacter> characters;
	
	public CharacterData(Connection connection) {		
		super(connection);
	}
	
	@Override
	public void updateData() {
		String query = "SELECT name, role FROM Character";
		
		PreparedStatement statement = createPreparedStatement(query);
		queryServerPrepared(statement);
		
		super.updateData();
	}

	@Override
	public void handleError(int errorCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parseResponse(ResultSet resultSet) {
		characters = new ArrayList<>();
		
		try {
			while(resultSet.next()) {
				characters.add(new OWCharacter(resultSet.getString(1), resultSet.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to parse Character response");
		}
	}
	
	public ArrayList<OWCharacter> getCharacters(boolean forceUpdate) {
		updateIfNecessary(forceUpdate);
				
		return characters;
	}
}
