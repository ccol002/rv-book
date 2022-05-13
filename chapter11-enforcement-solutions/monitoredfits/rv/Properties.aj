
package rv;

import java.util.HashMap;

import assertion.Assertion;
import fits.BankAccount;
import fits.FrontEnd;
import fits.UserInfo;
import timers.Timer;




public aspect Properties {
static void setupVerification() {
initialisegreylisted();initialisecountTransfers();
}

static HashMap<UserInfo, Boolean> greylisted = new HashMap<UserInfo, Boolean>();
static void initialisegreylisted(){greylisted= new HashMap<UserInfo, Boolean>(); }


static HashMap<UserInfo, Integer> countTransfers = new HashMap<UserInfo, Integer>();
static void initialisecountTransfers(){countTransfers= new HashMap<UserInfo, Integer>(); }



before (BankAccount a, Double amount) throws RVExceptionProperty3: call(* BankAccount.withdraw(..)) &&
    args(amount) &&
    target(a) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (a.getBalance() - amount < 0) { Assertion.alert("P3 violated"); throw new RVExceptionProperty3("P3 violated"); }
  }

before (): call(* FrontEnd.ADMIN_reconcile(..)) &&
    args() &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.reconcile_timer.disable(); }
  }

before (Timer timer): call(* Timer.fire(..)) &&
    target(timer) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) {
    	if (timer.getIdentifier().equals("TIMEOUT PROPERTY 14")) { 
    		System.out.println("** Reconciled to enforce Property 14 **"); 
    		Verification.frontend.ADMIN_reconcile(); } }
  }

after (UserInfo u): call(* UserInfo.makeGreylisted(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
greylisted.put(u,true);countTransfers.put(u,0);}


after (UserInfo u): call(* UserInfo.depositTo(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (greylisted.computeIfAbsent(u, (k) -> false)) {countTransfers.put(u,countTransfers.computeIfAbsent(u, (k) -> 0) + 1);}}


after (UserInfo u) throws RVExceptionProperty6: call(* UserInfo.makeWhitelisted(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (( greylisted.computeIfAbsent(u, (k) -> false) && countTransfers.computeIfAbsent(u, (k) -> 0) < 3 )) {
Assertion.alert("P6 violated");  throw new RVExceptionProperty6("P6 violated");}
  }


after (UserInfo u): call(* UserInfo.makeWhitelisted(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
greylisted.put(u,false);}


after (UserInfo u) throws RVExceptionProperty1: call(* UserInfo.makeGoldUser(..)) &&
    args() &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!u.getCountry().equals("Argentina")) { Assertion.alert("P1 violated"); throw new RVExceptionProperty1("P1 violated"); }
  }

after (): call(* *.initialise(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.initialised = true; }
  }

after () throws RVExceptionProperty2: call(* UserInfo.openSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.initialised) { Assertion.alert("P2 enforced"); throw new RVExceptionProperty2("P2 violated"); }
  }

after (FrontEnd fe): call(* FrontEnd.ADMIN_initialise(..)) &&
    args() &&
    target(fe) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Timer timer = new Timer( "TIMEOUT PROPERTY 14", 5*1000l - 500l ); Verification.reconcile_timer = timer; timer.reset();  Verification.frontend = fe; }
  }

}