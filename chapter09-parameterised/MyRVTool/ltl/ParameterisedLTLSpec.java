package ltl;

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

		// TODO
		return "";
	}

}
