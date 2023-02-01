package edu.kit.kastel.sdq.solidityroleadapter.items;

import edu.kit.kastel.sdq.solidityroleadapter.output.RolesAnnotatedDTO;
import edu.kit.kastel.sdq.solidityroleadapter.output.RolesAnnotatedFunctionDTO;

public class Function extends RolesAnnotatedObject{
	
	public Function(String context, String name, SingleRoles roles) {
		super(context, name, roles);
	}

	public String toString() {
		return this.getContextAndName() + " " + super.roles.toString();
	}
	
	public String getContextAndName() {
		return super.toString() + "()";
	}
	
	@Override
	public RolesAnnotatedDTO getDTO(Roles correctedRoles) {
		RolesAnnotatedFunctionDTO dto = new RolesAnnotatedFunctionDTO(super.context, super.name);
		
		dto.setMissingRoles(((SingleRoles) correctedRoles).getMinus((SingleRoles)super.roles));
		
		return dto;
	}
}
