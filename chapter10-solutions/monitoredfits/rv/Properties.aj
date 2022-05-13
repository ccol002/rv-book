
package rv;

import java.util.ArrayList;
import java.util.HashMap;

import assertion.Assertion;
import fits.BackEnd;
import fits.FrontEnd;
import fits.UserInfo;
import fits.UserSession;
import timers.Timer;
import timers.TimerManager;




public aspect Properties {
static void setupVerification() {
initialiseisBlacklisted();initialisejustWhitelisted();initialisesinceWhitelisted();
initialiseaccountCreationTimes();
initialisesinceLastActivity();
}

static HashMap<UserInfo, Boolean> isBlacklisted = new HashMap<UserInfo, Boolean>();
static void initialiseisBlacklisted(){isBlacklisted= new HashMap<UserInfo, Boolean>(); }


static HashMap<UserInfo, Boolean> justWhitelisted = new HashMap<UserInfo, Boolean>();
static void initialisejustWhitelisted(){justWhitelisted= new HashMap<UserInfo, Boolean>(); }


static HashMap<UserInfo, Long> sinceWhitelisted = new HashMap<UserInfo, Long>();
static void initialisesinceWhitelisted(){sinceWhitelisted= new HashMap<UserInfo, Long>(); }



static HashMap<UserInfo, ArrayList<Long>> accountCreationTimes = new HashMap<UserInfo, ArrayList<Long>>();
static void initialiseaccountCreationTimes(){accountCreationTimes= new HashMap<UserInfo, ArrayList<Long>>(); }



static HashMap<UserSession, Timer> sinceLastActivity = new HashMap<UserSession, Timer>();
static void initialisesinceLastActivity(){sinceLastActivity= new HashMap<UserSession, Timer>(); }



before (UserSession s): call(* UserSession.log(..)) &&
    target(s) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
sinceLastActivity.computeIfAbsent(s, (k) -> new Timer("sinceLastActivity",15*60*1000l)).reset();}


before (UserSession s): call(* UserSession.closeSession(..)) &&
    args() &&
    target(s) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
sinceLastActivity.computeIfAbsent(s, (k) -> new Timer("sinceLastActivity",15*60*1000l)).disable();}


before (): call(* BackEnd.initialise(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.initialisedTime = TimerManager.currentTimeMillis(); }
  }

before (): call(* UserInfo.openSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (TimerManager.currentTimeMillis() - Verification.initialisedTime < 10*1000) { Assertion.alert("P11 violated"); }
  }

after (UserInfo u): call(* UserInfo.makeBlacklisted(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
isBlacklisted.put(u,true);justWhitelisted.put(u,false);}


after (UserInfo u): call(* UserInfo.makeWhitelisted(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (isBlacklisted.computeIfAbsent(u, (k) -> false)) {
justWhitelisted.put(u,true);isBlacklisted.put(u,false);sinceWhitelisted.put(u,TimerManager.currentTimeMillis());}
  }


after (UserInfo u, String account_number, double amount): call(* UserInfo.withdrawFrom(..)) &&
    args(account_number, amount) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (justWhitelisted.computeIfAbsent(u, (k) -> false) && TimerManager.currentTimeMillis() - sinceWhitelisted.computeIfAbsent(u, (k) -> 0l) < 12*60*60*1000 && amount > 100) {
Assertion.alert("P12 violated");}
  }


after (UserInfo u): call(* UserInfo.createAccount(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
while (accountCreationTimes.computeIfAbsent(u, (k) -> new ArrayList<Long>()).size()>0) { if (accountCreationTimes.computeIfAbsent(u, (k) -> new ArrayList<Long>()).get(0) > TimerManager.currentTimeMillis() + 24*60*60*1000l) accountCreationTimes.computeIfAbsent(u, (k) -> new ArrayList<Long>()).remove(0); else break; } if (accountCreationTimes.computeIfAbsent(u, (k) -> new ArrayList<Long>()).size() > 2) Assertion.alert("P13 violated"); else accountCreationTimes.computeIfAbsent(u, (k) -> new ArrayList<Long>()).add(TimerManager.currentTimeMillis());}


after (UserSession s): call(* UserSession.openSession(..)) &&
    args() &&
    target(s) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
sinceLastActivity.computeIfAbsent(s, (k) -> new Timer("sinceLastActivity",15*60*1000l)).enable(); sinceLastActivity.computeIfAbsent(s, (k) -> new Timer("sinceLastActivity",15*60*1000l)).reset();}


after (): call(* BackEnd.initialise(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (Verification.initialisationTimerSet) { Assertion.alert("P14 violated"); }
  }

after (): call(* BackEnd.initialise(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.initialisationTimer.reset(); Verification.fitsReconciled = false; Verification.initialisationTimerSet = true;   }
  }

after (): call(* FrontEnd.ADMIN_reconcile(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.fitsReconciled = true; Verification.initialisationTimerSet = false; }
  }

after (Timer t): call(* Timer.fire(..)) &&
    target(t) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (t.getIdentifier().equals("initialisationTimer") && !Verification.fitsReconciled) { Assertion.alert("P14 violated"); }
  }

after (String s): call(* UserSession.log(..)) &&
    args(s) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (s.startsWith("Request new account with number <")) { s = s.substring(s.indexOf("<")+1); s = s.substring(0,s.length()-1); new Timer("awaitingApproval" + s,24*60*60*1000l).reset(); Verification.awaitingApproval.add(s); }
  }

after (Integer uid, String accnum): call(* *.ADMIN_approveOpenAccount(..)) &&
    args(uid, accnum) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.awaitingApproval.remove(accnum); }
  }

after (Integer uid, String accnum): call(* *.ADMIN_rejectOpenAccount(..)) &&
    args(uid, accnum) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.awaitingApproval.remove(accnum); }
  }

after (Timer t): call(* Timer.fire(..)) &&
    target(t) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (t.getIdentifier().startsWith("awaitingApproval")) { if (Verification.awaitingApproval.contains(t.getIdentifier().substring(16))) Assertion.alert("P15 violated"); }
  }

after (Timer t): call(* Timer.fire(..)) &&
    target(t) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (t.getIdentifier().startsWith("sinceLastActivity")) { Assertion.alert("P16 violated"); }
  }

}