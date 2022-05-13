package ltl.structure;

import java.util.HashSet;
import java.util.Set;

public class Bool extends LTL {

	private boolean value;

	public Bool(String value) {
		if (value.equals("true")) {
			this.value = true;
		} else if (value.equals("false")) {
			this.value = false;
		}
	}

	public Bool(boolean value) {
		this.value = value;
	}

	public Boolean getValue() {
		return value;
	}

	public String toString() {
		return value + "";
	}

	public Set<Event> getRelevantEvents() {
		return new HashSet<Event>();
	}

	public LTL clone() {
		return new Bool(this.value);
	}

	public LTL derivative(Event event) {
		return this;
	}

	public String getConstructor() {
		return "new Bool(" + this.value + ")";
	}

	public boolean cannotMatch() {
		return !value;
	}

	public LTL simplify() {

		return this;
	}
}
