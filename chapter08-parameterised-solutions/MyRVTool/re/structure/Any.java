package re.structure;

import java.util.HashSet;
import java.util.Set;

public class Any extends RegExp {

	public Any() {
	}

	public Set<Event> getRelevantEvents() {
		return new HashSet<Event>();
	}

	public String toString() {
		return "?";
	}

	public RegExp clone() {
		return new Any();
	}

	public boolean hasEmpty() {
		return false;
	}

	public boolean cannotMatch() {
		return false;
	}

	public String getConstructor() {
		return "new Any()";
	}

	public RegExp derivative(Event event) {
		return new EmptyTrace();
	}

	public RegExp simplify() {
		return this;
	}

}