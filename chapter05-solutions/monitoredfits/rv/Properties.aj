package rv;

import assertion.Assertion;
import fits.BackEnd;
import fits.BankAccount;
import fits.FrontEnd;
import fits.UserInfo;
import fits.UserSession;

public aspect Properties {



	// Property 1
	before (): call(* *.makeGoldUser()) {
		UserInfo u = (UserInfo)(thisJoinPoint.getTarget());
		Assertion.check(
				u.getCountry().equals("Argentina"), 
				"P1 violated"
				); 
	}

	// Property 2
	after (): call(* BackEnd.initialise()) {
		Verification.fitsInitialisation();
	}

	before (): call(* UserInfo.openSession()) {
		Verification.fitsOpenSession();
	}


	// Property 3
	after (): call(* BankAccount.withdraw(..)) || call(* BankAccount.deposit(..)) {
		BankAccount a = (BankAccount)(thisJoinPoint.getTarget());

		Assertion.check(a.getBalance() >= 0, "P3 violated");
	}

	// Property 4
	before (FrontEnd fe, String accnum): call(* FrontEnd.ADMIN_approveOpenAccount(..)) && target(fe) && args(*,accnum) {

		Verification.fitsAdminApprovingAccount(accnum,fe.getBackEnd());
	}

	// Property 5
	before (FrontEnd fe, Integer uid): call(* FrontEnd.ADMIN_disableUser(..)) && target(fe) && args(uid){

		Verification.userMakeDisabled(fe.getBackEnd().getUserInfo(uid));
	}
	before (FrontEnd fe, Integer uid): call(* FrontEnd.ADMIN_enableUser(..)) && target(fe) && args(uid){

		Verification.userMakeEnabled(fe.getBackEnd().getUserInfo(uid));
	}
	before (UserInfo u): call(* UserInfo.withdrawFrom(..)) && target(u) {
		
		Verification.userWithdrawal_SOLUTION_2(u);
	}

	// Property 6
	before (FrontEnd fe, Integer uid): call(* FrontEnd.USER_depositFromExternal(..)) && target(fe) && args(uid,*,*,*) {
		
		Verification.userIncomingTransfer(fe.getBackEnd().getUserInfo(uid));
	}
	
	before (UserInfo u): call(* UserInfo.makeGreylisted(..)) && target(u) {
		
		Verification.userMakeGreylisted(u);
	}
	
	before (UserInfo u): call(* UserInfo.makeBlacklisted(..)) && target(u) {
		
		Verification.userMakeBlacklisted(u);
	}
	before (UserInfo u): call(* UserInfo.makeWhitelisted(..)) && target(u) {
		Verification.userMakeWhitelisted(u);
	}

	// Property 7	
	before (FrontEnd fe, Integer uid, Integer sid): call(* FrontEnd.USER_requestAccount(..))
	&& target(fe) && args(uid,sid) {
		Verification.sessionRequestAccount(fe.getBackEnd().getUserInfo(uid).getSession(sid));
	}


	// Property 8
	before (Double amount): 
		call(* *.USER_payToExternal(..))
		&& args(*,*,*, amount) {
		Verification.fitsAttemptedExternalMoneyTransfer(amount);
		}
	
	after (): call(* *.ADMIN_reconcile()) {
		Verification.fitsReconcile();
	}

	// Property 9
	before (UserInfo u): call(* UserInfo.openSession(..)) && target(u) {
		
		Verification.userOpenSession(u); 
		
	}
	before (UserInfo u): call(* UserInfo.closeSession(..)) && target(u) {
		
		Verification.userCloseSession(u); 
	}

	// Property 10
	before (UserSession s): call(* UserSession.openSession(..)) && target(s) {
		
		Verification.sessionOpen(s); 
	}
	
	before (UserSession s): call(* UserSession.closeSession(..)) && target(s) {
		
		Verification.sessionClose(s);
	}
	before (UserSession s): call(* UserSession.log(..)) && target(s) {
		Verification.sessionLogInformation(s);
	}


}
