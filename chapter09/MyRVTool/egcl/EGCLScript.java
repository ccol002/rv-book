package egcl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class EGCLScript {

	// Data
	private ArrayList<Foreach> foreaches;
	private ArrayList<Rule> rules;
	private String verificationCode;
	private String prelude;

	// Constructors
	public EGCLScript() {
		foreaches = new ArrayList<Foreach>();
		rules = new ArrayList<Rule>();
		verificationCode = "";
		prelude = "";
	}

	public EGCLScript(String filename) throws Exception {
		String rules_txt = "";
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
					if (line.equals("RULES")) {
						readMode = 3;
					} else {
						prelude_txt += line + "\n";
					}
					break;
				case 3:
					if (!line.startsWith("//")) {
						rules_txt += line + " ";
					}
				}
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		if (readMode < 3)
			throw (new Exception("Missing parts of EGCL script"));

		verificationCode = code_txt;
		prelude = prelude_txt;
		RuleParser.parseForeaches(rules_txt);
		rules = RuleParser.rules;
		foreaches = RuleParser.foreaches;
	}

	// Getters
	public ArrayList<Rule> getRules() {
		return rules;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public String getPrelude() {
		return prelude;
	}

	public ArrayList<Foreach> getForeaches() {
		return foreaches;
	}

	// Setters
	public void setVerificationCode(String s) {
		verificationCode = s;
	}

	public void addRule(Rule rule) {
		rules.add(rule);
	}

	public void setPrelude(String s) {
		prelude = s;
	}

	// Pretty printing
	public String toString() {
		String result = verificationCode + "\n\nRULES\n\n";

		Iterator<Rule> iterator = rules.iterator();
		while (iterator.hasNext()) {
			result += iterator.next().toString() + ";\n";
		}

		return result;
	}

	public String toAspectJ() {
		AspectJScript aspectJrules = new AspectJScript();

		// Start with the prelude
		aspectJrules.addToFilePrelude(prelude);
		// Add foreach specific code
		aspectJrules.addToAspectPrelude("static void setupVerification() {");
		for (Foreach f : foreaches)
			aspectJrules.addToAspectPrelude(f.getInit());
		aspectJrules.addToAspectPrelude("}");

		// Add the rules from the foreach structures
		for (Foreach f : foreaches)
			f.toAspectJ(aspectJrules);

		// Add the remaining rules
		for (Rule r : rules)
			r.toAspectJ(aspectJrules);

		return aspectJrules.toString();
	}

}