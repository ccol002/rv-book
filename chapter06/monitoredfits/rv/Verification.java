
package rv;

public class Verification {

	private static Boolean fitsHasBeenInitialised = false;

// Called to start verification
	public static void setupVerification() {
		fitsHasBeenInitialised = false;
	}

// Called from TransactionSystem.initialise
	public static void fitsInitialisation() {
		fitsHasBeenInitialised = true;
	}

// Check whether initialised
	public static Boolean isInitialised() {
		return fitsHasBeenInitialised;
	}
}
