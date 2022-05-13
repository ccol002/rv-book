package ltl.structure;

public class Implies extends Binary {

	public Implies(LTL left, LTL right) {
		super(left, right);
	}

	public Implies() {
		super();
	}

	@Override
	public String toString() {
		String res = "(";
		res += left.toString();
		res += " implies ";
		res += right.toString();
		res += ")";

		return res;
	}

	@Override
	public LTL clone() {
		return new Implies(left.clone(), right.clone());
	}

	@Override
	public String getConstructor() {
		return "new Implies(" + left.getConstructor() + "," + right.getConstructor() + ")";

	}

	@Override
	public boolean cannotMatch() {
		return !left.cannotMatch() && right.cannotMatch();
	}

	public LTL simplify() {

		super.simplify();

		if (left instanceof Bool) {
			if (!((Bool) left).getValue())
				return new Bool(true);
			else if (right instanceof Bool)
				return new Bool(((Bool) right).getValue());

		} else if (right instanceof Bool) {
			if (((Bool) right).getValue())
				return new Bool(true);
			else
				return new Bool(!((Bool) left).getValue());

		}
		return this;
	}

}
