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

/**
 *
 * @author Kyle Goyette and Simon Pilon Besozzi
 */
public class UnionFindTree implements UnionFind 
{
    private int[] parent;
    private int[] size;
    private int[] rank;
    private static long countGet;
    private static long countSet;
    
    //These booleans are to toggle the different union-find methods and the operations count
    private static final boolean DEBUG_COUNT_OPERATIONS = true;
    private static final boolean PATH_COMPRESSION = false;
    private static final boolean PATH_COMPRESSION_HALF = false;
    private static final boolean LINK_BY_RANK = true;

    //@param n (number of vertices)
    public UnionFindTree(int n)
    {
        this.parent = new int[n];
        this.size = new int[n];
        this.rank = new int[n];
        for (int i=0; i<n; i++)
        {
            parent[i] = i;
            size[i]=1;
            if (LINK_BY_RANK) {
            	rank[i] = 1;
            }
        }  
    }

    //Set resets the operations count
    //Get returns the operations count
    public void setCountGet(){ countGet = 0;}
    public void setCountSet(){ countSet = 0;}
    public long getCountGet(){ return countGet;}
    public long getCountSet(){ return countSet;}
    
    /**
     * Union avec taille du résultat retourné.
     * 
     * @param x
     * @param y
     * @return taille après fusion
     */
    public int union(int x, int y)
    {
        int p = find(x);
        int q = find(y);
        if (p!=q)
        {
            int s = size[p]+size[q];
            size[p] = s;
            size[q] = s;
            if (LINK_BY_RANK) {
            	join(p,q);
            } else {
            	setParent(p,q);
            }
        }
        return size[p];
    }
    
    
    //Finds the representative element of the set
    public int find(int x)
    {
        if (PATH_COMPRESSION_HALF) {
        	while (x != getParent(x)) {
            	setParent(x,getParent(getParent(x)));
            	x = getParent(x);
        	}
        } else if (PATH_COMPRESSION) {
        	int y = getParent(x);
        	if (x != y) {
        		setParent(x,find(y));
        		return getParent(x);
        	}
        } else {
        	while (getParent(x) != x) {
        		x = getParent(x);
        	}
        }
        return x;
    }
    
      /**
     * This secondary hasEdge method is used for bigger graphs, where using an 
     * adjacency list would make the program too slow (for 2^24+ vertices)
     * @param x
     * @param y
     * @return true if x and y have an edge between them
     */
    public boolean hasEdge(int x, int y) {
    
    	if(parent[x]==y || parent[y]==x) {
    		return true;
    	}
    	
    	return false;
    }
    
    //Returns the parent of x
    public int getParent(int x) {
    	if (DEBUG_COUNT_OPERATIONS) countGet++;
    	return parent[x];
    }
    
    //Sets the parent of x
    public void setParent(int x,int val) {
    	if (DEBUG_COUNT_OPERATIONS) countSet++;
    	parent[x] = val;
    }
    
    public void join(int x,int y) {
    	if ( rank[y] < rank[x] ) {
    		int temp = x;
    		x=y;
    		y=temp;
    	} else if (rank[x] == rank[y]) {
    		rank[y] += 1;
    	}
    	setParent(x,y);
    }
    
    public void printObject() {
    	System.out.print("[");
    	for (int i=0;i<parent.length;i++) {
    		System.out.print(parent[i]);
    		if (i <parent.length - 1) {
    			System.out.print(", ");
    		}
    	}
    	System.out.println("]");
    }
}
