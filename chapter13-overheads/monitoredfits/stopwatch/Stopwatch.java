package stopwatch;

public class Stopwatch {
	private enum Mode {
		STOPPED, RUNNING
	};

	private Mode mode;
	private long start_time;
	private long fastforwarded_time;

	// Constructor, starts stopwatch in stopped mode
	public Stopwatch() {
		reset();
	}

	// Fastforward time
	public void fastforward(long amount) {
		if (mode == Mode.RUNNING)
			fastforwarded_time += amount;
	}

	// Start stopwatch running if currently stopped
	public void start() {
		if (mode == Mode.STOPPED) {
			start_time = System.currentTimeMillis();
			fastforwarded_time = 0;
			mode = Mode.RUNNING;
		}
	}

	// Stop and reset stopwatch
	public void reset() {
		mode = Mode.STOPPED;
	}

	// Get time elapsed since the stopwatch was started (-1 if it was reset)
	public long getTimeElapsed() {
		if (mode == Mode.RUNNING)
			return System.currentTimeMillis() - start_time + fastforwarded_time;
		else
			return -1;
	}
}