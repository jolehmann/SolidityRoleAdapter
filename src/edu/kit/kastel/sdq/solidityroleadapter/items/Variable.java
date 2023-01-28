package edu.kit.kastel.sdq.solidityroleadapter.items;

public class Variable {
	String name;
	SingleRoles modificationRoles;
	SingleRoles influenceRoles;
	
	public Variable (String name, SingleRoles modificationRoles, SingleRoles influenceRoles) {
		this.name = name;
		this.modificationRoles = modificationRoles;
		this.influenceRoles = influenceRoles;
	}
	
	public String getName() {
		return this.name;
	}
	public SingleRoles getModRoles() {
		return this.modificationRoles;
	}
	public SingleRoles getInflRoles() {
		return this.influenceRoles;
	}
	public String toString() {
		return this.name + " " + this.modificationRoles.toString() + " " + this.influenceRoles.toString();
	}
	public String toBracketNotation() {
		return "(" + this.name + ", mod" + this.modificationRoles.toString() + ", infl" + this.influenceRoles.toString() + ")";
	}
}
