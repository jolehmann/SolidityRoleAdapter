package edu.kit.kastel.sdq.solidityroleadapter.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.kit.kastel.sdq.solidityroleadapter.items.RelationDecoratedObject;
import edu.kit.kastel.sdq.solidityroleadapter.items.Roles;
import edu.kit.kastel.sdq.solidityroleadapter.items.RolesAnnotatedObject;

/**
 * A WorkingSet contains a Set of RelationDecoratedObject and a SolvingStrategy.
 * The algorithm of the SolvingStrategy can be executed by running
 * solve().<br />
 * <br />
 * 
 * It should always be possible to undo changes by calling reset().<br />
 * <br />
 * 
 * So it is necessary that every method that not only modifies 'correctedRoles'
 * of the items but also 'relations' of the items must return a new WorkingSet
 * like copy(). Example: mergeWith() merges results but also relations -->
 * operates on a copy.
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

	public void addRolesTo(RolesAnnotatedObject rAO, Roles rolesToAdd) {
		RelationDecoratedObject rDO = this.getOrCreateAndPut(rAO);
		rDO.getCorrectedRoles().add(rolesToAdd);
	}

	/**
	 * Transfers only the corrected Roles Sets of the workingObjects to the other set.
	 * The relations are not transferred
	 * @param other the other WorkingSet to which the data is transferred
	 * @return other for convenience
	 */
	public WorkingSet transferTo(WorkingSet other) {
		for (RelationDecoratedObject rDO : this.getAllWorkingObjects()) {
			other.addRolesTo(rDO.getDecoratedObject(), rDO.getCorrectedRoles());
		}
		return other;
	}

	/**
	 * Creates a new WorkingSet and merges all information of both (this and other) WorkingSets into it.
	 * Like copy() it returns deep copy of the internal information.
	 * @param other the other WorkingSet to merge with
	 * @return a new WorkingSet, merged out of both
	 */
	public WorkingSet mergeWith(WorkingSet other) {
		WorkingSet deepCopy = new WorkingSet(this.solvingStrategy.mergeWith(other.getSolvingStrategy()));
		List<RelationDecoratedObject> mergedObjects = this.getAllWorkingObjects();
		mergedObjects.addAll(other.getAllWorkingObjects());
		for (RelationDecoratedObject origin : mergedObjects) {
			origin.getRelationTargets()
					.forEach(target -> deepCopy.addRelation(origin.getDecoratedObject(), target.getDecoratedObject()));
			deepCopy.addRolesTo(origin.getDecoratedObject(), origin.getCorrectedRoles());
			// TODO copy roleOrigins
		}
		return deepCopy;
	}

	/**
	 * Uses the assigned SolvingStrategy to evaluate the relations.
	 * @return this for convenience
	 */
	public WorkingSet solve() {
		this.solvingStrategy.solve(this.getAllWorkingObjects());
		return this;
	}

	/**
	 * Creates a deep copy of all internal workingObjects, their relations and their correctedRoles.
	 * @return a deep copy of this.
	 */
	public WorkingSet copy() {
		return this.mergeWith(new WorkingSet(this.solvingStrategy));
	}

	/**
	 * Resets all correctedRoles fields of its workingObjects.
	 * @return this for convenience
	 */
	public WorkingSet reset() {
		for (RelationDecoratedObject rDO : this.getAllWorkingObjects()) {
			rDO.resetCorrectedRoles();
		}
		return this;
	}

	public List<RelationDecoratedObject> getAllWorkingObjects() {
		ArrayList<RelationDecoratedObject> result = new ArrayList<RelationDecoratedObject>();
		for (HashMap<String, RelationDecoratedObject> context : workingObjects.values()) {
			result.addAll(context.values());
		}
		return result;
	}
	
	public List<RelationDecoratedObject> getAllWorkingObjectsWithChangedRoles() {
		ArrayList<RelationDecoratedObject> result = new ArrayList<RelationDecoratedObject>();
		for (RelationDecoratedObject rDO : this.getAllWorkingObjects()) {
			if(rDO.haveRolesChanged()) {
				result.add(rDO);
			}
		}
		return result;
	}
	
	public List<String> relationsToString() {
		List<String> linesOfResult = new ArrayList<String>();
		for (RelationDecoratedObject rDO : this.getAllWorkingObjects()) {
			String relationsToString = rDO.relationsToString();
			if(relationsToString != "") {
				linesOfResult.add(relationsToString);
			}
		}
		return linesOfResult;
	}
}
