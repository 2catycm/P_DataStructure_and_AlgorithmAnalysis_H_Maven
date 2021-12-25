package lab10;

import java.util.BitSet;
import java.util.Stack;

class StronglyConnectedComponents {
    private BitSet isVisited;
    private int[] id; // identifier for every vertices.
    private int count; // number of components, also the next index of it.
    private DirectedGraph digraph;
    private DirectedGraph reverse;
    public StronglyConnectedComponents(DirectedGraph digraph, DirectedGraph reverse) {
        this.digraph = digraph;
        this.reverse = reverse;
        isVisited = new BitSet();
        id = new int[digraph.verticesCnt+1];
        final var order = new DepthFirstOrder(reverse).getReversePost();
        while (!order.empty()){
            final var pop = order.pop();
            if (isVisited.get(pop))
                continue; //已经归类为某个连通分量了。
            dfs(pop);
            count++;
        }
    }
    private void dfs(int current) {
        isVisited.set(current);
        id[current] = count; //将这个顶点归类为当前count的分量。
        for (var relative:digraph.relativesOf(current))
            if (!isVisited.get(relative))
                dfs(relative);
    }
    public boolean isStronglyConnected(){
        return count==1;
    }
}
class DepthFirstOrder{
    private BitSet isVisited;
    private Stack<Integer> reversePost;
    private DirectedGraph digraph;
    public DepthFirstOrder(DirectedGraph digraph) {
        this.digraph = digraph;
        isVisited = new BitSet();
        reversePost = new Stack<>();
        for (int i = 1; i <= digraph.verticesCnt; i++) {
            if (!isVisited.get(i))
                dfs(i);
        }
    }

    private void dfs(int current) {
        isVisited.set(current);
        for (var relative:digraph.relativesOf(current))
            if (!isVisited.get(relative))
                dfs(relative);
        //所有邻接节点访问结束了。
        reversePost.push(current);
    }

    public Stack<Integer> getReversePost() {
        return reversePost;
    }
}