package egcl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class EGCLScript {

	// Data
	private ArrayList<Rule> rules;
	private String verificationCode;
	private String prelude;

	// Constructors
	public EGCLScript() {
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
		rules = RuleParser.parseRules(rules_txt);
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
		AspectJScript aspectJScript = new AspectJScript();

		aspectJScript.addToFilePrelude(prelude);

		for (Rule rule : rules)
			rule.toAspectJ(aspectJScript);

		return aspectJScript.toString();
	}

}