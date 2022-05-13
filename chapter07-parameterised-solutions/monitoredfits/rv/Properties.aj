
package rv;

import assertion.Assertion;
import fits.UserInfo;
import fits.UserSession;
import java.util.HashMap;








public aspect Properties {
static void setupVerification() {
initialisehasTriggeredb9742();initialisestateb9742();
initialisehasTriggered6d69c();initialisestate6d69c();initialisecount();
initialisehasTriggered2e922();initialisestate2e922();
initialisehasTriggered5154f();initialisestate5154f();
}

static HashMap<UserInfo, Boolean> hasTriggeredb9742 = new HashMap<UserInfo, Boolean>();
static void initialisehasTriggeredb9742(){hasTriggeredb9742= new HashMap<UserInfo, Boolean>(); }


static HashMap<UserInfo, String> stateb9742 = new HashMap<UserInfo, String>();
static void initialisestateb9742(){stateb9742= new HashMap<UserInfo, String>(); }



static HashMap<UserInfo, Boolean> hasTriggered6d69c = new HashMap<UserInfo, Boolean>();
static void initialisehasTriggered6d69c(){hasTriggered6d69c= new HashMap<UserInfo, Boolean>(); }


static HashMap<UserInfo, String> state6d69c = new HashMap<UserInfo, String>();
static void initialisestate6d69c(){state6d69c= new HashMap<UserInfo, String>(); }


static HashMap<UserInfo, Integer> count = new HashMap<UserInfo, Integer>();
static void initialisecount(){count= new HashMap<UserInfo, Integer>(); }



static HashMap<UserInfo, Boolean> hasTriggered2e922 = new HashMap<UserInfo, Boolean>();
static void initialisehasTriggered2e922(){hasTriggered2e922= new HashMap<UserInfo, Boolean>(); }


static HashMap<UserInfo, String> state2e922 = new HashMap<UserInfo, String>();
static void initialisestate2e922(){state2e922= new HashMap<UserInfo, String>(); }



static HashMap<UserSession, Boolean> hasTriggered5154f = new HashMap<UserSession, Boolean>();
static void initialisehasTriggered5154f(){hasTriggered5154f= new HashMap<UserSession, Boolean>(); }


static HashMap<UserSession, String> state5154f = new HashMap<UserSession, String>();
static void initialisestate5154f(){state5154f= new HashMap<UserSession, String>(); }



before (UserInfo u): call(* UserInfo.makeDisabled(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggeredb9742.computeIfAbsent(u, (k) -> false) && stateb9742.computeIfAbsent(u, (k) -> "Enabled").equals("Enabled") && (true)) {
{}stateb9742.put(u,"Disabled");hasTriggeredb9742.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.makeEnabled(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggeredb9742.computeIfAbsent(u, (k) -> false) && stateb9742.computeIfAbsent(u, (k) -> "Enabled").equals("Disabled") && (true)) {
{}stateb9742.put(u,"Enabled");hasTriggeredb9742.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.withdrawFrom(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggeredb9742.computeIfAbsent(u, (k) -> false) && stateb9742.computeIfAbsent(u, (k) -> "Enabled").equals("Disabled") && (true)) {
{}stateb9742.put(u,"Bad");hasTriggeredb9742.put(u,true);System.out.println("P5 violated");}
  }


before (UserInfo u): call(* UserInfo.makeGreylisted(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggered6d69c.computeIfAbsent(u, (k) -> false) && state6d69c.computeIfAbsent(u, (k) -> "NonGreylisted").equals("NonGreylisted") && (true)) {
{count.put(u,0);}state6d69c.put(u,"Greylisted");hasTriggered6d69c.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.depositTo(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggered6d69c.computeIfAbsent(u, (k) -> false) && state6d69c.computeIfAbsent(u, (k) -> "NonGreylisted").equals("Greylisted") && (true)) {
{count.put(u,count.computeIfAbsent(u, (k) -> 0) + 1);}state6d69c.put(u,"Greylisted");hasTriggered6d69c.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.makeBlacklisted(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggered6d69c.computeIfAbsent(u, (k) -> false) && state6d69c.computeIfAbsent(u, (k) -> "NonGreylisted").equals("Greylisted") && (true)) {
{count.put(u,0);}state6d69c.put(u,"NonGreylisted");hasTriggered6d69c.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.makeWhitelisted(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggered6d69c.computeIfAbsent(u, (k) -> false) && state6d69c.computeIfAbsent(u, (k) -> "NonGreylisted").equals("Greylisted") && (count.computeIfAbsent(u, (k) -> 0) >= 3)) {
{}state6d69c.put(u,"NonGreylisted");hasTriggered6d69c.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.makeWhitelisted(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggered6d69c.computeIfAbsent(u, (k) -> false) && state6d69c.computeIfAbsent(u, (k) -> "NonGreylisted").equals("Greylisted") && (count.computeIfAbsent(u, (k) -> 0) < 3)) {
{}state6d69c.put(u,"Bad");hasTriggered6d69c.put(u,true);System.out.println("P6 violated");}
  }


before (UserInfo u): call(* UserInfo.openSession(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggered2e922.computeIfAbsent(u, (k) -> false) && state2e922.computeIfAbsent(u, (k) -> "Start").equals("Start") && (true)) {
{ Verification.countSessions.put(u,Verification.countSessions.getOrDefault(u,0)+1); if (Verification.countSessions.getOrDefault(u,0) > 3) { Assertion.alert("P9 violated"); } }state2e922.put(u,"Start");hasTriggered2e922.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.closeSession(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggered2e922.computeIfAbsent(u, (k) -> false) && state2e922.computeIfAbsent(u, (k) -> "Start").equals("Start") && (true)) {
{ Verification.countSessions.put(u,Verification.countSessions.get(u)-1); }state2e922.put(u,"Start");hasTriggered2e922.put(u,true);}
  }


before (UserSession s): call(* UserSession.openSession(..)) &&
    target(s) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggered5154f.computeIfAbsent(s, (k) -> false) && state5154f.computeIfAbsent(s, (k) -> "LoggedOut").equals("LoggedOut") && (true)) {
{}state5154f.put(s,"LoggedIn");hasTriggered5154f.put(s,true);}
  }


before (UserSession s): call(* UserSession.closeSession(..)) &&
    target(s) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggered5154f.computeIfAbsent(s, (k) -> false) && state5154f.computeIfAbsent(s, (k) -> "LoggedOut").equals("LoggedIn") && (true)) {
{}state5154f.put(s,"LoggedOut");hasTriggered5154f.put(s,true);}
  }


before (UserSession s): call(* UserSession.log(..)) &&
    target(s) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!hasTriggered5154f.computeIfAbsent(s, (k) -> false) && state5154f.computeIfAbsent(s, (k) -> "LoggedOut").equals("LoggedOut") && (true)) {
{}state5154f.put(s,"Bad");hasTriggered5154f.put(s,true);System.out.println("P10 violated");}
  }


before (): call(* *.*(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { hasTriggeredb9742 = new HashMap<UserInfo,Boolean> (); hasTriggered6d69c = new HashMap<UserInfo,Boolean> (); hasTriggered2e922 = new HashMap<UserInfo,Boolean> (); hasTriggered5154f = new HashMap<UserSession,Boolean> ();}
  }

after (): call(* *.*(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { hasTriggeredb9742 = new HashMap<UserInfo,Boolean> (); hasTriggered6d69c = new HashMap<UserInfo,Boolean> (); hasTriggered2e922 = new HashMap<UserInfo,Boolean> (); hasTriggered5154f = new HashMap<UserSession,Boolean> (); }
  }

}