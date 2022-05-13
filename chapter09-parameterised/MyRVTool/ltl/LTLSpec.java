package ltl;

import java.util.Set;

import ltl.structure.Event;
import ltl.structure.LTL;

public class LTLSpec {

	// bad behaviour LTL is not supported
	public enum Matching {
		GOOD
	};

	private LTL ltl;
	private Matching matching;

//	private String target_type;
//	private String target_variable;

	private Set<String> identifiers;

	public LTLSpec(LTL ltl, Matching matching) {
		this.ltl = ltl;
		this.matching = matching;
	}

	// getters

	public LTL getLTL() {
		return ltl;
	}

	public Matching getMatching() {
		return matching;
	}

	public Set<String> getIdentifiers() {
		return identifiers;
	}

	// tostring
	public String toString() {
		String result = "property ";

		result += "{ " + matching + " " + ltl + "}";

		return result;

	}

	// return a unique string for this object
	public String getInit() {
		return "init" + super.toString().substring(12) + "(); ";
	}

	public String getId() {
		// obtain a unique number
		return super.toString().substring(14);

	}

	public String toEGCLSetupVerificationCodeDecl() throws Exception {
		String result = "";

		// initial state
		result += "\r\nstatic public LTL currentLTL" + getId() + ";";
		result += "\r\nstatic public Boolean triggered" + getId() + ";";

		return result;
	}

	public String toEGCLVerificationCodeDecl() throws Exception {
		String result = "";

		// initial state
		result += "\r\ncurrentLTL" + getId() + " = " + this.getLTL().getConstructor() + ";";
		result += "\r\ntriggered" + getId() + " = false;";

		return result;
	}

	public String toEGCL() throws Exception {

		String result = "\n\n// the code for " + matching + ": " + ltl.toString();

		String verification = "Verification.";

		Set<Event> events = this.ltl.getRelevantEvents();

		// perform derivative for each event matched
		for (Event e : events) {

			result += "\n\n " + e.getModality() + " " + e.getEvent() + "(..) " + "\n | !" + verification + "triggered"
					+ getId() + "\n -> {\r\n " + verification + "currentLTL" + getId() + " = " + verification
					+ "currentLTL" + getId() + ".derivative(" + e.getConstructor() + ");" + "\r\n " + verification
					+ "triggered" + getId() + " = true;" + "}";
		}

		return result;
	}

	public String getWildcardChecks(String modality) {

		String result = "";
		String verification = "Verification.";
		// check for "non-matches"
		result += "\r\n if (!" + verification + "triggered" + getId() + ") {" + verification + "currentLTL" + getId()
				+ " = " + verification + "currentLTL" + getId() + ".derivative(new Event(\"" + modality
				+ "\", \"#\")); " + "\n " + verification + "triggered" + getId() + " = true; }";

		result += "\r\n if (" + verification + "currentLTL" + getId() + ".cannotMatch()) {"
				+ "\n    Assertion.alert(\"Violation detected on " + toString() + "\"); }" + "\n" + verification
				+ "triggered" + getId() + " = false; ";

		return result;
	}

}
