package edu.kit.kastel.sdq.solidityroleadapter.operation;

import java.util.HashMap;

import edu.kit.kastel.sdq.solidityroleadapter.items.RelationDecoratedObject;
import edu.kit.kastel.sdq.solidityroleadapter.items.RolesAnnotatedObject;

/**
 * A WorkingSet contains a Set of RelationDecoratedObject and a SolvingStrategy.
 * The algorithm of the SolvingStrategy can be executed by running solve().<br /><br />
 * 
 * It should always be possible to undo changes by calling reset().<br /><br />
 * 
 * So it is necessary that every method that not only modifies 'correctedRoles'
 * of the items but also 'relations' of the items must return a new WorkingSet like copy().
 * Example: mergeWith() merges results but also relations --> operates on a copy.
 * 
 * @author Jonas Lehmann
 */
public class WorkingSet {
	
	private HashMap<String, HashMap<String, RelationDecoratedObject>> workingObjects;
	private SolvingStrategy solvingStrategy;

	public WorkingSet(SolvingStrategy solvingStrategy) {
		this.workingObjects = new HashMap<String, HashMap<String, RelationDecoratedObject>>();
		this.solvingStrategy = solvingStrategy;
	}
	
	public SolvingStrategy getSolvingStrategy() {
		return this.solvingStrategy;
	}
	
	public void addRelation(RolesAnnotatedObject origin, RolesAnnotatedObject target) {
		
		RelationDecoratedObject first = this.getOrCreateAndPut(origin);
		RelationDecoratedObject second = this.getOrCreateAndPut(target);
		
		first.addRelationTarget(second);
	}
	
	private RelationDecoratedObject getOrCreateAndPut(RolesAnnotatedObject item) {
		// Get or add context
		this.workingObjects.putIfAbsent(item.getContext(), new HashMap<String, RelationDecoratedObject>());
		HashMap<String, RelationDecoratedObject> context = this.workingObjects.get(item.getContext());
		// Get or add item
		context.putIfAbsent(item.getName(), new RelationDecoratedObject(item));
		return context.get(item.getName());
	}

	public WorkingSet transferTo( WorkingSet other) {
		return other;
	}
	
	public WorkingSet mergeWith(WorkingSet other) {
		return new WorkingSet(this.solvingStrategy.mergeWith(other.getSolvingStrategy()));
	}
	
	public WorkingSet solve() {
		return this;
	}
	
	public WorkingSet copy() {
		return new WorkingSet(this.solvingStrategy);
	}
	
	public WorkingSet reset() {
		return this;
	}
	
	public void printConsoleInfo() {
		System.out.println("-----WorkingSet Relations-----");
		for (HashMap<String, RelationDecoratedObject> context : workingObjects.values()) {
			for (RelationDecoratedObject rDO : context.values()) {
				System.out.println(rDO.toString());
			}
		}
		System.out.println("");
	}
}
