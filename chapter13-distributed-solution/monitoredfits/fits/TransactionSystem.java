package fits;

import fits.Balance;

public class TransactionSystem {

	private Balance balance;
	private FrontEnd frontend;
	private BackEnd backend;

	public TransactionSystem() {
		setup();
	}

	public FrontEnd getFrontEnd() {
		return frontend;
	}

	public BackEnd getBackEnd() {
		return backend;
	}

	public void setup() {
		balance = new Balance();
		backend = new BackEnd(balance);
		frontend = new FrontEnd(backend);
		if (otherTransactionSystem != null) {
			backend.register(otherTransactionSystem);
		}
	}

	// Add knowledge of the other system
	private TransactionSystem otherTransactionSystem = null;

	public TransactionSystem getOtherTransactionSystem() {
		return otherTransactionSystem;
	}

	public void register(TransactionSystem ts) {
		otherTransactionSystem = ts;
		if (backend != null) {
			backend.register(ts);
		}
	}

	private void sendToOtherInstance(String msg) {
		if (otherTransactionSystem != null) {
			otherTransactionSystem.message(msg);
		}
	}

	public void message(String m) {
		// Handle the receiving of messages
		// Blacklisting performed
		if (m.matches("^B\\d+$")) {
			Integer uid = Integer.parseInt(m.substring(1));
			UserInfo u = backend.getUserInfo(uid);
			if (u != null) {
				u.blacklistedOnOtherInstance();
			}
			return;
		}
		// Whitelisting performed
		if (m.matches("^W\\d+$")) {
			Integer uid = Integer.parseInt(m.substring(1));
			UserInfo u = backend.getUserInfo(uid);
			if (u != null) {
				u.whitelistedOnOtherInstance();
			}
			return;
		}
		// Greylisting performed
		if (m.matches("^G\\d+$")) {
			Integer uid = Integer.parseInt(m.substring(1));
			UserInfo u = backend.getUserInfo(uid);
			if (u != null) {
				u.greylistedOnOtherInstance();
			}
			return;
		}
		// Transfer performed
		if (m.matches("^T[\\+\\-](\\d*\\.?\\d+)$")) {
			Double amount = Double.parseDouble(m.substring(1));
			balance.updateBy(amount);
			return;
		}
	}

}
