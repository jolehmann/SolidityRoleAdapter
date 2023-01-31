package edu.kit.kastel.sdq.solidityroleadapter.operation;

import java.util.Set;

import edu.kit.kastel.sdq.solidityroleadapter.items.RelationDecoratedObject;

public interface SolvingStrategy {
	public void solve(Set<RelationDecoratedObject> workingObjects);
	
	public SolvingStrategy mergeWith(SolvingStrategy other);
}
