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
	
	public Roles add(Set<Role> roles) {
		this.roles.addAll(roles);
		return this;
	}
	
	/**
	 * Returns a Set of all contained Role objects.
	 * @return
	 */
	public Set<Role> toSet() {
		Set<Role> rolesCopy = new HashSet<Role>();
		rolesCopy.addAll(this.roles);
		return rolesCopy;
	}
	
	/**
	 * Returns a new Roles object with the union of both roles
	 * @param otherRoles
	 * @return
	 */
	public Roles copyAndAdd(Roles otherRoles) {
		Roles copy = new Roles();
		copy.add(this.toSet());
		copy.add(otherRoles.toSet());
		return copy;
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
