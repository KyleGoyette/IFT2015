import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.HashMap;

public class Coalescence {
	private PriorityQueue<Sim> coalescenceQM;
	private PriorityQueue<Sim> coalescenceQF;
	public HashMap<Double, Integer> PA;
	public HashMap<Double,Integer> MA;
	
	
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
	
	public void makePA() {
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
		System.out.println(PA.size());
	}
	 
	public void makeMA() {
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
	}
	
}
