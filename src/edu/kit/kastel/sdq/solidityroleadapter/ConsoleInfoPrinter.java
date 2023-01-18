package edu.kit.kastel.sdq.solidityroleadapter;

import java.util.List;
import java.util.Set;

import edu.kit.kastel.sdq.solidityroleadapter.items.IllegalModification;
import edu.kit.kastel.sdq.solidityroleadapter.items.InfluenceDecoratedVariable;
import edu.kit.kastel.sdq.solidityroleadapter.items.InfluencerRelation;

public class ConsoleInfoPrinter {

	public void print(List<IllegalModification> illegalModifications, RoleAnnotations roleAnnotations,
			List<InfluencerRelation> influencerRelations, Set<InfluenceDecoratedVariable> variablesWithChangedRoles,
			List<String> linesOfResult) {

		// read files info
		System.out.println("read file from: " + SolidityRoleAdapter.URI_SOLC_VERIFY);
		System.out.println("read file from: " + SolidityRoleAdapter.URI_ROLE_ANNOTATIONS);
		System.out.println("");

		// Roles of Vars and Funcs Info
		roleAnnotations.printDataInfo();

		// Illegal Modifications
		System.out.println("-----Parsed Illegal Modifications-----");
		for (IllegalModification i : illegalModifications) {
			System.out.println(i.toString());
		}
		System.out.println("");

		// Influence Relations
		System.out.println("-----Parsed Influencer Relations-----");
		for (InfluencerRelation i : influencerRelations) {
			System.out.println(i.toString());
		}
		System.out.println("");

		// variables with changed roles
		System.out.println("-----Fixpoint Iteration Result-----");
		for (InfluenceDecoratedVariable v : variablesWithChangedRoles) {
			System.out.println(v.toString());
		}
		System.out.println("");

		// write file info
		System.out.println("-----Generated Output-----");
		System.out.println(String.join(System.lineSeparator(), linesOfResult));
		System.out.println("");

		// terminated Info
		System.out.println("The program was terminated after execution.");
		System.out.println("Results were written to: " + SolidityRoleAdapter.URI_NEW_VERSION_OF_RESULT_FILE);
	}
}
