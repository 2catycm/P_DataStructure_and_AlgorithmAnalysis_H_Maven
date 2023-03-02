package lab4_linkedlist;

import java.util.Arrays;

public class Problem4New {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        IndexedInteger[] weights = IndexedInteger.nextIndexedIntegerArray(in);
        // 比如获得了 2,3,1,5,4
        Arrays.sort(weights);
        // 获得了 1,2,3,4,5. 携带 2,0,1, 4, 3 代表原下标
        BidirectionalLinkedNodes<IndexedInteger> nodes = new BidirectionalLinkedNodes<>();
        for (int i = 0; i < weights.length; i++) {
            nodes.pushAfter(weights[i]);
        }//转换为链表nodes，按照大小顺序链接。

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
        //遍历nodes，获得了linkedArray, 按照原本的下标顺序排列的一个数组，每个数组元素有值和下标（下标没用了）。 数组元素是链表节点，所以链接着前面大小和后面大小的。

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
            //这一步至关重要，改变了复杂度！
            //删除了小的节点，那么左右必定是大的节点！

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