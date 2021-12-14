package lab8;

import java.lang.reflect.Array;
import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntConsumer;

abstract class AbstractPriorityQueue<Key extends Comparable<Key>> extends AbstractQueue<Key> {
    protected Comparable<Key>[] heap;//存在1-size当中，0不使用。
    protected int size = 0;
    protected int capacity;//表示可以存储至多capacity个元素，所以数组length比capacity多1.
    protected static final int DEFAULT_INITIAL_CAPACITY = 11;
    protected AbstractPriorityQueue(Key[] arrayStartingFromZero) {
        super();
        this.capacity = arrayStartingFromZero.length;
        this.size = arrayStartingFromZero.length;
        this.heap = /*(Key[])*/ new Comparable[capacity+1];
        System.arraycopy(arrayStartingFromZero, 0, heap, 1, capacity);
//        for (int i = 0; i < capacity; i++) {
//            heap[i+1] = arrayStartingFromZero[i];
//        }
        for (int i = size; i >= 1; i--) {
            rootFix(i);
        }
    }

    protected void rootFix(int subTreeRoot){
        int biggerChild = subTreeRoot<<1;
        if (biggerChild>size) //也就是subTreeRoot就是叶子节点之一。
            return;
        if (biggerChild<size && lessThan((Key)heap[biggerChild], (Key)heap[biggerChild+1]))
            biggerChild++;
        if (lessThan(subTreeRoot, biggerChild)) {
            heapExchange(subTreeRoot, biggerChild);
//            if (problem2Counter!=null)
//                problem2Counter.accept(1);
            rootFix(biggerChild/*, problem2Counter*/);
        }
//        else
//            return;
    }

    public AbstractPriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY);
    }
    public AbstractPriorityQueue(int capacity) {
        super();
        this.capacity = capacity;
        heap = (Key[]) new Comparable[capacity+1];
    }
    //实现offer就行了，add在queue里面帮忙写了调用offer。
    @Override
    public boolean offer(Key newKey) {
        return offer(newKey, null);
    }
    public boolean offer(Key newKey, IntConsumer problemACounter) {
        if (++size>capacity)
            expand();
        heap[size] = newKey;
        swim(/*problemACounter*/);
        return true;
    }
    @Override
    public Key poll() {
        final var max = heap[1];
        heapExchange(1, size);
        heap[size] = null;
        size--;
        sink();
        return (Key)max;
    }

    @Override
    public Key peek() {
        return (Key)heap[1];
    }

    private void sink() {
        for (int newParent = 1, child = newParent<<1; child<=size; newParent = child, child<<=1){
            if (child<size && lessThan(child, child+1))
                child++;//默认是左子。如果右子存在（最后一层不完全），而且比左子大，就转移。
            //现在，child是更大的合法的孩子。
            if (lessThan(child, newParent))
                break;
            heapExchange(newParent, child);
        }
    }
    private void swim(/*IntConsumer problemACounter*/) {
        for (int newChild = size, parent = newChild>>1; newChild>1 && lessThan(parent, newChild);newChild = parent, parent>>=1) {
            heapExchange(parent, newChild);
            /*if (problemACounter!=null)
                problemACounter.accept(1);*/
        }
    }

    public abstract boolean lessThan(Key assertSmaller, Key assertLarger);
    public boolean lessThan(int assertSmaller, int assertLarger){
        return lessThan((Key)heap[assertSmaller], (Key)heap[assertLarger]);
    }
    public void heapExchange(int i, int j){
        final var hI = heap[i];
        heap[i] = heap[j];
        heap[j] = hI;
    }
    private void expand() {
        final var newHeap = /*(Key[])*/ new Comparable[capacity << 2];
        System.arraycopy(heap, 0, newHeap, 0, capacity+1);
        heap = newHeap;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }
    @Override
    public Iterator<Key> iterator() {
        return new Iterator<Key>() {
            @Override
            public boolean hasNext() {
                return !isEmpty();
            }

            @Override
            public Key next() {
                return poll();
            }
        };
    }

    @Override
    public String toString() {
        return "AbstractPriorityQueue{" +
                "heap=" + Arrays.toString(heap) +
                ", size=" + size +
                ", capacity=" + capacity +
                '}';
    }
//    public static void main(String[] args) {
////        java.util.PriorityQueue
//    }
}
class PriorityQueue<Key extends Comparable<Key>> extends AbstractPriorityQueue<Key>{
    public PriorityQueue(Key[] arrayStartingFromZero) {
        super(arrayStartingFromZero);
    }

    public PriorityQueue() {
    }

    public PriorityQueue(int capacity) {
        super(capacity);
    }

    @Override
    public boolean lessThan(Key assertSmaller, Key assertLarger) {
        return assertSmaller.compareTo(assertLarger)<0;
    }
}
class InferiorityQueue<Key extends Comparable<Key>> extends AbstractPriorityQueue<Key>{
    public InferiorityQueue(Key[] arrayStartingFromZero) {
        super(arrayStartingFromZero);
    }

    public InferiorityQueue() {
    }

    public InferiorityQueue(int capacity) {
        super(capacity);
    }

    @Override
    public boolean lessThan(Key assertSmaller, Key assertLarger) {
        return assertSmaller.compareTo(assertLarger)>0;
    }
}