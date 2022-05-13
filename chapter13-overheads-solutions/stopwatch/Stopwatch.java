package stopwatch;

public class Stopwatch {
	private enum Mode {
		STOPPED, RUNNING, PAUSED
	};

	private Mode mode;
	private long start_time;
	private long pause_time; // time when it was paused (if currently paused)
	private long dead_time; // time spent paused from when it started running till the last time it was
							// paused
	private long fastforwarded_time; // amount of time fastforwarded through

	// Constructor, starts stopwatch in stopped mode
	public Stopwatch() {
		reset();
	}

	// Start stopwatch running if currently stopped
	public void start() {
		if (mode == Mode.STOPPED) {
			start_time = System.currentTimeMillis();
			dead_time = 0;
			fastforwarded_time = 0;
			mode = Mode.RUNNING;
		}
	}

	// Pause the stopwatch if is running
	public void pause() {
		if (mode == Mode.RUNNING) {
			mode = Mode.PAUSED;
			pause_time = System.currentTimeMillis();
		}
	}

	// Resume the stopwatch if was paused
	public void resume() {
		if (mode == Mode.PAUSED) {
			mode = Mode.RUNNING;
			dead_time += System.currentTimeMillis() - pause_time;
		}
	}

	// Fastforward time without waiting
	public void fastforward(long amount) {
		if (mode == Mode.RUNNING)
			fastforwarded_time += amount;
	}

	// Stop and reset stopwatch
	public void reset() {
		mode = Mode.STOPPED;
		dead_time = 0;
	}

	// Get time elapsed since the stopwatch was started taking
	// into account that it may be paused (returns -1 if it was
	// reset and not restarted)
	public long getTimeElapsed() {
		if (mode == Mode.RUNNING)
			return System.currentTimeMillis() - start_time - dead_time + fastforwarded_time;

		if (mode == Mode.PAUSED)
			return pause_time - start_time - dead_time + fastforwarded_time;

		return -1;
	}
}