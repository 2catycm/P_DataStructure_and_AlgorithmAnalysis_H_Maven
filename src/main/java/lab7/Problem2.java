package lab7;

import java.util.BitSet;

//#pragma oj Main
public class Problem2 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final var tree = OldEdgeBasedTree.<Void>readUnweightedEdgeBasedTreeFromOJ(in);
//        final var solve = solve(tree);
//        solve.sort(Integer::compare);
//        out.printlnList(solve(tree));

        final var solve = solve(tree);
        for (int i = 0; i < solve.length(); ) {
            final var i1 = solve.nextSetBit(i);
            out.print(i1);out.print(" ");
            i = i1+1;
        }
        out.println();
    }

    private static BitSet solve(OldEdgeBasedTree<Void> tree) {
//        final var leaves = new ArrayList<Integer>();
//        return leaves;
        final var leaves = new BitSet();
        dfs(0, 1,tree, leaves);
        return leaves;
    }

    private static void dfs(int parent, int current, OldEdgeBasedTree<Void> tree, BitSet leaves) {
        final var closedRelatives = tree.vertices[current].closedRelatives;
        if (closedRelatives.size()==1 && closedRelatives.get(0).vertexVIndex==parent)
//            leaves.add(current);
            leaves.set(current);
        for(var relative:closedRelatives){
            if (relative.vertexVIndex==parent)
                continue;
            dfs(current, relative.vertexVIndex, tree, leaves);
        }
    }
}
//#include "OnlineJudgeIO.java"
//#include "OJTree.java"