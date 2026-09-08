package rv;

import re.structure.*;
import java.util.HashMap;

import fits.*;

import assertion.Assertion;







public aspect Properties {
static void setupVerification() {
initialisetriggered8efb846();initialisecurrentRegExp8efb846();
initialisetriggered1b28cdfa();initialisecurrentRegExp1b28cdfa();
initialisetriggeredeed1f14();initialisecurrentRegExpeed1f14();
initialisetriggered7229724f();initialisecurrentRegExp7229724f();
}

static HashMap<UserInfo, Boolean> triggered8efb846 = new HashMap<UserInfo, Boolean>();
static void initialisetriggered8efb846(){triggered8efb846= new HashMap<UserInfo, Boolean>(); }


static HashMap<UserInfo, RegExp> currentRegExp8efb846 = new HashMap<UserInfo, RegExp>();
static void initialisecurrentRegExp8efb846(){currentRegExp8efb846= new HashMap<UserInfo, RegExp>(); }



static HashMap<UserInfo, Boolean> triggered1b28cdfa = new HashMap<UserInfo, Boolean>();
static void initialisetriggered1b28cdfa(){triggered1b28cdfa= new HashMap<UserInfo, Boolean>(); }


static HashMap<UserInfo, RegExp> currentRegExp1b28cdfa = new HashMap<UserInfo, RegExp>();
static void initialisecurrentRegExp1b28cdfa(){currentRegExp1b28cdfa= new HashMap<UserInfo, RegExp>(); }



static HashMap<UserSession, Boolean> triggeredeed1f14 = new HashMap<UserSession, Boolean>();
static void initialisetriggeredeed1f14(){triggeredeed1f14= new HashMap<UserSession, Boolean>(); }


static HashMap<UserSession, RegExp> currentRegExpeed1f14 = new HashMap<UserSession, RegExp>();
static void initialisecurrentRegExpeed1f14(){currentRegExpeed1f14= new HashMap<UserSession, RegExp>(); }



static HashMap<UserSession, Boolean> triggered7229724f = new HashMap<UserSession, Boolean>();
static void initialisetriggered7229724f(){triggered7229724f= new HashMap<UserSession, Boolean>(); }


static HashMap<UserSession, RegExp> currentRegExp7229724f = new HashMap<UserSession, RegExp>();
static void initialisecurrentRegExp7229724f(){currentRegExp7229724f= new HashMap<UserSession, RegExp>(); }



before (UserInfo u): call(* UserInfo.makeEnabled(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
currentRegExp8efb846.put(u,currentRegExp8efb846.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserInfo.makeDisabled"))),new MatchEvent(new Event("before","UserInfo.makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","UserInfo.withdrawFrom")))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))))).derivative(new Event("before","UserInfo.makeEnabled")));triggered8efb846.put(u,true);}


before (UserInfo u): call(* UserInfo.makeDisabled(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
currentRegExp8efb846.put(u,currentRegExp8efb846.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserInfo.makeDisabled"))),new MatchEvent(new Event("before","UserInfo.makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","UserInfo.withdrawFrom")))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))))).derivative(new Event("before","UserInfo.makeDisabled")));triggered8efb846.put(u,true);}


before (UserInfo u): call(* UserInfo.withdrawFrom(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
currentRegExp8efb846.put(u,currentRegExp8efb846.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserInfo.makeDisabled"))),new MatchEvent(new Event("before","UserInfo.makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","UserInfo.withdrawFrom")))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))))).derivative(new Event("before","UserInfo.withdrawFrom")));triggered8efb846.put(u,true);}


before (UserInfo u): call(* UserInfo.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered8efb846.computeIfAbsent(u, (k) -> false)) {
currentRegExp8efb846.put(u,currentRegExp8efb846.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserInfo.makeDisabled"))),new MatchEvent(new Event("before","UserInfo.makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","UserInfo.withdrawFrom")))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))))).derivative(new Event("before", "#")));triggered8efb846.put(u,true);}
  }


after (UserInfo u): call(* UserInfo.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered8efb846.computeIfAbsent(u, (k) -> false)) {
currentRegExp8efb846.put(u,currentRegExp8efb846.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserInfo.makeDisabled"))),new MatchEvent(new Event("before","UserInfo.makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","UserInfo.withdrawFrom")))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))))).derivative(new Event("after", "#")));triggered8efb846.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (currentRegExp8efb846.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserInfo.makeDisabled"))),new MatchEvent(new Event("before","UserInfo.makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","UserInfo.withdrawFrom")))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))))).cannotMatch()) { Assertion.alert("Violation detected on foreach target (UserInfo u) property { GOOD (((((!(before UserInfo.makeDisabled))* ; before UserInfo.makeDisabled) ; (!(before UserInfo.withdrawFrom))*) ; before UserInfo.makeEnabled))*}"); }triggered8efb846.put(u,false);}


after (UserInfo u): call(* UserInfo.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (currentRegExp8efb846.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserInfo.makeDisabled"))),new MatchEvent(new Event("before","UserInfo.makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","UserInfo.withdrawFrom")))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))))).cannotMatch()) { Assertion.alert("Violation detected on foreach target (UserInfo u) property { GOOD (((((!(before UserInfo.makeDisabled))* ; before UserInfo.makeDisabled) ; (!(before UserInfo.withdrawFrom))*) ; before UserInfo.makeEnabled))*}"); }triggered8efb846.put(u,false);}


before (UserInfo u): call(* withdrawFrom(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
currentRegExp1b28cdfa.put(u,currentRegExp1b28cdfa.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new Any()),new MatchEvent(new Event("before","makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","makeEnabled")))),new MatchEvent(new Event("before","withdrawFrom")))).derivative(new Event("before","withdrawFrom")));triggered1b28cdfa.put(u,true);}


before (UserInfo u): call(* makeDisabled(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
currentRegExp1b28cdfa.put(u,currentRegExp1b28cdfa.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new Any()),new MatchEvent(new Event("before","makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","makeEnabled")))),new MatchEvent(new Event("before","withdrawFrom")))).derivative(new Event("before","makeDisabled")));triggered1b28cdfa.put(u,true);}


before (UserInfo u): call(* makeEnabled(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
currentRegExp1b28cdfa.put(u,currentRegExp1b28cdfa.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new Any()),new MatchEvent(new Event("before","makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","makeEnabled")))),new MatchEvent(new Event("before","withdrawFrom")))).derivative(new Event("before","makeEnabled")));triggered1b28cdfa.put(u,true);}


before (UserInfo u): call(* UserInfo.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered1b28cdfa.computeIfAbsent(u, (k) -> false)) {
currentRegExp1b28cdfa.put(u,currentRegExp1b28cdfa.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new Any()),new MatchEvent(new Event("before","makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","makeEnabled")))),new MatchEvent(new Event("before","withdrawFrom")))).derivative(new Event("before", "#")));triggered1b28cdfa.put(u,true);}
  }


