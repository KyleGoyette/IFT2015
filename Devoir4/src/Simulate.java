import java.util.*;

public class Simulate {
	public double BIRTHRATE;
	private static final double FIDELITY = 0.1;
	private Random RND = new Random();
	private Population population;
	private PriorityQueue<Event> eventQ;
	private Coalescence coalescence;
	
	public void simulate(int n, double Tmax) {
		double lastReportTime = 0;
		AgeModel ageModel = new AgeModel();
		population = new Population(n);
		this.BIRTHRATE = 2.0/ageModel.expectedParenthoodSpan(Sim.MIN_MATING_AGE_F, Sim.MAX_MATING_AGE_F);
		Sim founder;
		this.eventQ = new PriorityQueue<Event>();
		for (int i=0; i<n; i++) {
			if (RND.nextFloat()>0.5 ) {
				founder = new Sim(Sim.Sex.M);
			} else {
				founder = new Sim(Sim.Sex.F);
			}

			Event E = new Event(founder, Event.eventType.Birth, 0.0);
			eventQ.add(E);
		}

        while (!eventQ.isEmpty() && eventQ.peek().time<Tmax) {
        	Event E = eventQ.poll();
            
            if (E.time>Tmax) break;


            if (E.time>lastReportTime+100) {
            	System.out.println("TIME: "+E.time);
                //System.out.println("Number of events left: " + eventQ.size());
                System.out.println("Population size: " + population.getSize());
                lastReportTime = E.time;
            }
            
            
			/*remove all Sims meant to die
			Sim deathSim = population.peakMin();
			while (deathSim.getDeathTime()<=E.time) {
				population.removeMin();
				deathSim = population.peakMin();
			}*/ //Now done with a Death event type
			if (E!= null && E.subject.getDeathTime()>E.time) {
				switch (E.type) {
				case Birth:
					if (E.subject.getSex() == Sim.Sex.F) {
						double startReproTime = Sim.MIN_MATING_AGE_F + E.time + AgeModel.randomWaitingTime(RND, BIRTHRATE);
						Event reproEvent = new Event(E.subject,Event.eventType.Reproduction,startReproTime);
                        //System.out.println("REPRO TIME: "+(startReproTime-E.time));
                        eventQ.add(reproEvent);
					}
					double deathTime = ageModel.randomAge(RND) + E.time;
					E.subject.setDeathTime(deathTime);
					Event death = new Event(E.subject, Event.eventType.Death, deathTime);
					eventQ.add(death);
                    population.insert(E.subject);
                    //System.out.println("Sim "+E.subject.toString()+" is born");
                    break;
               case Reproduction:
					Sim lastMate = E.subject.getMate();
					Sim mate = chooseMate(E.subject,lastMate,E.time);
					Sim child = reproduce(E.subject,mate,E.time);
					Event childBirth = new Event(child,Event.eventType.Birth,E.time);
					eventQ.add(childBirth);
	
					double reproductionWaitTime = AgeModel.randomWaitingTime(RND, BIRTHRATE);
					if (E.subject.isMatingAge(E.time + reproductionWaitTime)) {
						Event reproEvent = new Event(E.subject, Event.eventType.Reproduction, E.time + reproductionWaitTime);
						eventQ.add(reproEvent);
					}

               case Death:
				   population.population.remove(E.subject);
                }
			}
			else{
				population.population.remove(E.subject); // Kills the SIM
                    //System.out.println("Sim "+E.subject.toString()+" is dead");

            }
            
		}
		
	}
	public Sim chooseMate(Sim subject, Sim lastMate, double time) {
		Sim newMate;
		if (lastMate != null && Math.random() > FIDELITY) {
			return lastMate;
		} else {

		    newMate = chooseRandomMate(time);

		}

		return newMate;
	}
	
	public Sim chooseRandomMate(double time) {
        Sim newMate = null;

        //System.out.println(population.getSize());

        while (newMate == null) {
            int rdm = (int) Math.round(Math.random() * (population.getSize() - 1));
            if (rdm < population.population.size()) {
                Sim potential = population.population.get((rdm));
                if (potential.getSex().equals(Sim.Sex.M) && potential.isMatingAge(time)) {
                    if (!potential.isInARelationship(time)) {
                        newMate = potential;
                    } else if (Math.random() < FIDELITY) {
                        newMate = potential;
                    }
                }
            }
        }

        return newMate;
    }
	
	public Sim reproduce(Sim mother, Sim father, double time) {
		Sim.Sex sex = Math.random() < 0.5? Sim.Sex.M : Sim.Sex.F;
		Sim child = new Sim(mother,father,time,sex);
		mother.setMate(father);
		father.setMate(mother);
		return child;
	}
	
	   public Sim ancestryP(Sim a){

	        while(a.getFather()!=null){
	            a = a.getFather();
	        }

	        return a;
	   }
	   
	   public Sim ancestryM(Sim a){

	        while(a.getMother()!=null){
	            a = a.getMother();
	        }

	        return a;

	    }

	    public LinkedList<Sim> coalP(Population population){

	        LinkedList<Sim> result = new LinkedList<>();

	        for(int i = population.population.size()-1; i>=0; i--){

	            Sim currentA = ancestryP(population.population.get(i));

	            if(!result.contains(currentA)){
	                result.add(currentA);
	            }
	        }

	        System.out.println("Number of paternal lineages: "+result.size());
	        return result;
	    }

	    public LinkedList<Sim> coalM(Population population){

	        LinkedList<Sim> result = new LinkedList<>();

	        for(int i = population.population.size()-1; i>=0; i--){

	            Sim currentA = ancestryM(population.population.get(i));

	            if(!result.contains(currentA)){
	                result.add(currentA);
	            }
	        }

	        System.out.println("Number of maternal lineages: "+result.size());
	        return result;
	    }
	    

	    public static void main(String[] args) {
	        Simulate test = new Simulate();
	        test.simulate(1000,1000);
	        System.out.println(test.BIRTHRATE);
	        Coalescence coalescence = new Coalescence(test.population);
	        coalescence.makePA();
	        coalescence.makeMA();
	        coalescence.PA.entrySet().forEach(System.out::println);
	    }
}
