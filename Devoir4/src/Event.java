

public class Event {
	public enum eventType {Birth, Reproduction};
	public Sim subject;
	public eventType type;
	public double time;
	
	public Event(Sim subject, Event.eventType type, double time) {
		this.type = type;
		if(time>= 0) {
			this.time = time;
		}
		
		else {
			throw new InvalidParameterException("The time parameter must be non-negative");
		}
		this.subject = subject;
	}
	
	
}
