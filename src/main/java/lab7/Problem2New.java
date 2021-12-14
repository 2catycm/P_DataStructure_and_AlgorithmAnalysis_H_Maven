package lab7;

import java.util.BitSet;

//# pragma OJ Main
public class Problem2New {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final var tree = new VoidUnWeighedEdgeBasedTree(in.nextInt());
        tree.readEdgesFromOJ(in);

        final var solve = solve(tree);
        for (int i = 0; i < solve.length(); ) {
            final var i1 = solve.nextSetBit(i);
            out.print(i1);out.print(" ");
            i = i1+1;
        }
        out.println();
    }

    private static BitSet solve(VoidUnWeighedEdgeBasedTree tree) {
//        final var leaves = new ArrayList<Integer>();
//        return leaves;
        final var leaves = new BitSet();
        dfs(0, 1,tree, leaves);
        return leaves;
    }

    private static void dfs(int parent, int current, VoidUnWeighedEdgeBasedTree tree, BitSet leaves) {
        if(tree.vertices[current].size()==1&& tree.vertices[current].get(0)==parent){
            leaves.set(current);
            return;
        }
        for (var rel : tree.vertices[current])
        {
            if (rel==parent)
                continue;
            dfs(current, rel, tree, leaves);
        }
    }
}
//# include "OnlineJudgeIO.java"
//# include "OJTree.java"