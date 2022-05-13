
package rv;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;

import fits.BackEnd;
import fits.BankAccount;
import fits.FrontEnd;
import fits.UserInfo;




public aspect Properties {
static void setupVerification() {
initialiseisEnabled();
}

static HashMap<UserInfo, Boolean> isEnabled = new HashMap<UserInfo, Boolean>();
static void initialiseisEnabled(){isEnabled= new HashMap<UserInfo, Boolean>(); }



before (UserInfo u): call(* UserInfo.makeDisabled(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
isEnabled.put(u,false);}


before (UserInfo u): call(* UserInfo.makeEnabled(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
isEnabled.put(u,true);}


before (UserInfo u): call(* UserInfo.withdrawFrom(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
assertTrue(!isEnabled.computeIfAbsent(u, (k) -> false),"P5 violated");}


before (UserInfo u): call(* *.makeGoldUser(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { assertTrue(u.getCountry().equals("Argentina"),"P1 violated"); }
  }

before (): call(* UserInfo.openSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { assertTrue(Verification.fitsHasBeenInitialised,"P2 violated"); }
  }

before (FrontEnd fe, Integer uid, String accnum): call(* FrontEnd.ADMIN_approveOpenAccount(..)) &&
    args(uid, accnum) &&
    target(fe) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.fitsAdminApprovingAccount(accnum,fe.getBackEnd()); }
  }

after (): call(* BackEnd.initialise(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.fitsHasBeenInitialised = true; }
  }

after (BankAccount a): call(* BankAccount.withdraw(..)) &&
    target(a) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { assertTrue(a.getBalance() >= 0,"P3 violated"); }
  }

after (BankAccount a): call(* BankAccount.deposit(..)) &&
    target(a) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { assertTrue(a.getBalance() >= 0,"P3 violated"); }
  }

}