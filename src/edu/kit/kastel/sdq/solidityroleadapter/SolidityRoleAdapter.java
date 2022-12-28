package edu.kit.kastel.sdq.solidityroleadapter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import edu.kit.kastel.sdq.solidityroleadapter.parser.RoleAnnotationParser;
import edu.kit.kastel.sdq.solidityroleadapter.parser.SolcVerifyResultParser;

public class SolidityRoleAdapter {
	private static final String URI_SOLC_VERIFY = "data/SolcVerifyResults.txt";
	private static final String URI_ROLE_ANNOTATIONS = "data/RoleAnnotations.txt";

	private static final String URI_NEW_VERSION_OF_RESULT_FILE = "data/SolcVerifyResultsWithRoleAnnotations.txt";
	
	private static final boolean PRINT_CONSOLE_INFO = true;

	public static void main(String[] args) {
		
		RoleAnnotationParser roleAP = new RoleAnnotationParser();
		SolcVerifyResultParser solcVRP = new SolcVerifyResultParser();
		
		try {
			if (PRINT_CONSOLE_INFO) printReadFilesInfo();
			
			roleAP.parse(URI_ROLE_ANNOTATIONS);
			solcVRP.parse(URI_SOLC_VERIFY);
			
			if (PRINT_CONSOLE_INFO) roleAP.printParsedDataInfo();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//TODO Create result
		List <String> linesOfSolcVerifyResult = new ArrayList<String>();
		
		try {
			if (PRINT_CONSOLE_INFO) printWriteFileInfo(linesOfSolcVerifyResult);
			
			writeBackToResultFile(linesOfSolcVerifyResult);
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
	
	static void printWriteFileInfo(List <String> linesOfSolcVerifyResult) {
		System.out.println("-----Generated Output-----");
		System.out.println(String.join(System.lineSeparator(), linesOfSolcVerifyResult));
	}
	
	static void printTerminatedInfo() {
		System.out.println("The program was terminated after execution.");
		System.out.println("Results were written to: " + URI_NEW_VERSION_OF_RESULT_FILE);
	}
}
