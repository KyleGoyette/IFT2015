/**
 * 
 */
package unionfind;

/**
 * @author kyle
 *
 */
public interface UnionFind {
    void setCountGet();
    void setCountSet();
    long getCountGet();
    long getCountSet();
    public int union(int p, int q);
    public int find(int x);
    public void printObject();
    public boolean hasEdge(int x, int y);
 }
