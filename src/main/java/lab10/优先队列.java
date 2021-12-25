package lab10;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Iterator;
import java.lang.reflect.Array;
import java.util.Objects;

interface IndexQueue<Key extends Comparable<Key>> {
    boolean offer(int recordID, Key key);

    Key peek();//front

    Key poll();

    int peekIndex();

    int pollIndex();

    boolean updateKey(int recordID, Key newKey);

    Key keyOf(int recordID);

    boolean contains(int recordID);

    boolean isEmpty();

    int size();
}

abstract class AbstractIndexPriorityQueue<Key extends Comparable<Key>> implements IndexQueue<Key> {
    protected int[] heap; // binary tree index（primary key） -> record id
    protected int[] inverseHeap; // record id -> binary tree index（primary key）
    protected Key[] keys; // record id -> record key value（Comparable key）
    protected int capacity, size; //capacity is not actual size.
    public static final int DEFAULT_INITIAL_CAPACITY = 11;

    public AbstractIndexPriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * For example, 10. then 1,2,3,4,5,...,10 index can be associated with key1, key2, ..., key10
     *
     * @param capacity the capacity of heap and index.
     */
    public AbstractIndexPriorityQueue(int capacity) {
//        this.clazz = (Class<Key>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.capacity = capacity;
        size = 0;
        heap = new int[capacity + 1];
        inverseHeap = new int[capacity + 1];
//        keys = (Key[]) Array.newInstance(this.getKeyClass(), capacity + 1);
//        keys = (Key[]) Array.newInstance(clazz, capacity + 1);
        keys = (Key[]) new Comparable[capacity + 1];
//        Arrays.fill(inverseHeap, -1); //表示堆中没有这个元素
    }

    public boolean offer(int recordID, Key key) {
        if (!inBound(recordID) || Objects.nonNull(keys[recordID]))
            return false;
        size++;
        keys[recordID] = key; //which is new.
        inverseHeap[recordID] = size;
        heap[size] = recordID;
        swim(size);
        return true;
    }

    @Override
    public int pollIndex() {
        final var max = heap[1];
        heapExchange(1, size);
        heap[size] = 0;
        inverseHeap[max] = 0;
        keys[max] = null; //非常纠结，要不要保留。
        size--;
        sink(1);
        return max;
    }

    @Override
    public Key poll() {
        return keys[pollIndex()];
    }

    @Override
    public boolean updateKey(int recordID, Key newKey) {
        if (!inBound(recordID) || Objects.isNull(keys[recordID]))
            return false;
        keys[recordID] = newKey;
        sink(inverseHeap[recordID]);
        swim(inverseHeap[recordID]);
        return true;
    }

    private void swim(int heapIndex) {
        for (int newChild = heapIndex, parent = newChild >> 1; newChild > 1 && lessThan(parent, newChild); newChild = parent, parent >>= 1) {
            heapExchange(parent, newChild);
        }
    }

    private void sink(int heapIndex) {
        for (int newParent = heapIndex, child = newParent << 1; child <= size; newParent = child, child <<= 1) {
            if (child < size && lessThan(child, child + 1))
                child++;//默认是左子。如果右子存在（最后一层不完全），而且比左子大，就转移。
            //现在，child是更大的合法的孩子。
            if (lessThan(child, newParent))
                break;
            heapExchange(newParent, child);
        }
    }

    @Override
    public Key keyOf(int recordID) {
        return keys[recordID];
    }

    //不仅要要求keys中有映射，还要要求堆里面有这个值。
    @Override
    public boolean contains(int recordID) {
//        return inBound(recordID) && Objects.nonNull(keys[recordID]) && inverseHeap[recordID] != 0;
//        return inverseHeap[recordID]!=-1; //堆中是否有这个值。
        return inverseHeap[recordID]!=0; //堆中是否有这个值。
    }

    @Override
    public Key peek() {
        return keys[heap[1]];
    }

    @Override
    public int peekIndex() {
        return heap[1];
    }

    public boolean inBound(int recordID) {
        return 1 <= recordID && recordID <= capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public void heapExchange(int heapIndexI, int heapIndexJ) {
        assert (heapIndexI != heapIndexJ); // 不然不能交换。 这里priority queue的浮沉要求父子节点的堆索引不一样。
        heap[heapIndexI] ^= heap[heapIndexJ];
        heap[heapIndexJ] ^= heap[heapIndexI];
        heap[heapIndexI] ^= heap[heapIndexJ];
        inverseHeap[heap[heapIndexI]] = heapIndexI;
        inverseHeap[heap[heapIndexJ]] = heapIndexJ;
    }

    //    private void expand() {
//        final var newHeap = /*(Key[])*/ new Comparable[capacity << 2];
//        System.arraycopy(heap, 0, newHeap, 0, capacity + 1);
//        heap = newHeap;
//    }
    public abstract boolean lessThan(Key smaller, Key larger);

    public boolean lessThan(int heapIndexSmaller, int heapIndexLarger) {
        return lessThan(keys[heap[heapIndexSmaller]], keys[heap[heapIndexLarger]]);
    }
//    protected Class<Key> clazz;
}

class IndexPriorityQueue<Key extends Comparable<Key>> extends AbstractIndexPriorityQueue<Key> {
    public IndexPriorityQueue() {
    }

    public IndexPriorityQueue(int capacity) {
        super(capacity);
    }

    @Override
    public boolean lessThan(Key assertSmaller, Key assertLarger) {
        return assertSmaller.compareTo(assertLarger) < 0;
    }
}

class IndexInferiorityQueue<Key extends Comparable<Key>> extends AbstractIndexPriorityQueue<Key> {
    public IndexInferiorityQueue() {
    }

    public IndexInferiorityQueue(int capacity) {
        super(capacity);
    }

    @Override
    public boolean lessThan(Key assertSmaller, Key assertLarger) {
        return assertSmaller.compareTo(assertLarger) > 0;
    }
}