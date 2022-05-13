package ltl.structure;

public class And extends Binary {

	public And(LTL left, LTL right) {
		super(left, right);
	}

	public And() {
		super();
	}

	@Override
	public String toString() {
		String res = "(";
		res += left.toString();
		res += " and ";
		res += right.toString();
		res += ")";

		return res;
	}

	public LTL clone() {
		return new And(left.clone(), right.clone());
	}

	public String getConstructor() {
		return "new And(" + left.getConstructor() + "," + right.getConstructor() + ")";
	}

	public boolean cannotMatch() {
		return (left.cannotMatch() || right.cannotMatch());
	}

	public LTL simplify() {

		super.simplify();

		if (left instanceof Bool) {
			if (((Bool) left).getValue())
				return right.simplify();
			else
				return left;
		} else if (right instanceof Bool) {
			if (((Bool) right).getValue())
				return left.simplify();
			else
				return left;

		} else
			return this;
	}
}
