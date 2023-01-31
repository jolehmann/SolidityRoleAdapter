package edu.kit.kastel.sdq.solidityroleadapter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import edu.kit.kastel.sdq.solidityroleadapter.operation.RoleAnnotations;
import edu.kit.kastel.sdq.solidityroleadapter.operation.UniDirectionalSolvingStrategy;
import edu.kit.kastel.sdq.solidityroleadapter.operation.WorkingSet;
import edu.kit.kastel.sdq.solidityroleadapter.operation.WorklistSolvingStrategy;
import edu.kit.kastel.sdq.solidityroleadapter.parser.RoleAnnotationParser;
import edu.kit.kastel.sdq.solidityroleadapter.parser.SlitherResultParser;
import edu.kit.kastel.sdq.solidityroleadapter.parser.SolcVerifyResultParser;

public class SolidityRoleAdapter {
	public static final String URI_SOLC_VERIFY = "data/SolcVerifyResults.txt";
	public static final String URI_SLITHER = "data/SlitherResults - Market.txt";
	public static final String URI_ROLE_ANNOTATIONS = "data/RoleAnnotations.txt";

	public static final String URI_NEW_VERSION_OF_RESULT_FILE = "data/SolidityRoleAdapter - Results.txt";

	private static final ConsoleInfoPrinter consoleInfoPrinter = new ConsoleInfoPrinter();
	private static final boolean PRINT_CONSOLE_INFO = true;

	public static void main(String[] args) {

		RoleAnnotationParser roleAP = new RoleAnnotationParser();
		SolcVerifyResultParser solcVRP = new SolcVerifyResultParser();
		SlitherResultParser slitherRP = new SlitherResultParser();

		RoleAnnotations roleAnnotations = new RoleAnnotations();
		
		WorkingSet funcToVarRelationsSolc = new WorkingSet(new UniDirectionalSolvingStrategy());
		WorkingSet varToVarRelationsSlither = new WorkingSet(new WorklistSolvingStrategy());

		try {

			roleAP.parse(URI_ROLE_ANNOTATIONS, roleAnnotations);
			solcVRP.parse(URI_SOLC_VERIFY, roleAnnotations, funcToVarRelationsSolc);
			slitherRP.parse(URI_SLITHER, roleAnnotations, varToVarRelationsSlither);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Run solvers/ Composition types and get items with changed roles
		// Set<InfluenceDecoratedVariable> variablesWithChangedRoles =
		// fixPI.getVariablesWithChangedRoles();

		// Create result
		List<String> linesOfResult = new ArrayList<String>();
//		for (IllegalModification i : illegalModifications) {
//			linesOfResult.add(i.toBracketNotation());
//		}

		try {

			writeBackToResultFile(linesOfResult);
			// --> stattdessen JSON output

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (PRINT_CONSOLE_INFO)
			;
		// consoleInfoPrinter.print(illegalModifications, roleAnnotations,
		// influencerRelations,variablesWithChangedRoles, linesOfResult);
	}

	static void writeBackToResultFile(List<String> linesOfSolcVerifyResult) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(URI_NEW_VERSION_OF_RESULT_FILE),
				Charset.forName("UTF-8"));
		writer.write(String.join(System.lineSeparator(), linesOfSolcVerifyResult));
		writer.close();

	}
}
