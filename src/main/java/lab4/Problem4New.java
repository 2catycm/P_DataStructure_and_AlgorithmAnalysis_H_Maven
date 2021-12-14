package lab4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Problem4New {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        IndexedInteger[] weights = IndexedInteger.nextIndexedIntegerArray(in);
        Arrays.sort(weights);
        BidirectionalLinkedNodes<IndexedInteger> nodes = new BidirectionalLinkedNodes<>();
        for (int i = 0; i < weights.length; i++) {
            nodes.pushAfter(weights[i]);
        }
//        final ArrayList<BidirectionalNode<IndexedInteger>> linkedArray = new ArrayList<>(weights.length); //按照原来的顺序的数组，但是有链接提示每个节点它的大小关系的前后在哪里
//
//        for (BidirectionalNode<IndexedInteger> currentNode = nodes.getFirst();currentNode!=null; currentNode = currentNode.next){
//            linkedArray.set(currentNode.value.getOrder(), currentNode);
//        }

//        final BidirectionalNode<IndexedInteger>[] bidirectionalNodes = new BidirectionalNode<IndexedInteger>[weights.length];

        final BidirectionalLinkedNodes.BidirectionalNode<IndexedInteger>[] linkedArray = new BidirectionalLinkedNodes.BidirectionalNode[weights.length];
        for (BidirectionalLinkedNodes.BidirectionalNode<IndexedInteger> currentNode = nodes.getFirst(); currentNode.value!=null; currentNode = currentNode.next){
            linkedArray[currentNode.value.getOrder()] =  currentNode;
        }

//        for (var it = linkedArray(); it.hasNext();) {
        for (int i = 0; i < linkedArray.length-1; i++) {
//            final BidirectionalNode<IndexedInteger> currentWeight = /*it.next();*/
            final BidirectionalLinkedNodes.BidirectionalNode<IndexedInteger> currentWeight = linkedArray[i];
            int minDifference = Integer.MAX_VALUE;
            if (currentWeight.previous.value!=null){
                minDifference = Math.min(minDifference, Math.abs(currentWeight.previous.value.getData()-currentWeight.value.getData()));
            }
            if (currentWeight.next.value!=null){
                minDifference = Math.min(minDifference, Math.abs(currentWeight.next.value.getData()-currentWeight.value.getData()));
            }
            currentWeight.selfRemove(nodes);
//            System.out.println(minDifference+" ");
            out.print(minDifference+" ");
        }
        out.println();
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "双向链表打包.java"
//#include "IndexedInteger.java"