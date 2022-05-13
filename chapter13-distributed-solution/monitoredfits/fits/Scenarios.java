package fits;

public class Scenarios {

	private static Integer SCENARIO_COUNT = 2;

	static DistTransactionSystem disttransactionsystem = new DistTransactionSystem();

	private static void runScenario(Integer n) {
		FrontEnd frontend1 = disttransactionsystem.getFirstTransactionSystem().getFrontEnd();
		FrontEnd frontend2 = disttransactionsystem.getSecondTransactionSystem().getFrontEnd();

		Integer uid1, uid2;
		Integer sid1, sid2;
		String account_number1, account_number2;

		switch (n) {
		case 1:
			// Violation of DISTRIBUTED PROPERTY 1:
			// The balance of accounts held by the two instances of FiTS together
			// should not exceed $10,000,000.
			frontend1.ADMIN_initialise();
			frontend2.ADMIN_initialise();

			uid1 = frontend1.ADMIN_createUser("Fred", "France");
			frontend1.ADMIN_enableUser(uid1);
			sid1 = frontend1.USER_login(uid1);
			account_number1 = frontend1.USER_requestAccount(uid1, sid1);
			frontend1.ADMIN_approveOpenAccount(uid1, account_number1);

			uid2 = frontend2.ADMIN_createUser("Fred", "France");
			frontend2.ADMIN_enableUser(uid2);
			sid2 = frontend2.USER_login(uid2);
			account_number2 = frontend2.USER_requestAccount(uid2, sid2);
			frontend2.ADMIN_approveOpenAccount(uid2, account_number2);

			frontend1.USER_depositFromExternal(uid1, sid1, account_number1, 700000.00);
			frontend2.USER_depositFromExternal(uid2, sid2, account_number2, 500000.00);
			break;
		case 2:
			// Violation of DISTRIBUTED PROPERTY 2:
			// Once a user is blacklisted on one of the instances of FiTS, they must also
			// be blacklisted on the other instance of FiTS by the end of its next
			// reconciliation.

			frontend1.ADMIN_initialise();
			frontend2.ADMIN_initialise();

			uid1 = frontend1.ADMIN_createUser("Fred", "France");
			frontend1.ADMIN_enableUser(uid1);

			uid2 = frontend2.ADMIN_createUser("Fred", "France");
			frontend2.ADMIN_enableUser(uid2);

			frontend1.ADMIN_blacklistUser(uid1);
			frontend2.ADMIN_reconcile();
			break;

		default:
			System.out.println("Requested scenario " + n.toString() + " which is not defined.");
		}
	}

	private static void resetScenarios() {
		disttransactionsystem.setup();
	}

	public static void runAllScenarios() {
		for (Integer n = 1; n <= SCENARIO_COUNT; n++) {
			resetScenarios();
			;
			runScenario(n);
		}
	}
}
