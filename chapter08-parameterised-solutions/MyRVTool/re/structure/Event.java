package re.structure;

public class Event {

	private String modality;
	private String event;

	public Event(String modality, String event) {
		this.modality = modality;
		this.event = event;
	}

	// this is what Set uses to check for equality
	public int hashCode() {
		return modality.hashCode() * event.hashCode();
	}

	public boolean equals(Object event) {
		if (event instanceof Event) {
			Event e = (Event) event;
			return this.modality.equals(e.getModality()) && this.event.equals(e.getEvent());
		}
		return false;
	}

	public String getConstructor() {
		return "new Event(\"" + modality + "\",\"" + event + "\")";
	}

	public String getModality() {
		return modality;
	}

	public String getEvent() {
		return event;
	}

	public String toString() {
		return modality + " " + event;
	}

}
