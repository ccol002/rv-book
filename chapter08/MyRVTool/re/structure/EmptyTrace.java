package re.structure;

import java.util.Set;

public class EmptyTrace extends RegExp {

	public EmptyTrace() {
	}

	public String toString() {
		return "1";
	}

	public RegExp clone() {
		return new EmptyTrace();
	}

	public String getConstructor() {
		return "new EmptyTrace()";
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