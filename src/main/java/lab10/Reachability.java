package lab10;

import java.util.BitSet;
import java.util.Collection;
import java.util.Set;

class Reachability {
    private BitSet isVisited;
    private DirectedGraph digraph;
//    private Collection<Integer> sources;//有contains方法
    private Iterable<Integer> sources;
    public int getUnreachableCnt(){
        return digraph.verticesCnt - isVisited.cardinality();
    }
    public boolean isReachable(){
        return isVisited.cardinality()== digraph.verticesCnt;
    }
    public Reachability(DirectedGraph digraph, Collection<Integer> sources) {
        this.digraph = digraph;
        this.sources = sources;
        isVisited = new BitSet();
        for (var s:sources){
            if (!isVisited.get(s))
                dfs(s);
        }
    }

    private void dfs(int s) {
        isVisited.set(s);
        for (var relative:digraph.relativesOf(s))
            if (!isVisited.get(relative))
                dfs(relative);
    }
}
