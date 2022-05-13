package egcl;

import java.util.ArrayList;

public class Rule {
	// The event part
	private Event event;
	private String condition;
	private String action;

	// Constructors
	// Rule with all values
	public Rule(Boolean before, String event, ArrayList<String> vars, ArrayList<String> types, String target_var,
			String target_type, String condition, String action) {

		this.event = new Event(before, event, vars, types, target_var, target_type);
		this.condition = condition;
		this.action = action;
	}

	// Rule with parameters but no target
	public Rule(Boolean before, String event, ArrayList<String> vars, ArrayList<String> types, String condition,
			String action) {
		this(before, event, vars, types, null, null, condition, action);
	}

	// Rule with no target and no parameters
	public Rule(Boolean before, String event, String condition, String action) {
		this(before, event, new ArrayList<String>(), new ArrayList<String>(), null, null, condition, action);
	}

	// Getters
	public Event getEvent() {
		return event;
	}

	public String getCondition() {
		return condition;
	}

	public String getAction() {
		return action;
	}

	public String toString() {

		// Event
		String result = event.toString();

		// Guard
		result += " | { " + condition + " }";
		// Action
		result += " -> { " + action + " }";

		return (result);
	}

	// replace references to variable with get

	public String replaceReplicatedStateAssignment(String code, String foreach_var, ArrayList<String> vars,
			ArrayList<String> defaults) {
		String result = "";
		String[] splitByEquals = code.split("=");
		String s = splitByEquals[0].trim();

		// get assigned var
		for (int i = 0; i < splitByEquals.length - 1; i++) {
			String[] words = s.split("\\W+");// split by all non-alphanumeric character
			String varAssigned = words[words.length - 1];

			int index_varAssigned_start = s.length() - varAssigned.length();

			if (index_varAssigned_start > 0) {
				s = s.substring(0, index_varAssigned_start).trim();
				// replace refs in code preceding assignment statement
				for (int k = 0; k < vars.size(); k++)
					s = s.replaceAll(vars.get(k),
							vars.get(k) + ".computeIfAbsent(" + foreach_var + ", (k) -> " + defaults.get(k) + ")");

				result += s;
			}

			String t = splitByEquals[i + 1].trim();

			int index_semicolon = t.indexOf(";");
			String assignment = t.substring(0, index_semicolon).trim();

			t = t.substring(index_semicolon + 1).trim(); // this will be handled by next iteration
			s = t;

			// replace refs in assignment
			for (int j = 0; j < vars.size(); j++)
				assignment = assignment.replaceAll(vars.get(j),
						vars.get(j) + ".computeIfAbsent(" + foreach_var + ", (k) -> " + defaults.get(j) + ")");

			if (vars.contains(varAssigned))// this is a managed replicated state
				result += varAssigned + ".put(" + foreach_var + "," + assignment + ");";
			else // this is some other assignment statement
				result += varAssigned + " = " + assignment + ";";
		}

		s = replaceReplicatedStateReference(s, foreach_var, vars, defaults);

		result += s;

		return result;
	}

	public String replaceReplicatedStateReference(String code, String foreach_var, ArrayList<String> vars,
			ArrayList<String> defaults) {
		String result = "";
		String s = code;

		for (int k = 0; k < vars.size(); k++)
			s = s.replaceAll(vars.get(k),
					vars.get(k) + ".computeIfAbsent(" + foreach_var + ", (k) -> " + defaults.get(k) + ")");

		result += s;

		return result;
	}

	public void toAspectJ(AspectJScript aspectJrules) {
		toAspectJ(aspectJrules, null, null, null);
	}

	public void toAspectJ(AspectJScript aspectJrules, String foreach_var, ArrayList<String> vars,
			ArrayList<String> defaults) {

		String result = event.toAspectJ();

		if (foreach_var == null)
			result += " {\n" + "    if (" + getCondition() + ") {" + getAction() + "}\n" + "  }";
		else {
			if (condition == "true") {
				result += " {\n" + replaceReplicatedStateAssignment(getAction(), foreach_var, vars, defaults) + "}\n";
			} else {
				result += " {\n" + "    if ("
						+ replaceReplicatedStateReference(getCondition(), foreach_var, vars, defaults) + ") {\n"
						+ replaceReplicatedStateAssignment(getAction(), foreach_var, vars, defaults) + "}\n" + "  }\n";
			}
		}

		aspectJrules.addAfterAJRule(result);

	}

}
