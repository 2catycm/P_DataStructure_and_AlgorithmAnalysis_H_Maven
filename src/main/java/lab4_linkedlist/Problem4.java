package lab4_linkedlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import lab4_linkedlist.BidirectionalLinkedNodes.BidirectionalNode;
public class Problem4 {
    private static OJReader in = new OJReader();
    public static void main(String[] args) {
        int n = in.nextInt();
        ArrayList<BidirectionalNode<IndexedInteger>> nodes = new ArrayList<>(n);
        BidirectionalNode<IndexedInteger> previous = new BidirectionalNode<>(null, null, null);
        BidirectionalNode<IndexedInteger> newNode;
        for (int i = 0; i < n; i++) {
            newNode = previous.linkAfter(new IndexedInteger(in.nextInt(), i));
            nodes.add(newNode);
            previous = newNode;
        }
        nodes.sort(Comparator.comparingInt(e-> e.value.getData()));
        solve(nodes, previous);
    }
    private static void solveNew(ArrayList<BidirectionalNode<IndexedInteger>> nodes, BidirectionalNode<IndexedInteger> fake_head) {

    }
    private static void solve(ArrayList<BidirectionalNode<IndexedInteger>> nodes, BidirectionalNode<IndexedInteger> fake_head) {
        Integer[] result = new Integer[nodes.size()];
        BidirectionalNode<IndexedInteger> current, next;
        current = nodes.get(0);
        for (int i = 0; i < nodes.size()-1; i++) {
            next = nodes.get(i+1);
            result[current.value.getOrder()] = next.value.getData() - current.value.getData();
            current = next;
        }
        Arrays.stream(result).filter(Objects::nonNull).forEach(System.out::println);
    }
}
//#include "OJReader.java"
//#include "双向链表打包.java"
//#include "IndexedInteger.java"