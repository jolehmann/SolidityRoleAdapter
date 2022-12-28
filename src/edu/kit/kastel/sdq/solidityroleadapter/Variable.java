package edu.kit.kastel.sdq.solidityroleadapter;

public class Variable {
	String name;
	String modRoles;
	String inflRoles;
	
	public Variable (String name, String modRoles, String inflRoles) {
		this.name = name;
		this.modRoles = modRoles;
		this.inflRoles = inflRoles;
	}
	
	public String getName() {
		return this.name;
	}
	public String getModRoles() {
		return this.modRoles;
	}
	public String getInflRoles() {
		return this.inflRoles;
	}
	public String toString() {
		return this.name + " " + this.modRoles + " " + this.inflRoles;
	}
}
