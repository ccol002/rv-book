
package rv;

import re.structure.Any;
import re.structure.Event;
import re.structure.MatchEvent;
import re.structure.MatchEventComplement;
import re.structure.RegExp;
import re.structure.Repetition;
import re.structure.SequentialComposition;

public class Verification {

	public static Boolean initialised = false;
	static public RegExp currentRegExp60e53b93;
	static public Boolean triggered60e53b93;

	static public RegExp currentRegExp5e2de80c;
	static public Boolean triggered5e2de80c;

	static public RegExp currentRegExp1d44bcfa;
	static public Boolean triggered1d44bcfa;

	static public RegExp currentRegExp266474c2;
	static public Boolean triggered266474c2;

	static public RegExp currentRegExp6f94fa3e;
	static public Boolean triggered6f94fa3e;

	static public void setupVerification() {

		currentRegExp60e53b93 = new SequentialComposition(
				new SequentialComposition(
						new SequentialComposition(
								new Repetition(new MatchEventComplement(new Event("before", "BackEnd.initialise"))),
								new Repetition(new SequentialComposition(
										new SequentialComposition(
												new MatchEvent(new Event("before", "BackEnd.initialise")),
												new Repetition(new MatchEventComplement(
														new Event("before", "BackEnd.shutdown")))),
										new MatchEvent(new Event("before", "BackEnd.shutdown"))))),
						new Repetition(new MatchEventComplement(new Event("before", "BackEnd.initialise")))),
				new MatchEvent(new Event("before", "UserInfo.openSession")));
		triggered60e53b93 = false;

		currentRegExp5e2de80c = new Repetition(
				new SequentialComposition(
						new SequentialComposition(
								new SequentialComposition(
										new Repetition(
												new MatchEventComplement(new Event("before", "UserInfo.makeDisabled"))),
										new MatchEvent(new Event("before", "UserInfo.makeDisabled"))),
								new Repetition(new MatchEventComplement(new Event("before", "UserInfo.withdrawFrom")))),
						new MatchEvent(new Event("before", "UserInfo.makeEnabled"))));
		triggered5e2de80c = false;

		currentRegExp1d44bcfa = new SequentialComposition(
				new SequentialComposition(
						new SequentialComposition(new Repetition(new Any()),
								new MatchEvent(new Event("before", "makeDisabled"))),
						new Repetition(new MatchEventComplement(new Event("before", "makeEnabled")))),
				new MatchEvent(new Event("before", "withdrawFrom")));
		triggered1d44bcfa = false;

		currentRegExp266474c2 = new SequentialComposition(
				new SequentialComposition(
						new SequentialComposition(
								new Repetition(
										new MatchEventComplement(new Event("before", "UserSession.openSession"))),
								new Repetition(new SequentialComposition(
										new SequentialComposition(
												new MatchEvent(new Event("before", "UserSession.openSession")),
												new Repetition(new MatchEventComplement(
														new Event("before", "UserSession.closeSession")))),
										new MatchEvent(new Event("before", "UserSession.closeSession"))))),
						new Repetition(new MatchEventComplement(new Event("before", "UserSession.openSession")))),
				new MatchEvent(new Event("before", "UserSession.log")));
		triggered266474c2 = false;

		currentRegExp6f94fa3e = new Repetition(
				new SequentialComposition(
						new SequentialComposition(
								new SequentialComposition(
										new Repetition(
												new MatchEventComplement(new Event("before", "UserSession.log"))),
										new MatchEvent(new Event("before", "UserSession.openSession"))),
								new Repetition(
										new MatchEventComplement(new Event("before", "UserSession.closeSession")))),
						new MatchEvent(new Event("before", "UserSession.closeSession"))));
		triggered6f94fa3e = false;

		Properties.setupVerification();
		initialised = true;
	}
}
