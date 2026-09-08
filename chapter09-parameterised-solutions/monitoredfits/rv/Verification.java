
package rv;

import ltl.structure.*;

public class Verification {

public static Boolean initialised = false;
static public LTL currentLTLa418fc;
static public Boolean triggereda418fc;

static public void setupVerification() {

currentLTLa418fc = new Until(new Not(new MatchEvent(new Event("before","UserInfo.openSession"))),new MatchEvent(new Event("after","BackEnd.initialise")));
triggereda418fc = false;



Properties.setupVerification();
initialised = true;} }



