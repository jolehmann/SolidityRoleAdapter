package edu.kit.kastel.sdq.solidityroleadapter;

import java.util.HashMap;

import edu.kit.kastel.sdq.solidityroleadapter.items.Function;
import edu.kit.kastel.sdq.solidityroleadapter.items.Variable;

public class RoleAnnotations {
	HashMap<String, HashMap<String, Function>> functionContexts;
	HashMap<String, HashMap<String, Variable>> variableContexts;
	
	public RoleAnnotations() {
		this.functionContexts = new HashMap<String, HashMap<String, Function>>();
		this.variableContexts = new HashMap<String, HashMap<String, Variable>>();
	}
	
	public void addFunction(String context, Function function) {
		HashMap<String, Function> functions = this.functionContexts.get(context);
		if (functions == null) {
			functions = new HashMap<String, Function>();
			this.functionContexts.put(context, functions);
		}
		functions.put(function.getName(), function);
	}
	
	public void addVariable(String context, Variable variable) {
		HashMap<String, Variable> variables = this.variableContexts.get(context);
		if (variables == null) {
			variables = new HashMap<String, Variable>();
			this.variableContexts.put(context, variables);
		}
		variables.put(variable.getName(), variable);
	}
	
	/**
	 * Returns the Function with annotated roles
	 * 
	 * @param context The name of the surrounding class of the function
	 * @param name The name of the function
	 * @return The function object or null if not found
	 */
	public Function getFunction (String context, String name) {
		HashMap<String, Function> functions = this.functionContexts.get(context);
		return (functions == null || !functions.containsKey(name))? null : functions.get(name);
	}
	
	/**
	 * Returns the Variable with annotated roles
	 * 
	 * @param context The name of the surrounding class of the variable
	 * @param name The name of the variable
	 * @return The variable object or null if not found
	 */
	public Variable getVariable (String context, String name) {
		HashMap<String, Variable> variables = this.variableContexts.get(context);
		return (variables == null || !variables.containsKey(name))? null : variables.get(name);
	}
	
	public void printDataInfo() {

		System.out.println("-----Parsed Variables-----");
		for (HashMap<String, Variable> variables : variableContexts.values()) {
			for (Variable v : variables.values()) {
				System.out.println(v.toString());
			}
		}
		System.out.println("");

		System.out.println("-----Parsed Functions-----");
		for (HashMap<String, Function> functions : functionContexts.values()) {
			for (Function f : functions.values()) {
				System.out.println(f.toString());
			}
		}
		System.out.println("");
	}
}
