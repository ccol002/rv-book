package player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Replayer {
	// The trace which the replayer will playout
	private ArrayList<Joinpoint> trace;

	// Getters
	public Integer getTraceLength() {
		return trace.size();
	}

	public Joinpoint getTraceEvent(Integer i) {
		if (i >= 0 && i < getTraceLength())
			return trace.get(i);

		return null;
	}

	// Replay the trace
	public void replay() {
		for (Joinpoint jp : trace) {
			jp.replay();
		}
	}

	// The constructor reads the trace from a text file
	public Replayer(String filename) {
		BufferedReader reader = null;
		try {
			// Parses the logfile appearing as joinpoints in textform separated by an empty
			// line
			reader = new BufferedReader(new FileReader(filename));

			trace = new ArrayList<Joinpoint>();

			String line = "";
			while (line != null) {
				// Read joinpoint
				String text_jp = "";
				// Skip initial empty lines
				while (line.equals(""))
					line = reader.readLine();
				// Parse until an empty line or the end of file occurs
				while (line != null && !line.equals("")) {
					text_jp += line + "\n";
					line = reader.readLine();
				}
				if (!text_jp.equals("")) {
					Joinpoint jp = new Joinpoint(text_jp);
					trace.add(jp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
