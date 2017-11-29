import java.util.ArrayList;
import java.util.Collections;


public class Population {
	public ArrayList<Sim> population;
	public int capacity;
	
	public Population(int n) {

		population = new ArrayList<Sim>(n);
		
	}
	
	public void insert(Sim newSim) {
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
		Sim leftSim; Sim rightSim; Sim parentSim = population.get(index);
		if (2*index>population.size()) {
			return;
		} else if (2*index==population.size()) { 
			parentSim = population.get(index);
			leftSim = population.get(2*index);
			if (parentSim.getDeathTime() < leftSim.getDeathTime()) {
				Collections.swap(population,index,2*index);
			}
			return;
		} else {
			leftSim = population.get(2*index);
			rightSim  = population.get(2*index+1);
		}
		
		
		if (leftSim.getDeathTime() <= rightSim.getDeathTime() && leftSim.getDeathTime() < parentSim.getDeathTime()) {
			Collections.swap(population, index, 2*index);
			siftDown(2*index);
		} else if (rightSim.getDeathTime() <= leftSim.getDeathTime() && rightSim.getDeathTime() < parentSim.getDeathTime()) {
			Collections.swap(population, index, 2*index+1);
			siftDown(2*index + 1);
		}
		
		return;		
	}
	
	public Sim removeMin() {
		int lastIndex = population.size() - 1;
		Collections.swap(population, lastIndex, 0);
		
		Sim removedSim = population.remove(lastIndex);
		siftDown(0);
		
		return removedSim;
	}
	
	public Sim peakMin() {
		return population.get(0);
	}
	
	public int getSize() {
		return population.size();
	}

}
