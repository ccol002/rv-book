package rv;

import java.util.HashMap;

import ltl.structure.*;
import fits.*;
import assertion.Assertion;






public aspect Properties {
static void setupVerification() {
}

before (): call(* UserInfo.openSession(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.triggereded5528) { Verification.currentLTLed5528 = Verification.currentLTLed5528.derivative(new Event("before","UserInfo.openSession")); Verification.triggereded5528 = true;}
  }

after (): call(* BackEnd.initialise(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (!Verification.triggereded5528) { Verification.currentLTLed5528 = Verification.currentLTLed5528.derivative(new Event("after","BackEnd.initialise")); Verification.triggereded5528 = true;}
  }

before (): call(* *.*(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (Verification.initialised) { if (!Verification.triggereded5528) {Verification.currentLTLed5528 = Verification.currentLTLed5528.derivative(new Event("before", "#")); Verification.triggereded5528 = true; } if (Verification.currentLTLed5528.cannotMatch()) { Assertion.alert("Violation detected on property { GOOD (not [before UserInfo.openSession] until [after BackEnd.initialise])}"); } Verification.triggereded5528 = false; }
  }

after (): call(* *.*(..)) &&
   !(adviceexecution())  &&
   !cflow(adviceexecution())  &&
   !cflowbelow(adviceexecution())  {
    if (Verification.initialised) { if (!Verification.triggereded5528) {Verification.currentLTLed5528 = Verification.currentLTLed5528.derivative(new Event("after", "#")); Verification.triggereded5528 = true; } if (Verification.currentLTLed5528.cannotMatch()) { Assertion.alert("Violation detected on property { GOOD (not [before UserInfo.openSession] until [after BackEnd.initialise])}"); } Verification.triggereded5528 = false; }
  }

}