

public class Event {
	public enum eventType {Birth, Reproduction};
	public Sim subject;
	public eventType type;
	public double time;
	
	public Event(Sim subject, Event.eventType type, double time) {
		this.type = type;
		this.time = time;
		this.subject = subject;
	}
	
	
}
