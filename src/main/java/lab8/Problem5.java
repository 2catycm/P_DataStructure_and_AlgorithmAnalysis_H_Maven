package lab8;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Iterator;


//# pragma OJ Main
public class Problem5 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final var ints = in.nextIntArray();
        out.printlnIntArray(solve(ints));
    }

    static int[] solve(int[] ints) {
//        final var result = new int[ints.length];
//        if(ints.length<=2){
//            if (ints.length>=1)
//                result[0] = ints[0];
//            if (ints.length>=2)
//                result[1] = Math.min(ints[0], ints[1]);
//            return result;
//        } //特判是可以消除的。
//        final var iterator = Arrays.stream(ints).iterator();
        final var medianQueue = new MedianQueue<Integer>(ints.length);
        return Arrays.stream(ints).map(anInt -> {
            medianQueue.offer(anInt);
            return medianQueue.peek();
        }).toArray();
    }
}
class MedianQueue <Key extends Comparable<Key>> extends AbstractQueue<Key> {
    private int size = 0;
    private int capacity = 0;
    private PriorityQueue<Key> left;
    private InferiorityQueue<Key> right;
    public MedianQueue(int capacity) {
        this.capacity = capacity;
//        left = new PriorityQueue<Key>(capacity / 2);
//        right = new InferiorityQueue<Key>(capacity / 2); //如果把X和Y，或者记录prevX的话，那么/2就够了，因为只需要偶数个。
        //但是，现在需要把东西倒过去，比如数组长度是1，本来元素存在prevX。现在必须倒进去。
        left = new PriorityQueue<Key>(capacity / 2 +1);
        right = new InferiorityQueue<Key>(capacity / 2 +1); //如果在capacity加一，表示对新的容量做/2，若新的容量是奇数，则不能容纳
    }

    @Override
    public boolean offer(Key key) {
        if (++size<=1) {
            left.offer(key);
            return true;
        }
        if (size<=2){
            left.offer(key);
            right.offer(left.poll()); //把大的倒出来放到右边。
            return true;
        }//前两个不变式建立了。
        //本质上是分类讨论，就是讨论新的key x与左边的顶a和右边的顶b的关系。理想的关系是a<=x<=b， 如果不是这个关系，把放到左边再提回来，可以修复x<a的情况，右边同理。
        left.offer(key);
        right.offer(left.poll());
        final var properX = right.poll();
        //下一步是分奇偶讨论.
        //若加入X后为奇数，X为答案，我们可以暂时倒到左边。
        //若加入X后为偶数，上一步操作把X规范到上一次的X'与b之间，这个时候应该把X倒到右边。
        if (size%2==1)
            left.offer(properX);
        else
            right.offer(properX);
        return false;
    }
    @Override
    public Key peek() {
        return left.peek();
    }
    @Override
    public Key poll() {
        throw new UnsupportedOperationException("Polling is not allowed.");
    }


    @Override
    public Iterator<Key> iterator() {
        throw new UnsupportedOperationException("iterating is not allowed.");
    }

    @Override
    public int size() {
        return size;
    }
}
//# include "优先队列.java"
//# include "OnlineJudgeIO.java"