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
        // init de structures de données
        this.UF = new UnionFind(n);
        this.adjacencyList = new AdjacencyList(n);
        this.getCount = 0;
        this.setCount = 0;
    }
    
    private final UnionFind UF;
    private final Random RND = new Random();
    private AdjacencyList adjacencyList;
    private static long getCount;
    private static long setCount;
    
    boolean hasEdge(int x, int y)
    {
        if (adjacencyList.hasEdge(x, y)) {
        	return true;
        } else {
        	return false;
        }
        
    }
    
    int addEdge(int x, int y)
    {
    	adjacencyList.addEdge(x, y);
        return UF.union(x, y);
    }
    
    /**
     * Ajoute des arêtes jusqu'à ce que le graphe devient connexe.
     * 
     * @param n
     * @return 
     */
    long getTransitionGiantComponent(int n)
    {
        UnionFind UF = new UnionFind(n);
        int maxt = 1; // taille max.
        long N = 0L;
        while (maxt<n)
        {
            int x = RND.nextInt(n);
            int y = RND.nextInt(n);
            if (x != y && ! hasEdge(x, y))
            {
            	
                int s = addEdge(x,y);
                N++;
                if (s>maxt) maxt=s;
                
            }
        }
        this.getCount = UF.getCountGet();
        this.setCount = UF.getCountSet();
        UF.setCountGet();
        UF.setCountSet();
        return N;
    }
    
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);
        int rep = Integer.parseInt(args[1]);
        
        for (int r=0; r<rep; r++)
        {
        	
            ERPhaseTransition PT = new ERPhaseTransition(n);
            
            long N = PT.getTransitionGiantComponent(n);
            
            System.out.println(n+"\t"+N+"\t"+getCount+"\t"+setCount + "\t" + (getCount+setCount));
        }
    }
    
}