package edu.kit.kastel.sdq.solidityroleadapter.operation;

import java.util.List;

import edu.kit.kastel.sdq.solidityroleadapter.items.RelationDecoratedObject;

public interface SolvingStrategy {
	public void solve(List<RelationDecoratedObject> workingObjects);
	
	public SolvingStrategy mergeWith(SolvingStrategy other);
}
