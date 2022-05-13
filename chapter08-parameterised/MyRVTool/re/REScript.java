package re;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class REScript {

	// Data
	private ArrayList<RegExpSpec> regExps;
	private ArrayList<ParametrisedRegExpSpec> parametrisedRegExps;

	private String verificationCode;
	private String prelude;

	// Constructors
	public REScript() {
		regExps = new ArrayList<RegExpSpec>();
		verificationCode = "";
		prelude = "";
	}

	public REScript(String filename) throws Exception {
		String re_txt = "";
		String code_txt = "";
		String prelude_txt = "";
		Integer readMode = 0;
		// 0=before VERIFICATIONCODE, 1=in VERIFICATIONCODE, 2=in PRELUDE, 3=in RULES

		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			String line = br.readLine();

			while (line != null) {
				line = line.trim();

				switch (readMode) {
				case 0:
					if (line.equals("VERIFICATIONCODE")) {
						readMode = 1;
					} else if (!(line.equals("") || line.startsWith("//"))) {
						throw (new Exception("Non-comment line before VERIFICATIONMODULE"));
					}
					break;
				case 1:
					if (line.equals("PRELUDE")) {
						readMode = 2;
					} else {
						code_txt += line + "\n";
					}
					break;
				case 2:
					if (line.equals("REGULAREXPRESSIONS")) {
						readMode = 3;
					} else {
						prelude_txt += line + "\n";
					}
					break;
				case 3:
					if (!line.startsWith("//")) {
						re_txt += line + " ";
					}
				}
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		if (readMode < 3)
			throw (new Exception("Missing parts of REGULAREXPRESSIONS script"));

		verificationCode = code_txt;
		prelude = prelude_txt;
		REParser.parseRegExps(this, re_txt);
	}

	// Getters
	public ArrayList<RegExpSpec> getRegExpSpecs() {
		return regExps;
	}

	public ArrayList<ParametrisedRegExpSpec> getParametrisedRegExpSpecs() {
		return parametrisedRegExps;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public String getPrelude() {
		return prelude;
	}

	// Setters
	public void setRegExpSpecs(ArrayList<RegExpSpec> regExpSpecs) {
		this.regExps = regExpSpecs;
	}

	public void setParametrisedRegExpSpecs(ArrayList<ParametrisedRegExpSpec> parametrisedRegExpSpecs) {
		this.parametrisedRegExps = parametrisedRegExpSpecs;
	}

	public void setVerificationCode(String s) {
		verificationCode = s;
	}

	public void addRegExp(RegExpSpec regExp) {
		regExps.add(regExp);
	}

	public void setPrelude(String s) {
		prelude = s;
	}

	// Pretty printing
	public String toString() {
		String result = verificationCode + "\n\nREGULAREXPRESSIONS\n\n";

		for (RegExpSpec re : regExps)
			result += re.toString() + ";\n";

		for (ParametrisedRegExpSpec re : parametrisedRegExps)
			result += re.toString() + ";\n";

		return result;
	}

	public String toEGCL() throws Exception {

		String result = "\n\nVERIFICATIONCODE" + "\n\npackage rv;";
		result += "\n\nimport re.structure.*;";
		result += "\n\npublic class Verification {" + "\n\n public static Boolean initialised = false;";

		for (RegExpSpec a : regExps)
			result += a.toEGCLSetupVerificationCodeDecl() + "\n\n";

		result += "static public void setupVerification() {\n";

		for (RegExpSpec a : regExps)
			result += a.toEGCLVerificationCodeDecl() + "\n\n";

		result += "\n\nProperties.setupVerification();" + "\n initialised = true;";

		result += "} }" + "\n\n" + "\n\nPRELUDE" + prelude + "\n\nRULES\n\n ";

		result += "\n\nafter *.*(..) | Verification.initialised -> {";
		for (RegExpSpec a : regExps)
			result += a.getWildcardChecks("after");

		result += "}";

		for (RegExpSpec a : getRegExpSpecs())
			// generate code for RegExp
			result += a.toEGCL() + "\n\n";

		result += "\n\nbefore *.*(..) | Verification.initialised -> {";
		for (RegExpSpec a : regExps) {
			result += a.getWildcardChecks("before");
		}
		result += "}";

		// parametrised
		for (ParametrisedRegExpSpec re : parametrisedRegExps)
			result += re.toEGCL() + "\n\n";

		return result;

	}

}