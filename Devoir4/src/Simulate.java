import java.util.PriorityQueue;
import java.util.Random;

public class Simulate {
	

	private Population population;
	private PriorityQueue<Event> eventQ;
	
	public void simulate(int n, double Tmax) {
		Random RND = new Random();
		AgeModel ageModel = new AgeModel();
		double birthrate = 5;

		
		this.eventQ = new PriorityQueue<Event>();
		for (int i=0; i<n; i++) {
			Sim founder = new Sim(Sim.Sex.M);
			Event E = new Event(founder, Event.eventType.Birth, 0.0);
			eventQ.add(E);
		}
		
		while (!eventQ.isEmpty()) {
			Event E = eventQ.poll();
			if (E.time>Tmax) break;
			
			//remove all Sims meant to die
			Sim deathSim = population.peakMin();
			while (deathSim.getDeathTime()<=E.time) {
				population.removeMin();
				deathSim = population.peakMin();
			}
			if (E.subject.getDeathTime()>E.time) {
				switch (E.type) {
				case Birth:
					if (E.subject.getSex() == Sim.Sex.F) {
						double startReproTime = Sim.MIN_MATING_AGE_F + E.time;
						Event reproEvent = new Event(E.subject,Event.eventType.Reproduction,startReproTime);
						eventQ.add(reproEvent);
					}
					double deathTime = ageModel.randomAge(RND) + E.time;
					E.subject.setDeathTime(deathTime);
					population.insert(E.subject);
				case Reproduction:
					Sim lastMate = E.subject.getMate();
					Sim mate = chooseMate(E.subject,lastMate,E.time);
					Sim child = reproduce(E.subject,mate,E.time);
					Event childBirth = new Event(child,Event.eventType.Birth,E.time);
					eventQ.add(childBirth);
	
					double reproductionWaitTime = AgeModel.randomWaitingTime(RND, birthrate);
					if (E.subject.isMatingAge(E.time + reproductionWaitTime)) {
						Event reproEvent = new Event(E.subject, Event.eventType.Reproduction, E.time + reproductionWaitTime);
						eventQ.add(reproEvent);
					}
				}
			}
		}
		
	}
	public Sim chooseMate(Sim subject, Sim lastMate, double time) {
		Sim newMate;
		if (lastMate != null) {
			
		} else {
			//newMate = chooseRandomMate(time);
			//while (newMate.isMatingAge(time)) {
				
			}
		}
		return lastMate;
	}
	
	public Sim chooseRandomMate(double time) {
		return;
	}
	
	public Sim reproduce(Sim mother, Sim father, double time) {
		Sim.Sex sex = Sim.Sex.M;
		Sim child = new Sim(mother,father,time,sex);
		return child;
	}
	public static void main(String[] args) {
		
	}

}
