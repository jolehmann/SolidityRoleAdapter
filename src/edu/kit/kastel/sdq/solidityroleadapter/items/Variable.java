package edu.kit.kastel.sdq.solidityroleadapter.items;

public class Variable {
	String name;
	Roles modificationRoles;
	Roles influenceRoles;
	
	public Variable (String name, Roles modificationRoles, Roles influenceRoles) {
		this.name = name;
		this.modificationRoles = modificationRoles;
		this.influenceRoles = influenceRoles;
	}
	
	public String getName() {
		return this.name;
	}
	public Roles getModRoles() {
		return this.modificationRoles;
	}
	public Roles getInflRoles() {
		return this.influenceRoles;
	}
	public String toString() {
		return this.name + " " + this.modificationRoles.toString() + " " + this.influenceRoles.toString();
	}
	public String toBracketNotation() {
		return "(" + this.name + ", mod" + this.modificationRoles.toString() + ", infl" + this.influenceRoles.toString() + ")";
	}
}
