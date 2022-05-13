package rv;

import assertion.Assertion;
import fits.BackEnd;
import fits.UserInfo;
import fits.UserSession;
import re.structure.Event;







public aspect Properties {
static void setupVerification() {
}

after (): call(* *.*(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (Verification.initialised) { if (!Verification.triggered60e53b93) {Verification.currentRegExp60e53b93 = Verification.currentRegExp60e53b93.derivative(new Event("after", "#")); Verification.triggered60e53b93 = true; } if (Verification.currentRegExp60e53b93.hasEmpty()) { Assertion.alert("Violation detected on  property { BAD ((((!(before BackEnd.initialise))* ; (((before BackEnd.initialise ; (!(before BackEnd.shutdown))*) ; before BackEnd.shutdown))*) ; (!(before BackEnd.initialise))*) ; before UserInfo.openSession)}"); } Verification.triggered60e53b93 = false; if (!Verification.triggered5e2de80c) {Verification.currentRegExp5e2de80c = Verification.currentRegExp5e2de80c.derivative(new Event("after", "#")); Verification.triggered5e2de80c = true; } if (Verification.currentRegExp5e2de80c.cannotMatch()) { Assertion.alert("Violation detected on  property { GOOD (((((!(before UserInfo.makeDisabled))* ; before UserInfo.makeDisabled) ; (!(before UserInfo.withdrawFrom))*) ; before UserInfo.makeEnabled))*}"); } Verification.triggered5e2de80c = false; if (!Verification.triggered1d44bcfa) {Verification.currentRegExp1d44bcfa = Verification.currentRegExp1d44bcfa.derivative(new Event("after", "#")); Verification.triggered1d44bcfa = true; } if (Verification.currentRegExp1d44bcfa.hasEmpty()) { Assertion.alert("Violation detected on  property { BAD ((((?)* ; before makeDisabled) ; (!(before makeEnabled))*) ; before withdrawFrom)}"); } Verification.triggered1d44bcfa = false; if (!Verification.triggered266474c2) {Verification.currentRegExp266474c2 = Verification.currentRegExp266474c2.derivative(new Event("after", "#")); Verification.triggered266474c2 = true; } if (Verification.currentRegExp266474c2.hasEmpty()) { Assertion.alert("Violation detected on  property { BAD ((((!(before UserSession.openSession))* ; (((before UserSession.openSession ; (!(before UserSession.closeSession))*) ; before UserSession.closeSession))*) ; (!(before UserSession.openSession))*) ; before UserSession.log)}"); } Verification.triggered266474c2 = false; if (!Verification.triggered6f94fa3e) {Verification.currentRegExp6f94fa3e = Verification.currentRegExp6f94fa3e.derivative(new Event("after", "#")); Verification.triggered6f94fa3e = true; } if (Verification.currentRegExp6f94fa3e.cannotMatch()) { Assertion.alert("Violation detected on  property { GOOD (((((!(before UserSession.log))* ; before UserSession.openSession) ; (!(before UserSession.closeSession))*) ; before UserSession.closeSession))*}"); } Verification.triggered6f94fa3e = false; }
  }

before (): call(* BackEnd.shutdown(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp60e53b93 = Verification.currentRegExp60e53b93.derivative(new Event("before","BackEnd.shutdown")); Verification.triggered60e53b93 = true;}
  }

before (): call(* UserInfo.openSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp60e53b93 = Verification.currentRegExp60e53b93.derivative(new Event("before","UserInfo.openSession")); Verification.triggered60e53b93 = true;}
  }

before (): call(* BackEnd.initialise(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp60e53b93 = Verification.currentRegExp60e53b93.derivative(new Event("before","BackEnd.initialise")); Verification.triggered60e53b93 = true;}
  }

before (): call(* UserInfo.makeEnabled(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp5e2de80c = Verification.currentRegExp5e2de80c.derivative(new Event("before","UserInfo.makeEnabled")); Verification.triggered5e2de80c = true;}
  }

before (): call(* UserInfo.makeDisabled(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp5e2de80c = Verification.currentRegExp5e2de80c.derivative(new Event("before","UserInfo.makeDisabled")); Verification.triggered5e2de80c = true;}
  }

before (): call(* UserInfo.withdrawFrom(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp5e2de80c = Verification.currentRegExp5e2de80c.derivative(new Event("before","UserInfo.withdrawFrom")); Verification.triggered5e2de80c = true;}
  }

before (): call(* withdrawFrom(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp1d44bcfa = Verification.currentRegExp1d44bcfa.derivative(new Event("before","withdrawFrom")); Verification.triggered1d44bcfa = true;}
  }

before (): call(* makeDisabled(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp1d44bcfa = Verification.currentRegExp1d44bcfa.derivative(new Event("before","makeDisabled")); Verification.triggered1d44bcfa = true;}
  }

before (): call(* makeEnabled(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp1d44bcfa = Verification.currentRegExp1d44bcfa.derivative(new Event("before","makeEnabled")); Verification.triggered1d44bcfa = true;}
  }

before (): call(* UserSession.openSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp266474c2 = Verification.currentRegExp266474c2.derivative(new Event("before","UserSession.openSession")); Verification.triggered266474c2 = true;}
  }

