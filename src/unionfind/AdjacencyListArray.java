package unionfind;

import java.util.ArrayList;
import java.util.LinkedList;
public class AdjacencyListArray {
	private ArrayList[] adjacencyList;
	
	public AdjacencyListArray(int n) {
		adjacencyList = (ArrayList[]) new ArrayList[n];
		
		for (int i = 0; i<n; i++) {
			adjacencyList[i] = new ArrayList<Integer>();
		}
		
	}
	//Adds an edge between the vertices p and q 
	public void addEdge(int p, int q) {
		adjacencyList[p].add(q);
		adjacencyList[q].add(p);
	}
		
		//Returns true if p and q are adjacent
	public boolean hasEdge(int p, int q) {
		if (adjacencyList[p].contains(q)) {
			return true;
		} else {
			return false;
		}
	}
}
