package rv;

import assertion.Assertion;

public class Verification {

	private Verification() {
		setupVerification();
	}

	private static Boolean fitsHasBeenInitialised = false;

	// Called to start verification
	public static void setupVerification() {
		fitsHasBeenInitialised = false;
	}

	// Called from TransactionSystem.initialise
	public static void fitsInitialisation() {
		fitsHasBeenInitialised = true;
	}

	// Called from UserInfo.openSession
	public static void fitsOpenSession() {
		Assertion.check(fitsHasBeenInitialised, "Property 2 violated");
	}

}
