package fits;

import assertion.Assertion;

public class Balance {
	protected Double balance = 0.00;

	public void updateBy(Double amount) {
		balance += amount;
		Assertion.check(balance <= 1000000, "DP1 violated");
	}

	public Double getBalance() {
		return balance;
	}
}