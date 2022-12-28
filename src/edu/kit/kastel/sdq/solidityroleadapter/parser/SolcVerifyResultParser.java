package edu.kit.kastel.sdq.solidityroleadapter.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import edu.kit.kastel.sdq.solidityroleadapter.items.IllegalModification;

public class SolcVerifyResultParser {

	public SolcVerifyResultParser() {
	}
	
	public void parse(final String uri) throws IOException {
		this.parseLines(this.readLines(uri));
	}
	
	private List<String> readLines (final String uri) throws IOException {
		return Files.readAllLines(Paths.get(uri));
	}
	
	private void parseLines(List<String> linesOfRoleAnnotationFile) {
//		// Add RoleAnnotations behind each Function name
//					linesOfSolcVerifyResult
//							.replaceAll(line -> (line.split(": ")[0] != null && functions.containsKey(line.split(": ")[0])
//									? functions.get(line.split(": ")[0]) + ": " + line.split(": ")[1]
//									: line));
//
//					// Add RoleAnnotations behind each Variable name, in the illegally modification phrases
//					linesOfSolcVerifyResult
//							.replaceAll(line -> (getVariableOfLine(line) != "" && variables.containsKey(getVariableOfLine(line))
//									? line.replace(getVariableOfLine(line), variables.get(getVariableOfLine(line)).toString())
//									: line));
//
//					// Output to console and result to URI_NEW_VERSION_OF_RESULT_FILE
		
//		static String getVariableOfLine(String line) {
//			int firstBound = line.indexOf("'");
//			int secondBound = line.indexOf("'", firstBound + 1);
//			return (firstBound != -1 && secondBound != -1) ? line.substring(firstBound + 1, secondBound) : "";
//		}
	}
	
	/**
	 * Returns all found illegal modifications
	 * @return List of IllegalModification
	 */
	public List <IllegalModification> getIllegalModifications () {
		return null;
	}
}
