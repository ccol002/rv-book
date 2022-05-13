
package rv;

import assertion.Assertion;
import fits.BackEnd;
import fits.BankAccount;
import fits.UserInfo;

public class Verification {
	static public boolean fitsHasBeenInitialised;
	static public Integer fitsExternalMoneyTransferCount;
	static public Double fitsExternalMoneyTransferAmount;

	static public void setupVerification() {
		fitsHasBeenInitialised = false;
		fitsExternalMoneyTransferCount = 0;
		fitsExternalMoneyTransferAmount = 0.0;
	}

// Property 2 verification

// Called from TransactionSystem.initialise
	public static void fitsInitialisation() {
		fitsHasBeenInitialised = true;
	}

// Check whether initialised
	public static Boolean isInitialised() {
		return fitsHasBeenInitialised;
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

}
