package re.structure;

import java.util.HashSet;
import java.util.Set;

public class EmptyTrace extends RegExp {

	public EmptyTrace() {
	}

	public String toString() {
		return "1";
	}

	public Set<Event> getRelevantEvents() {
		return new HashSet<Event>();
	}

	public RegExp clone() {
		return new EmptyTrace();
	}

	public boolean hasEmpty() {
		return true;
	}

	public boolean cannotMatch() {
		return false;
	}

	public String getConstructor() {
		return "new EmptyTrace()";
	}

	public RegExp derivative(Event event) {
		return new Nothing();
	}

	public RegExp simplify() {
		return this;
	}

}