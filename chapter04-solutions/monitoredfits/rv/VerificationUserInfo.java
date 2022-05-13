package rv;

import assertion.Assertion;

public class VerificationUserInfo {
	// Property 5
	// Once a user is disabled, he or she may not withdraw from an account until
	// the administrator enables them again

	private Boolean isEnabled = false;

	public void userMakeDisabled() {
		isEnabled = false;
	}

	public void userMakeEnabled() {
		isEnabled = true;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void userWithdrawal() {
		Assertion.check(isEnabled, "P5 violated");
	}

	// Property 6
	// Once greylisted, a user must perform at least three incoming transfers before
	// being whitelisted

	private Integer incomingTransfersSinceGreylisted = 0;
	private Boolean isGreylisted = false;

	public void userIncomingTransfer() {
		if (isGreylisted) {
			incomingTransfersSinceGreylisted++;
		}
	}

	public void userMakeWhitelisted() {
		if (isGreylisted) {
			Assertion.check(incomingTransfersSinceGreylisted >= 3, "P6 violated");
		}
	}

	public void userMakeGreylisted() {
		if (!isGreylisted) {
			incomingTransfersSinceGreylisted = 0;
			isGreylisted = true;
		}
	}

	public void userMakeBlacklisted() {
		incomingTransfersSinceGreylisted = 0;
		isGreylisted = false;
	}

	// Property 9
	// A user may not have more than 3 active sessions at any point in time

	private Integer numberOfOpenSessions = 0;

	public void userOpenSession() {
		Assertion.check(numberOfOpenSessions < 3, "P9 violated");

		numberOfOpenSessions++;
	}

	public void userCloseSession() {
		numberOfOpenSessions--;
	}

}
