package lab4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntFunction;

public class Problem7 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final int T = in.nextInt();
        int n, k;
        for (int i = 0; i < T; i++) {
            n = in.nextInt();
            k = in.nextInt();
//            out.println(bruteForceSolve(in.nextIntArray(n), k));
//            out.println(solve(in.nextIntArray(n), k));
            out.println(newSolve(in.nextIntArray(n), k));
        }
    }
//    static int newNewSolve(int[] array, int k) {
//
//    }
    static int newSolve(int[] array, int k) {
        long sum = 0;
        BidirectionalLinkedNodes<IndexedInteger> nodes = new BidirectionalLinkedNodes<>();
        //bug3 strategy
        nodes.getLeftMargin().value = new IndexedInteger(0, 0);
        nodes.getRightMargin().value = new IndexedInteger(array.length+1, array.length+1);
        //数组序回操作
        AtomicInteger i = new AtomicInteger(1);
        Arrays.stream(array).forEachOrdered(e -> nodes.pushAfter(new IndexedInteger(e, i.getAndIncrement())));
        BidirectionalLinkedNodes.BidirectionalNode<IndexedInteger>[] linkedArray = new BidirectionalLinkedNodes.BidirectionalNode[array.length];
        for (var it = nodes.getFirst(); it != nodes.getRightMargin(); it = it.next) {
            linkedArray[it.value.getData() - 1] = it; //不是根据原本的order排序，而是根据data
        }
        for (int x = 1; x <= linkedArray.length - k + 1; x++) {
            final var kthLargestNode = linkedArray[x - 1]; //Final是不变的，不是迭代器，因为第k大的节点不变。

            //窗口左节点就位，同时记录下初始窗口的最大值
            var leftWindowEnd = kthLargestNode;
            final PriorityQueue<Integer> window = new PriorityQueue<>(Comparator.comparingInt(new ToIntFunction<Integer>() {
                @Override
                public int applyAsInt(Integer value) {
                    return value;
                }
            }).reversed());
            int leftStepCount = 0;
            for (; leftStepCount < k-1&&leftWindowEnd!=nodes.getFirst(); leftStepCount++, leftWindowEnd = leftWindowEnd.previous){
                window.add(leftWindowEnd.value.getData());
            }
            window.add(leftWindowEnd.value.getData());
            //窗口右节点也要就位。根据左窗口的移动情况
            var rightWindowEnd = kthLargestNode;
            int rightStepCount = 0;
            for (;rightStepCount<k-1-leftStepCount&&rightWindowEnd!=nodes.getLast();rightStepCount++, rightWindowEnd = rightWindowEnd.next){
                window.add(rightWindowEnd.value.getData());
            }
            window.add(rightWindowEnd.value.getData());


            //开始滑动窗口，对每一窗口计算贡献。
            for (/*var rightWindowEnd = kthLargestNode*/;leftWindowEnd!=kthLargestNode.next&&rightWindowEnd!=nodes.getRightMargin();window.remove(leftWindowEnd.value.getData()),leftWindowEnd = leftWindowEnd.next, rightWindowEnd = rightWindowEnd.next, window.add(rightWindowEnd.value.getData())){
//                int maxValue = Math.max(maxValuesForThisNode[leftWindowEnd.value.getOrder()-1], maxValuesForThisNode[rightWindowEnd.value.getOrder()-1]);
                int maxValue = window.peek();
                int single_contribution  = (int) (((long)maxValue*x)%moder);
                //参差荇菜，左右芼之。
                int leftDeletedCount = 0;
                int rightDeletedCount = 0;
//                if (leftWindowEnd.previous!=nodes.getLeftMargin()){
                if (leftWindowEnd.previous!=null){ //bug3 fixing
                    leftDeletedCount = leftWindowEnd.value.getOrder() - leftWindowEnd.previous.value.getOrder() - 1;//bug5 -1
                }
                if (rightWindowEnd.next!=null){   //bug3 fixing
                    rightDeletedCount = rightWindowEnd.next.value.getOrder() - rightWindowEnd.value.getOrder() - 1;
                }
                int deletedCount = (int) ((1+leftDeletedCount+rightDeletedCount+((long)leftDeletedCount*rightDeletedCount)%moder)%moder);
                sum+= ((long) single_contribution *(deletedCount))%moder;
                sum %=moder;
            }
            //窗口贡献计算结束，收尾：删除本节点
            kthLargestNode.selfRemove(nodes);
        }
        return (int)sum;
    }

        //bug1: 滑动窗口不能直愣愣的往左边走，应该记录下往左边走了多少步，然后右边也要走。
    //bug2：窗口最大值初始化有问题，到达相应的位置之后，无法做到最大值比较
    //bug3: 窗口的左边是假头假尾也要算！
    //bug4: 最大值的维护: 不能保存第二大的值，是错误的。需要优先队列。这题可以记录左右的最大值
    //bug5: 左右流之的时候，可以左右一起流之
    static int solve(int[] array, int k) {
        //array本身就是原本的序号，而排好序的就是升序的1-n
//        BidirectionalLinkedNodes<Integer> nodes = new BidirectionalLinkedNodes<>();
//        for (int i = 0; i < array.length; i++) {
//            nodes.pushAfter(i+1);
//        }
//        final BidirectionalLinkedNodes.BidirectionalNode<Integer>[] linkedArray = new BidirectionalLinkedNodes.BidirectionalNode[array.length];
//        for (BidirectionalLinkedNodes.BidirectionalNode<Integer> currentNode = nodes.getFirst(); currentNode.value!=null; currentNode = currentNode.next){
//            linkedArray[currentNode.value-1] =  currentNode;
//        }
        int sum = 0;
        BidirectionalLinkedNodes<IndexedInteger> nodes = new BidirectionalLinkedNodes<>();
        //bug3 strategy
        nodes.getLeftMargin().value = new IndexedInteger(0, 0);
        nodes.getRightMargin().value = new IndexedInteger(array.length+1, array.length+1);
        //数组序回操作
        AtomicInteger i = new AtomicInteger(1);
        Arrays.stream(array).forEachOrdered(e -> nodes.pushAfter(new IndexedInteger(e, i.getAndIncrement())));
        BidirectionalLinkedNodes.BidirectionalNode<IndexedInteger>[] linkedArray = new BidirectionalLinkedNodes.BidirectionalNode[array.length];
        for (var it = nodes.getFirst(); it != nodes.getRightMargin(); it = it.next) {
//            linkedArray[it.value.getOrder() - 1] = it;
            linkedArray[it.value.getData() - 1] = it; //不是根据原本的order排序，而是根据data
        }
        for (int x = 1; x <= linkedArray.length - k + 1; x++) {
            final var kthLargestNode = linkedArray[x - 1]; //Final是不变的，不是迭代器，因为第k大的节点不变。
//            for (var leftWindowEnd = kthLargestNode.retreatNoChecking())
            //窗口左节点就位，同时记录下初始窗口的最小值
            var leftWindowEnd = kthLargestNode;

            int[] maxValuesForThisNode = scan(kthLargestNode, nodes, array.length);
//            int maxValue = Integer.MIN_VALUE;
//            int maxValue = x;
            int leftStepCount = 0;
            for (; leftStepCount < k-1&&leftWindowEnd!=nodes.getFirst(); leftStepCount++, leftWindowEnd = leftWindowEnd.previous) ;
//            for (; leftStepCount < k-1&&leftWindowEnd!=nodes.getFirst(); leftStepCount++, leftWindowEnd = leftWindowEnd.previous) {
//            maxValue = Math.max(maxValue, leftWindowEnd.value.getData()); //最后一次没有执行！
//            for (; leftStepCount < k&&leftWindowEnd!=nodes.getLeftMargin(); leftStepCount++, leftWindowEnd = leftWindowEnd.previous) {
////                maxValue = Math.max(maxValue, leftWindowEnd.value.getData());
////                maxValuesForThisNode[leftWindowEnd.value.getOrder()-1] = maxValue;//bug 4 fix
//            }
            //窗口右节点也要就位。根据左窗口的移动情况
//            maxValue = Integer.MIN_VALUE; //右边的最大值情况与左边不能一起定义。
            var rightWindowEnd = kthLargestNode;
            int rightStepCount = 0;
            for (;rightStepCount<k-1-leftStepCount&&rightWindowEnd!=nodes.getLast();rightStepCount++, rightWindowEnd = rightWindowEnd.next) ;
//            for (;rightStepCount<k-1-leftStepCount&&rightWindowEnd!=nodes.getLast();rightStepCount++, rightWindowEnd = rightWindowEnd.next)
//            maxValue = Math.max(maxValue, rightWindowEnd.value.getData());//补上最后一次。
//            for (;rightStepCount<k-1-leftStepCount&&rightWindowEnd!=nodes.getRightMargin();rightStepCount++, rightWindowEnd = rightWindowEnd.next) {
//                maxValue = Math.max(maxValue, rightWindowEnd.value.getData());
//                maxValuesForThisNode[rightWindowEnd.value.getOrder()-1] = maxValue;//bug 4 fix
//            }
//            if (leftStepCount+rightStepCount!=k-1) throw new RuntimeException("窗口初始定位算法有问题"); //assertion
            //开始滑动窗口，对每一窗口计算贡献。
            for (/*var rightWindowEnd = kthLargestNode*/;leftWindowEnd!=kthLargestNode.next&&rightWindowEnd!=nodes.getRightMargin();leftWindowEnd = leftWindowEnd.next, rightWindowEnd = rightWindowEnd.next){
                //更新最大值,第一次可以不更新，但是写在循环尾可能导致null pointer exception
                //不可以不更新，因为上面有个leftWindowEnd!=first，如果一开始就是first，就会导致maxValue没有经过赋值
//                if (leftWindowEnd!=nodes.getFirst())
//                    maxValue = Math.min(maxValue, leftWindowEnd.previous.value.getData()); //bug4 wrong fix
//                maxValue = Math.max(maxValue, rightWindowEnd.value.getData());
                int maxValue = Math.max(maxValuesForThisNode[leftWindowEnd.value.getOrder()-1], maxValuesForThisNode[rightWindowEnd.value.getOrder()-1]);
                int single_contribution  = (maxValue*x)%moder;
                //参差荇菜，左右芼之。
                int leftDeletedCount = 0;
                int rightDeletedCount = 0;
//                if (leftWindowEnd.previous!=nodes.getLeftMargin()){
                if (leftWindowEnd.previous!=null){ //bug3 fixing
                    leftDeletedCount = leftWindowEnd.value.getOrder() - leftWindowEnd.previous.value.getOrder() - 1;//bug5 -1
//                    sum+= (single_contribution*deletedCount)%moder;
//                    sum %=moder;
                }
//                if (rightWindowEnd.next!=nodes.getRightMargin()){
                if (rightWindowEnd.next!=null){   //bug3 fixing
                    rightDeletedCount = rightWindowEnd.next.value.getOrder() - rightWindowEnd.value.getOrder() - 1;
//                    sum+= (single_contribution*deletedCount)%moder;
//                    sum %=moder;
                }
                int deletedCount = (1+leftDeletedCount+rightDeletedCount+(leftDeletedCount*rightDeletedCount)%moder)%moder;
                sum+= (single_contribution*(deletedCount))%moder;
                sum %=moder;
            }
            //窗口贡献计算结束，收尾：删除本节点
            kthLargestNode.selfRemove(nodes);
        }
        return sum;
    }
    //bug5 删除节点之后，定位不准确
