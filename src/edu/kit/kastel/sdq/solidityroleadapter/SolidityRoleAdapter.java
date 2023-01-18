package edu.kit.kastel.sdq.solidityroleadapter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.kit.kastel.sdq.solidityroleadapter.items.IllegalModification;
import edu.kit.kastel.sdq.solidityroleadapter.items.InfluenceDecoratedVariable;
import edu.kit.kastel.sdq.solidityroleadapter.items.InfluencerRelation;
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
		List<IllegalModification> illegalModifications = new ArrayList<IllegalModification>();
		List<InfluencerRelation> influencerRelations = new ArrayList<InfluencerRelation>();

		try {

			roleAP.parse(URI_ROLE_ANNOTATIONS, roleAnnotations);
			solcVRP.parse(URI_SOLC_VERIFY, illegalModifications);
			slitherRP.parse(URI_SLITHER, influencerRelations);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Annotate all roles to the parsed data
		for (IllegalModification i : illegalModifications) {
			i.annotateWith(roleAnnotations);
		}
		for (InfluencerRelation i : influencerRelations) {
			i.annotateWith(roleAnnotations);
		}

		// Process Fixpoint Iteration of Slither Results
		FixpointIteration fixPI = new FixpointIteration(influencerRelations);
		fixPI.run();
		Set<InfluenceDecoratedVariable> variablesWithChangedRoles = fixPI.getVariablesWithChangedRoles();

		// Create result
		List<String> linesOfResult = new ArrayList<String>();
		for (IllegalModification i : illegalModifications) {
			linesOfResult.add(i.toBracketNotation());
		}

		try {

			writeBackToResultFile(linesOfResult);

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (PRINT_CONSOLE_INFO)
			consoleInfoPrinter.print(illegalModifications, roleAnnotations, influencerRelations,
					variablesWithChangedRoles, linesOfResult);
	}

	static void writeBackToResultFile(List<String> linesOfSolcVerifyResult) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(URI_NEW_VERSION_OF_RESULT_FILE),
				Charset.forName("UTF-8"));
		writer.write(String.join(System.lineSeparator(), linesOfSolcVerifyResult));
		writer.close();

	}
}
