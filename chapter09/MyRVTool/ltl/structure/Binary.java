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
		// TODO
	}

	public LTL derivative(Event event) {
		// TODO
	}

	public LTL simplify() {
		left = left.simplify();
		right = right.simplify();
		return this;
	}
}
