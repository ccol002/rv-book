package timers;

import java.util.ArrayList;

public class Timer {

	// a timer identifier
	private String name;
	// a list of time elapses at which the timer will trigger
	private ArrayList<Long> registered = new ArrayList<Long>();

	// whether the timer is enabled or not (no events will fire if not enabled)
	private boolean enabled = true;
	// whether the timer is currently paused
	private boolean paused = false;

	private long durationPaused = 0;
	private long whenPaused;
	private long starting;

	public Timer() {
		this.name = this.toString();
	}

	public Timer(String name) {
		this.name = name;
	}

	public Timer(String name, Long firingTime) {
		this.name = name;
		this.addFiringTime(firingTime);
	}

	public Timer(Long firingTime) {
		this.name = "default";
		this.addFiringTime(firingTime);
	}

	public String getIdentifier() {
		return name;
	}

	public Timer disable() {
		synchronized (this) {
			enabled = false;
			return this;
		}
	}

	public Timer enable() {
		synchronized (this) {
			enabled = true;
			return this;
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Timer reset() {
		synchronized (this) {
			paused = false;
			durationPaused = 0;
			starting = TimerManager.currentTimeMillis();
			for (int i = 0; i < registered.size(); i++)
				registerWithTimerManager(registered.get(i), starting);
			// no need to un-register the existing events which belong to this clock
			// this will be automatically ignored
			return this;
		}
	}

	// checks the clock is enabled and not paused
	// and that the registration is an active one
	// (when a timer is paused events are not unregistered from the TimerManager)
	protected boolean verified(Long starting) {
		synchronized (this) {
			if (enabled && !paused)
				return (this.starting + durationPaused) == starting;
			else
				return false;
		}
	}

	public Timer pause() {
		synchronized (this) {
			paused = true;
			whenPaused = TimerManager.currentTimeMillis();
			return this;
		}
	}

	// continue
	public Timer resume() {
		// avoids deadlock..."resume" may be waiting for the "register" to complete
		// while holding "this object" as a lock while "verified" is also holding
		// "this object" as a lock and its caller is holding "lock" which is required by
		// "register"
		// note the order of obtained locks!!!
		// this order of locking is crucial when the method registers with the global
		// clock!!
		synchronized (this) {
			long now = TimerManager.currentTimeMillis();
			durationPaused += now - whenPaused;
			paused = false;// unpause here because this will effect the current time of the clock
			for (int i = 0; i < registered.size(); i++)
				if (registered.get(i) > current_long(now))// filter those events which occurred before pause
					TimerManager.register(registered.get(i), starting, durationPaused, this);
			return this;
		}
	}

	// returns the time in millis
	public Long time() {
		synchronized (this) {
			return current_long();
		}
	}

	private long current_long(long now) {
		synchronized (this) {
			if (paused)
				return (whenPaused - starting - durationPaused);
			else
				return (now - starting - durationPaused);
		}
	}

	private long current_long() {
		synchronized (this) {
			if (paused)
				return (whenPaused - starting - durationPaused);
			else
				return (TimerManager.currentTimeMillis() - starting - durationPaused);
		}
	}

	// this takes affect on the next reset
	public Timer addFiringTime(Long millis) {
		synchronized (this) {
			registered.add(millis);
			return this;
		}
	}

	private void registerWithTimerManager(Long millis, Long current) {
		TimerManager.register(millis, current, this);
	}

	// method which triggers the event
	public void fire(Long millis) {
		System.out.println("Timer '" + name + "' event fired after " + millis / 1000 + " second(s)");
	}

}