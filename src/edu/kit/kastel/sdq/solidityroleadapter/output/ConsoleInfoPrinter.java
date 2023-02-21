package edu.kit.kastel.sdq.solidityroleadapter.output;

import java.util.List;

import edu.kit.kastel.sdq.solidityroleadapter.SolidityRoleAdapter;
import edu.kit.kastel.sdq.solidityroleadapter.operation.RoleAnnotations;
import edu.kit.kastel.sdq.solidityroleadapter.operation.WorkingSet;

public class ConsoleInfoPrinter {

	public void print(RoleAnnotations roleAnnotations, WorkingSet result, List<String> linesOfResult) {

		// read files info
		System.out.println("read file from: " + SolidityRoleAdapter.URI_SOLC_VERIFY);
		System.out.println("read file from: " + SolidityRoleAdapter.URI_ROLE_ANNOTATIONS);
		System.out.println("");

		// Roles of Vars and Funcs Info
		System.out.println("-----Parsed Roles Annotated Objects-----");
		System.out.println(String.join(System.lineSeparator(), roleAnnotations.allAnnotationsToString()));
		System.out.println("");

		// relations
		System.out.println("-----Parsed Relations-----");
		System.out.println(String.join(System.lineSeparator(), result.relationsToString()));
		System.out.println("");

		// write file info
		System.out.println("-----Generated Output: Changed Roles-----");
		System.out.println(String.join(System.lineSeparator(), linesOfResult));
		System.out.println("");

		// terminated Info
		System.out.println("The program was terminated after execution.");
		System.out.println("Results were written to: " + SolidityRoleAdapter.URI_OF_RESULT_TXT_FILE);
		System.out.println("JSON result is written to: " + SolidityRoleAdapter.URI_OF_RESULT_JSON_FILE);
	}
}
