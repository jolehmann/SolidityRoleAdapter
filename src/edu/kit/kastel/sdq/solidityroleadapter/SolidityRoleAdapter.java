package edu.kit.kastel.sdq.solidityroleadapter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import edu.kit.kastel.sdq.solidityroleadapter.items.IllegalModification;
import edu.kit.kastel.sdq.solidityroleadapter.items.InfluencerRelation;
import edu.kit.kastel.sdq.solidityroleadapter.parser.RoleAnnotationParser;
import edu.kit.kastel.sdq.solidityroleadapter.parser.SlitherResultParser;
import edu.kit.kastel.sdq.solidityroleadapter.parser.SolcVerifyResultParser;

public class SolidityRoleAdapter {
	private static final String URI_SOLC_VERIFY = "data/SolcVerifyResults.txt";
	private static final String URI_SLITHER = "data/SlitherResults - Market.txt";
	private static final String URI_ROLE_ANNOTATIONS = "data/RoleAnnotations.txt";

	private static final String URI_NEW_VERSION_OF_RESULT_FILE = "data/SolidityRoleAdapter - Results.txt";
	
	private static final boolean PRINT_CONSOLE_INFO = true;

	public static void main(String[] args) {
		
		RoleAnnotationParser roleAP = new RoleAnnotationParser();
		SolcVerifyResultParser solcVRP = new SolcVerifyResultParser();
		SlitherResultParser slitherRP = new SlitherResultParser();
		
		RoleAnnotations roleAnnotations = new RoleAnnotations();
		List<IllegalModification> illegalModifications = new ArrayList<IllegalModification>();
		List<InfluencerRelation> influencerRelations = new ArrayList<InfluencerRelation>();
		
		try {
			if (PRINT_CONSOLE_INFO) printReadFilesInfo();
			
			roleAP.parse(URI_ROLE_ANNOTATIONS, roleAnnotations);
			solcVRP.parse(URI_SOLC_VERIFY, illegalModifications);
			slitherRP.parse(URI_SLITHER, influencerRelations);
			
			if (PRINT_CONSOLE_INFO) roleAnnotations.printDataInfo();
			if (PRINT_CONSOLE_INFO) {
				System.out.println("-----Parsed Illegal Modifications-----");
				for(IllegalModification i : illegalModifications) {
					System.out.println(i.toString());
				}
				System.out.println("");
			}
			if (PRINT_CONSOLE_INFO) {
				System.out.println("-----Parsed Influencer Relations-----");
				for(InfluencerRelation i : influencerRelations) {
					System.out.println(i.toString());
				}
				System.out.println("");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Annotate all roles to the parsed illegalModifications
		for(IllegalModification i : illegalModifications) {
			i.annotateWith(roleAnnotations);
		}
		
		// Create result
		List <String> linesOfResult = new ArrayList<String>();
		for(IllegalModification i : illegalModifications) {
			linesOfResult.add(i.toBracketNotation());
		}
		
		try {
			if (PRINT_CONSOLE_INFO) printWriteFileInfo(linesOfResult);
			
			writeBackToResultFile(linesOfResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		printTerminatedInfo();
	}

	static void writeBackToResultFile(List<String> linesOfSolcVerifyResult) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(URI_NEW_VERSION_OF_RESULT_FILE),
				Charset.forName("UTF-8"));
		writer.write(String.join(System.lineSeparator(), linesOfSolcVerifyResult));
		writer.close();
		
	}
	
	static void printReadFilesInfo() {
		System.out.println("read file from: " + URI_SOLC_VERIFY);
		System.out.println("read file from: " + URI_ROLE_ANNOTATIONS);
		System.out.println("");
	}
	
	static void printWriteFileInfo(List <String> linesOfResult) {
		System.out.println("-----Generated Output-----");
		System.out.println(String.join(System.lineSeparator(), linesOfResult));
		System.out.println("");
	}
	
	static void printTerminatedInfo() {
		System.out.println("The program was terminated after execution.");
		System.out.println("Results were written to: " + URI_NEW_VERSION_OF_RESULT_FILE);
	}
}
