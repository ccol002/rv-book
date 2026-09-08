package rv;

import java.util.HashMap;

import ltl.structure.*;
import fits.*;
import assertion.Assertion;






public aspect Properties {
static void setupVerification() {
initialisetriggered29724f();initialisecurrentLTL29724f();
initialisetriggered9a45b3();initialisecurrentLTL9a45b3();
}

static HashMap<UserInfo, Boolean> triggered29724f = new HashMap<UserInfo, Boolean>();
static void initialisetriggered29724f(){triggered29724f= new HashMap<UserInfo, Boolean>(); }


static HashMap<UserInfo, LTL> currentLTL29724f = new HashMap<UserInfo, LTL>();
static void initialisecurrentLTL29724f(){currentLTL29724f= new HashMap<UserInfo, LTL>(); }



static HashMap<UserSession, Boolean> triggered9a45b3 = new HashMap<UserSession, Boolean>();
static void initialisetriggered9a45b3(){triggered9a45b3= new HashMap<UserSession, Boolean>(); }


static HashMap<UserSession, LTL> currentLTL9a45b3 = new HashMap<UserSession, LTL>();
static void initialisecurrentLTL9a45b3(){currentLTL9a45b3= new HashMap<UserSession, LTL>(); }



before (UserInfo u): call(* UserInfo.makeEnabled(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered29724f.computeIfAbsent(u, (k) -> false)) {
currentLTL29724f.put(u,currentLTL29724f.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))),new Globally(new Implies(new MatchEvent(new Event("before","UserInfo.makeDisabled")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled")))))))).derivative(new Event("before","UserInfo.makeEnabled")));triggered29724f.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.makeDisabled(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered29724f.computeIfAbsent(u, (k) -> false)) {
currentLTL29724f.put(u,currentLTL29724f.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))),new Globally(new Implies(new MatchEvent(new Event("before","UserInfo.makeDisabled")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled")))))))).derivative(new Event("before","UserInfo.makeDisabled")));triggered29724f.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.withdrawFrom(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered29724f.computeIfAbsent(u, (k) -> false)) {
currentLTL29724f.put(u,currentLTL29724f.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))),new Globally(new Implies(new MatchEvent(new Event("before","UserInfo.makeDisabled")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled")))))))).derivative(new Event("before","UserInfo.withdrawFrom")));triggered29724f.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered29724f.computeIfAbsent(u, (k) -> false)) {
currentLTL29724f.put(u,currentLTL29724f.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))),new Globally(new Implies(new MatchEvent(new Event("before","UserInfo.makeDisabled")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled")))))))).derivative(new Event("before", "#")));triggered29724f.put(u,true);}
  }


after (UserInfo u): call(* UserInfo.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered29724f.computeIfAbsent(u, (k) -> false)) {
currentLTL29724f.put(u,currentLTL29724f.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))),new Globally(new Implies(new MatchEvent(new Event("before","UserInfo.makeDisabled")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled")))))))).derivative(new Event("after", "#")));triggered29724f.put(u,true);}
  }


before (UserInfo u): call(* UserInfo.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (currentLTL29724f.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))),new Globally(new Implies(new MatchEvent(new Event("before","UserInfo.makeDisabled")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled")))))))).cannotMatch()) { Assertion.alert("Violation detected on foreach target (UserInfo u) property { GOOD ((not [before UserInfo.withdrawFrom] until [before UserInfo.makeEnabled]) and Globally (([before UserInfo.makeDisabled] implies next ((not [before UserInfo.withdrawFrom] until [before UserInfo.makeEnabled])))))}"); }triggered29724f.put(u,false);}


after (UserInfo u): call(* UserInfo.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (currentLTL29724f.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled"))),new Globally(new Implies(new MatchEvent(new Event("before","UserInfo.makeDisabled")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserInfo.withdrawFrom"))),new MatchEvent(new Event("before","UserInfo.makeEnabled")))))))).cannotMatch()) { Assertion.alert("Violation detected on foreach target (UserInfo u) property { GOOD ((not [before UserInfo.withdrawFrom] until [before UserInfo.makeEnabled]) and Globally (([before UserInfo.makeDisabled] implies next ((not [before UserInfo.withdrawFrom] until [before UserInfo.makeEnabled])))))}"); }triggered29724f.put(u,false);}


