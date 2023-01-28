package edu.kit.kastel.sdq.solidityroleadapter.items;

public class Variable extends RolesAnnotatedObject{
	
	public Variable (String context, String name, TupleRoles roles) {
		super(context, name, roles);
	}
	
	public String toString() {
		return super.toString() + " " + super.roles.toString();
	}
}
