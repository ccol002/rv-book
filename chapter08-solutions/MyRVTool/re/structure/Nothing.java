package re.structure;

import java.util.HashSet;
import java.util.Set;

public class Nothing extends RegExp {

	public Nothing() {
	}

	public Set<Event> getRelevantEvents() {
		return new HashSet<Event>();
	}

	public String toString() {
		return "0";
	}

	public boolean hasEmpty() {
		return false;
	}

	public RegExp clone() {
		return new Nothing();
	}

	public boolean cannotMatch() {
		return true;
	}

	public String getConstructor() {
		return "new Nothing()";
	}

	public RegExp derivative(Event event) {
		return new Nothing();
	}

	public RegExp simplify() {
		return this;
	}
}