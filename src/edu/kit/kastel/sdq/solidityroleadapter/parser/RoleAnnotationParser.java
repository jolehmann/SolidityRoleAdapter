package edu.kit.kastel.sdq.solidityroleadapter.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.kastel.sdq.solidityroleadapter.items.Function;
import edu.kit.kastel.sdq.solidityroleadapter.items.Role;
import edu.kit.kastel.sdq.solidityroleadapter.items.SingleRoles;
import edu.kit.kastel.sdq.solidityroleadapter.items.TupleRoles;
import edu.kit.kastel.sdq.solidityroleadapter.items.Variable;
import edu.kit.kastel.sdq.solidityroleadapter.operation.RoleAnnotations;

public class RoleAnnotationParser {

	private static final String REGEX_ROLE_ANNOTATIONS = "(?<context>[^:]+)::(?<name>[^\\s]+) (\\{(?<roles1>[^\\}]*)\\})(, (\\{(?<roles2>[^\\}]*)\\}))*";
	private static final String CONTEXT_GROUP = "context", NAME_GROUP = "name", FIRST_ROLES_GROUP = "roles1",
			SECOND_ROLES_GROUP = "roles2";

	public RoleAnnotationParser() {
	}

	/**
	 * Reads the file with the provided uri and writes all found variables and
	 * functions with their roles into the RoleAnnotations object.
	 * 
	 * @param uri             the path to the RoleAnnotations.txt file
	 * @param roleAnnotations An RoleAnnotations object, to which the data should be
	 *                        added
	 * @throws IOException if the path is not accessible
	 */
	public void parse(final String uri, RoleAnnotations roleAnnotations) throws IOException {
		this.parseRoleAnnotations(this.readLines(uri), roleAnnotations);
	}

	private List<String> readLines(final String uri) throws IOException {
		return Files.readAllLines(Paths.get(uri));
	}

	private void parseRoleAnnotations(List<String> linesOfRoleAnnotationFile, RoleAnnotations roleAnnotations) {
		// Read lines and add them to the roleAnnotations
		for (String line : linesOfRoleAnnotationFile) {
			Pattern pattern = Pattern.compile(REGEX_ROLE_ANNOTATIONS);
			Matcher matcher = pattern.matcher(line);

			if (matcher.matches()) {
				if (matcher.group(SECOND_ROLES_GROUP) == null) {
					// Function
					SingleRoles roles = parseRoles(matcher.group(FIRST_ROLES_GROUP));
					roleAnnotations.add(new Function(matcher.group(CONTEXT_GROUP), matcher.group(NAME_GROUP), roles));
				} else {
					// Variable
					TupleRoles roles = new TupleRoles(parseRoles(matcher.group(FIRST_ROLES_GROUP)),
							parseRoles(matcher.group(SECOND_ROLES_GROUP)));
					roleAnnotations.add(new Variable(matcher.group(CONTEXT_GROUP), matcher.group(NAME_GROUP), roles));
				}
			}
		}
	}

	private SingleRoles parseRoles(String rolesInBrackets) {
		SingleRoles roles = new SingleRoles();
		for (String name : rolesInBrackets.split(", ")) {
			if (name != "") {
				roles.add(new Role(name));
			}
		}
		return roles;
	}
}