before (): call(* UserSession.log(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp266474c2 = Verification.currentRegExp266474c2.derivative(new Event("before","UserSession.log")); Verification.triggered266474c2 = true;}
  }

before (): call(* UserSession.closeSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp266474c2 = Verification.currentRegExp266474c2.derivative(new Event("before","UserSession.closeSession")); Verification.triggered266474c2 = true;}
  }

before (): call(* UserSession.openSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp6f94fa3e = Verification.currentRegExp6f94fa3e.derivative(new Event("before","UserSession.openSession")); Verification.triggered6f94fa3e = true;}
  }

before (): call(* UserSession.log(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp6f94fa3e = Verification.currentRegExp6f94fa3e.derivative(new Event("before","UserSession.log")); Verification.triggered6f94fa3e = true;}
  }

before (): call(* UserSession.closeSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (true) { Verification.currentRegExp6f94fa3e = Verification.currentRegExp6f94fa3e.derivative(new Event("before","UserSession.closeSession")); Verification.triggered6f94fa3e = true;}
  }

before (): call(* *.*(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (Verification.initialised) { if (!Verification.triggered60e53b93) {Verification.currentRegExp60e53b93 = Verification.currentRegExp60e53b93.derivative(new Event("before", "#")); Verification.triggered60e53b93 = true; } if (Verification.currentRegExp60e53b93.hasEmpty()) { Assertion.alert("Violation detected on  property { BAD ((((!(before BackEnd.initialise))* ; (((before BackEnd.initialise ; (!(before BackEnd.shutdown))*) ; before BackEnd.shutdown))*) ; (!(before BackEnd.initialise))*) ; before UserInfo.openSession)}"); } Verification.triggered60e53b93 = false; if (!Verification.triggered5e2de80c) {Verification.currentRegExp5e2de80c = Verification.currentRegExp5e2de80c.derivative(new Event("before", "#")); Verification.triggered5e2de80c = true; } if (Verification.currentRegExp5e2de80c.cannotMatch()) { Assertion.alert("Violation detected on  property { GOOD (((((!(before UserInfo.makeDisabled))* ; before UserInfo.makeDisabled) ; (!(before UserInfo.withdrawFrom))*) ; before UserInfo.makeEnabled))*}"); } Verification.triggered5e2de80c = false; if (!Verification.triggered1d44bcfa) {Verification.currentRegExp1d44bcfa = Verification.currentRegExp1d44bcfa.derivative(new Event("before", "#")); Verification.triggered1d44bcfa = true; } if (Verification.currentRegExp1d44bcfa.hasEmpty()) { Assertion.alert("Violation detected on  property { BAD ((((?)* ; before makeDisabled) ; (!(before makeEnabled))*) ; before withdrawFrom)}"); } Verification.triggered1d44bcfa = false; if (!Verification.triggered266474c2) {Verification.currentRegExp266474c2 = Verification.currentRegExp266474c2.derivative(new Event("before", "#")); Verification.triggered266474c2 = true; } if (Verification.currentRegExp266474c2.hasEmpty()) { Assertion.alert("Violation detected on  property { BAD ((((!(before UserSession.openSession))* ; (((before UserSession.openSession ; (!(before UserSession.closeSession))*) ; before UserSession.closeSession))*) ; (!(before UserSession.openSession))*) ; before UserSession.log)}"); } Verification.triggered266474c2 = false; if (!Verification.triggered6f94fa3e) {Verification.currentRegExp6f94fa3e = Verification.currentRegExp6f94fa3e.derivative(new Event("before", "#")); Verification.triggered6f94fa3e = true; } if (Verification.currentRegExp6f94fa3e.cannotMatch()) { Assertion.alert("Violation detected on  property { GOOD (((((!(before UserSession.log))* ; before UserSession.openSession) ; (!(before UserSession.closeSession))*) ; before UserSession.closeSession))*}"); } Verification.triggered6f94fa3e = false; }
  }

}