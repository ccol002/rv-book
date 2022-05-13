
package rv;

import java.util.HashMap;

import assertion.Assertion;
import fits.BackEnd;
import fits.BankAccount;
import fits.FrontEnd;
import fits.UserInfo;
import fits.UserSession;




public aspect Properties {
static void setupVerification() {
initialiseisEnabled();
initialiseisGreylisted();initialiseincomingTransfersSinceGreylisted();
initialisenumberOfOpenSessions();
initialisesessionIsOpen();
}

static HashMap<UserInfo, Boolean> isEnabled = new HashMap<UserInfo, Boolean>();
static void initialiseisEnabled(){isEnabled= new HashMap<UserInfo, Boolean>(); }



static HashMap<UserInfo, Boolean> isGreylisted = new HashMap<UserInfo, Boolean>();
static void initialiseisGreylisted(){isGreylisted= new HashMap<UserInfo, Boolean>(); }


static HashMap<UserInfo, Integer> incomingTransfersSinceGreylisted = new HashMap<UserInfo, Integer>();
static void initialiseincomingTransfersSinceGreylisted(){incomingTransfersSinceGreylisted= new HashMap<UserInfo, Integer>(); }



static HashMap<UserInfo, Integer> numberOfOpenSessions = new HashMap<UserInfo, Integer>();
static void initialisenumberOfOpenSessions(){numberOfOpenSessions= new HashMap<UserInfo, Integer>(); }



static HashMap<UserSession, Boolean> sessionIsOpen = new HashMap<UserSession, Boolean>();
static void initialisesessionIsOpen(){sessionIsOpen= new HashMap<UserSession, Boolean>(); }



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
    if (!isEnabled.computeIfAbsent(u, (k) -> false)) {
Assertion.alert("P5 violated");}
  }


after (UserInfo u): call(* UserInfo.makeGreylisted(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
isGreylisted.put(u,true);incomingTransfersSinceGreylisted.put(u,0);}


before (UserInfo u): call(* UserInfo.depositTo(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
incomingTransfersSinceGreylisted.put(u,incomingTransfersSinceGreylisted.computeIfAbsent(u, (k) -> 0) + 1);}


before (UserInfo u): call(* UserInfo.makeWhitelisted(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (isGreylisted.computeIfAbsent(u, (k) -> false) && incomingTransfersSinceGreylisted.computeIfAbsent(u, (k) -> 0) < 3) {
Assertion.alert("P6 violated");}
  }


before (UserInfo u): call(* UserInfo.makeBlacklisted(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
incomingTransfersSinceGreylisted.put(u,0);isGreylisted.put(u,false);}


before (UserInfo u): call(* UserInfo.openSession(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
numberOfOpenSessions.put(u,numberOfOpenSessions.computeIfAbsent(u, (k) -> 0) + 1);if (numberOfOpenSessions.computeIfAbsent(u, (k) -> 0) > 3) { Assertion.alert("P9 violated"); }}


before (UserInfo u): call(* UserInfo.closeSession(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
numberOfOpenSessions.put(u,numberOfOpenSessions.computeIfAbsent(u, (k) -> 0) - 1);}


before (UserSession s): call(* UserSession.openSession(..)) &&
    target(s) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
sessionIsOpen.put(s,true);}


before (UserSession s): call(* UserSession.closeSession(..)) &&
    target(s) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
sessionIsOpen.put(s,false);}


before (UserSession s): call(* UserSession.log(..)) &&
    target(s) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!sessionIsOpen.computeIfAbsent(s, (k) -> false)) {
Assertion.alert("P10 violated");}
  }


before (UserInfo u): call(* *.makeGoldUser(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!(u.getCountry().equals("Argentina"))) { Assertion.alert("P1 violated"); }
  }

after (): call(* BackEnd.initialise(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.fitsHasBeenInitialised = true; }
  }

before (): call(* UserInfo.openSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.fitsHasBeenInitialised) { Assertion.alert("P2 violated"); }
  }

after (BankAccount a): call(* BankAccount.withdraw(..)) &&
    target(a) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (a.getBalance() < 0) { Assertion.alert("P3 violated"); }
  }

after (BankAccount a): call(* BankAccount.deposit(..)) &&
    target(a) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (a.getBalance() < 0) { Assertion.alert("P3 violated"); }
  }

before (FrontEnd fe, Integer uid, String accnum): call(* FrontEnd.ADMIN_approveOpenAccount(..)) &&
    args(uid, accnum) &&
    target(fe) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.fitsAdminApprovingAccount(accnum,fe.getBackEnd()); }
  }

before (Integer uid, Integer sid, String to_account_number, Double amount): call(* *.USER_payToExternal(..)) &&
    args(uid, sid, to_account_number, amount) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.fitsExternalMoneyTransferCount ++; Verification.fitsExternalMoneyTransferAmount += amount; if (Verification.fitsExternalMoneyTransferCount >= 1000 || Verification.fitsExternalMoneyTransferAmount >= 1000000) { Assertion.alert("P8 violated"); } }
  }

after (): call(* *.ADMIN_reconcile(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.fitsExternalMoneyTransferCount = 0; Verification.fitsExternalMoneyTransferAmount = 0.0; }
  }

}