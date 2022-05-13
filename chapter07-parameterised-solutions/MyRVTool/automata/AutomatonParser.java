package automata;

import java.util.ArrayList;

public class AutomatonParser {

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

	// Returns index of first '->' or '>>>' not enclosed in '(' and ')'
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
			case '>':
				if (i + 2 < s.length() && s.charAt(i + 1) == '>' && s.charAt(i + 2) == '>' && balance == 0)
					return i;
			}
		}
		if (balance != 0)
			throw (new Exception("Unclosed '(' in guard"));
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

	// Returns index of first ' ' or '[' or '}'
	static private Integer getIndexEndOfDestinationState(String s) throws Exception {
		Integer i;

		for (i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case ' ':
			case '[':
			case '}':
				return i;
			}
		}
		throw new Exception("End of destination state not found! " + s);
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
					throw (new Exception("Unbalanced '{' in automaton"));
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

	// Automata parser
	static public ArrayList<Automaton> parseAutomata(String s) throws Exception {
		ArrayList<Automaton> automata = new ArrayList<Automaton>();
		s = s.trim();

		// loop over automata
		while (s.length() != 0) {

			System.out.println("Parsing automaton");

			String starting_state;
			String target_type = null;
			String target_variable = null;

			// Property
			Integer index_property = s.indexOf("property");
			if (index_property == -1)
				throw (new Exception("property keyword not found"));

			s = s.substring(index_property + 8).trim();

			// Foreach (optional)
			if (s.startsWith("foreach")) // note that the foreach keyword is optional
			{
				s = s.substring(7).trim();

				// now expecting the kind of binding
				// currently only supporting "Target" binding
				Integer index_target = s.indexOf("target");
				if (index_target == -1)
					throw (new Exception("target keyword not found"));

				s = s.substring(index_target + 6).trim();

				// Foreach type
				Integer index_opening_bracket = s.indexOf("(");
				if (index_opening_bracket == -1)
					throw (new Exception("Opening bracket not found"));

				s = s.substring(1).trim();

				Integer index_space_separator = s.indexOf(" ");
				if (index_space_separator == -1)
					throw (new Exception("Type or variable name not found"));

				target_type = s.substring(0, index_space_separator).trim();
				System.out.println("  - Foreach type is [" + target_type + "]");

				s = s.substring(index_space_separator + 1).trim();

				// Foreach variable
				Integer index_closing_bracket = s.indexOf(")");
				if (index_closing_bracket == -1)
					throw (new Exception("Closing bracket not found"));

				target_variable = s.substring(0, index_closing_bracket).trim();
				System.out.println("  - Foreach variable is [" + target_variable + "]");

				s = s.substring(index_closing_bracket + 1).trim();

			}

			// starting state
			Integer index_start_state = s.indexOf("starting");
			if (index_start_state == -1)
				throw (new Exception("starting keyword not found"));

			s = s.substring(index_start_state + 8).trim();

			Integer index_end_starting = s.indexOf("{");
			if (index_end_starting == -1)
				throw (new Exception("{ not found"));

			Integer index_keep = s.indexOf("keep");
			if (index_keep != -1 && index_keep < index_end_starting)
				index_end_starting = index_keep;

			starting_state = s.substring(0, index_end_starting).trim();

			s = s.substring(index_end_starting).trim();

			ArrayList<String> replicated_state_types = new ArrayList<String>();
			ArrayList<String> replicated_state_vars = new ArrayList<String>();
			ArrayList<String> replicated_state_defaults = new ArrayList<String>();

			// foreach (UserInfo u) keep (Boolean disabled defaultTo false)
			// Integer index_start_of_keep = s.indexOf("keep");
			if (s.startsWith("keep")) {
				// note that in the case of automata, keep is optional
				// this is not so in the case of GCL as a foreach doesn't make sense without any
				// state

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
				} // while

				s = s.substring(index_end_of_event_parameters + 1).trim();
			} // end of keep

			if (s.startsWith("{"))
				s = s.substring(1).trim();

			// transitions
			ArrayList<Transition> transitions = new ArrayList<Transition>();

			// loop over transitions
			while (!s.startsWith("}")) {

				Boolean before = true;
				String event;
				ArrayList<String> parameter_types;
				ArrayList<String> parameter_variables;
				String condition;
				String action;

				String source;
				String destination;
				String destination_display;

				System.out.println("Parsing transition");

				// Source state
				Integer index_first_arrow = s.indexOf(">>>");
				if (index_first_arrow == -1)
					throw (new Exception("Source state not found"));

				source = s.substring(0, index_first_arrow).trim();
				System.out.println("  - Source state is [" + source + "]");

				s = s.substring(index_first_arrow + 3).trim();

				// Event
				if (s.startsWith("before"))
					s = s.substring(7).trim();
				else if (s.startsWith("after")) {
					before = false;
					s = s.substring(5).trim();
				}
				Integer index_end_of_event = s.indexOf('(');
				if (index_end_of_event == -1)
					throw (new Exception("Event parameters not specified"));

				event = s.substring(0, index_end_of_event).trim();
				System.out.println("  - Event is [" + event + "]");

				s = s.substring(index_end_of_event + 1).trim();

				// Parameters
				Integer index_end_of_event_parameters2 = s.indexOf(')');
				if (index_end_of_event_parameters2 == -1)
					throw (new Exception("Error in event parameters"));

				String parameters_string2 = s.substring(0, index_end_of_event_parameters2).trim();

				if (parameters_string2.equals("..")) {
					parameter_types = null;
					parameter_variables = null;
					System.out.println("    - Generic parameter matching");
				} else {
					parameter_types = new ArrayList<String>();
					parameter_variables = new ArrayList<String>();

					while (!parameters_string2.equals("")) {
						Integer pos = getIndexEndOfFirstParameter(parameters_string2);

						// Separate first parameter
						String parameter = parameters_string2.substring(0, pos).trim();

						Integer space = parameter.lastIndexOf(' ');
						String parameter_type = parameter.substring(0, space).trim();
						String parameter_variable = parameter.substring(space + 1).trim();

						parameter_types.add(parameter_type);
						parameter_variables.add(parameter_variable);
						System.out.println(
								"    - Parameter [" + parameter_variable + "] with type [" + parameter_type + "]");

						// Drop the first parameter from the string to be parsed
						if (pos == parameters_string2.length()) {
							parameters_string2 = "";
						} else {
							parameters_string2 = parameters_string2.substring(pos + 1).trim();
						}
					} // while
				} // else

				s = s.substring(index_end_of_event_parameters2 + 1).trim();

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
				} // else

				// Guard (optional)

				if (s.startsWith("|")) {

					s = s.substring(1).trim();

					Integer index_end_of_guard = getIndexEndOfGuard(s);

					condition = s.substring(0, index_end_of_guard).trim();

					if (condition.length() == 0)
						condition = "true";

					System.out.println("  - Guard is [" + condition + "]");

					s = s.substring(index_end_of_guard).trim();
				} else
					condition = "true";

				// Action (optional)
				if (s.startsWith("->")) {

					s = s.substring(2).trim();
					Integer index_end_of_action = getCurlyBracketed(s);

					action = s.substring(0, index_end_of_action);
					System.out.println("  - Action is [" + action + "]");

					s = s.substring(index_end_of_action).trim();

				} else {
					action = "{}";
				}

				// Destination state
				Integer index_second_arrow = s.indexOf(">>>");
				if (index_second_arrow == -1)
					throw (new Exception("Destination state not found"));

				s = s.substring(index_second_arrow + 3).trim();

				Integer index_end_destination_state = getIndexEndOfDestinationState(s);

				destination = s.substring(0, index_end_destination_state).trim();
				System.out.println("  - Destination state is [" + destination + "]");

				s = s.substring(index_end_destination_state).trim();

				// Destination display (text to be shown upon reaching a particular state)
				// optional
				if (s.startsWith("[")) {

					s = s.substring(1).trim();

					Integer index_closing_square_bracket = s.indexOf("]");

					destination_display = s.substring(0, index_closing_square_bracket).trim();
					System.out.println("  - Destination display text is [" + destination_display + "]");

					s = s.substring(index_closing_square_bracket + 1).trim();
				} else {
					destination_display = "";
				}

				Transition t = new Transition(before, source, destination, event, parameter_variables, parameter_types,
						target_variable, target_type, condition, action, destination_display);

				transitions.add(t);

			} // end while

			// consume } ending automaton declaration
			s = s.substring(1).trim();

			automata.add(new Automaton(starting_state, transitions, target_type, target_variable,
					replicated_state_types, replicated_state_vars, replicated_state_defaults));

		}

		return automata;
	}

}
