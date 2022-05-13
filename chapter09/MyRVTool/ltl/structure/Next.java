package ltl.structure;

public class Next extends Unary {

	public Next(LTL right) {
		super(right);
	}

	public Next() {
		super();
	}

	@Override
	public String toString() {
		String res = "next (";
		res += child.toString();
		res += ")";

		return res;
	}

	@Override
	public LTL clone() {
		return new Next(child.clone());
	}

	@Override
	public String getConstructor() {
		return "new Next(" + child.getConstructor() + ")";
	}

	public LTL derivative(Event event) {
		// TODO
	}

	@Override
	public boolean cannotMatch() {
		return child.cannotMatch();
	}

}
