package re.structure;

public class Event {

	private String modality;
	private String event;

	public Event(String modality, String event) {
		this.modality = modality;
		this.event = event;
	}

	public boolean equals(Event event) {
		return this.modality.equals(event.getModality()) && this.event.equals(event.getEvent());
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
