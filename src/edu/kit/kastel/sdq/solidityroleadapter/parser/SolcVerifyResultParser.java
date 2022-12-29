package edu.kit.kastel.sdq.solidityroleadapter.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.kastel.sdq.solidityroleadapter.items.IllegalModification;

public class SolcVerifyResultParser {

	private static final String REGEX_ERROR_FOUND = "(?<context>[^:]+)::(?<name>[^:]+):(.)*(?<error>ERROR)";
	private static final String REGEX_MOD_ILLEGALLY = "(.)*?(?<name>[^(\\s | ')]+)('){0,1} (?<illegally>illegally)";
	private static final String CONTEXT_GROUP = "context", NAME_GROUP = "name", ERROR_GROUP = "error",
			ILLEGALLY_GROUP = "illegally";

	public SolcVerifyResultParser() {
	}

	/**
	 * Reads the file with the provided uri and adds all found illegalModifications into the IllegalModification List.
	 * @param uri the path to the RoleAnnotations.txt file
	 * @param illegalModifications An IllegalModification List, to which the data should be added
	 * @throws IOException if the path is not accessible
	 */
	public void parse(final String uri, List<IllegalModification> illegalModifications) throws IOException {
		this.parseLines(this.readLines(uri), illegalModifications);
	}

	private List<String> readLines(final String uri) throws IOException {
		return Files.readAllLines(Paths.get(uri));
	}

	private void parseLines(List<String> linesOfSolcVerifyResultFile, List<IllegalModification> illegalModifications) {

		for (int i = 0; i < linesOfSolcVerifyResultFile.size(); i++) {
			Pattern pattern = Pattern.compile(REGEX_ERROR_FOUND);
			Matcher matcher = pattern.matcher(linesOfSolcVerifyResultFile.get(i));
			if (matcher.matches() && matcher.group(ERROR_GROUP) != null) {

				String context = matcher.group(CONTEXT_GROUP);
				String functionName = matcher.group(NAME_GROUP);

				for (int j = i + 1; j < linesOfSolcVerifyResultFile.size(); j++) {
					Pattern modIlligallyPattern = Pattern.compile(REGEX_MOD_ILLEGALLY);
					Matcher mocIllegallyMatcher = modIlligallyPattern.matcher(linesOfSolcVerifyResultFile.get(j));

					if (!mocIllegallyMatcher.matches() || mocIllegallyMatcher.group(ILLEGALLY_GROUP) == null) {
						break;
					}
					String variableName = mocIllegallyMatcher.group(NAME_GROUP);
					illegalModifications.add(new IllegalModification(context, functionName, variableName));
				}
			}
		}
	}
}
