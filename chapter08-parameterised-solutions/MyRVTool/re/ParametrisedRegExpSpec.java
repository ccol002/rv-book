package re;

import java.util.Set;

import re.RegExpSpec.Matching;
import re.structure.Event;

public class ParametrisedRegExpSpec {

	private RegExpSpec re;

	private String target_type;
	private String target_variable;

	public ParametrisedRegExpSpec(RegExpSpec re, String target_type, String target_variable) {
		this.re = re;
		this.target_type = target_type;
		this.target_variable = target_variable;
	}

	// getters

	public RegExpSpec getRegExpSpec() {
		return re;
	}

	public String getTargetType() {
		return target_type;
	}

	public String getTargetVariable() {
		return target_variable;
	}

	// tostring
	public String toString() {
		String result = "foreach target (" + target_type + " " + target_variable + ")\n";

		result += re.toString();

		return result;

	}

	public String toEGCL() throws Exception {

		String result = "\n\n// the code for " + re.getMatching() + ": " + re.toString();

		String target = target_type;
		String targetDirective = "target (" + target_type + " " + target_variable + ")";

		result += "\r\nforeach " + targetDirective + "keep (Boolean triggered" + re.getId() + " defaultTo false, "
				+ "RegExp currentRegExp" + re.getId() + " defaultTo " + re.getRegExp().getConstructor() + ") {";

		Set<Event> events = getRegExpSpec().getRegExp().getRelevantEvents();

		// perform derivative for each event matched
		for (Event e : events) {

			result += "\n\n " + e.getModality() + " " + e.getEvent() + "(..) target (" + target_type + " "
					+ target_variable + ") | -> {\r\n currentRegExp" + getRegExpSpec().getId() + " = currentRegExp"
					+ getRegExpSpec().getId() + ".derivative(" + e.getConstructor() + ");" + "\r\n triggered"
					+ getRegExpSpec().getId() + " = true;" + "}";
		}

		// check for "non-matches"
		result += "\n\nbefore " + target + ".*(..) " + targetDirective + " | !triggered" + re.getId()
				+ " -> { \n currentRegExp" + re.getId() + " = currentRegExp" + re.getId()
				+ ".derivative(new Event(\"before\", \"#\")); " + "\ntriggered" + re.getId() + " = true; } ";

		result += "\n\nafter " + target + ".*(..) " + targetDirective + " | !triggered" + re.getId()
				+ " -> { \n currentRegExp" + re.getId() + " = currentRegExp" + re.getId()
				+ ".derivative(new Event(\"after\", \"#\")); " + "\n triggered" + re.getId() + " = true; }";

		// check violation
		// before events
		if (re.getMatching().equals(Matching.BAD))
			result += "\n\nbefore " + target + ".*(..) " + targetDirective + "| -> {" + "\nif (currentRegExp"
					+ re.getId() + ".hasEmpty()) {" + "\n    Assertion.alert(\"Violation detected on " + toString()
					+ "\"); }" + "\ntriggered" + re.getId() + " = false; }";

		else
			result += "\n\nbefore " + target + ".*(..) " + targetDirective + " | -> {" + "\nif (currentRegExp"
					+ re.getId() + ".cannotMatch()) {" + "\n    Assertion.alert(\"Violation detected on " + toString()
					+ "\"); }" + "\ntriggered" + re.getId() + " = false; }";

		// after events
		if (re.getMatching().equals(Matching.BAD))
			result += "\n\nafter " + target + ".*(..) " + targetDirective + " | -> {" + "\nif (currentRegExp"
					+ re.getId() + ".hasEmpty()) {" + "\n    Assertion.alert(\"Violation detected on " + toString()
					+ "\"); }" + "\ntriggered" + re.getId() + " = false; }";

		else
			result += "\n\nafter " + target + ".*(..) " + targetDirective + " | -> {" + "\nif (currentRegExp"
					+ re.getId() + ".cannotMatch()) {" + "\n    Assertion.alert(\"Violation detected on " + toString()
					+ "\"); }" + "\ntriggered" + re.getId() + " = false; }";

		result += "}";

		return result;
	}

}
