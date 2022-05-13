
package rv;

import java.util.HashMap;

import fits.UserInfo;
import fits.UserSession;

public class Verification {

	static public HashMap<UserInfo, Integer> countSessions;

	static public void setupVerification() {
// required to reset the automata to their initial state
		Properties.setupVerification();

		hasTriggeredb9742 = new HashMap<UserInfo, Boolean>();
		stateb9742 = new HashMap<UserInfo, String>();

		hasTriggered6d69c = new HashMap<UserInfo, Boolean>();
		state6d69c = new HashMap<UserInfo, String>();

		hasTriggered2e922 = new HashMap<UserInfo, Boolean>();
		state2e922 = new HashMap<UserInfo, String>();

		hasTriggered5154f = new HashMap<UserSession, Boolean>();
		state5154f = new HashMap<UserSession, String>();
		countSessions = new HashMap<UserInfo, Integer>();

	}

	static public HashMap<UserInfo, String> stateb9742;
	static public HashMap<UserInfo, Boolean> hasTriggeredb9742;

	static public HashMap<UserInfo, String> state6d69c;
	static public HashMap<UserInfo, Boolean> hasTriggered6d69c;

	static public HashMap<UserInfo, String> state2e922;
	static public HashMap<UserInfo, Boolean> hasTriggered2e922;

	static public HashMap<UserSession, String> state5154f;
	static public HashMap<UserSession, Boolean> hasTriggered5154f;
}
