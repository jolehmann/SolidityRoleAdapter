package edu.kit.kastel.sdq.solidityroleadapter.output;

import java.util.ArrayList;
import java.util.List;

public class RolesAnnotatedVariableDTO extends RolesAnnotatedDTO {

	List<String> missingModRoles;
	List<String> missingInflRoles;

	public RolesAnnotatedVariableDTO(String context, String name) {
		super(context, name);
		this.missingModRoles = new ArrayList<String>();
		this.missingInflRoles = new ArrayList<String>();
	}
	
	public void setMissingModRoles(List<String> missingModRoles) {
		this.missingModRoles = missingModRoles;
	}

	public void setMissingInflRoles(List<String> missingInflRoles) {
		this.missingInflRoles = missingInflRoles;
	}
	
	public List<String> getMissingModRoles() {
		return missingModRoles;
	}
	
	public List<String> getMissingInflRoles() {
		return missingInflRoles;
	}

	@Override
	public boolean isFunction() {
		return false;
	}

}
