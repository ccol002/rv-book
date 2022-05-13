package re.structure;

import java.util.Set;

public class Nothing extends RegExp {

	public Nothing() {
	}

	public String toString() {
		return "0";
	}

	public RegExp clone() {
		return new Nothing();
	}

	public String getConstructor() {
		return "new Nothing()";
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

	public RegExp simplify() {
		return this;
	}
}