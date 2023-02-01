package edu.kit.kastel.sdq.solidityroleadapter.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.kit.kastel.sdq.solidityroleadapter.items.RolesAnnotatedObject;

/**
 * Database for RolesAnnotatedObjects.
 * 
 * @author Jonas Lehmann
 */
public class RoleAnnotations {

	HashMap<String, HashMap<String, RolesAnnotatedObject>> roleAnnotations;

	public RoleAnnotations() {
		this.roleAnnotations = new HashMap<String, HashMap<String, RolesAnnotatedObject>>();
	}

	public void add(RolesAnnotatedObject rAO) {

		this.roleAnnotations.putIfAbsent(rAO.getContext(), new HashMap<String, RolesAnnotatedObject>());
		HashMap<String, RolesAnnotatedObject> context = this.roleAnnotations.get(rAO.getContext());
		context.put(rAO.getName(), rAO);
	}

	public RolesAnnotatedObject get(String context, String name) {
		HashMap<String, RolesAnnotatedObject> contextMap = this.roleAnnotations.get(context);
		return (contextMap == null || !contextMap.containsKey(name)) ? null : contextMap.get(name);
	}

	public List<String> allAnnotationsToString() {
		List<String> linesOfResult = new ArrayList<String>();
		for (HashMap<String, RolesAnnotatedObject> names : roleAnnotations.values()) {
			for (RolesAnnotatedObject rAO : names.values()) {
				linesOfResult.add(rAO.toString());
			}
		}
		return linesOfResult;
	}
}
