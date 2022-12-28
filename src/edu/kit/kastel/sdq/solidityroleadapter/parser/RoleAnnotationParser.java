package edu.kit.kastel.sdq.solidityroleadapter.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.kastel.sdq.solidityroleadapter.Function;
import edu.kit.kastel.sdq.solidityroleadapter.Variable;

public class RoleAnnotationParser {
	
	private static final String REGEX_ROLE_ANNOTATIONS = "(?<name>[^\\s]+) (?<roles1>\\{[^\\}]*\\})(, (?<roles2>\\{[^\\}]*\\}))*";
	private static final String NAME_GROUP = "name", FIRST_ROLES_GROUP = "roles1", SECOND_ROLES_GROUP = "roles2";

	HashMap<String, Function> functions;
	HashMap<String, Variable> variables;
	
	public RoleAnnotationParser() {
		this.functions = new HashMap<String, Function>();
		this.variables = new HashMap<String, Variable>();
	}
	
	public void parse(final String uri) throws IOException {
		this.parseRoleAnnotations(this.readLines(uri));
	}
	
	private List<String> readLines (final String uri) throws IOException {
		return Files.readAllLines(Paths.get(uri));
	}
	
	private void parseRoleAnnotations(List<String> linesOfRoleAnnotationFile) {
		// Load RoleAnnotations into Hashmaps for functions and variables
		for (String line : linesOfRoleAnnotationFile) {
			Pattern pattern = Pattern.compile(REGEX_ROLE_ANNOTATIONS);
			Matcher matcher = pattern.matcher(line);
			if (matcher.matches()) {
				if (matcher.group(SECOND_ROLES_GROUP) == null) {
					functions.put(matcher.group(NAME_GROUP), new Function(matcher.group(NAME_GROUP), matcher.group(FIRST_ROLES_GROUP)));
				} else {
					String name = matcher.group(NAME_GROUP).replaceFirst("[^:]+::", "");
					variables.put(name, new Variable(name, matcher.group(FIRST_ROLES_GROUP), matcher.group(SECOND_ROLES_GROUP)));
				}
			}
		}
	}
	
	public void printParsedDataInfo() {
		
		System.out.println("-----Parsed Variables-----");
		for (Variable v : variables.values()) {
			System.out.println(v.toString());
		}
		System.out.println("");

		System.out.println("-----Parsed Functions-----");
		for (Function f : functions.values()) {
			System.out.println(f.toString());
		}
		System.out.println("");
	}
	
	/**
	 * Returns the Function with annotated roles
	 * 
	 * @param context The name of the class of the function
	 * @param name
	 * @return
	 */
	public Function getFunction (String context, String name) {
		return null;
	}
}
