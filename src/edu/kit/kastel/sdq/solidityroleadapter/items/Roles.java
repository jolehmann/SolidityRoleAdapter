package edu.kit.kastel.sdq.solidityroleadapter.items;

import java.util.Set;

public interface Roles {

	//public Roles add(Role role);
	
	//public Roles add(Set<Role> roles);
	
	public Roles add(Roles otherRoles);
	
	public Set<Role> toSet();
	
	public Roles copy();
	
	public Roles copyAndAdd(Roles otherRoles);
	
	public boolean contains(Role role);
	
	public boolean contains(Roles otherRoles);
	
	public boolean equals(Object obj);
	
	public String toString();
}
