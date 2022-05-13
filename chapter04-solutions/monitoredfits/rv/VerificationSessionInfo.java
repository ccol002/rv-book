package rv;

import assertion.Assertion;

public class VerificationSessionInfo {
	// Property 7
	// No user may request more than 10 new accounts in a single session
	private Integer accountRequestCount = 0;

	public void sessionRequestAccount() {
		Assertion.check(accountRequestCount < 10, "P7 violated");

		accountRequestCount++;
	}

	// Property 10
	// Logging can only be made to an active session (i.e. between a login and a
	// logout)

	private Boolean sessionIsOpen = false;

	public void sessionOpen() {
		sessionIsOpen = true;
	}

	public void sessionClose() {
		sessionIsOpen = false;
	}

	public void sessionLogInformation() {
		Assertion.check(sessionIsOpen, "P10 violated");
	}

}
