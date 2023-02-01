package edu.kit.kastel.sdq.solidityroleadapter.items;

import edu.kit.kastel.sdq.solidityroleadapter.output.RolesAnnotatedDTO;

public abstract class RolesAnnotatedObject {

	String context;
	String name;
	protected Roles roles;
	
	public RolesAnnotatedObject(String context, String name, Roles roles) {
		this.context = context;
		this.name = name;
		this.roles = roles;
	}
	
	public String getContext() {
		return context;
	}

	public String getName() {
		return name;
	}
	
	public Roles getRolesCopy() {
		return this.roles.copy();
	}
	
	public String toString() {
		return this.context + "::" + this.name;
	}
	
	public String getContextAndName() {
		return this.context + "::" + this.name;
	}
	
	/**
	 * Returns a DataTransferObject for JSON, consisting of the set of missing roles compared to the correctedRoles (specified by the parameter).
	 * @param correctedRoles the new roles-set to compare to
	 * @return A specific RolesAnnotatedDTO that should be selected by the subclasses
	 */
	public abstract RolesAnnotatedDTO getDTO(Roles correctedRoles);
}
