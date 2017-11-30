import java.security.InvalidParameterException;

public class Event implements Comparable<Event> {
	public enum eventType {Birth, Reproduction, Death};
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

	@Override
	public int compareTo(Event o) {
		if(this.time<o.time){
			return -1;
		}

		if(this.time==o.time){
			return 0;
		}

		else{
			return 1;
		}
	}
}
