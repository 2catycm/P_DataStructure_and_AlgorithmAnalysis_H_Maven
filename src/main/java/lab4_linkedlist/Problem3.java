package lab4_linkedlist;

import lab4_linkedlist.BidirectionalLinkedNodes.BidirectionalNode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Problem3 {
    private static OJReader in = new OJReader();
    public static void main(String[] args) {
        int n = in.nextInt();
        int p = in.nextInt();
        int q = in.nextInt();
        var nodes = in.nextIntArray(n);
        for (int i = 0; i < p; i++) {
            union(nodes, in.nextInt(), in.nextInt());
            find(q);
        }
    }
//    private static BidirectionalLinkedNodes<BidirectionalLinkedNodes<Integer>> relationships = new BidirectionalLinkedNodes<>();
    private static void find(int q) {

    }

    private static void union(int[] nodes, int a, int b) {
        final int max = Math.max(a, b);
        final int min = Math.min(a, b);
        final List<BidirectionalNode<Integer>> collect = Arrays.stream(nodes).mapToObj(e -> new BidirectionalNode<Integer>(e, null, null)).collect(Collectors.toList());
//        collect.get(min).linkAfter(collect.get(max));
    }
}
//#include "OJReader.java"
//#include "双向链表打包.java"