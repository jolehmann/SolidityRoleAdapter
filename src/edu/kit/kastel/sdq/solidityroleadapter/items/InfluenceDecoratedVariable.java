package edu.kit.kastel.sdq.solidityroleadapter.items;

import java.util.HashSet;
import java.util.Set;

/**
 * A decorator class of Variable. It adds a set of influenced variables to the
 * decorated one. The getRoles-methods are delegated to the actual Variables
 * while the getPostProcessedRoles-methods return the Roles objects of the
 * Decorator-class. These postprocessed roles should be composed of own roles
 * and all influencer roles.
 * 
 * @author Jonas Lehmann
 *
 */
public class InfluenceDecoratedVariable extends Variable {
	Variable decoratedVariable;
	Set<InfluenceDecoratedVariable> influencedVariables;

	public InfluenceDecoratedVariable(Variable decoratedVariable) {
		super(decoratedVariable.getName(), decoratedVariable.getModRoles().copy(),
				decoratedVariable.getInflRoles().copy());
		this.decoratedVariable = decoratedVariable;
		this.influencedVariables = new HashSet<InfluenceDecoratedVariable>();
	}

	public void addTarget(InfluenceDecoratedVariable target) {
		influencedVariables.add(target);
	}

	public Set<InfluenceDecoratedVariable> getInfluencedVariables() {
		return this.influencedVariables;
	}

	public Roles getPostProcessedModRoles() {
		return this.modificationRoles;
	}

	public Roles getPostProcessedInflRoles() {
		return this.influenceRoles;
	}

	public boolean haveRolesChanged() {
		return !this.getPostProcessedInflRoles().equals(this.decoratedVariable.getInflRoles())
				|| !this.getPostProcessedModRoles().equals(this.decoratedVariable.getModRoles());
	}

	public String getName() {
		return this.decoratedVariable.getName();
	}

	public Roles getModRoles() {
		return this.decoratedVariable.getModRoles();
	}

	public Roles getInflRoles() {
		return this.decoratedVariable.getInflRoles();
	}

	public String toString() {
		return this.decoratedVariable.toString() + " --> " + this.modificationRoles.toString() + " " + this.influenceRoles.toString();
	}

	public String toBracketNotation() {
		return this.decoratedVariable.toBracketNotation();
	}
}
