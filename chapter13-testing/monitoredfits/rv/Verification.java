
package rv;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;

import fits.BackEnd;
import fits.BankAccount;
import fits.UserInfo;

public class Verification {
	static public boolean fitsHasBeenInitialised;
	static public HashMap<UserInfo, HashMap<Integer, Integer>> accountRequestCount;
	static public Integer fitsExternalMoneyTransferCount;
	static public Double fitsExternalMoneyTransferAmount;

	static public void setupVerification() {
		fitsHasBeenInitialised = false;
		accountRequestCount = new HashMap<UserInfo, HashMap<Integer, Integer>>();
		fitsExternalMoneyTransferCount = 0;
		fitsExternalMoneyTransferAmount = 0.0;

		Properties.setupVerification();
	}

// Property 4 verification
// A bank account approved by the administrator may not have the same account
// number as any other bank account already existing in the system

	public static void fitsAdminApprovingAccount(String new_account_number, BackEnd fits) {
		for (UserInfo user : fits.getUsers()) {
			for (BankAccount account : user.getAccounts()) {
				if (account.isOpen()) {
					assertTrue(!account.getAccountNumber().equals(new_account_number), "P4 violated");
				}
			}
		}
	}

}
