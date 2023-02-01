package edu.kit.kastel.sdq.solidityroleadapter.operation.composition;

import java.util.List;

import edu.kit.kastel.sdq.solidityroleadapter.operation.WorkingSet;

public class SolvingAfterRelationMerging implements SolvingComposition {

	@Override
	public WorkingSet compose(List<WorkingSet> workingSets) {
		
		if(workingSets.isEmpty()) {
			return null;
		}
		
		WorkingSet result = workingSets.get(0);
		for(int i = 1; i < workingSets.size(); i++) {
			result = result.mergeWith(workingSets.get(i));
		}
		return result.solve();
	}

}
