package ltl;

import java.util.Set;

import ltl.structure.Event;

public class ParameterisedLTLSpec {

	private LTLSpec ltl;

	private String target_type;
	private String target_variable;

	public ParameterisedLTLSpec(LTLSpec ltl, String target_type, String target_variable) {
		this.ltl = ltl;
		this.target_type = target_type;
		this.target_variable = target_variable;
	}

	// getters

	public LTLSpec getLTLSpec() {
		return ltl;
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

		result += ltl.toString();

		return result;

	}

	public String toEGCL() throws Exception {

		String result = "\n\n// the code for " + ltl.getMatching() + ": " + ltl.toString();

		String target = target_type;
		String targetDirective = "target (" + target_type + " " + target_variable + ")";

		result += "\r\nforeach " + targetDirective + "keep (Boolean triggered" + ltl.getId() + " defaultTo false, "
				+ "LTL currentLTL" + ltl.getId() + " defaultTo " + ltl.getLTL().getConstructor() + ") {";

		Set<Event> events = getLTLSpec().getLTL().getRelevantEvents();

		// perform derivative for each event matched
		for (Event e : events) {

			result += "\n\n " + e.getModality() + " " + e.getEvent() + "(..) target (" + target_type + " "
					+ target_variable + ") " + "\n | !triggered" + getLTLSpec().getId() + "\n -> {\r\n currentLTL"
					+ getLTLSpec().getId() + " = currentLTL" + getLTLSpec().getId() + ".derivative("
					+ e.getConstructor() + ");" + "\r\n triggered" + getLTLSpec().getId() + " = true;" + "}";
		}

		// check for "non-matches"
		result += "\n\nbefore " + target + ".*(..) " + targetDirective + " | !triggered" + ltl.getId()
				+ " -> { \n currentLTL" + ltl.getId() + " = currentLTL" + ltl.getId()
				+ ".derivative(new Event(\"before\", \"#\")); " + "\ntriggered" + ltl.getId() + " = true; } ";

		result += "\n\nafter " + target + ".*(..) " + targetDirective + " | !triggered" + ltl.getId()
				+ " -> { \n currentLTL" + ltl.getId() + " = currentLTL" + ltl.getId()
				+ ".derivative(new Event(\"after\", \"#\")); " + "\n triggered" + ltl.getId() + " = true; }";

		// check violation
		// before events

		result += "\n\nbefore " + target + ".*(..) " + targetDirective + " | -> {" + "\nif (currentLTL" + ltl.getId()
				+ ".cannotMatch()) {" + "\n    Assertion.alert(\"Violation detected on " + toString() + "\"); }"
				+ "\ntriggered" + ltl.getId() + " = false; }";

		result += "\n\nafter " + target + ".*(..) " + targetDirective + " | -> {" + "\nif (currentLTL" + ltl.getId()
				+ ".cannotMatch()) {" + "\n    Assertion.alert(\"Violation detected on " + toString() + "\"); }"
				+ "\ntriggered" + ltl.getId() + " = false; }";

		result += "}";

		return result;
	}

}
