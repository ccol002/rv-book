package egcl;

import java.util.ArrayList;

public class RuleParser {

	// Returns index of first ',' (or end of string if none) outside delimiters '<'
	// and '>'
	// Throws exceptions if the string contains unbalanced or unclosed delimiters
	// '<' and '>'
	static private Integer getIndexEndOfFirstParameter(String s) throws Exception {
		Integer balance = 0, i;

		for (i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case '<':
				balance++;
				break;
			case '>':
				if (balance == 0) {
					throw (new Exception("Unbalanced '<' in parameter types"));
				}
				balance--;
				break;
			case ',':
				if (balance == 0)
					return i;
			}
		}
		if (balance != 0)
			throw (new Exception("Unclosed '<' in parameter types"));
		return i;
	}

	// Returns index of first '->' not enclosed in '(' and ')'
	// Throws an exception if the string contains unbalanced or unclosed delimiters
	// '(' and ')'.
	static private Integer getIndexEndOfGuard(String s) throws Exception {
		Integer balance = 0, i;

		for (i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case '(':
				balance++;
				break;
			case ')':
				if (balance == 0) {
					throw (new Exception("Unbalanced '(' in guard"));
				}
				balance--;
				break;
			case '-':
				if (i + 1 < s.length() && s.charAt(i + 1) == '>' && balance == 0)
					return i;
			}
		}
		if (balance != 0)
			throw (new Exception("Unclosed '(' in guard"));
		return i;
	}

	// The initial string enclosed between '{' and '}'.
	// Throws an exception if (i) the string does not start with '{'; or (i)
	// contains
	// unbalanced or unclosed delimiters.
	static private Integer getCurlyBracketed(String s) throws Exception {
		Integer balance = 0, i;

		if (s.charAt(0) != '{')
			throw (new Exception("Action not enclosed in curly brackets"));

		for (i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case '{':
				balance++;
				break;
			case '}':
				if (balance == 0) {
					throw (new Exception("Unbalanced '{' in rule"));
				}
				if (balance == 1) {
					return (i + 1);
				}
				balance--;
				break;
			}

		}
		throw (new Exception("Unclosed '{' in rule"));
	}

	// Single rule parser
	static public ArrayList<Rule> parseRules(String s) throws Exception {
		ArrayList<Rule> rules = new ArrayList<Rule>();
		s = s.trim();

		while (!s.equals("")) {
			Boolean before = true;
			String event;
			ArrayList<String> parameter_types;
			ArrayList<String> parameter_variables;
			String target_type;
			String target_variable;
			String condition;
			String action;

			System.out.println("Parsing rule");

			if (s.startsWith("before"))
				s = s.substring(7).trim();
			else if (s.startsWith("after")) {
				before = false;
				s = s.substring(5).trim();
			}
			// Event
			Integer index_end_of_event = s.indexOf('(');
			if (index_end_of_event == -1)
				throw (new Exception("Event parameters not specified"));

			event = s.substring(0, index_end_of_event).trim();
			System.out.println("  - Event is [" + event + "]");

			s = s.substring(index_end_of_event + 1).trim();

			// Parameters
			Integer index_end_of_event_parameters = s.indexOf(')');
			if (index_end_of_event_parameters == -1)
				throw (new Exception("Error in event parameters"));

			String parameters_string = s.substring(0, index_end_of_event_parameters).trim();

			if (parameters_string.equals("..")) {
				parameter_types = null;
				parameter_variables = null;
				System.out.println("    - Generic parameter matching");
			} else {
				parameter_types = new ArrayList<String>();
				parameter_variables = new ArrayList<String>();

				while (!parameters_string.equals("")) {
					Integer pos = getIndexEndOfFirstParameter(parameters_string);

					// Separate first parameter
					String parameter = parameters_string.substring(0, pos).trim();

					Integer space = parameter.lastIndexOf(' ');
					String parameter_type = parameter.substring(0, space).trim();
					String parameter_variable = parameter.substring(space + 1).trim();

					parameter_types.add(parameter_type);
					parameter_variables.add(parameter_variable);
					System.out
							.println("    - Parameter [" + parameter_variable + "] with type [" + parameter_type + "]");

					// Drop the first parameter from the string to be parsed
					if (pos == parameters_string.length()) {
						parameters_string = "";
					} else {
						parameters_string = parameters_string.substring(pos + 1).trim();
					}
				}
			}

			s = s.substring(index_end_of_event_parameters + 1).trim();

			// Target
			if (s.startsWith("target")) {
				s = s.substring(6).trim();
				if (s.charAt(0) != '(') {
					throw (new Exception("Error in target specification"));
				}

				Integer index_end_of_target_specification = s.indexOf(')');
				if (index_end_of_target_specification == -1)
					throw (new Exception("Error in target specification"));

				String target_string = s.substring(1, index_end_of_target_specification).trim();

				Integer space = target_string.lastIndexOf(' ');
				target_type = target_string.substring(0, space).trim();
				target_variable = target_string.substring(space + 1).trim();

				System.out.println("  - Target [" + target_variable + "] with type [" + target_type + "]");

				s = s.substring(index_end_of_target_specification + 1).trim();
			} else {
				target_type = null;
				target_variable = null;

				System.out.println("  - No target named");
			}

			// Guard
			if (s.charAt(0) != '|') {
				throw (new Exception("No guard condition separator (|) found"));
			}

			s = s.substring(1).trim();

			if (s.startsWith("->")) {
				condition = "true";
				System.out.println("  - Empty guard taken to be [" + condition + "]");
			} else {
				Integer index_end_of_guard = getIndexEndOfGuard(s);

				condition = s.substring(0, index_end_of_guard).trim();
				System.out.println("  - Guard is [" + condition + "]");

				s = s.substring(index_end_of_guard).trim();
			}

			// Action
			if (!s.startsWith("->")) {
				throw (new Exception("No action separator (->) found"));
			}
			s = s.substring(2).trim();
			Integer index_end_of_action = getCurlyBracketed(s);

			action = s.substring(0, index_end_of_action);
			System.out.println("  - Action is [" + action + "]");

			s = s.substring(index_end_of_action).trim();

			rules.add(new Rule(before, event, parameter_variables, parameter_types, target_variable, target_type,
					condition, action));
		}
		return rules;
	}
}
