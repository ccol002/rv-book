package timers;

public class TimerManager implements Runnable {

	private static boolean on = false;
	private static IterableList events;
	private static Object lock;
	private static Long fastForwarded = 0l;
	private static Thread t;

	public static void reset() {
		// trigger any remaining events
		if (lock != null) {
			synchronized (lock) {
				lock.notify();
				try {// wait for TimerManager to end
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		on = false;// turn off TimerManager
		// wait for the thread to die
		while (t != null && t.isAlive()) {
			synchronized (lock) {
				lock.notify();
			}
		}
		start();
	}

	public static Long currentTimeMillis() {
		return System.currentTimeMillis() + fastForwarded;
	}

	public static void fastForward(Long millis) {
		fastForwarded += millis;
		synchronized (lock) {
			lock.notify();// in case the clock is idle
		}
	}

	public static void start() {
		if (!on) {
			on = true;
			events = new IterableList();
			lock = new Object();
			fastForwarded = 0l;
			t = new Thread(new TimerManager());
			t.setPriority(Thread.MAX_PRIORITY);
			t.setDaemon(true);
			t.start();
		}
	}

	public static void register(Long l, Long current, Timer c) {
		TimerManager.events.add(l + current, l, c);
		synchronized (lock) {
			lock.notify();// in case the clock is idle
		}
	}

	public static void register(Long l, Long current, Long paused, Timer c) {
		TimerManager.events.add(l + current + paused, l, c);
		synchronized (lock) {
			lock.notify();// in case the clock is idle
		}
	}

	public void run() {
		try {
			while (on)
				if (events.getNext() != null) {

					long next = events.current();

					long cur = currentTimeMillis();
					long tmp = next - cur;
					if (on && tmp > 0)
						synchronized (lock) {
							lock.wait(tmp);
						}

					cur = currentTimeMillis();
					if (on && next <= cur) {
						events.remove();
						for (int i = 0; i < events.currentClocks().size(); i++) {
							Timer c = events.currentClocks().get(i);
							long d = events.currentDurations().get(i);

							if (c.verified(next - d))
								c.fire(d);
						}
					}
				} else {
					synchronized (lock) {
						lock.wait();
					}
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}