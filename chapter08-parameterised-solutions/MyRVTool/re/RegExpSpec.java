package re;

import java.util.Set;

import re.structure.Event;
import re.structure.RegExp;

public class RegExpSpec {

	public enum Matching {
		GOOD, BAD
	};

	private RegExp re;
	private Matching matching;

	public RegExpSpec(RegExp re, Matching matching) {
		this.re = re;
		this.matching = matching;
	}

	// getters

	public RegExp getRegExp() {
		return re;
	}

	public Matching getMatching() {
		return matching;
	}

	// tostring
	public String toString() {
		String result = " property ";

		result += "{ " + matching + " " + re + "}";

		return result;

	}

	public String getId() {
		// obtain a unique number
		return super.toString().substring(14);

	}

	public String toEGCLSetupVerificationCodeDecl() throws Exception {
		String result = "";

		// initial state
		result += "\r\nstatic public RegExp currentRegExp" + getId() + ";";
		result += "\r\nstatic public Boolean triggered" + getId() + ";";

		return result;
	}

	public String toEGCLVerificationCodeDecl() throws Exception {
		String result = "";

		// initial state
		result += "\r\ncurrentRegExp" + getId() + " = " + this.getRegExp().getConstructor() + ";";
		result += "\r\ntriggered" + getId() + " = false;";

		return result;
	}

	public String toEGCL() throws Exception {

		String result = "\n\n// the code for " + matching + ": " + re.toString();

		String verification = "Verification.";

		Set<Event> events = this.re.getRelevantEvents();

		// perform derivative for each event matched
		for (Event e : events) {

			result += "\n\n " + e.getModality() + " " + e.getEvent() + "(..) " + " | -> {\r\n " + verification
					+ "currentRegExp" + getId() + " = " + verification + "currentRegExp" + getId() + ".derivative("
					+ e.getConstructor() + ");" + "\r\n " + verification + "triggered" + getId() + " = true;" + "}";
		}

		return result;
	}

	public String getWildcardChecks(String modality) {

		String result = "";
		String verification = "Verification.";
		// check for "non-matches"
		result += "\r\n if (!" + verification + "triggered" + getId() + ") {" + verification + "currentRegExp" + getId()
				+ " = " + verification + "currentRegExp" + getId() + ".derivative(new Event(\"" + modality
				+ "\", \"#\")); " + "\n " + verification + "triggered" + getId() + " = true; }";

		// check violation
		// before events
		if (getMatching().equals(Matching.BAD))
			result += "\r\n if (" + verification + "currentRegExp" + getId() + ".hasEmpty()) {"
					+ "\n    Assertion.alert(\"Violation detected on " + toString() + "\"); }" + "\n" + verification
					+ "triggered" + getId() + " = false; ";

		else
			result += "\r\n if (" + verification + "currentRegExp" + getId() + ".cannotMatch()) {"
					+ "\n    Assertion.alert(\"Violation detected on " + toString() + "\"); }" + "\n" + verification
					+ "triggered" + getId() + " = false; ";

		return result;
	}
}
