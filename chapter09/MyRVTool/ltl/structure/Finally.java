package ltl.structure;

public class Finally extends Unary {

	public Finally(LTL right) {
		super(right);
	}

	public Finally() {
		super();
	}

	@Override
	public String toString() {
		String res = "finally (";
		res += child.toString();
		res += ")";

		return res;
	}

	public LTL clone() {
		return new Finally(child.clone());
	}

	public LTL derivative(Event event) {
		LTL thisFinally = this.clone();
		// TODO
	}

	public String getConstructor() {
		return "new Finally(" + child.getConstructor() + ")";
	}

	public boolean cannotMatch() {
		return child.cannotMatch();
	}

	public LTL simplify() {
		super.simplify();

		if (child instanceof Bool)
			return child;
		else
			return this;

	}

}
