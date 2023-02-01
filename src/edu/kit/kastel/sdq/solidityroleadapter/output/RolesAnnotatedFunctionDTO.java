package edu.kit.kastel.sdq.solidityroleadapter.output;

import java.util.ArrayList;
import java.util.List;

public class RolesAnnotatedFunctionDTO extends RolesAnnotatedDTO {

	List<String> missingRoles;

	public RolesAnnotatedFunctionDTO(String context, String name) {
		super(context, name);
		this.missingRoles = new ArrayList<String>();
	}
	
	public void setMissingRoles(List<String> missingRoles) {
		this.missingRoles = missingRoles;
	}
	
	public List<String> getMissingRoles() {
		return missingRoles;
	}

	@Override
	public boolean isFunction() {
		return true;
	}

}
