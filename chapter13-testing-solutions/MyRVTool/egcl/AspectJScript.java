package egcl;

import java.util.ArrayList;

public class AspectJScript {

	private String filePrelude = "";
	private String aspectPrelude = "";
	private ArrayList<String> beforeAJRules = new ArrayList<String>();
	private ArrayList<String> afterAJRules = new ArrayList<String>();

	public void AspectJ() {
		filePrelude = "";
		aspectPrelude = "";
		beforeAJRules = new ArrayList<String>();
		afterAJRules = new ArrayList<String>();
	}

	public void addBeforeAJRule(String text) {
		beforeAJRules.add(text);
	}

	public void addAfterAJRule(String text) {
		afterAJRules.add(text);
	}

	public void addToFilePrelude(String text) {
		filePrelude += text + "\n";
	}

	public void addToAspectPrelude(String text) {
		aspectPrelude += text + "\n";
	}

	public String toString() {
		String result = filePrelude + "\n\npublic aspect Properties {\n" + aspectPrelude + "\n";
		for (String aj_rule : beforeAJRules) {
			result += aj_rule + "\n\n";
		}
		for (String aj_rule : afterAJRules) {
			result += aj_rule + "\n\n";
		}
		result += "}";
		return result;
	}
}
