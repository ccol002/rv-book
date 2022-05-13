
package rv;

import assertion.Assertion;
import fits.BankAccount;
import fits.FrontEnd;
import fits.UserInfo;
import fits.BackEnd;





public aspect Properties {

before (UserInfo u): call(* *.makeGoldUser(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!(u.getCountry().equals("Argentina"))) {{ Assertion.alert("P1 violated"); }}
  }

after (): call(* BackEnd.initialise(..)) &&
    args() &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) {{ Verification.fitsInitialisation(); }}
  }

before (): call(* UserInfo.openSession(..)) &&
    args() &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.isInitialised()) {{ Assertion.alert("P2 violated"); }}
  }

after (BankAccount a): call(* BankAccount.withdraw(..)) &&
    target(a) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (a.getBalance() < 0) {{ Assertion.alert("P3 violated"); }}
  }

after (BankAccount a): call(* BankAccount.deposit(..)) &&
    target(a) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (a.getBalance() < 0) {{ Assertion.alert("P3 violated"); }}
  }

before (FrontEnd fe, Integer uid, String accnum): call(* FrontEnd.ADMIN_approveOpenAccount(..)) &&
    args(uid, accnum) &&
    target(fe) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) {{ Verification.fitsAdminApprovingAccount(accnum,fe.getBackEnd()); }}
  }

before (Integer uid, Integer sid, String to_account_number, Double amount): call(* *.USER_payToExternal(..)) &&
    args(uid, sid, to_account_number, amount) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) {{ Verification.fitsExternalMoneyTransferCount ++; Verification.fitsExternalMoneyTransferAmount += amount; if (Verification.fitsExternalMoneyTransferCount >= 1000 || Verification.fitsExternalMoneyTransferAmount >= 1000000) { Assertion.alert("P8 violated"); } }}
  }

after (): call(* *.ADMIN_reconcile(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) {{ Verification.fitsExternalMoneyTransferCount = 0; Verification.fitsExternalMoneyTransferAmount = 0.0; }}
  }

}