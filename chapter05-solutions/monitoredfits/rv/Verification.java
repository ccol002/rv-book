package rv;

import java.util.HashMap;

import assertion.Assertion;
import fits.BackEnd;
import fits.BankAccount;
import fits.UserInfo;
import fits.UserSession;

public class Verification {
	public static void setupVerification() {
		setupVerificationGlobal1();
		setupVerificationGlobal2();
		setupVerificationPerUser();
		setupVerificationPerSession();
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

	// Property 4 verification
	// A bank account approved by the administrator may not have the same account
	// number as any other bank account already existing in the system

	public static void fitsAdminApprovingAccount(String new_account_number, BackEnd fits) {
		for (UserInfo user : fits.getUsers()) {
			for (BankAccount account : user.getAccounts()) {
				if (account.isOpen()) {
					Assertion.check(!account.getAccountNumber().equals(new_account_number), "P4 violated");
				}
			}
		}
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
		Assertion.check(fitsExternalMoneyTransferCount < 1000 && fitsExternalMoneyTransferAmount + amount < 1000000.00,
				"P8 violated");
	}

	// Property 5 verification
	// Once a user is disabled, he or she may not withdraw from an account until
	// the administrator enables them again

	// Solution trusting information kept in UserInfo object
	// Which doesn't work in this case, since Unfreezing an account, incorrectly
	// enables the user
	public static void userWithdrawal_SOLUTION_1(UserInfo u) {
		Assertion.check(u.isEnabled(), "P5 violated");
	}

	// Alternative solution not trusting information kept in UserInfo object

	public static HashMap<UserInfo, VerificationUserInfo> userVerifier = new HashMap<UserInfo, VerificationUserInfo>();

	public static void setupVerificationPerUser() {
		userVerifier = new HashMap<UserInfo, VerificationUserInfo>();
	}

	public static void userMakeDisabled(UserInfo u) {
		userVerifier.computeIfAbsent(u, k -> new VerificationUserInfo()).userMakeDisabled();
	}

	public static void userMakeEnabled(UserInfo u) {
		userVerifier.computeIfAbsent(u, k -> new VerificationUserInfo()).userMakeEnabled();
	}

	public static void userWithdrawal_SOLUTION_2(UserInfo u) {
		userVerifier.computeIfAbsent(u, k -> new VerificationUserInfo()).userWithdrawal();
	}

	// Property 6 verification
	// Once greylisted, a user must perform at least three incoming transfers before
	// being whitelisted

	public static void userIncomingTransfer(UserInfo u) {
		userVerifier.computeIfAbsent(u, k -> new VerificationUserInfo()).userIncomingTransfer();
	}

	public static void userMakeWhitelisted(UserInfo u) {
		userVerifier.computeIfAbsent(u, k -> new VerificationUserInfo()).userMakeWhitelisted();
	}

	public static void userMakeGreylisted(UserInfo u) {
		userVerifier.computeIfAbsent(u, k -> new VerificationUserInfo()).userMakeGreylisted();
	}

	public static void userMakeBlacklisted(UserInfo u) {
		userVerifier.computeIfAbsent(u, k -> new VerificationUserInfo()).userMakeBlacklisted();
	}

	// Property 9 verification
	// A user may not have more than 3 active sessions at any point in time
	public static void userOpenSession(UserInfo u) {
		userVerifier.computeIfAbsent(u, k -> new VerificationUserInfo()).userOpenSession();
	}

	public static void userCloseSession(UserInfo u) {
		userVerifier.computeIfAbsent(u, k -> new VerificationUserInfo()).userCloseSession();
	}

	// Property 7 verification
	// No user may request more than 10 new accounts in a single session
	public static HashMap<UserSession, VerificationSessionInfo> sessionVerifier = new HashMap<UserSession, VerificationSessionInfo>();

	public static void setupVerificationPerSession() {
		sessionVerifier = new HashMap<UserSession, VerificationSessionInfo>();
	}

	public static void sessionRequestAccount(UserSession s) {
		sessionVerifier.computeIfAbsent(s, k -> new VerificationSessionInfo()).sessionRequestAccount();
	}

	// Property 10
	// Logging can only be made to an active session (i.e. between a login and a
	// logout)

	public static void sessionOpen(UserSession s) {
		sessionVerifier.computeIfAbsent(s, k -> new VerificationSessionInfo()).sessionOpen();
	}

	public static void sessionClose(UserSession s) {
		sessionVerifier.computeIfAbsent(s, k -> new VerificationSessionInfo()).sessionClose();
	}

	public static void sessionLogInformation(UserSession s) {
		sessionVerifier.computeIfAbsent(s, k -> new VerificationSessionInfo()).sessionLogInformation();
	}

}
