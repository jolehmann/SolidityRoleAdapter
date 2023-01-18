package edu.kit.kastel.sdq.solidityroleadapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.kit.kastel.sdq.solidityroleadapter.items.InfluenceDecoratedVariable;
import edu.kit.kastel.sdq.solidityroleadapter.items.InfluencerRelation;
import edu.kit.kastel.sdq.solidityroleadapter.items.Roles;
import edu.kit.kastel.sdq.solidityroleadapter.items.Variable;

public class FixpointIteration {

	HashMap<String, InfluenceDecoratedVariable> variables;

	public FixpointIteration(List<InfluencerRelation> influencerRelations) {
		this.variables = new HashMap<String, InfluenceDecoratedVariable>();
		this.createVariables(influencerRelations);
	}

	/**
	 * Runs a worklist-algorithm to process all roles for a variable by combining
	 * own roles with influencer roles.
	 */
	public void run() {
		LinkedHashSet<InfluenceDecoratedVariable> queue = new LinkedHashSet<InfluenceDecoratedVariable>();
		queue.addAll(this.variables.values());

		Iterator<InfluenceDecoratedVariable> i = queue.iterator();

		while (i.hasNext()) {

			InfluenceDecoratedVariable variable = i.next();
			i.remove();

			Roles ownInflRoles = variable.getPostProcessedInflRoles();
			Roles ownModRoles = variable.getPostProcessedModRoles();

			for (InfluenceDecoratedVariable target : variable.getInfluencedVariables()) {
				Roles targetInflRoles = target.getPostProcessedInflRoles();
				Roles targetModRoles = target.getPostProcessedModRoles();

				if (!ownInflRoles.isSubsetOf(targetInflRoles)) {
					targetInflRoles.add(ownInflRoles.toSet());
					queue.add(target);
				}
				if (!ownModRoles.isSubsetOf(targetModRoles)) {
					targetModRoles.add(ownModRoles.toSet());
					queue.add(target);
				}
			}
			i = queue.iterator();
		}
	}

	/**
	 * Returns all Variables which changed their set of roles during the processing
	 * of the algorithm.
	 * 
	 * @return A Set of InfluenceDecoratedVariable
	 */
	public Set<InfluenceDecoratedVariable> getVariablesWithChangedRoles() {
		Set<InfluenceDecoratedVariable> result = new HashSet<InfluenceDecoratedVariable>();
		for (InfluenceDecoratedVariable variable : this.variables.values()) {
			if (variable.haveRolesChanged()) {
				result.add(variable);
			}
		}
		return result;
	}

	private void createVariables(List<InfluencerRelation> influencerRelations) {
		for (InfluencerRelation i : influencerRelations) {

			Variable influencer = i.getInfluencerVariable();
			Variable target = i.getTargetVariable();

			variables.putIfAbsent(influencer.getName(), new InfluenceDecoratedVariable(influencer));
			InfluenceDecoratedVariable first = variables.get(influencer.getName());

			variables.putIfAbsent(target.getName(), new InfluenceDecoratedVariable(target));
			InfluenceDecoratedVariable second = variables.get(target.getName());

			first.addTarget(second);
		}
	}
}
