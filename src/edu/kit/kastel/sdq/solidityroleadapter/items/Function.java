package edu.kit.kastel.sdq.solidityroleadapter.items;

public class Function extends RolesAnnotatedObject{
	
	public Function(String context, String name, SingleRoles roles) {
		super(context, name, roles);
	}

	public String toString() {
		return super.toString() + "() " + super.roles.toString();
	}
}
