package edu.kit.kastel.sdq.solidityroleadapter.items;

public abstract class RolesAnnotatedObject {

	String context;
	String name;
	protected Roles roles;
	
	public RolesAnnotatedObject(String context, String name, Roles roles) {
		this.context = context;
		this.name = name;
		this.roles = roles;
	}
	
	public String getContext() {
		return context;
	}

	public String getName() {
		return name;
	}
	
	public Roles getRolesCopy() {
		return this.roles.copy();
	}
	
	public String toString() {
		return this.context + "::" + this.name;
	}
}
