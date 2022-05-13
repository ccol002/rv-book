package ltl.structure;

public class Or extends Binary {

	public Or(LTL left, LTL right) {
		super(left, right);
	}

	public Or() {
		super();
	}

	@Override
	public String toString() {
		String res = "(";
		res += left.toString();
		res += " or ";
		res += right.toString();
		res += ")";

		return res;
	}

	public LTL clone() {
		return new Or(left.clone(), right.clone());
	}

	public String getConstructor() {
		return "new Or(" + left.getConstructor() + "," + right.getConstructor() + ")";
	}

	public boolean cannotMatch() {
		return (left.cannotMatch() && right.cannotMatch());
	}

	public LTL simplify() {

		super.simplify();

		if (left instanceof Bool) {
			if (((Bool) left).getValue())
				return left;
			else
				return right.simplify();
		} else if (right instanceof Bool) {
			if (((Bool) right).getValue())
				return right;
			else
				return left.simplify();

		} else
			return this;
	}

}
