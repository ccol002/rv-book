package egcl;

import java.util.ArrayList;

public class RuleParser {

	public static ArrayList<Rule> rules;
	public static ArrayList<Foreach> foreaches;

	// Returns index of first ',' (or end of string if none) outside delimiters '<'
	// and '>'
	// Throws exceptions if the string contains unbalanced or unclosed delimiters
	// '<' and '>'
	static private Integer getIndexEndOfFirstParameter(String s) throws Exception {
		Integer balanceAng = 0, balanceRnd = 0, i;

		for (i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case '<':
				balanceAng++;
				break;
			case '(':
				balanceRnd++;
				break;
			case '>':
				if (balanceAng == 0) {
					throw (new Exception("Unbalanced '<' in parameter types"));
				}
				balanceAng--;
				break;
			case ')':
				if (balanceRnd == 0) {
					throw (new Exception("Unbalanced '(' in parameter types"));
				}
				balanceRnd--;
				break;
			case ',':
				if (balanceAng == 0 && balanceRnd == 0)
					return i;
			}
		}
		if (balanceAng != 0)
			throw (new Exception("Unclosed '<' in parameter types"));
		if (balanceRnd != 0)
			throw (new Exception("Unclosed '(' in parameter types"));
		return i;
	}

	static private Integer getIndexEndRoundBracket(String s) throws Exception {
		int indx = s.indexOf(")");
		if (indx == -1)
			return -1;

		int indxOpen = s.indexOf("(");
		if (indxOpen == -1)
			throw (new Exception("Unbalanced ')' in parameter types"));

		Integer balance = 1, i;

		for (i = indxOpen + 1; i < s.length(); i++) {

			if (balance == 0)
				break;

			switch (s.charAt(i)) {
			case '(':
				balance++;
				break;
			case ')':
				if (balance == 0) {
					throw (new Exception("Unbalanced '(' in parameter types"));
				}
				balance--;
				break;
			}
		}
		return i - 1;
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

	static public ArrayList<Rule> parseRules(String s) throws Exception {
		ArrayList<Rule> rules = new ArrayList<Rule>();
		s = s.trim();

		while (!s.equals("") && !s.startsWith("foreach")) {
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

			action = s.substring(1, index_end_of_action - 1); // drop curly brackets
			System.out.println("  - Action is [" + action + "]");

			s = s.substring(index_end_of_action).trim();

			rules.add(new Rule(before, event, parameter_variables, parameter_types, target_variable, target_type,
					condition, action));
		}
		return rules;
	}

	static public void parseForeaches(String s) throws Exception {
		foreaches = new ArrayList<Foreach>();
		rules = new ArrayList<Rule>();
		s = s.trim();

		do {

			int indxForeach = s.indexOf("foreach");
			if (indxForeach != -1) {
				String tillForeach = s.substring(0, indxForeach);
				s = s.substring(indxForeach).trim();
				rules.addAll(parseRules(tillForeach));

				// parse foreach
				System.out.println("Parsing foreach");

				String foreach_type;
				String foreach_var;

				ArrayList<String> replicated_state_types = new ArrayList<String>();
				ArrayList<String> replicated_state_vars = new ArrayList<String>();
				ArrayList<String> replicated_state_defaults = new ArrayList<String>();

				ArrayList<Rule> rules = new ArrayList<Rule>();

				// foreach target (UserInfo u)
				Integer index_start_of_foreach_type = s.indexOf('(');
				if (index_start_of_foreach_type == -1)
					throw (new Exception("foreach variable type not specified"));

				s = s.substring(index_start_of_foreach_type + 1).trim();

				Integer index_end_of_foreach_type = s.indexOf(' ');
				if (index_end_of_foreach_type == -1)
					throw (new Exception("foreach variable name not specified"));

				foreach_type = s.substring(0, index_end_of_foreach_type).trim();

				Integer index_end_of_foreach_var = s.indexOf(')');
				if (index_end_of_foreach_var == -1)
					throw (new Exception("foreach variable declaration not concluded"));

				foreach_var = s.substring(index_end_of_foreach_type, index_end_of_foreach_var).trim();
				System.out.println(" Foreach [" + foreach_type + " " + foreach_var + "]");

				s = s.substring(index_end_of_foreach_var + 1).trim();

				// foreach (UserInfo u) keep (Boolean disabled defaultTo false)
				Integer index_start_of_keep = s.indexOf("keep");
				if (index_start_of_keep == -1)
					throw (new Exception("keep expected"));

				Integer index_start_of_list = s.indexOf("(");
				if (index_start_of_list == -1)
					throw (new Exception("Error in state parameters"));

				Integer index_end_of_event_parameters = getIndexEndRoundBracket(s);
				if (index_end_of_event_parameters == -1)
					throw (new Exception("Error in state parameters"));

				String parameters_string = s.substring(index_start_of_list + 1, index_end_of_event_parameters).trim();

				while (!parameters_string.equals("")) {
					Integer pos = getIndexEndOfFirstParameter(parameters_string);

					// Separate first parameter
					String parameter = parameters_string.substring(0, pos).trim();

					Integer space = parameter.indexOf(' ');
					Integer index_start_of_defaultTo = parameter.indexOf("defaultTo");
					if (index_start_of_defaultTo == -1)
						throw (new Exception("defaultTo expected"));

					String parameter_type = parameter.substring(0, space).trim();
					String parameter_variable = parameter.substring(space + 1, index_start_of_defaultTo).trim();

					replicated_state_types.add(parameter_type);
					replicated_state_vars.add(parameter_variable);

					String parameter_default = parameter.substring(index_start_of_defaultTo + 9).trim();
					replicated_state_defaults.add(parameter_default);

					System.out.println("    - Parameter [" + parameter_variable + "] with type [" + parameter_type
							+ "] defaulting to [" + parameter_default + "]");

					// Drop the first parameter from the string to be parsed
					if (pos == parameters_string.length()) {
						parameters_string = "";
					} else {
						parameters_string = parameters_string.substring(pos + 1).trim();
					}
				}

				s = s.substring(index_end_of_event_parameters + 1).trim();

				int index_end_foreach = getCurlyBracketed(s);
				rules = parseRules(s.substring(1, index_end_foreach - 1));

				s = s.substring(index_end_foreach).trim();

				foreaches.add(new Foreach(rules, foreach_type, foreach_var, replicated_state_types,
						replicated_state_vars, replicated_state_defaults));

			} else {
				rules.addAll(parseRules(s));
				s = "";
			}
		} while (!s.equals(""));

	}
}
