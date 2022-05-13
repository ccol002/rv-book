package re.structure;

import java.util.HashSet;
import java.util.Set;

public class MatchEvent extends RegExp {

	private Event event;

	public MatchEvent(Event event) {
		this.event = event;
	}

	public String getEvent() {
		return event.toString();
	}

	public RegExp clone() {
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

	public boolean hasEmpty() {
		return false;
	}

	public boolean cannotMatch() {
		return false;
	}

	public String getConstructor() {
		return "new MatchEvent(" + event.getConstructor() + ")";
	}

	public boolean eventMatches(Event event) {
		return this.event.equals(event);
	}

	public RegExp derivative(Event event) {
		if (eventMatches(event))
			return new EmptyTrace();
		else
			return new Nothing();
	}

	public RegExp simplify() {
		return this;
	}

}
