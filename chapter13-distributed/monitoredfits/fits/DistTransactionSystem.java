package fits;

import fits.TransactionSystem;

public class DistTransactionSystem {

	private TransactionSystem ts1, ts2;

	public DistTransactionSystem() {
		ts1 = new TransactionSystem();
		ts2 = new TransactionSystem();

		ts1.register(ts2);
		ts2.register(ts1);
	}

	public void setup() {
		ts1.setup();
		ts2.setup();
	}

	public TransactionSystem getFirstTransactionSystem() {
		return ts1;
	}

	public TransactionSystem getSecondTransactionSystem() {
		return ts2;
	}
}
