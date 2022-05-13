package automata;

import java.util.ArrayList;

import egcl.Event;
import egcl.Rule;

public class Transition {

	// The event part
	private Rule rule;

	private String source_state;
	private String destination_state;
	private String destination_text;

	// Constructors
	// Rule with all values
	public Transition(Boolean before, String source_state, String destination_state, String event,
			ArrayList<String> vars, ArrayList<String> types, String target_var, String target_type, String condition,
			String action, String destination_text) {

		this.rule = new Rule(before, event, vars, types, target_var, target_type, condition, action);

		this.source_state = source_state;
		this.destination_state = destination_state;
		this.destination_text = destination_text;
	}

	// Automaton with parameters but no target
	public Transition(Boolean before, String source_state, String destination_state, String event,
			ArrayList<String> vars, ArrayList<String> types, String condition, String action, String destination_text) {
		this(before, source_state, destination_state, event, vars, types, null, null, condition, action,
				destination_text);
	}

	// Automaton with no target and no parameters
	public Transition(Boolean before, String source_state, String destination_state, String event, String condition,
			String action, String destination_text) {
		this(before, source_state, destination_state, event, new ArrayList<String>(), new ArrayList<String>(), null,
				null, condition, action, destination_text);
	}

	// Getters
	public Rule getRule() {
		return rule;
	}

	public String getSourceState() {
		return source_state;
	}

	public String getDestinationState() {
		return destination_state;
	}

	// To string

	public String toString() {
		String result;

		// Source state
		result = source_state + ">>>";

		result += rule.toString();

		// Destination state
		result += ">>>" + destination_state;

		return (result);
	}

	public String toEGCL(String id, String target_type) throws Exception {
		// eg
		// UserInfo.makeDisabled(..) target (UserInfo u) | -> { disabled = true; }

		Event event = rule.getEvent();

		String result = "\n\n" + event.getEvent();

		if (event.listParametersWithTypes() != null)// some params to bind
			result += event.listParametersWithTypes();
		else
			result += "() ";

		String nonforeach = "";

		if (event.getTargetType() != null)
			result += "target (" + event.getTargetType() + " " + event.getTargetVariable() + ")";

		if (target_type == null)// non foreach
			nonforeach = "Verification."; // this is until we support local variables better in automata/rules

		// condition including state check
		result += " | !" + nonforeach + "hasTriggered" + id + " && " + nonforeach + "state" + id + ".equals(\""
				+ this.source_state + "\")" + " && (" + rule.getCondition() + ")";

		// action including state update
		result += "\n  -> {" + rule.getAction() + nonforeach + "state" + id + " = \"" + this.destination_state + "\";";
		result += nonforeach + "hasTriggered" + id + " = true;";

		// display text related to destination state
		if (destination_text.length() > 0)
			result += "\n System.out.println(\"" + destination_text + "\");";

		return result + "}\n";
	}

}
