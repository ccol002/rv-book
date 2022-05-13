package ltl.structure;

public class Globally extends Unary {

	public Globally(LTL right) {
		super(right);
	}

	public Globally() {
		super();
	}

	@Override
	public String toString() {
		String res = "Globally (";
		res += child.toString();
		res += ")";

		return res;
	}

	public LTL clone() {
		return new Globally(child.clone());
	}

	public LTL derivative(Event event) {
		LTL thisGlobal = this.clone();
		return new And(child.derivative(event), thisGlobal).simplify();
	}

	public String getConstructor() {
		return "new Globally(" + child.getConstructor() + ")";
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
