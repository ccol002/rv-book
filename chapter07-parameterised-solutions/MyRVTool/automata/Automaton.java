package automata;

import java.util.ArrayList;

public class Automaton {

	private ArrayList<Transition> transitions;

	private String start_state;
	private String target_type;
	private String target_variable;

	ArrayList<String> replicated_state_types;
	ArrayList<String> replicated_state_vars;
	ArrayList<String> replicated_state_defaults;

	public Automaton(String start_state, ArrayList<Transition> transitions, String target_type, String target_variable,
			ArrayList<String> replicated_state_types, ArrayList<String> replicated_state_vars,
			ArrayList<String> replicated_state_defaults) {
		this.start_state = start_state;
		this.transitions = transitions;
		this.target_type = target_type;
		this.target_variable = target_variable;

		this.replicated_state_types = replicated_state_types;
		this.replicated_state_vars = replicated_state_vars;
		this.replicated_state_defaults = replicated_state_defaults;
	}

	public Automaton(String start_state, ArrayList<Transition> transitions) {
		this(start_state, transitions, null, null, null, null, null);
	}

	// getters
	public String getStartState() {
		return start_state;
	}

	public String getForeachType() {
		return target_type;
	}

	public String getForeachVariable() {
		return target_variable;
	}

	// tostring
	public String toString() {
		String result = "property ";

		if (target_type != null) {
			result += "foreach target (" + target_type + " " + target_variable + ") ";
		}

		result += "starting " + start_state;

		result += " keep ( ";

		for (int i = 0; i < replicated_state_types.size(); i++)
			result += replicated_state_types.get(i) + " " + replicated_state_vars.get(i) + " defaultTo "
					+ replicated_state_defaults.get(i) + ", ";

		result += ") { ";

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

		if (target_type != null)
		// foreach
		{
			// foreach (UserInfo u) keep (Boolean disabled defaultTo false) {
			result += "foreach target (" + target_type + " " + target_variable + ") keep (Boolean hasTriggered" + id
					+ " defaultTo false, String state" + id + " defaultTo \"" + start_state + "\"";

			for (int i = 0; i < replicated_state_types.size(); i++)
				result += "," + replicated_state_types.get(i) + " " + replicated_state_vars.get(i) + " defaultTo "
						+ replicated_state_defaults.get(i);

			result += ") { ";

		}

		for (Transition t : transitions)
			result += t.toEGCL(id, target_type);

		if (target_type != null)
		// foreach
		{
			result += "\n}";
		}

		return result;
	}

	public String getInit() {
		return "init" + super.toString().substring(22) + "(); ";
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
