package ltl.structure;

import java.util.Set;

public abstract class LTL {

	public int index = -1;
	public int lowlink = -1;

	public abstract String toString();

	public abstract Set<Event> getRelevantEvents();

	public abstract LTL clone();

	public abstract LTL derivative(Event event);

	public abstract LTL simplify();

	public abstract String getConstructor();

	public abstract boolean cannotMatch();

}
