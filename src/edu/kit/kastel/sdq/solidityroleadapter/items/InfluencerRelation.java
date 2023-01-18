package edu.kit.kastel.sdq.solidityroleadapter.items;

import edu.kit.kastel.sdq.solidityroleadapter.RoleAnnotations;

public class InfluencerRelation {
	private String context;
	private String targetVariableName;
	private String influencerVariableName;

	private Variable targetVariable = null;
	private Variable influencerVariable = null;

	public InfluencerRelation(String context, String targetVariableName, String influencerVariableName) {
		this.context = context;
		this.targetVariableName = targetVariableName;
		this.influencerVariableName = influencerVariableName;
	}
	
	/**
	 * Annotates this InfluencerRelation with the role annotations of variables found in the RoleAnnotations object.
	 * @param roleAnnotations the database of annotations to be used for search
	 */
	public void annotateWith(RoleAnnotations roleAnnotations) {
		this.targetVariable = roleAnnotations.getVariable(this.context, this.targetVariableName);
		this.influencerVariable = roleAnnotations.getVariable(this.context, this.influencerVariableName);
	}
	
	public Variable getTargetVariable() {
		return this.targetVariable;
	}
	public Variable getInfluencerVariable() {
		return this.influencerVariable;
	}
	
	public String toString() {
		return this.context + ": '" + this.targetVariableName + "' is influenced by '" + this.influencerVariableName + "'.";
	}
}
