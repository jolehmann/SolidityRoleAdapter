package edu.kit.kastel.sdq.solidityroleadapter.items;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	Map<Role, RelationDecoratedObject> roleOrigins; // backwards relation

	public RelationDecoratedObject(RolesAnnotatedObject decoratedObject) {
		super(decoratedObject.getContext(), decoratedObject.getName(), decoratedObject.getRolesCopy());
		this.decoratedObject = decoratedObject;
		this.relations = new HashSet<RelationDecoratedObject>();
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

	public boolean haveRolesChanged() {
		return !this.getCorrectedRoles().equals(decoratedObject.roles);
	}

	public String toString() {
		return super.toString() + " --> " + getCorrectedRoles().toString();
	}
}
