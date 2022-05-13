package fits;

import rv.Verification;

public class Scenarios {

	private static Integer SCENARIO_COUNT = 32;

	static TransactionSystem transactionsystem = new TransactionSystem();

	private static void runScenario(Integer n) {
		FrontEnd frontend = transactionsystem.getFrontEnd();

		String account_number, account_number1, account_number2, account_number_receiver;
		Integer uid, uid_receiver;
		Integer sid, sid1, sid2, sid_receiver;

		if (n > 0 && n <= SCENARIO_COUNT) {
			Integer prop = (n + 1) / 2;

			System.out.println("\nRunning scenario " + n + ".");
			System.out
					.println("This scenario should " + ((n % 2 == 1) ? "" : "not ") + "violate property " + prop + ".");
		}
		switch (n) {
		// PROPERTY 1: Only users based in Argentina can be Gold users.
		case 1:
			// violation
			frontend.ADMIN_initialise();
			uid = frontend.ADMIN_createUser("Fred", "France");
			frontend.ADMIN_enableUser(uid);
			frontend.ADMIN_makeGoldUser(uid);
			break;
		case 2:
			// non-violation
			frontend.ADMIN_initialise();
			uid = frontend.ADMIN_createUser("Fred", "France");
			frontend.ADMIN_enableUser(uid);
			frontend.ADMIN_makeSilverUser(uid);
			uid = frontend.ADMIN_createUser("Marge", "Argentina");
			frontend.ADMIN_enableUser(uid);
			frontend.ADMIN_makeGoldUser(uid);
			break;
		// PROPERTY 2: The transaction system must be initialised before any user logs
		// in.
		case 3:
			// P2 violation
			// note modified scenario for exercise 7.1
			frontend.ADMIN_initialise();
			uid = frontend.ADMIN_createUser("Fred", "France");
			frontend.ADMIN_enableUser(uid);
			frontend.ADMIN_shutdown();
			frontend.USER_login(uid);
			break;
		case 4:
			// P2 non-violation
			frontend.ADMIN_initialise();
			uid = frontend.ADMIN_createUser("Fred", "France");
			frontend.ADMIN_enableUser(uid);
			sid = frontend.USER_login(uid);
			frontend.USER_logout(uid, sid);
			break;
		// PROPERTY 3: No account may end up with a negative balance after being
		// accessed.
		case 5:
			// P3 violation
			frontend.ADMIN_initialise();
			uid = frontend.ADMIN_createUser("Fred", "France");
			frontend.ADMIN_enableUser(uid);
			sid = frontend.USER_login(uid);
			account_number = frontend.USER_requestAccount(uid, sid);
			frontend.ADMIN_approveOpenAccount(uid, account_number);
			frontend.USER_depositFromExternal(uid, sid, account_number, 500.00);
			frontend.USER_payToExternal(uid, sid, account_number, 495.00);
			break;
		case 6:
			// P3 non-violation
			frontend.ADMIN_initialise();
			uid = frontend.ADMIN_createUser("Fred", "France");
			frontend.ADMIN_enableUser(uid);
			sid = frontend.USER_login(uid);
			account_number = frontend.USER_requestAccount(uid, sid);
			frontend.ADMIN_approveOpenAccount(uid, account_number);
			frontend.USER_depositFromExternal(uid, sid, account_number, 500.00);
			frontend.USER_payToExternal(uid, sid, account_number, 100.00);
			frontend.USER_payToExternal(uid, sid, account_number, 100.00);
			break;
		// PROPERTY 4: An account approved by the administrator may not have the same
		// account number as any other already existing account in the system.
		case 7:
			// P4 violation
			frontend.ADMIN_initialise();
			for (Integer i = 0; i < 15; i++) {
				uid = frontend.ADMIN_createUser("Fred(" + i + ")", "France");
				frontend.ADMIN_enableUser(uid);
				for (Integer j = 0; j < 11; j++) {
					sid = frontend.USER_login(uid);
					account_number = frontend.USER_requestAccount(uid, sid);
					frontend.ADMIN_approveOpenAccount(uid, account_number);
					frontend.USER_logout(uid, sid);
				}
			}
			// Account 1 of user 11 and account 11 of user 1 will both be named "111"
			break;
		case 8:
			// P4 non-violation
			frontend.ADMIN_initialise();
			for (Integer i = 0; i < 10; i++) {
				uid = frontend.ADMIN_createUser("Fred(" + i + ")", "France");
				frontend.ADMIN_enableUser(uid);
				sid = frontend.USER_login(uid);
				for (Integer j = 0; j < 10; j++) {
					account_number = frontend.USER_requestAccount(uid, sid);
					frontend.ADMIN_approveOpenAccount(uid, account_number);
				}
				frontend.USER_logout(uid, sid);
			}
			break;
		// PROPERTY 5: Once a user is disabled by the administrator, he or she may not
		// withdraw from an account until being enabled again by the administrator.
		case 9:
			// P5 violation
			frontend.ADMIN_initialise();
			uid = frontend.ADMIN_createUser("Fred", "France");
			frontend.ADMIN_enableUser(uid);
			sid = frontend.USER_login(uid);
			account_number = frontend.USER_requestAccount(uid, sid);
			frontend.ADMIN_approveOpenAccount(uid, account_number);
			frontend.USER_depositFromExternal(uid, sid, account_number, 500.00);
			frontend.USER_payToExternal(uid, sid, account_number, 1000.00);
			frontend.ADMIN_disableUser(uid);
			frontend.USER_freezeUser(uid, sid);
			frontend.USER_unfreezeUser(uid, sid);
			frontend.USER_payToExternal(uid, sid, account_number, 200.00);
			break;
		case 10:
			// P5 non-violation
			frontend.ADMIN_initialise();
			uid = frontend.ADMIN_createUser("Fred", "France");
			frontend.ADMIN_enableUser(uid);
			sid = frontend.USER_login(uid);
			account_number = frontend.USER_requestAccount(uid, sid);
			frontend.ADMIN_approveOpenAccount(uid, account_number);
			frontend.USER_depositFromExternal(uid, sid, account_number, 500.00);
			frontend.USER_payToExternal(uid, sid, account_number, 1000.00);
			frontend.ADMIN_disableUser(uid);
			frontend.ADMIN_enableUser(uid);
			frontend.USER_payToExternal(uid, sid, account_number, 200.00);
			break;
		// PROPERTY 6: Once greylisted, a user must perform at least three external
		// transfers before being whitelisted.
		case 11:
			// P6 violation
			frontend.ADMIN_initialise();
			uid_receiver = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid_receiver);
			sid_receiver = frontend.USER_login(uid_receiver);
			account_number_receiver = frontend.USER_requestAccount(uid_receiver, sid_receiver);
			frontend.ADMIN_approveOpenAccount(uid_receiver, account_number_receiver);
			frontend.USER_logout(uid_receiver, sid_receiver);

			uid = frontend.ADMIN_createUser("Sandy", "Senegal");
			frontend.ADMIN_enableUser(uid);
			sid = frontend.USER_login(uid);
			account_number = frontend.USER_requestAccount(uid, sid);
			frontend.ADMIN_approveOpenAccount(uid, account_number);
			frontend.USER_logout(uid, sid);

			frontend.ADMIN_greylistUser(uid);

			for (Integer i = 0; i < 2; i++) {
				sid = frontend.USER_login(uid);
				frontend.USER_depositFromExternal(uid, sid, account_number, 1000.00);
				frontend.USER_logout(uid, sid);
			}

			frontend.ADMIN_whitelistUser(uid);

			break;
		case 12:
			// P6 non-violation
			frontend.ADMIN_initialise();
			uid_receiver = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid_receiver);
			sid_receiver = frontend.USER_login(uid_receiver);
			account_number_receiver = frontend.USER_requestAccount(uid_receiver, sid_receiver);
			frontend.ADMIN_approveOpenAccount(uid_receiver, account_number_receiver);
			frontend.USER_logout(uid_receiver, sid_receiver);

