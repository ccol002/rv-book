package rv;

import assertion.Assertion;
import fits.BankAccount;
import fits.UserInfo;

public class Verification {

	public static void setupVerification() {
		setupVerificationGlobal1();
		setupVerificationGlobal2();
	}

	// Property 1 verification code
	// Only users based in Argentina can be gold users

	// Since the user's country cannot be set after initialisation, the property may
	// only be violated when a user is upgraded to a gold user.
	public static void fitsMakingGoldUser(UserInfo u) {
		Assertion.check(u.getCountry().equals("Argentina"), "P1 violated");
	}

	// Property 2 verification code
	// The transaction system must be initialised before any user logs in
	private static Boolean fitsHasBeenInitialised = false;

	// Called to start verification
	public static void setupVerificationGlobal1() {
		fitsHasBeenInitialised = false;
	}

	// Called from Backend.initialise
	public static void fitsInitialisation() {
		fitsHasBeenInitialised = true;
	}

	// Called from UserInfo.openSession
	public static void fitsOpenSession() {
		Assertion.check(fitsHasBeenInitialised, "P2 violated");
	}

	// Property 3 verification code
	// No account may end up with a negative balance after being accessed

	public static void fitsAccountJustAccessed(BankAccount a) {
		Assertion.check(a.getBalance() >= 0, "P3 violated");
	}

	// Property 8
	// The administrator must reconcile accounts every 1000 attempted outgoing
	// external money transfers
	// or an aggregate total of one million dollars in attempted outgoing external
	// transfers (attempted
	// transfers include transfers requested which never took place due to lack of
	// funds).

	private static Integer fitsExternalMoneyTransferCount = 0;
	private static Double fitsExternalMoneyTransferAmount = 0.00;

	// Called to start verification
	public static void setupVerificationGlobal2() {
		fitsExternalMoneyTransferCount = 0;
		fitsExternalMoneyTransferAmount = 0.00;
	}

	public static void fitsReconcile() {
		fitsExternalMoneyTransferCount = 0;
		fitsExternalMoneyTransferAmount = 0.00;
	}

	public static void fitsAttemptedExternalMoneyTransfer(Double amount) {
		fitsExternalMoneyTransferCount++;
		fitsExternalMoneyTransferAmount += amount;
		Assertion.check(fitsExternalMoneyTransferCount < 1000 && fitsExternalMoneyTransferAmount < 1000000.00,
				"P8 violated");
	}
}
