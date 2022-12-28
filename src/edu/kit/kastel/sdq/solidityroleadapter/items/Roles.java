package edu.kit.kastel.sdq.solidityroleadapter.items;

import java.util.HashSet;
import java.util.Set;

public class Roles {

	Set<Role> roles;
	
	public Roles () {
		this.roles = new HashSet<Role>();
	}
	
	public Roles add(Role role) {
		roles.add(role);
		return this;
	}
	
	public boolean contains(Role role) {
		return roles.contains(role);
	}
	
	public String toString() {
		String output = "{";
		for (Role role : roles) {
			output = output + role.toString() + ", ";
		}
		return roles.isEmpty()? "{}" : output.substring(0, output.length() - 2) + "}";
	}
}
