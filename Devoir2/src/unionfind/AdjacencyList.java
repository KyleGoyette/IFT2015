package unionfind;

import java.util.LinkedList;

public class AdjacencyList {

	//Instanciates the adjacency list, an array of LinkedLists
	private final LinkedList[] adjacencyList;
	
	public AdjacencyList(int n) {
		adjacencyList = (LinkedList[]) new LinkedList[n];
		
		for (int i = 0; i<n; i++) {
			adjacencyList[i] = new LinkedList<>();
		}
	}
	
	//Adds an edge between the vertices p and q
	public void addEdge(int p, int q) {
		adjacencyList[p].add(q);
		adjacencyList[q].add(p);
	}
	
	//Returns true if p and q are adjacents
	public boolean hasEdge(int p, int q) {
		if (adjacencyList[p].contains(q)) {
			return true;
		} else {
			return false;
		}
	}
}
