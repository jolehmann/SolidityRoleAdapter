package edu.kit.kastel.sdq.solidityroleadapter;

public class Function {
	String name;
	String roles;

	public Function(String name, String roles) {
		this.name = name;
		this.roles = roles;
	}

	public String getName() {
		return this.name;
	}
	public String getRoles() {
		return this.roles;
	}
	public String toString() {
		return this.name + " " + this.roles;
	}
}
