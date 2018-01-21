package com.overwatchleague.client.database;

public class OWCharacter {
	private String name;
	private String role;
	
	public OWCharacter(String name, String role) {
		this.name = name;
		this.role = role;
	}
	
	public String getName() {
		return name;
	}
	
	public String getRole() {
		return role;
	}
}
