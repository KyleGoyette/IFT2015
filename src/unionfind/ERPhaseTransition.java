/*
 * Copyright (c) 2017, Mikl&oacute;s Cs&#369;r&ouml;s
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package unionfind;

import java.util.Random;

/**
 * 
 * Appel à la ligne de commande: 
 * <code>java unionfind.ERPhaseTransition n rep</code>
 * 
 * @author Mikl&oacute;s Cs&#369;r&ouml;s
 */

public class ERPhaseTransition 
{
    private ERPhaseTransition(int n)
    {
        //Union-Find Structure
        this.UF = new UnionFindNaive(n);
        this.adjacencyList = new AdjacencyList(n);
        this.getCount = 0;
        this.setCount = 0;
    }
    
    private final UnionFindNaive UF;
    private final Random RND = new Random();
    private AdjacencyList adjacencyList;
    private static long getCount;
    private static long setCount;
    private final boolean useAdjacencyList = false;
    
    //Returns true if the vertices x and y are connected
    boolean hasEdge(int x, int y)
    {
    	if (useAdjacencyList) {
    		if (adjacencyList.hasEdge(x, y)) {
    			return true;
    		} else {
    			return false;
    		}
    	} else {
    		return UF.hasEdge(x, y);
    	}
        
    }
    
    /**
     * Adds an edge between vertex x and vertex y
     * @return subset size
     */
    int addEdge(int x, int y)
    {
    	if (useAdjacencyList) {
    		adjacencyList.addEdge(x, y);
    	}
        return UF.union(x, y);
    }
    
    /**
     * Adds edges until the graph is connected
     * 
     * @param n
     * @return 
     */
    long getTransitionGiantComponent(int n)
    {
        //The maximum size of the subsets
        int maxt = 1;
        
        //Before the procedure, the graph has no edges
        long N = 0L;
        
        //Graph is connected when maxt == n
        while (maxt<n)
        {
            //Randomly chosen vertex x
        	int x = RND.nextInt(n);
        	//Randomly chosen vertex y
            int y = RND.nextInt(n);
            
            /*
             * If x and y are already connected, continue
             * else add an edge between x and y and increment edge count
             * If the size of the subset of x and y is greater than maxt, update maxt
             */
            if (x != y && ! hasEdge(x, y))
            {
            	
                int s = addEdge(x,y);
                N++;
                if (s>maxt) maxt=s;
                
            }
        }
        
        /*
         * Get operations counts from the union-find object then reset it
         * Return the new edge count
         */
        ERPhaseTransition.getCount = UF.getCountGet();
        ERPhaseTransition.setCount = UF.getCountSet();
        UF.setCountGet();
        UF.setCountSet();
        return N;
    }
    
    public static void main(String[] args)
    {
        //The random graph contains n vertices.
    	int n = Integer.parseInt(args[0]);  
    	//The amount of connected graphs to generate
    	int rep = Integer.parseInt(args[1]);
        
        //Generate random graphs rep times
    	for (int r=0; r<rep; r++)
        {
        	
    		//Adds edges to a new graph until it's connected
            ERPhaseTransition PT = new ERPhaseTransition(n);
            
            //N denotes the amount of edges randomly generated until the graph is connected
            long N = PT.getTransitionGiantComponent(n);
            
            /*
             * Outputs the number of vertices, edges, the amount of get operations, set operations,
             * total operations and amortized cost
             */
            System.out.println(n+"\t"+N+"\t"+getCount+"\t"+setCount + "\t" + (getCount+setCount) + "\t" +
             (getCount+setCount)/N);
        }
    }
    
}