			uid = frontend.ADMIN_createUser("Sandy", "Senegal");
			frontend.ADMIN_enableUser(uid);
			sid = frontend.USER_login(uid);
			account_number = frontend.USER_requestAccount(uid, sid);
			frontend.ADMIN_approveOpenAccount(uid, account_number);
			frontend.USER_logout(uid, sid);

			frontend.ADMIN_greylistUser(uid);
			// more than three external transfers before whitelisting
			for (Integer i = 0; i < 2; i++) {
				sid = frontend.USER_login(uid);
				frontend.USER_depositFromExternal(uid, sid, account_number, 1000.00);
				frontend.USER_depositFromExternal(uid, sid, account_number, 100.00);
				frontend.USER_logout(uid, sid);
			}

			frontend.ADMIN_whitelistUser(uid);
			break;
		// PROPERTY 7: No user may request more than 10 new accounts in a single
		// session.
		case 13:
			// P7 violation
			frontend.ADMIN_initialise();
			uid = frontend.ADMIN_createUser("Fred", "France");
			frontend.ADMIN_enableUser(uid);
			sid = frontend.USER_login(uid);
			for (Integer i = 0; i < 11; i++) {
				account_number = frontend.USER_requestAccount(uid, sid);
				if (i % 2 == 0)
					frontend.ADMIN_approveOpenAccount(uid, account_number);
			}
			frontend.USER_logout(uid, sid);
			break;
		case 14:
			// P7 non-violation
			frontend.ADMIN_initialise();
			uid = frontend.ADMIN_createUser("Fred", "France");
			frontend.ADMIN_enableUser(uid);
			sid = frontend.USER_login(uid);
			for (Integer i = 0; i < 30; i++) {
				account_number = frontend.USER_requestAccount(uid, sid);
				if (i % 2 == 0)
					frontend.ADMIN_approveOpenAccount(uid, account_number);
				if (i % 9 == 5) {// note session being closed and a new one opened
					frontend.USER_logout(uid, sid);
					sid = frontend.USER_login(uid);
				}
			}
			frontend.USER_logout(uid, sid);
			break;
		// PROPERTY 8: The administrator must reconcile accounts every 1000 external
		// money
		// transfers or an aggregate total of one million dollars in external transfers.
		case 15:
			// P8 violation
			frontend.ADMIN_initialise();
			for (Integer i = 0; i < 50; i++) {// note that one million is exceeded
				uid = frontend.ADMIN_createUser("Fred(" + i + ")", "France");
				frontend.ADMIN_enableUser(uid);
				sid = frontend.USER_login(uid);
				account_number = frontend.USER_requestAccount(uid, sid);
				frontend.ADMIN_approveOpenAccount(uid, account_number);
				frontend.USER_depositFromExternal(uid, sid, account_number, 25000.00);
				frontend.USER_payToExternal(uid, sid, account_number, 20000.00);
				frontend.USER_logout(uid, sid);
			}
			break;
		case 16:
			// P8 non-violation
			Integer total = 0;

