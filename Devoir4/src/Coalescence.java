import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.HashMap;

public class Coalescence {
	private PriorityQueue<Sim> coalescenceQM;
	private PriorityQueue<Sim> coalescenceQF;
	public HashMap<Double, Integer> PA;
	public HashMap<Double,Integer> MA;
	
	/*Constructs priority queues from existing population but now ordered by birthdate
	 * @params population (a binary heap of sims) ordered by deathtime 
	 */
	public Coalescence(Population population) {
		coalescenceQM = new PriorityQueue<Sim>();
		coalescenceQF = new PriorityQueue<Sim>();
	    Iterator<Sim> popIterator = population.population.iterator();
	    while (popIterator.hasNext()) {
	    	Sim nextSim = popIterator.next();
	    	if (nextSim.getSex() == Sim.Sex.M) {
	    		coalescenceQM.add(nextSim);
	    	} else {
	    		coalescenceQF.add(nextSim);
	    	}
	    }
	   PA = new HashMap<Double, Integer>();
	   MA = new HashMap<Double, Integer>();
	}

	/* Build Coalescance Map between time and number of lineages for patriarchal lines
	 * @returns Hashmap of time to number of father lineages
	 */
	public HashMap<Double, Integer> makePA() {
		while (!coalescenceQM.isEmpty()) {
			Sim youngestSim = coalescenceQM.poll();
			double birthtime = youngestSim.getBirthTime();
			Sim father = youngestSim.getFather();
			if (coalescenceQM.contains(father) || father ==null) {				
				PA.put(birthtime, coalescenceQM.size());
			} else {
				coalescenceQM.add(father);
			}
		}
		return PA;
	}

	/* Build Coalescance Map between time and number of lineages for matriarchal lines
	 * @returns Hashmap of time to number of mother lineages
	 */
	public HashMap<Double, Integer> makeMA() {
		while (!coalescenceQF.isEmpty()) {
			Sim youngestSim = coalescenceQF.remove();
			double birthtime = youngestSim.getBirthTime();
			Sim mother = youngestSim.getMother();
			
			if (coalescenceQF.contains(mother) || mother == null) {
				MA.put(birthtime, coalescenceQF.size());
			} else {
				coalescenceQF.add(mother);
			}
		}
		return MA;
	}
	
}