//    @Deprecated //O(n) is slow
//    private static int[] scan(BidirectionalLinkedNodes.BidirectionalNode<IndexedInteger> kthLargestNode, BidirectionalLinkedNodes<IndexedInteger> nodes, int n) {
////        int[] maxValuesForThisNode = new int[nodes.size()];
//        int[] maxValuesForThisNode = new int[n]; //不管删除了几个节点，都把最大值附上去，如果跳过了节点，就还是0
//        int maxValue = Integer.MIN_VALUE;
//        for (var leftIt = kthLargestNode; leftIt!=nodes.getLeftMargin(); leftIt = leftIt.previous){
//            maxValue = Math.max(maxValue, leftIt.value.getData());
//            maxValuesForThisNode[leftIt.value.getOrder()-1] = maxValue;//bug 4 fix
//        }
//        maxValue = Integer.MIN_VALUE;
//        for (var rightIt = kthLargestNode; rightIt!=nodes.getRightMargin(); rightIt = rightIt.next){
//            maxValue = Math.max(maxValue, rightIt.value.getData());
//            maxValuesForThisNode[rightIt.value.getOrder()-1] = maxValue;//bug 4 fix
//        }
//        return maxValuesForThisNode;
//    }
    //O(k)
    private static int[] scan(BidirectionalLinkedNodes.BidirectionalNode<IndexedInteger> kthLargestNode, BidirectionalLinkedNodes<IndexedInteger> nodes, int n) {
//        int[] maxValuesForThisNode = new int[nodes.size()];
        int[] maxValuesForThisNode = new int[n]; //不管删除了几个节点，都把最大值附上去，如果跳过了节点，就还是0
        int maxValue = Integer.MIN_VALUE;
        for (var leftIt = kthLargestNode; leftIt!=nodes.getLeftMargin(); leftIt = leftIt.previous){
            maxValue = Math.max(maxValue, leftIt.value.getData());
            maxValuesForThisNode[leftIt.value.getOrder()-1] = maxValue;//bug 4 fix
        }
        maxValue = Integer.MIN_VALUE;
        for (var rightIt = kthLargestNode; rightIt!=nodes.getRightMargin(); rightIt = rightIt.next){
            maxValue = Math.max(maxValue, rightIt.value.getData());
            maxValuesForThisNode[rightIt.value.getOrder()-1] = maxValue;//bug 4 fix
        }
        return maxValuesForThisNode;
    }

    static final int moder = 1000_000_007;

    static int bruteForceSolve(int[] array, int k) {
        int sum = 0;
        for (int l = 0; l < array.length; l++) {
            for (int r = l; r < array.length; r++) {
                if (r - l + 1 < k)
                    continue;
                final int[] subarray = new int[r - l + 1];
                System.arraycopy(array, l, subarray, 0, r - l + 1);
                Arrays.sort(subarray);
                sum += (subarray[r - l - k + 1] * subarray[r - l]) % moder;
                sum = sum % moder;
            }
        }
        return sum;
    }
}
//class Window<T>{//a view of the array
//    private T[] array;
//
//}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "双向链表打包.java"
//#include "IndexedInteger.java"