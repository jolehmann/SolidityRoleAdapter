package edu.kit.kastel.sdq.solidityroleadapter.operation;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import edu.kit.kastel.sdq.solidityroleadapter.items.RelationDecoratedObject;
import edu.kit.kastel.sdq.solidityroleadapter.items.Roles;

public class WorklistSolvingStrategy implements SolvingStrategy {

	@Override
	public void solve(List<RelationDecoratedObject> workingObjects) {

		LinkedHashSet<RelationDecoratedObject> queue = new LinkedHashSet<RelationDecoratedObject>();
		queue.addAll(workingObjects);

		Iterator<RelationDecoratedObject> i = queue.iterator();

		while (i.hasNext()) {

			RelationDecoratedObject origin = i.next();
			i.remove();

			Roles originRoles = origin.getCorrectedRoles();

			for (RelationDecoratedObject target : origin.getRelationTargets()) {
				Roles targetRoles = target.getCorrectedRoles();
				if (!targetRoles.contains(originRoles)) {
					targetRoles.add(originRoles);
					queue.add(target);
				}
			}
			i = queue.iterator();
		}
	}

	@Override
	public SolvingStrategy mergeWith(SolvingStrategy other) {
		return this;
	}
}