			frontend.ADMIN_initialise();
			for (Integer i = 0; i < 1000; i++) {
				uid = frontend.ADMIN_createUser("Fred(" + i + ")", "France");
				frontend.ADMIN_enableUser(uid);
				sid = frontend.USER_login(uid);
				account_number = frontend.USER_requestAccount(uid, sid);
				frontend.ADMIN_approveOpenAccount(uid, account_number);
				frontend.USER_depositFromExternal(uid, sid, account_number, 1000.00);
				frontend.USER_payToExternal(uid, sid, account_number, 500.00);
				total += 1525;// this is the total plus charges
				if (total > 500000)
					frontend.ADMIN_reconcile();// reconciliation is actually being done every 500000
				frontend.USER_logout(uid, sid);
			}
			break;
		// PROPERTY 9: A user may not have more than 3 active sessions at once.
		case 17:
			// P9 violation
			frontend.ADMIN_initialise();
			for (Integer i = 0; i < 4; i++) {
				uid = frontend.ADMIN_createUser("Fred(" + i + ")", "France");
				frontend.ADMIN_enableUser(uid);
				for (Integer j = 0; j <= i; j++) {
					sid = frontend.USER_login(uid);
				}
			}
			break;
		case 18:
			// P9 non-violation
			frontend.ADMIN_initialise();
			for (Integer i = 0; i < 4; i++) {
				uid = frontend.ADMIN_createUser("Fred(" + i + ")", "France");
				frontend.ADMIN_enableUser(uid);
				for (Integer j = 0; j < 5; j++) {
					sid = frontend.USER_login(uid);
					frontend.USER_logout(uid, sid);
				}
				for (Integer j = 0; j < 2; j++) {// note that at most, only two sessions are open at the same time
					sid = frontend.USER_login(uid);
				}
			}
			break;
		// PROPERTY 10: Transfers may only be made during an active session (i.e.
		// between a
		// login and a logout)
		case 19:
			// violation
			frontend.ADMIN_initialise();
			uid_receiver = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid_receiver);
			sid_receiver = frontend.USER_login(uid_receiver);
			account_number_receiver = frontend.USER_requestAccount(uid_receiver, sid_receiver);
			frontend.ADMIN_approveOpenAccount(uid_receiver, account_number_receiver);
			frontend.USER_logout(uid_receiver, sid_receiver);

			for (Integer i = 0; i < 5; i++) {
				uid = frontend.ADMIN_createUser("Sandy(" + i + ")", "Senegal");
				frontend.ADMIN_enableUser(uid);
				sid = frontend.USER_login(uid);
				account_number = frontend.USER_requestAccount(uid, sid);
				frontend.ADMIN_approveOpenAccount(uid, account_number);
				frontend.USER_depositFromExternal(uid, sid, account_number, 1000.00);
				frontend.USER_logout(uid, sid);
				// note activity after logout
				if (i == 3)
					frontend.USER_payToExternal(uid, sid, account_number, 100.00);
			}
			break;
		case 20:
			// non-violation
			frontend.ADMIN_initialise();
			uid_receiver = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid_receiver);
			sid_receiver = frontend.USER_login(uid_receiver);
			account_number_receiver = frontend.USER_requestAccount(uid_receiver, sid_receiver);
			frontend.ADMIN_approveOpenAccount(uid_receiver, account_number_receiver);
			frontend.USER_logout(uid_receiver, sid_receiver);

			for (Integer i = 0; i < 5; i++) {
				uid = frontend.ADMIN_createUser("Sandy(" + i + ")", "Senegal");
				frontend.ADMIN_enableUser(uid);
				sid = frontend.USER_login(uid);
				account_number = frontend.USER_requestAccount(uid, sid);
				frontend.ADMIN_approveOpenAccount(uid, account_number);
				frontend.USER_depositFromExternal(uid, sid, account_number, 1000.00);
				frontend.USER_payToExternal(uid, sid, account_number, 100.00);
				frontend.USER_transferToOtherAccount(uid, sid, account_number, uid_receiver, account_number_receiver,
						100.00);
				frontend.USER_logout(uid, sid);
			}
			break;
		// PROPERTY 11: A session should not be opened in the first ten seconds
		// immediately
		// after system initialisation
		case 21:
			// P11 violation
			frontend.ADMIN_initialise();
			uid = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid);
			System.out.println("Time is fast-forwarded by 00h00m03s");
			frontend.USER_login(uid);
			break;
		case 22:
			// P11 non-violation
			frontend.ADMIN_initialise();
			uid = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid);
			System.out.println("Time is fast-forwarded by 00h00m21s");
			frontend.USER_login(uid);
			break;
		// PROPERTY 12: Once a blacklisted user is whitelisted, they may not perform any
		// single
		// external transfer worth more than $100 for 12 hours
		case 23:
			// P12 violation
			frontend.ADMIN_initialise();
			// - create the user
			uid = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid);
			// - user logs in
			sid = frontend.USER_login(uid);
			// - account requested
			account_number = frontend.USER_requestAccount(uid, sid);
			// - account approved
			frontend.ADMIN_approveOpenAccount(uid, account_number);
			// - money loaded in account
			frontend.USER_depositFromExternal(uid, sid, account_number, 1000.00);
			// - blacklist user
			frontend.ADMIN_blacklistUser(uid);
			// - whitelist user
			System.out.println("Time is fast-forwarded by 00h00m03s");
			frontend.ADMIN_whitelistUser(uid);
			// - pay external party more than $100
			System.out.println("Time is fast-forwarded by 10h30m00s");
			frontend.USER_payToExternal(uid, sid, account_number, 120.00);
			break;
		case 24:
			// P12 non-violation
			frontend.ADMIN_initialise();
			// - create the user
			uid = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid);
			// - user logs in
			sid = frontend.USER_login(uid);
			// - accounts requested
			account_number1 = frontend.USER_requestAccount(uid, sid);
			account_number2 = frontend.USER_requestAccount(uid, sid);
			// - accounts approved
			frontend.ADMIN_approveOpenAccount(uid, account_number1);
			frontend.ADMIN_approveOpenAccount(uid, account_number2);
			// - money loaded in account
			frontend.USER_depositFromExternal(uid, sid, account_number1, 1000.00);
			// - blacklist user
			frontend.ADMIN_blacklistUser(uid);
			// - whitelist user
			System.out.println("Time is fast-forwarded by 00h00m03s");
			frontend.ADMIN_whitelistUser(uid);
			// - pay external party less than $100 per transaction
			System.out.println("Time is fast-forwarded by 10h30m00s");
			frontend.USER_payToExternal(uid, sid, account_number1, 80.00);
			frontend.USER_payToExternal(uid, sid, account_number1, 70.00);
			// - internal transfer of more than $100
			frontend.USER_transferOwnAccounts(uid, sid, account_number1, account_number2, 120.00);
			// - pay external party more than $100
			System.out.println("Time is fast-forwarded by 05h20m00s");
			frontend.USER_payToExternal(uid, sid, account_number1, 140.00);
			break;

		// PROPERTY 13: A user may not request the creation of more than three accounts
		// within
		// any 24 hour period.
		case 25:
			// P13 violation
			frontend.ADMIN_initialise();
			// - create the user
			uid = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid);
			// - user logs in
			sid = frontend.USER_login(uid);
			// - account requested
			System.out.println("Time is fast-forwarded by 23h00m00s");
			account_number = frontend.USER_requestAccount(uid, sid);
			// - account approved
			frontend.ADMIN_approveOpenAccount(uid, account_number);
			// - accounts requested
			System.out.println("Time is fast-forwarded by 03h00m00s");
			account_number1 = frontend.USER_requestAccount(uid, sid);
			System.out.println("Time is fast-forwarded by 12h10m00s");
			account_number2 = frontend.USER_requestAccount(uid, sid);
			break;
		case 26:
			// P13 non-violation
			frontend.ADMIN_initialise();
			// - create the user
			uid = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid);
			// - user logs in
			sid = frontend.USER_login(uid);
			// - account requested
			System.out.println("Time is fast-forwarded by 23h00m00s");
			account_number = frontend.USER_requestAccount(uid, sid);
			// - accounts requested
			System.out.println("Time is fast-forwarded by 03h00m00s");
			account_number1 = frontend.USER_requestAccount(uid, sid);
			System.out.println("Time is fast-forwarded by 21h10m00s");
			account_number2 = frontend.USER_requestAccount(uid, sid);
			break;

		// PROPERTY 14: An administrator must reconcile accounts within 5 minutes
		// of initialisation.
		case 27:
			// P14 violation
			frontend.ADMIN_initialise();
			System.out.println("Time is fast-forwarded by 00h07m00s");
			break;
		case 28:
			// P14 non-violation
			frontend.ADMIN_initialise();
			System.out.println("Time is fast-forwarded by 00h03m00s");
			frontend.ADMIN_reconcile();
			break;

		// PROPERTY 15: A new account must be approved or rejected by an
		// administrator within 24 hours of its creation.
		case 29:
			// P15 violation
			frontend.ADMIN_initialise();
			// - create the user
			uid = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid);
			// - user logs in
			sid = frontend.USER_login(uid);
			// - account requested
			System.out.println("Time is fast-forwarded by 00h03m00s");
			account_number = frontend.USER_requestAccount(uid, sid);
			// - user logs out
			System.out.println("Time is fast-forwarded by 00h01m00s");
			frontend.USER_logout(uid, sid);
			// - do nothing
			System.out.println("Time is fast-forwarded by 99h00m00s");
			break;
		case 30:
			// P15 non-violation
			frontend.ADMIN_initialise();
			// - create the user
			uid = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid);
			// - user logs in
			sid = frontend.USER_login(uid);
			// - accounts requested
			System.out.println("Time is fast-forwarded by 00h03m00s");
			account_number1 = frontend.USER_requestAccount(uid, sid);
			System.out.println("Time is fast-forwarded by 00h03m00s");
			account_number2 = frontend.USER_requestAccount(uid, sid);
			// - first account approved
			System.out.println("Time is fast-forwarded by 00h02m00s");
			frontend.ADMIN_approveOpenAccount(uid, account_number1);
			// - user logs out
			System.out.println("Time is fast-forwarded by 00h04m00s");
			frontend.USER_logout(uid, sid);
			// - account rejected
			System.out.println("Time is fast-forwarded by 22h04m00s");
			frontend.ADMIN_rejectOpenAccount(uid, account_number2);
			break;

		// PROPERTY 16: A session is always closed within 15 minutes of user inactivity.
		case 31:
			// P16 violation
			frontend.ADMIN_initialise();
			// - create the user
			uid = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid);
			// - user logs in
			sid = frontend.USER_login(uid);
			// - account requested
			System.out.println("Time is fast-forwarded by 00h03m00s");
			account_number = frontend.USER_requestAccount(uid, sid);
			// - account approved
			System.out.println("Time is fast-forwarded by 00h10m00s");
			frontend.ADMIN_approveOpenAccount(uid, account_number);
			// - no more activity
			System.out.println("Time is fast-forwarded by 00h08m00s");
			break;
		case 32:
			// P16 non-violation
			frontend.ADMIN_initialise();
			// - create the user
			uid = frontend.ADMIN_createUser("Roger", "Romania");
			frontend.ADMIN_enableUser(uid);
			// - user logs in twice
			sid1 = frontend.USER_login(uid);
			System.out.println("Time is fast-forwarded by 00h3m00s");
			sid2 = frontend.USER_login(uid);
			// - account requested from session 1
			System.out.println("Time is fast-forwarded by 00h03m00s");
			account_number = frontend.USER_requestAccount(uid, sid1);
			// - account approved
			System.out.println("Time is fast-forwarded by 00h10m00s");
			frontend.ADMIN_approveOpenAccount(uid, account_number);
			// - second session is closed
			System.out.println("Time is fast-forwarded by 00h01m00s");
			frontend.USER_logout(uid, sid2);
			// - first session is closed
			System.out.println("Time is fast-forwarded by 00h02m00s");
			frontend.USER_logout(uid, sid1);
			break;

		default:
			System.out.println("Requested scenario " + n.toString() + " which is not defined.");
		}
	}

	public static void runViolatingScenarioForProperty(Integer n) {
		runScenario(2 * n - 1);
	}

	public static void runNonViolatingScenarioForProperty(Integer n) {
		runScenario(2 * n);
	}

	public static void runNonScenariosForProperty(Integer n) {
		runViolatingScenarioForProperty(n);
		runNonViolatingScenarioForProperty(n);
	}

	private static void resetScenarios() {
		Verification.setupVerification();
		transactionsystem.setup();
	}

	public static void runAllScenarios() {
		for (Integer n = 1; n <= SCENARIO_COUNT; n++) {
			resetScenarios();
			runScenario(n);
		}
	}
}
