package fits;

import rv.Verification;

// The methods called by the user interface for (i) the ADMINistrator; and (ii) normal USERs
public class FrontEnd {
	protected BackEnd backend;

	// Constructor of the interface
	public FrontEnd(BackEnd be) {
		backend = be;
	}

	public BackEnd getBackEnd() {
		return backend;
	}

	// ADMINistrator methods
	// * Initialise the transaction system
	public void ADMIN_initialise() {
		backend.initialise();
	}

	// * Reconcile the transaction system
	public void ADMIN_reconcile() {
		Verification.fitsReconcile();
		// Here the developers would have added code to reconcile the user accounts
	}

	// * Create a new user
	public Integer ADMIN_createUser(String name, String country) {
		Integer uid = backend.addUser(name, country);
		backend.getUserInfo(uid).makeDisabled();
		return uid;
	}

	// * Enable a user
	public void ADMIN_enableUser(Integer uid) {

		Verification.userMakeEnabled(backend.getUserInfo(uid));

		backend.getUserInfo(uid).makeEnabled();
	}

	// * Disable a user (initially disabled)
	public void ADMIN_disableUser(Integer uid) {

		Verification.userMakeDisabled(backend.getUserInfo(uid));

		backend.getUserInfo(uid).makeDisabled();
	}

	// * Black/grey or whitelist a user
	public void ADMIN_blacklistUser(Integer uid) {
		backend.getUserInfo(uid).makeBlacklisted();
	}

	public void ADMIN_greylistUser(Integer uid) {
		backend.getUserInfo(uid).makeGreylisted();
	}

	public void ADMIN_whitelistUser(Integer uid) {
		backend.getUserInfo(uid).makeWhitelisted();
	}

	// * Change user type (Gold, Silver or Normal User)
	public void ADMIN_makeGoldUser(Integer uid) {
		backend.getUserInfo(uid).makeGoldUser();
	}

	public void ADMIN_makeSilverUser(Integer uid) {
		backend.getUserInfo(uid).makeSilverUser();
	}

	public void ADMIN_makeNormalUser(Integer uid) {
		backend.getUserInfo(uid).makeNormalUser();
	}

	// * Approve the opening of an account
	public void ADMIN_approveOpenAccount(Integer uid, String accnum) {
		Verification.fitsAdminApprovingAccount(accnum, backend);

		backend.getUserInfo(uid).getAccount(accnum).enableAccount();
	}

	// * Reject the opening of an account
	public void ADMIN_rejectOpenAccount(Integer uid, String accnum) {
		// nothing to do here
	}

	// USER methods
	// * Login into the system (allows only ENABLED users to login)
	public Integer USER_login(Integer uid) {
		UserInfo u = backend.getUserInfo(uid);

		if (u.isEnabled()) {
			return (u.openSession());
		} else {
			return -1;
		}
	}

	// * Logout of the chosen session
	public void USER_logout(Integer uid, Integer sid) {
		backend.getUserInfo(uid).closeSession(sid);
	}

	// * Freeze his/her own user account
	public Boolean USER_freezeUser(Integer uid, Integer sid) {
		UserInfo u = backend.getUserInfo(uid);
		u.getSession(sid).log("Freeze account");
		u.makeFrozen();
		return true;
	}

	// * Unfreeze his/her own user account
	public Boolean USER_unfreezeUser(Integer uid, Integer sid) {
		UserInfo u = backend.getUserInfo(uid);
		UserSession s = u.getSession(sid);
		if (u.isFrozen()) {
			s.log("Unfreeze account");
			u.makeEnabled();
			return true;
		}
		s.log("FAILED (user account not frozen): Unfreeze account");
		return false;
	}

	// * Open a new money account
	public String USER_requestAccount(Integer uid, Integer sid) {
		UserInfo u = backend.getUserInfo(uid);
		UserSession s = u.getSession(sid);
		String account_number = u.createAccount(sid);
		s.log("Request new account with number <" + account_number + ">");

		Verification.sessionRequestAccount(s);
		return (account_number);

	}

	// * Close an existing money account
	public void USER_closeAccount(Integer uid, Integer sid, String accnum) {
		UserInfo u = backend.getUserInfo(uid);
		UserSession s = u.getSession(sid);
		s.log("Close account number <" + accnum + ">");
		u.deleteAccount(accnum);
	}

	// * Deposit money from an external source (e.g. from a credit card)
	public void USER_depositFromExternal(Integer uid, Integer sid, String accnumDst, Double amount) {

		UserInfo u = backend.getUserInfo(uid);
		UserSession s = u.getSession(sid);
		s.log("Deposit $" + amount + "to account <" + accnumDst + ">");
		u.depositTo(accnumDst, amount);

		Verification.userIncomingTransfer(u);
	}

	// * Pay a bill (i.e. an external money account) - charges apply
	public Boolean USER_payToExternal(Integer uid, Integer sid, String accnumSrc, Double amount) {
		Verification.fitsAttemptedExternalMoneyTransfer(amount);

		UserInfo u = backend.getUserInfo(uid);
		UserSession s = u.getSession(sid);

		if (s == null)
			return false;

		Double total_amount = amount + backend.getUserInfo(uid).getChargeRate(amount);
		if (u.getAccount(accnumSrc).getBalance() >= amount) {
			s.log("Payment of $" + amount + " from account <" + accnumSrc + ">");
			u.withdrawFrom(accnumSrc, total_amount);
			return true;
		}
		s.log("FAILED (not enough funds): Payment of $" + amount + " from account <" + accnumSrc + ">");
		return false;
	}

	// * Transfer money to another user's account - charges apply
	public Boolean USER_transferToOtherAccount(Integer uidSrc, Integer sidSrc, String accnumSrc, Integer uidDst,
			String accnumDst, Double amount) {
		UserInfo from_u = backend.getUserInfo(uidSrc);
		UserSession s = from_u.getSession(sidSrc);

		if (s == null)
			return false;

		Double total_amount = amount + backend.getUserInfo(uidSrc).getChargeRate(amount);

		if (from_u.getAccount(accnumSrc).getBalance() >= total_amount) {
			from_u.withdrawFrom(accnumSrc, total_amount);
			backend.getUserInfo(uidDst).depositTo(accnumDst, amount);
			s.log("Payment of $" + amount + " from account <" + accnumSrc + "> to account " + "<" + accnumDst
					+ " of user " + uidDst);
			return true;
		}
		s.log("FAILED (not enough funds): " + "Payment of $" + amount + " from account <" + accnumSrc + "> to account "
				+ "<" + accnumDst + " of user " + uidDst);
		return false;
	}

	// * Transfer money across own accounts - charges do not apply
	public Boolean USER_transferOwnAccounts(Integer uid, Integer sid, String from_account_number,
			String to_account_number, Double amount) {
		UserInfo u = backend.getUserInfo(uid);
		UserSession s = u.getSession(sid);
		BankAccount from_a = backend.getUserInfo(uid).getAccount(from_account_number),
				to_a = backend.getUserInfo(uid).getAccount(to_account_number);

		if (from_a.getBalance() >= amount) {
			from_a.withdraw(amount);
			to_a.deposit(amount);
			s.log("Transfer of $" + amount + " from account <" + from_account_number + "> to own account <"
					+ to_account_number);
			return true;
		}
		s.log("FAILED (not enough funds)" + "Transfer of $" + amount + " from account <" + from_account_number
				+ "> to own account <" + to_account_number);
		return false;
	}

}
