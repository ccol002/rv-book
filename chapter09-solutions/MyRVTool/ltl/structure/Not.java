package ltl.structure;

public class Not extends Unary {

	public Not(LTL right) {
		super(right);
	}

	public Not() {
		super();
	}

	@Override
	public String toString() {
		String res = "not ";
		res += child.toString();

		return res;
	}

	public LTL derivative(Event event) {
		child = child.derivative(event).simplify();
		return this;
	}

	public LTL clone() {
		return new Not(child.clone());
	}

	public String getConstructor() {
		return "new Not(" + child.getConstructor() + ")";
	}

	public boolean cannotMatch() {
		return !child.cannotMatch();
	}

	public LTL simplify() {

		super.simplify();

		if (child instanceof Bool)
			return new Bool(!((Bool) child).getValue());
		else
			return this;
	}

}
