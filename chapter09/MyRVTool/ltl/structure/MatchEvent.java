package ltl.structure;

import java.util.HashSet;
import java.util.Set;

public class MatchEvent extends LTL {

	private Event event;

	public MatchEvent(Event event) {
		this.event = event;
	}

	public String getEvent() {
		return event.toString();
	}

	public LTL clone() {
		return new MatchEvent(event);
	}

	public Set<Event> getRelevantEvents() {
		Set<Event> events = new HashSet<Event>();
		events.add(this.event);

		return events;
	}

	public String toString() {
		return event.toString();// "'" + var + "'";
	}

	public boolean cannotMatch() {
		return false;
	}

	public String getConstructor() {
		return "new MatchEvent(" + event.getConstructor() + ")";
	}

	public boolean eventMatches(Event event) {
		// TODO
	}

	public LTL derivative(Event event) {

		return new Bool(eventMatches(event));
	}

	public LTL simplify() {

		return this;
	}

}
