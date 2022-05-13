package re.structure;

import java.util.Set;

public class Any extends RegExp {

	public Any() {
	}

	public String toString() {
		return "?";
	}

	public RegExp clone() {
		return new Any();
	}

	public Set<Event> getRelevantEvents() {
		// TODO
	}

	public boolean hasEmpty() {
		// TODO
	}

	public boolean cannotMatch() {
		// TODO
	}

	public RegExp derivative(Event event) {
		// TODO
	}

	public String getConstructor() {
		return "new Any()";
	}

	public RegExp simplify() {
		return this;
	}

}