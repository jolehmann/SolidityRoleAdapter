package edu.kit.kastel.sdq.solidityroleadapter.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.kastel.sdq.solidityroleadapter.items.RolesAnnotatedObject;
import edu.kit.kastel.sdq.solidityroleadapter.operation.RoleAnnotations;
import edu.kit.kastel.sdq.solidityroleadapter.operation.WorkingSet;

public class SlitherResultParser {
	private static final String REGEX_FIRST_LINE_CONTEXT = "Contract (?<context>.+)";
	private static final String REGEX_VARIABLE_INFLUENCERS_LINE = "\\|([\\s]+)(?<name>[^\\s]+)([\\s]+)\\|([\\s]+)\\[(?<influencers>[^\\]]*)\\]([\\s]+)\\|";
	private static final String CONTEXT_GROUP = "context", NAME_GROUP = "name", INFLUENCERS_GROUP = "influencers";
	private static final String REGEX_PIPE = "|", REGEX_WHITESPACE_OR_QUOTATION_MARK = "[\\s']", REGEX_COMMA = ",";

	public SlitherResultParser() {
	}

	/**
	 * Reads the file with the provided uri and adds all found influencerRelations
	 * into the InfluencerRelation List.
	 * 
	 * @param uri                 the path to the RoleAnnotations.txt file
	 * @param influencerRelations An InfluencerRelation List, to which the data
	 *                            should be added
	 * @throws IOException if the path is not accessible
	 */
	public void parse(final String uri, RoleAnnotations roleAnnotations, WorkingSet varToVarRelationsSlither)
			throws IOException {
		this.parseLines(this.readLines(uri), roleAnnotations, varToVarRelationsSlither);
	}

	private List<String> readLines(final String uri) throws IOException {
		return Files.readAllLines(Paths.get(uri));
	}

	private void parseLines(List<String> linesOfSolcVerifyResultFile, RoleAnnotations roleAnnotations,
			WorkingSet varToVarRelationsSlither) {

		String context = this.getContext(linesOfSolcVerifyResultFile.get(0));

		for (int i = 4; i < linesOfSolcVerifyResultFile.size(); i++) {
			if (!linesOfSolcVerifyResultFile.get(i).startsWith(REGEX_PIPE))
				break;

			Pattern pattern = Pattern.compile(REGEX_VARIABLE_INFLUENCERS_LINE);
			Matcher matcher = pattern.matcher(linesOfSolcVerifyResultFile.get(i));

			if (matcher.matches() && matcher.group(INFLUENCERS_GROUP) != null
					&& matcher.group(INFLUENCERS_GROUP) != "") {
				String targetVariableName = matcher.group(NAME_GROUP);
				String allInfluencers = matcher.group(INFLUENCERS_GROUP);
				allInfluencers = allInfluencers.replaceAll(REGEX_WHITESPACE_OR_QUOTATION_MARK, "");
				String[] influencers = allInfluencers.split(REGEX_COMMA);

				for (String influencerVariableName : influencers) {

					RolesAnnotatedObject influencerVariable = roleAnnotations.get(context, influencerVariableName);
					RolesAnnotatedObject targetVariable = roleAnnotations.get(context, targetVariableName);
					if (influencerVariable != null && targetVariable != null) {
						varToVarRelationsSlither.addRelation(influencerVariable, targetVariable);
					}
				}
			}

		}
	}

	private String getContext(String firstLine) {

		Pattern pattern = Pattern.compile(REGEX_FIRST_LINE_CONTEXT);
		Matcher matcher = pattern.matcher(firstLine);

		return (matcher.matches() && matcher.group(CONTEXT_GROUP) != null) ? matcher.group(CONTEXT_GROUP) : "";
	}
}
