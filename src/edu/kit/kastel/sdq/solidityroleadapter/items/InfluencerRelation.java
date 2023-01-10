package edu.kit.kastel.sdq.solidityroleadapter.items;

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
	
	public String toString() {
		return this.context + ": " + this.targetVariableName + "is influenced by '" + this.influencerVariableName + "'.";
	}
}
