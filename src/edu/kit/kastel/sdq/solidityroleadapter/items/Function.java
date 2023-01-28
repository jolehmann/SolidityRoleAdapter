package edu.kit.kastel.sdq.solidityroleadapter.items;

public class Function {
	String name;
	SingleRoles roles;

	public Function(String name, SingleRoles roles) {
		this.name = name;
		this.roles = roles;
	}

	public String getName() {
		return this.name;
	}
	public SingleRoles getRoles() {
		return this.roles;
	}
	public String toString() {
		return this.name + "() " + this.roles.toString();
	}
	public String toBracketNotation() {
		return "(" + this.name + "(), " + this.roles.toString() + ")";
	}
}
