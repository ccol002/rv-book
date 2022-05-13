package automata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class AutomataScript {

	// Data
	private ArrayList<Automaton> automata;
	private String verificationCode;
	private String prelude;

	// Constructors
	public AutomataScript() {
		automata = new ArrayList<Automaton>();
		verificationCode = "";
		prelude = "";
	}

	public AutomataScript(String filename) throws Exception {
		String automata_txt = "";
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
					if (line.equals("AUTOMATA")) {
						readMode = 3;
					} else {
						prelude_txt += line + "\n";
					}
					break;
				case 3:
					if (!line.startsWith("//")) {
						automata_txt += line + " ";
					}
				}
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		if (readMode < 3)
			throw (new Exception("Missing parts of AUTOMATA script"));

		verificationCode = code_txt;
		prelude = prelude_txt;
		automata = AutomatonParser.parseAutomata(automata_txt);
	}

	// Getters
	public ArrayList<Automaton> getAutomata() {
		return automata;
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

	public void addAutomaton(Automaton automaton) {
		automata.add(automaton);
	}

	public void setPrelude(String s) {
		prelude = s;
	}

	// Pretty printing
	public String toString() {
		String result = verificationCode + "\n\nAUTOMATA\n\n";

		Iterator<Automaton> iterator = automata.iterator();
		while (iterator.hasNext()) {
			result += iterator.next().toString() + ";\n";
		}

		return result;
	}

	// toEGCL
	public String toEGCL() throws Exception {

//TODO
		return "";
	}

}