
package rv;

import fits.FrontEnd;
import timers.Timer;
import timers.TimerManager;

public class Verification {

	public static Boolean initialised = false;
	public static Timer reconcile_timer = null;
	public static FrontEnd frontend = null;

	static public void setupVerification() {
		TimerManager.reset();
		initialised = false;
		reconcile_timer = null;
		frontend = null;

		Properties.setupVerification();
	}
}
