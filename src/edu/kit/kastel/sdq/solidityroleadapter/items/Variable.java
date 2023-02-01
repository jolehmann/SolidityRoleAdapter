package edu.kit.kastel.sdq.solidityroleadapter.items;

import edu.kit.kastel.sdq.solidityroleadapter.output.RolesAnnotatedDTO;
import edu.kit.kastel.sdq.solidityroleadapter.output.RolesAnnotatedVariableDTO;

public class Variable extends RolesAnnotatedObject{
	
	public Variable (String context, String name, TupleRoles roles) {
		super(context, name, roles);
	}
	
	public String toString() {
		return this.getContextAndName() + " " + super.roles.toString();
	}
	
	public String getContextAndName() {
		return super.toString();
	}

	@Override
	public RolesAnnotatedDTO getDTO(Roles correctedRoles) {
		
		RolesAnnotatedVariableDTO dto = new RolesAnnotatedVariableDTO(super.context, super.name);
		
		TupleRoles originalRoles = (TupleRoles)super.roles;
		TupleRoles newRoles = (TupleRoles) correctedRoles;
		
		dto.setMissingModRoles(newRoles.getModRolesMinus(originalRoles));
		dto.setMissingInflRoles(newRoles.getInflRolesMinus(originalRoles));
		
		return dto;
	}
}