after (UserInfo u): call(* UserInfo.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered1b28cdfa.computeIfAbsent(u, (k) -> false)) {
currentRegExp1b28cdfa.put(u,currentRegExp1b28cdfa.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new Any()),new MatchEvent(new Event("before","makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","makeEnabled")))),new MatchEvent(new Event("before","withdrawFrom")))).derivative(new Event("after", "#")));triggered1b28cdfa.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (currentRegExp1b28cdfa.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new Any()),new MatchEvent(new Event("before","makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","makeEnabled")))),new MatchEvent(new Event("before","withdrawFrom")))).hasEmpty()) { Assertion.alert("Violation detected on foreach target (UserInfo u) property { BAD ((((?)* ; before makeDisabled) ; (!(before makeEnabled))*) ; before withdrawFrom)}"); }triggered1b28cdfa.put(u,false);}


after (UserInfo u): call(* UserInfo.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (currentRegExp1b28cdfa.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new Any()),new MatchEvent(new Event("before","makeDisabled"))),new Repetition(new MatchEventComplement(new Event("before","makeEnabled")))),new MatchEvent(new Event("before","withdrawFrom")))).hasEmpty()) { Assertion.alert("Violation detected on foreach target (UserInfo u) property { BAD ((((?)* ; before makeDisabled) ; (!(before makeEnabled))*) ; before withdrawFrom)}"); }triggered1b28cdfa.put(u,false);}


before (UserSession u): call(* UserSession.openSession(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
currentRegExpeed1f14.put(u,currentRegExpeed1f14.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession"))),new Repetition(new SequentialComposition(new SequentialComposition(new MatchEvent(new Event("before","UserSession.openSession")),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))),new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession")))),new MatchEvent(new Event("before","UserSession.log")))).derivative(new Event("before","UserSession.openSession")));triggeredeed1f14.put(u,true);}


before (UserSession u): call(* UserSession.log(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
currentRegExpeed1f14.put(u,currentRegExpeed1f14.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession"))),new Repetition(new SequentialComposition(new SequentialComposition(new MatchEvent(new Event("before","UserSession.openSession")),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))),new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession")))),new MatchEvent(new Event("before","UserSession.log")))).derivative(new Event("before","UserSession.log")));triggeredeed1f14.put(u,true);}


before (UserSession u): call(* UserSession.closeSession(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
currentRegExpeed1f14.put(u,currentRegExpeed1f14.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession"))),new Repetition(new SequentialComposition(new SequentialComposition(new MatchEvent(new Event("before","UserSession.openSession")),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))),new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession")))),new MatchEvent(new Event("before","UserSession.log")))).derivative(new Event("before","UserSession.closeSession")));triggeredeed1f14.put(u,true);}


