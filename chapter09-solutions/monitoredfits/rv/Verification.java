
package rv;

import ltl.structure.*;

public class Verification {

public static Boolean initialised = false;
static public LTL currentLTLed5528;
static public Boolean triggereded5528;

static public void setupVerification() {

currentLTLed5528 = new Until(new Not(new MatchEvent(new Event("before","UserInfo.openSession"))),new MatchEvent(new Event("after","BackEnd.initialise")));
triggereded5528 = false;



Properties.setupVerification();
initialised = true;} }



