package re.structure;

import java.util.Set;

public class Repetition extends RegExp {

	private RegExp op;

	public Repetition(RegExp op) {
		this.op = op;
	}

	public RegExp getChild() {
		return op;
	}

	public Set<Event> getRelevantEvents() {
		return op.getRelevantEvents();
	}

	public RegExp clone() {
		return new Repetition(op.clone());
	}

	public String toString() {
		return "(" + op + ")*";
	}

	public boolean hasEmpty() {
		return true;
	}

	public boolean cannotMatch() {
		return op.cannotMatch();
	}

	public String getConstructor() {
		return "new Repetition(" + op.getConstructor() + ")";
	}

	public RegExp derivative(Event event) {
		return new SequentialComposition(op.derivative(event), this.clone()).simplify();
	}

	public RegExp simplify() {
		op = op.simplify();
		return this;
	}

}