before (UserSession u): call(* UserSession.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggeredeed1f14.computeIfAbsent(u, (k) -> false)) {
currentRegExpeed1f14.put(u,currentRegExpeed1f14.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession"))),new Repetition(new SequentialComposition(new SequentialComposition(new MatchEvent(new Event("before","UserSession.openSession")),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))),new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession")))),new MatchEvent(new Event("before","UserSession.log")))).derivative(new Event("before", "#")));triggeredeed1f14.put(u,true);}
  }


after (UserSession u): call(* UserSession.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggeredeed1f14.computeIfAbsent(u, (k) -> false)) {
currentRegExpeed1f14.put(u,currentRegExpeed1f14.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession"))),new Repetition(new SequentialComposition(new SequentialComposition(new MatchEvent(new Event("before","UserSession.openSession")),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))),new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession")))),new MatchEvent(new Event("before","UserSession.log")))).derivative(new Event("after", "#")));triggeredeed1f14.put(u,true);}
  }


before (UserSession u): call(* UserSession.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (currentRegExpeed1f14.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession"))),new Repetition(new SequentialComposition(new SequentialComposition(new MatchEvent(new Event("before","UserSession.openSession")),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))),new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession")))),new MatchEvent(new Event("before","UserSession.log")))).hasEmpty()) { Assertion.alert("Violation detected on foreach target (UserSession u) property { BAD ((((!(before UserSession.openSession))* ; (((before UserSession.openSession ; (!(before UserSession.closeSession))*) ; before UserSession.closeSession))*) ; (!(before UserSession.openSession))*) ; before UserSession.log)}"); }triggeredeed1f14.put(u,false);}


after (UserSession u): call(* UserSession.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (currentRegExpeed1f14.computeIfAbsent(u, (k) -> new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession"))),new Repetition(new SequentialComposition(new SequentialComposition(new MatchEvent(new Event("before","UserSession.openSession")),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))),new Repetition(new MatchEventComplement(new Event("before","UserSession.openSession")))),new MatchEvent(new Event("before","UserSession.log")))).hasEmpty()) { Assertion.alert("Violation detected on foreach target (UserSession u) property { BAD ((((!(before UserSession.openSession))* ; (((before UserSession.openSession ; (!(before UserSession.closeSession))*) ; before UserSession.closeSession))*) ; (!(before UserSession.openSession))*) ; before UserSession.log)}"); }triggeredeed1f14.put(u,false);}


before (UserSession u): call(* UserSession.openSession(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
currentRegExp7229724f.put(u,currentRegExp7229724f.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))).derivative(new Event("before","UserSession.openSession")));triggered7229724f.put(u,true);}


before (UserSession u): call(* UserSession.log(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
currentRegExp7229724f.put(u,currentRegExp7229724f.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))).derivative(new Event("before","UserSession.log")));triggered7229724f.put(u,true);}


before (UserSession u): call(* UserSession.closeSession(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
currentRegExp7229724f.put(u,currentRegExp7229724f.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))).derivative(new Event("before","UserSession.closeSession")));triggered7229724f.put(u,true);}


before (UserSession u): call(* UserSession.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered7229724f.computeIfAbsent(u, (k) -> false)) {
currentRegExp7229724f.put(u,currentRegExp7229724f.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))).derivative(new Event("before", "#")));triggered7229724f.put(u,true);}
  }


after (UserSession u): call(* UserSession.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered7229724f.computeIfAbsent(u, (k) -> false)) {
currentRegExp7229724f.put(u,currentRegExp7229724f.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))).derivative(new Event("after", "#")));triggered7229724f.put(u,true);}
  }


before (UserSession u): call(* UserSession.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (currentRegExp7229724f.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))).cannotMatch()) { Assertion.alert("Violation detected on foreach target (UserSession u) property { GOOD (((((!(before UserSession.log))* ; before UserSession.openSession) ; (!(before UserSession.closeSession))*) ; before UserSession.closeSession))*}"); }triggered7229724f.put(u,false);}


after (UserSession u): call(* UserSession.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (currentRegExp7229724f.computeIfAbsent(u, (k) -> new Repetition(new SequentialComposition(new SequentialComposition(new SequentialComposition(new Repetition(new MatchEventComplement(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Repetition(new MatchEventComplement(new Event("before","UserSession.closeSession")))),new MatchEvent(new Event("before","UserSession.closeSession"))))).cannotMatch()) { Assertion.alert("Violation detected on foreach target (UserSession u) property { GOOD (((((!(before UserSession.log))* ; before UserSession.openSession) ; (!(before UserSession.closeSession))*) ; before UserSession.closeSession))*}"); }triggered7229724f.put(u,false);}


after (): call(* *.*(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (Verification.initialised) {}
  }

before (): call(* *.*(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (Verification.initialised) {}
  }

}