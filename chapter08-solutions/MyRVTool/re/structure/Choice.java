package re.structure;

import java.util.Set;

public class Choice extends RegExp {

	private RegExp op1, op2;

	public Choice(RegExp op1, RegExp op2) {
		this.op1 = op1;
		this.op2 = op2;
	}

	public RegExp getLeft() {
		return op1;
	}

	public RegExp getRight() {
		return op2;
	}

	public Set<Event> getRelevantEvents() {
		Set<Event> events = op1.getRelevantEvents();
		events.addAll(op2.getRelevantEvents());

		return events;
	}

	public RegExp clone() {
		return new Choice(op1.clone(), op2.clone());
	}

	public String toString() {
		return "(" + op1 + " + " + op2 + ")";
	}

	public boolean hasEmpty() {
		return op1.hasEmpty() || op2.hasEmpty();
	}

	public boolean cannotMatch() {
		return op1.cannotMatch() && op2.cannotMatch();
	}

	public String getConstructor() {
		return "new Choice(" + op1.getConstructor() + "," + op2.getConstructor() + ")";
	}

	public RegExp derivative(Event event) {
		return new Choice(op1.derivative(event), op2.derivative(event)).simplify();
	}

	public RegExp simplify() {
		op1 = op1.simplify();
		op2 = op2.simplify();

		if (op1 instanceof Nothing)
			return op2;
		else if (op2 instanceof Nothing)
			return op1;

		return this;
	}

}
