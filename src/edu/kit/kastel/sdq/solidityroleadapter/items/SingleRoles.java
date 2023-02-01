package edu.kit.kastel.sdq.solidityroleadapter.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SingleRoles implements Roles{

	Set<Role> roles;
	
	public SingleRoles () {
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
	
	public Roles add(Roles otherRoles) {
		this.roles.addAll(otherRoles.toSet());
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
	
	public Roles copy() {
		SingleRoles copy = new SingleRoles();
		copy.add(this.toSet());
		return copy;
	}
	
	/**
	 * Returns a new Roles object with the union of both roles
	 * @param otherRoles
	 * @return
	 */
	public Roles copyAndAdd(Roles otherRoles) {
		return this.copy().add(otherRoles);
	}
	
	public boolean contains(Role role) {
		return this.roles.contains(role);
	}
	
	public boolean contains(Roles otherRoles) {
		for (Role r : otherRoles.toSet()) {
			if (!this.contains(r)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		final SingleRoles other = (SingleRoles) obj;
		return this.contains(other) && other.contains(this);
	}
	
	public String toString() {
		String output = "{";
		for (Role role : roles) {
			output = output + role.toString() + ", ";
		}
		return roles.isEmpty()? "{}" : output.substring(0, output.length() - 2) + "}";
	}
	
	public List<String> getMinus(SingleRoles otherRoles) {
		List<String> result = new ArrayList<String>();
		for (Role role : roles) {
			if(!otherRoles.contains(role)) {
				result.add(role.toString());
			}
		}
		return result;
	}
}
