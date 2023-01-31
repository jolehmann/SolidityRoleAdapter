package edu.kit.kastel.sdq.solidityroleadapter.operation;

import java.util.Set;

import edu.kit.kastel.sdq.solidityroleadapter.items.RelationDecoratedObject;

public class UniDirectionalSolvingStrategy implements SolvingStrategy {

	@Override
	public void solve(Set<RelationDecoratedObject> workingObjects) {
		for (RelationDecoratedObject origin : workingObjects) {
			origin.getRelationTargets().forEach(target -> target.getCorrectedRoles().add(origin.getCorrectedRoles()));
		}
	}

	@Override
	public SolvingStrategy mergeWith(SolvingStrategy other) {
		return other;
	}

}