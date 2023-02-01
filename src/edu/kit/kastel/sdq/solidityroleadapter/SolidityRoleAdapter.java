package edu.kit.kastel.sdq.solidityroleadapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.kit.kastel.sdq.solidityroleadapter.operation.RoleAnnotations;
import edu.kit.kastel.sdq.solidityroleadapter.operation.UniDirectionalSolvingStrategy;
import edu.kit.kastel.sdq.solidityroleadapter.operation.WorkingSet;
import edu.kit.kastel.sdq.solidityroleadapter.operation.WorklistSolvingStrategy;
import edu.kit.kastel.sdq.solidityroleadapter.operation.composition.SolvingAfterRelationMerging;
import edu.kit.kastel.sdq.solidityroleadapter.operation.composition.SolvingComposition;
import edu.kit.kastel.sdq.solidityroleadapter.output.AffectedObjectSetDTO;
import edu.kit.kastel.sdq.solidityroleadapter.output.ConsoleInfoPrinter;
import edu.kit.kastel.sdq.solidityroleadapter.output.ResultWriter;
import edu.kit.kastel.sdq.solidityroleadapter.parser.RoleAnnotationParser;
import edu.kit.kastel.sdq.solidityroleadapter.parser.SlitherResultParser;
import edu.kit.kastel.sdq.solidityroleadapter.parser.SolcVerifyResultParser;

public class SolidityRoleAdapter {
	public static final String URI_SOLC_VERIFY = "data/SolcVerifyResults.txt";
	public static final String URI_SLITHER = "data/SlitherResults - Market.txt";
	public static final String URI_ROLE_ANNOTATIONS = "data/RoleAnnotations.txt";

	public static final String URI_OF_RESULT_TXT_FILE = "data/SolidityRoleAdapter - Results.txt";
	public static final String URI_OF_RESULT_JSON_FILE = "data/SolidityRoleAdapter - Results.json";

	private static final ConsoleInfoPrinter consoleInfoPrinter = new ConsoleInfoPrinter();
	private static final boolean PRINT_CONSOLE_INFO = true;

	static RoleAnnotationParser roleAP = new RoleAnnotationParser();
	static SolcVerifyResultParser solcVRP = new SolcVerifyResultParser();
	static SlitherResultParser slitherRP = new SlitherResultParser();
	
	static ResultWriter resultWriter = new ResultWriter();
	
	public static void main(String[] args) {

		// Setup

		RoleAnnotations roleAnnotations = new RoleAnnotations();
		WorkingSet funcToVarRelationsSolc = new WorkingSet(new UniDirectionalSolvingStrategy());
		WorkingSet varToVarRelationsSlither = new WorkingSet(new WorklistSolvingStrategy());

		// Parsing

		try {
			roleAP.parse(URI_ROLE_ANNOTATIONS, roleAnnotations);
			solcVRP.parse(URI_SOLC_VERIFY, roleAnnotations, funcToVarRelationsSolc);
			slitherRP.parse(URI_SLITHER, roleAnnotations, varToVarRelationsSlither);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Processing

		SolvingComposition solvingARM = new SolvingAfterRelationMerging();
		WorkingSet result = solvingARM.compose(List.of(funcToVarRelationsSolc, varToVarRelationsSlither));

		// Create result for txt

		List<String> linesOfResult = new ArrayList<String>();
		result.getAllWorkingObjectsWithChangedRoles().forEach(item -> linesOfResult.add(item.toString()));

		// Create result for json

		AffectedObjectSetDTO resultDTOForJson = new AffectedObjectSetDTO();
		result.getAllWorkingObjectsWithChangedRoles().forEach(item -> resultDTOForJson.add(item.getDTO(null)));

		// Write to txt file and to json file

		try {
			resultWriter.writeToResultTxtFile(URI_OF_RESULT_TXT_FILE, linesOfResult);
			resultWriter.writeToResultJsonFile(URI_OF_RESULT_JSON_FILE, resultDTOForJson);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Print Log

		if (PRINT_CONSOLE_INFO)
			consoleInfoPrinter.print(roleAnnotations, result, linesOfResult);
	}
}
