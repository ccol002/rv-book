package ltl.structure;

import java.util.Set;

public abstract class Binary extends LTL {

	protected LTL left;
	protected LTL right;

	public Binary() {
		super();
	}

	public Binary(LTL left, LTL right) {
		this.left = left;
		this.right = right;
	}

	public LTL getLeft() {
		return left;
	}

	public LTL getRight() {
		return right;
	}

	public Set<Event> getRelevantEvents() {
		Set<Event> events = left.getRelevantEvents();
		events.addAll(right.getRelevantEvents());
		return events;
	}

	public LTL derivative(Event event) {
		left = left.derivative(event).simplify();
		right = right.derivative(event).simplify();
		return this;
	}

	public LTL simplify() {
		left = left.simplify();
		right = right.simplify();
		return this;
	}
}
