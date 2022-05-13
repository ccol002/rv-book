
package rv;

import ltl.structure.*;

public class Verification {

	public static Boolean initialised = false;
	static public LTL currentLTLe53b93;
	static public Boolean triggerede53b93;

	static public void setupVerification() {

		currentLTLe53b93 = new Until(new Not(new MatchEvent(new Event("before", "UserInfo.openSession"))),
				new MatchEvent(new Event("after", "BackEnd.initialise")));
		triggerede53b93 = false;

		Properties.setupVerification();
		initialised = true;
	}
}