before (UserSession u): call(* UserSession.openSession(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered9a45b3.computeIfAbsent(u, (k) -> false)) {
currentLTL9a45b3.put(u,currentLTL9a45b3.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Globally(new Implies(new MatchEvent(new Event("before","UserSession.closeSession")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession")))))))).derivative(new Event("before","UserSession.openSession")));triggered9a45b3.put(u,true);}
  }


before (UserSession u): call(* UserSession.log(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered9a45b3.computeIfAbsent(u, (k) -> false)) {
currentLTL9a45b3.put(u,currentLTL9a45b3.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Globally(new Implies(new MatchEvent(new Event("before","UserSession.closeSession")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession")))))))).derivative(new Event("before","UserSession.log")));triggered9a45b3.put(u,true);}
  }


before (UserSession u): call(* UserSession.closeSession(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered9a45b3.computeIfAbsent(u, (k) -> false)) {
currentLTL9a45b3.put(u,currentLTL9a45b3.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Globally(new Implies(new MatchEvent(new Event("before","UserSession.closeSession")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession")))))))).derivative(new Event("before","UserSession.closeSession")));triggered9a45b3.put(u,true);}
  }


before (UserSession u): call(* UserSession.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered9a45b3.computeIfAbsent(u, (k) -> false)) {
currentLTL9a45b3.put(u,currentLTL9a45b3.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Globally(new Implies(new MatchEvent(new Event("before","UserSession.closeSession")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession")))))))).derivative(new Event("before", "#")));triggered9a45b3.put(u,true);}
  }


after (UserSession u): call(* UserSession.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!triggered9a45b3.computeIfAbsent(u, (k) -> false)) {
currentLTL9a45b3.put(u,currentLTL9a45b3.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Globally(new Implies(new MatchEvent(new Event("before","UserSession.closeSession")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession")))))))).derivative(new Event("after", "#")));triggered9a45b3.put(u,true);}
  }


before (UserSession u): call(* UserSession.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (currentLTL9a45b3.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Globally(new Implies(new MatchEvent(new Event("before","UserSession.closeSession")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession")))))))).cannotMatch()) { Assertion.alert("Violation detected on foreach target (UserSession u) property { GOOD ((not [before UserSession.log] until [before UserSession.openSession]) and Globally (([before UserSession.closeSession] implies next ((not [before UserSession.log] until [before UserSession.openSession])))))}"); }triggered9a45b3.put(u,false);}


after (UserSession u): call(* UserSession.*(..)) &&
    target(u) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
if (currentLTL9a45b3.computeIfAbsent(u, (k) -> new And(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession"))),new Globally(new Implies(new MatchEvent(new Event("before","UserSession.closeSession")),new Next(new Until(new Not(new MatchEvent(new Event("before","UserSession.log"))),new MatchEvent(new Event("before","UserSession.openSession")))))))).cannotMatch()) { Assertion.alert("Violation detected on foreach target (UserSession u) property { GOOD ((not [before UserSession.log] until [before UserSession.openSession]) and Globally (([before UserSession.closeSession] implies next ((not [before UserSession.log] until [before UserSession.openSession])))))}"); }triggered9a45b3.put(u,false);}


before (): call(* UserInfo.openSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.triggereda418fc) { Verification.currentLTLa418fc = Verification.currentLTLa418fc.derivative(new Event("before","UserInfo.openSession")); Verification.triggereda418fc = true;}
  }

after (): call(* BackEnd.initialise(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.triggereda418fc) { Verification.currentLTLa418fc = Verification.currentLTLa418fc.derivative(new Event("after","BackEnd.initialise")); Verification.triggereda418fc = true;}
  }

before (): call(* *.*(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (Verification.initialised) { if (!Verification.triggereda418fc) {Verification.currentLTLa418fc = Verification.currentLTLa418fc.derivative(new Event("before", "#")); Verification.triggereda418fc = true; } if (Verification.currentLTLa418fc.cannotMatch()) { Assertion.alert("Violation detected on property { GOOD (not [before UserInfo.openSession] until [after BackEnd.initialise])}"); } Verification.triggereda418fc = false; }
  }

after (): call(* *.*(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (Verification.initialised) { if (!Verification.triggereda418fc) {Verification.currentLTLa418fc = Verification.currentLTLa418fc.derivative(new Event("after", "#")); Verification.triggereda418fc = true; } if (Verification.currentLTLa418fc.cannotMatch()) { Assertion.alert("Violation detected on property { GOOD (not [before UserInfo.openSession] until [after BackEnd.initialise])}"); } Verification.triggereda418fc = false; }
  }

}