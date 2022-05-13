package re;

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

		String result = "";

		// TODO

		return result;
	}

}
