package re.structure;

import java.util.Set;

public class MatchEventComplement extends RegExp {

	private Event event;

	public MatchEventComplement(Event event) {
		this.event = event;
	}

	public Event get() {
		return event;
	}

	public RegExp clone() {
		return new MatchEventComplement(event);
	}

	public String toString() {
		return "!(" + event + ")";
	}

	public String getConstructor() {
		return "new MatchEventComplement(" + event.getConstructor() + ")";
	}

	public boolean eventMatches(Event event) {
		return !this.event.equals(event);
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
