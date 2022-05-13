package egcl;

import java.util.ArrayList;
import java.util.Iterator;

public class Rule {
	// The event part
	private Boolean before;
	private String event_name;
	private ArrayList<String> event_parameter_vars;
	private ArrayList<String> event_parameter_types;
	private String target_type;
	private String target_var;

	private String throws_exception;

	private String condition;
	private String action;

	// Constructors
	// Rule with all values
	public Rule(Boolean before, String event, ArrayList<String> vars, ArrayList<String> types, String target_var,
			String target_type, String throws_exception, String condition, String action) {
		this.before = before;
		this.event_name = event;
		this.event_parameter_types = types;
		this.event_parameter_vars = vars;
		this.target_type = target_type;
		this.target_var = target_var;
		this.throws_exception = throws_exception;
		this.condition = condition;
		this.action = action;
	}

	// Rule with parameters but no target
	public Rule(Boolean before, String event, ArrayList<String> vars, ArrayList<String> types, String throws_exception,
			String condition, String action) {
		this(before, event, vars, types, null, null, throws_exception, condition, action);
	}

	// Rule with no target and no parameters
	public Rule(Boolean before, String event, String throws_exception, String condition, String action) {
		this(before, event, new ArrayList<String>(), new ArrayList<String>(), null, null, throws_exception, condition,
				action);
	}

	// Getters
	public ArrayList<String> getParameterVariables() {
		return event_parameter_vars;
	}

	public ArrayList<String> getParameterTypes() {
		return event_parameter_types;
	}

	public String getTargetType() {
		return target_type;
	}

	public String getTargetVariable() {
		return target_var;
	}

	public String getEvent() {
		return event_name;
	}

	public String getThrowsException() {
		return throws_exception;
	}

	public String getCondition() {
		return condition;
	}

	public String getAction() {
		return action;
	}

	// To string
	private String listTargetAndParametersWithTypes() {
		if (event_parameter_types == null)
			return "(..)";

		String result = "(" + target_type + " " + target_var;

		Iterator<String> iteratorTypes = event_parameter_types.iterator();
		Iterator<String> iteratorVars = event_parameter_vars.iterator();
		while (iteratorVars.hasNext()) {
			result += ", " + iteratorTypes.next() + " " + iteratorVars.next();
		}
		result += ")";
		return result;

	}

	private String listParametersWithoutTypes() {
		if (event_parameter_types == null)
			return "(..)";

		String result = "(";
		Boolean first = true;
		Iterator<String> iteratorVars = event_parameter_vars.iterator();
		while (iteratorVars.hasNext()) {
			if (!first) {
				result += ", ";
			} else {
				first = false;
			}
			result += iteratorVars.next();
		}
		result += ")";
		return result;
	}

	private String listParametersWithTypes() {
		if (event_parameter_types == null)
			return "(..)";

		String result = "(";
		Boolean first = true;
		Iterator<String> iteratorTypes = event_parameter_types.iterator();
		Iterator<String> iteratorVars = event_parameter_vars.iterator();
		while (iteratorVars.hasNext()) {
			if (!first) {
				result += ", ";
			} else {
				first = false;
			}
			result += iteratorTypes.next() + " " + iteratorVars.next();
		}
		result += ")";
		return result;
	}

	public String toString() {

		String result = "";

		if (before)
			result += "before ";
		else
			result += "after ";

		// Event + parameters (or ..)
		result += event_name + listParametersWithTypes();
		// Target
		if (target_type != null) {
			result += " target (" + target_type + " " + target_var + ")";
		}

		result += " && !adviceexecution() ";

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
		String result = "";

		if (getTargetVariable() == null && getParameterVariables() == null) {
			result += "()";
		} else if (getTargetVariable() == null) {
			result += listParametersWithTypes();
		} else if (getParameterVariables() == null) {
			result += "(" + getTargetType() + " " + getTargetVariable() + ")";
		} else {
			result += listTargetAndParametersWithTypes();
		}

		if (throws_exception != null)
			result += " throws " + throws_exception;

		result += ": call(* " + getEvent() + "(..))";

		if (getParameterVariables() != null) {
			result += " &&\n    args" + listParametersWithoutTypes();
		}
		if (getTargetVariable() != null) {
			result += " &&\n    target(" + getTargetVariable() + ")";
		}

		result += " &&\n   !(adviceexecution()) ";
		result += " &&\n   !cflow(adviceexecution()) ";
		result += " &&\n   !cflowbelow(adviceexecution()) ";

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
		if (before)
			aspectJrules.addBeforeAJRule("before " + result);
		else
			aspectJrules.addAfterAJRule("after " + result);

	}

}
