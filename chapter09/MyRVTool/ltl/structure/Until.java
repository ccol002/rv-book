package ltl.structure;

public class Until extends Binary {

	public Until(LTL left, LTL right) {
		super(left, right);
	}

	public Until() {
		super();
	}

	public LTL clone() {
		return new Until(left.clone(), right.clone());
	}

	public String getConstructor() {
		return "new Until(" + left.getConstructor() + "," + right.getConstructor() + ")";
	}

	public LTL derivative(Event event) {
		LTL thisUntil = this.clone();
		// TODO
	}

	public boolean cannotMatch() {
		return left.cannotMatch() && right.cannotMatch();
	}

	@Override
	public String toString() {
		String res = "(";
		res += left.toString();
		res += " until ";
		res += right.toString();
		res += ")";

		return res;
	}

	public LTL simplify() {

		super.simplify();

		if (right instanceof Bool) // strong interpretation of Until
			return right;
		else
			return this;
	}

}
