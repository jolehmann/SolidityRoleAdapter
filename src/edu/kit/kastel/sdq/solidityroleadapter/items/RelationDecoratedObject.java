package edu.kit.kastel.sdq.solidityroleadapter.items;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.kit.kastel.sdq.solidityroleadapter.output.RolesAnnotatedDTO;

/**
 * A decorator class of RolesAnnotatedObject. It adds a set of other RAObjects
 * to the decorated one to specify relations. The getRolesCopy-method returns
 * the old roles while the getCorrectedRoles-methods returns the Roles object
 * with changed roles after processing the calculations. These postprocessed
 * corrected roles should be composed of own roles and all influencer roles.
 * 
 * @author Jonas Lehmann
 *
 */
public class RelationDecoratedObject extends RolesAnnotatedObject {

	RolesAnnotatedObject decoratedObject;

	Set<RelationDecoratedObject> relations;
	Map<Role, String> roleOrigins; // backwards relation

	public RelationDecoratedObject(RolesAnnotatedObject decoratedObject) {
		super(decoratedObject.getContext(), decoratedObject.getName(), decoratedObject.getRolesCopy());
		this.decoratedObject = decoratedObject;
		this.relations = new HashSet<RelationDecoratedObject>();
	}

	public RolesAnnotatedObject getDecoratedObject() {
		return this.decoratedObject;
	}

	public void addRelationTarget(RelationDecoratedObject target) {
		this.relations.add(target);
	}

	public Set<RelationDecoratedObject> getRelationTargets() {
		return this.relations;
	}

	@Override
	public Roles getRolesCopy() {
		return this.decoratedObject.getRolesCopy();
	}

	public Roles getCorrectedRoles() {
		return super.roles;
	}

	public void resetCorrectedRoles() {
		super.roles = this.decoratedObject.getRolesCopy();
	}

	public boolean haveRolesChanged() {
		return !this.getCorrectedRoles().equals(decoratedObject.roles);
	}

	public String relationsToString() {
		String relationsToString = "";
		for(RelationDecoratedObject target : getRelationTargets()) {
			relationsToString = relationsToString + target.getContextAndName() + ", ";
		}
		if (relationsToString.isEmpty()) {
			return "";
		}
		return this.getContextAndName() + " --> ["
				+ relationsToString.substring(0, relationsToString.length() - 2) + "]";
	}

	public String toString() {
		return this.decoratedObject.toString() + " --> " + getCorrectedRoles().toString();
	}

	public String getContextAndName() {
		return this.decoratedObject.getContextAndName();
	}
	
	@Override
	public RolesAnnotatedDTO getDTO(Roles correctedRoles) {
		return this.decoratedObject.getDTO(this.getCorrectedRoles());
	}
}
