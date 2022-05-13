
package rv;

import java.util.HashMap;

import assertion.Assertion;
import fits.BankAccount;
import fits.UserInfo;


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

}