import java.util.ArrayList;
import java.util.Collections;


public class Population {
	public ArrayList<Sim> population;
	public int size;
	public int capacity;
	
	public Population(int n) {

		population = new ArrayList<Sim>(n);
		
	}
	
	public void insert(Sim newSim) {
		this.size +=1;
		population.add(newSim);
		int index = population.indexOf(newSim);
		siftUp(index);
		
	}
	
	public void siftUp(int index) {
		int parentIndex;
		Sim parent;
		Sim siftSim = population.get(index);
		if (index != 0) {
			parentIndex = (index -  1)/2;
			parent = population.get(parentIndex);
			if (parent.getDeathTime() > siftSim.getDeathTime()) {
				Collections.swap(population, index, parentIndex);
				this.siftUp(parentIndex);
			}
		}
	}
	
	public void siftDown(int index) {
		Sim leftSim = population.get(2*index);
		Sim rightSim  = population.get(2*index+1);
		if (leftSim.getDeathTime() <= rightSim.getDeathTime()) {
			Collections.swap(population, index, 2*index);
			siftDown(2*index);
		} else {
			Collections.swap(population, index, 2*index+1);
			siftDown(2*index + 1);
		}
		
		
		
		
	}
	
	public Sim removeMin() {
		this.size -= 1;
		int lastIndex = population.size() - 1;
		Collections.swap(population, lastIndex, 0);
		
		Sim removedSim = population.remove(lastIndex);
		siftDown(0);
		
		return removedSim;
		
		
	}

}
