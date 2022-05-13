
package rv;

import re.structure.*;

public class Verification {

	public static Boolean initialised = false;
	static public RegExp currentRegExp60e53b93;
	static public Boolean triggered60e53b93;

	static public RegExp currentRegExp5e2de80c;
	static public Boolean triggered5e2de80c;

	static public void setupVerification() {

		currentRegExp60e53b93 = new SequentialComposition(new SequentialComposition(
				new Repetition(new MatchEventComplement(new Event("before", "UserInfo.openSession"))),
				new MatchEvent(new Event("before", "BackEnd.initialise"))), new Repetition(new Any()));
		triggered60e53b93 = false;

		currentRegExp5e2de80c = new SequentialComposition(
				new Repetition(new MatchEventComplement(new Event("before", "BackEnd.initialise"))),
				new MatchEvent(new Event("before", "UserInfo.openSession")));
		triggered5e2de80c = false;

		Properties.setupVerification();
		initialised = true;
	}
}
