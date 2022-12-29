package edu.kit.kastel.sdq.solidityroleadapter.items;

import edu.kit.kastel.sdq.solidityroleadapter.RoleAnnotations;

public class IllegalModification {

	private String context;
	private String functionName;
	private String variableName;

	private Function function = null;
	private Variable variable = null;
	private Variable variableWithCorrectedRoles = null;

	public IllegalModification(String context, String functionName, String variableName) {
		this.context = context;
		this.functionName = functionName;
		this.variableName = variableName;
	}

	/**
	 * Annotates this IllegalModification with the role annotations of functions and variables found in the RoleAnnotations object.
	 * @param roleAnnotations the database of annotations to be used for search
	 */
	public void annotateWith(RoleAnnotations roleAnnotations) {
		this.function = roleAnnotations.getFunction(this.context, this.functionName);
		this.variable = roleAnnotations.getVariable(this.context, this.variableName);
		if (this.function != null && this.variable != null) {
			Roles newModRoles = this.variable.getModRoles().copyAndAdd(this.function.getRoles());
			Roles newInflRoles = this.variable.getInflRoles().copyAndAdd(this.function.getRoles());
			this.variableWithCorrectedRoles = new Variable(this.variable.getName(), newModRoles, newInflRoles);
		}
	}

	public String toBracketNotation() {
		if (this.function == null) {
			return this.toString();
		}
		if (this.variable == null) {
			return "// No Roles were annotated to '" + this.variableName + "', illegally modified by " + this.function;
		}
		return "R = " + this.function.toBracketNotation() + " " + this.variable.toBracketNotation()
				+ " => PostProcessing(R) ==> " + this.variableWithCorrectedRoles.toBracketNotation();
	}

	public String toString() {
		return this.context + ": " + this.functionName + "() might modify '" + this.variableName + "' illegally.";
	}
}
