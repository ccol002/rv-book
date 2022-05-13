package automata;

import java.util.ArrayList;

public class Automaton {

	private ArrayList<Transition> transitions;

	private String start_state;
	private String target_type;
	private String target_variable;

	public Automaton(String start_state, ArrayList<Transition> transitions, String foreach_type,
			String foreach_variable) {
		this.start_state = start_state;
		this.transitions = transitions;
		this.target_type = foreach_type;
		this.target_variable = foreach_variable;
	}

	public Automaton(String start_state, ArrayList<Transition> transitions) {
		this(start_state, transitions, null, null);
	}

	// getters
	public String getStartState() {
		return start_state;
	}

	public String getTargetType() {
		return target_type;
	}

	public String getTargetVariable() {
		return target_variable;
	}

	// tostring
	public String toString() {
		String result = "property ";

		if (target_type != null) {
			result += "foreach target (" + target_type + " " + target_variable + ") ";
		}

		result += "starting " + start_state + "{ ";

		for (Transition t : transitions)
			result += t.toString();

		result += "}";

		return result;

	}

	// toEGCL verification code (declaration of variables)
	public String toEGCLVerificationCodeDecl() {
		String id = super.toString().substring(22);// string id from object toString

		if (target_type != null)
			// foreach
			return "static public HashMap<" + target_type + ",String> state" + id + ";" + "\nstatic public HashMap<"
					+ target_type + ",Boolean> hasTriggered" + id + ";";
		else
			return "static public String state" + id + ";" + "\nstatic public Boolean hasTriggered" + id + ";";
	}

	// toEGCL verification code (declaration of variables)
	public String toEGCLSetupVerificationCodeDecl() {
		String id = super.toString().substring(22);// string id from object toString

		if (target_type != null)
			// foreach
			return "hasTriggered" + id + " = new HashMap<" + target_type + ",Boolean> ();" + "\nstate" + id
					+ " = new HashMap<" + target_type + ",String> ();";
		else
			return "hasTriggered" + id + " = false;" + "\nstate" + id + " = \"" + start_state + "\";";
	}

	// toEGCL
	public String toEGCL() throws Exception {
		String result = "";
		String id = super.toString().substring(22);// string id from object toString

		for (Transition t : transitions)
			result += t.toEGCL(id, target_type);

		return result;
	}

	public String getInit() {
		return "init" + super.toString().substring(19) + "(); ";
	}

	// toEGCL verification code (declaration of variables)
	public String getTriggerReset() {
		String id = super.toString().substring(22);// string id from object toString

		if (target_type != null)
			// foreach
			return "hasTriggered" + id + " = new HashMap<" + target_type + ",Boolean> ();";

		else
			return "Verification.hasTriggered" + id + " = false;";
	}

}
