package ltl.structure;

import java.util.Set;

public abstract class Unary extends LTL {

	protected LTL child;

	public Unary() {

	}

	public Unary(LTL right) {
		this.child = right;
	}

	public Set<Event> getRelevantEvents() {
		return child.getRelevantEvents();

	}

	public LTL derivative(Event event) {
		child = child.derivative(event).simplify();
		return this;
	}

	public String toString() {
		return child.toString();
	}

	public LTL simplify() {
		child = child.simplify();
		return this;
	}

}
