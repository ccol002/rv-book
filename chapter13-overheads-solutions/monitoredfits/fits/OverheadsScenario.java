package fits;

import java.util.ArrayList;

import stopwatch.Stopwatch;

public class OverheadsScenario {

	public static long runScenario() {
		Stopwatch stopwatch = new Stopwatch();
		stopwatch.reset();

		TransactionSystem ts = new TransactionSystem();
		FrontEnd frontend = ts.getFrontEnd();

		frontend.ADMIN_initialise();

		// 10000 users create are registered and they all open one bank account
		ArrayList<Integer> uids = new ArrayList<Integer>();
		ArrayList<String> account_numbers = new ArrayList<String>();

		for (int i = 0; i < 10000; i++) {
			// Create and enable user
			Integer uid = frontend.ADMIN_createUser("Fred" + i, "France");
			uids.add(uid);
			frontend.ADMIN_enableUser(uid);
			// Open a session
			Integer sid = frontend.USER_login(uid);
			// Open bank account and approve it
			String account_number = frontend.USER_requestAccount(uid, sid);
			account_numbers.add(account_number);
			frontend.ADMIN_approveOpenAccount(uid, account_number);
			// Deposit $1000 into that bank account
			frontend.USER_depositFromExternal(uid, sid, account_number, 10000.00);
			frontend.USER_logout(uid, sid);
		}

		stopwatch.start();
		stopwatch.pause();
		// Each user transfers money to another user
		for (int i = 0; i < 10000; i++) {
			Integer sid = frontend.USER_login(uids.get(i));
			stopwatch.resume();

			frontend.USER_transferToOtherAccount(uids.get(i), sid, account_numbers.get(i), uids.get((i + 1) % 10000),
					account_numbers.get((i + 1) % 10000), i * 1.0);
			stopwatch.pause();
			frontend.USER_logout(uids.get(i), sid);
		}

		return stopwatch.getTimeElapsed();
	}
}