package edu.kit.kastel.sdq.solidityroleadapter.operation.composition;

import java.util.List;

import edu.kit.kastel.sdq.solidityroleadapter.operation.WorkingSet;

public interface SolvingComposition {
	/**
	 * Sets up the order in which the workingSets are evaluated and composes the result.
	 * @param workingSets the workingSets which should be solved. For some implementations the order in this List could be important.
	 * @return the result as a new WorkingSet
	 */
	public WorkingSet compose(List<WorkingSet> workingSets);
}
