package edu.kit.kastel.sdq.solidityroleadapter.output;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class RolesAnnotatedDTO {

	String context;
	String name;
	
	public RolesAnnotatedDTO(String context, String name) {
		this.context = context;
		this.name = name;
	}
	
	public String getContext() {
		return context;
	}

	public String getName() {
		return name;
	}
	
	@JsonIgnore
	public abstract boolean isFunction();
}
