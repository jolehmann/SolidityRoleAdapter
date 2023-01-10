package edu.kit.kastel.sdq.solidityroleadapter.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.kastel.sdq.solidityroleadapter.items.InfluencerRelation;

public class SlitherResultParser {
	private static final String REGEX_ERROR_FOUND = "(?<context>[^:]+)::(?<name>[^:]+):(.)*(?<error>ERROR)";
	private static final String REGEX_MOD_ILLEGALLY = "(.)*?(?<name>[^(\\s | ')]+)('){0,1} (?<illegally>illegally)";
	private static final String CONTEXT_GROUP = "context", NAME_GROUP = "name", ERROR_GROUP = "error",
			ILLEGALLY_GROUP = "illegally";

	public SlitherResultParser() {
	}

	/**
	 * Reads the file with the provided uri and adds all found influencerRelations into the InfluencerRelation List.
	 * @param uri the path to the RoleAnnotations.txt file
	 * @param influencerRelations An InfluencerRelation List, to which the data should be added
	 * @throws IOException if the path is not accessible
	 */
	public void parse(final String uri, List<InfluencerRelation> influencerRelations) throws IOException {
		this.parseLines(this.readLines(uri), influencerRelations);
	}

	private List<String> readLines(final String uri) throws IOException {
		return Files.readAllLines(Paths.get(uri));
	}

	private void parseLines(List<String> linesOfSolcVerifyResultFile, List<InfluencerRelation> influencerRelations) {

		for (int i = 0; i < linesOfSolcVerifyResultFile.size(); i++) {
			Pattern pattern = Pattern.compile(REGEX_ERROR_FOUND);
			Matcher matcher = pattern.matcher(linesOfSolcVerifyResultFile.get(i));
			
		}
	}
}
