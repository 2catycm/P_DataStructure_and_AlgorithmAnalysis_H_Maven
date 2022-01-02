package lab4_linkedlist;

import java.util.Comparator;
import java.util.stream.IntStream;

class IndexedInteger implements Comparable<IndexedInteger>{
    public static IndexedInteger[] nextIndexedIntegerArray(OJReader in){
        return nextIndexedIntegerArray(in, in.nextInt());
    }
    public static IndexedInteger[] nextIndexedIntegerArray(OJReader in, int length){
        final IndexedInteger[] result = new IndexedInteger[length];
        for (int i = 0; i < length; i++) {
            result[i] = new IndexedInteger(in.nextInt(), i);
        }
        return result;
    }
    public static IndexedInteger[] copyFrom(Integer[] integers){
        return IntStream.range(0, integers.length).mapToObj(i -> new IndexedInteger(integers[i], i)).toArray(IndexedInteger[]::new);
    }
    public static IndexedInteger[] copyFrom(int[] integers){
        return IntStream.range(0, integers.length).mapToObj(i -> new IndexedInteger(integers[i], i)).toArray(IndexedInteger[]::new);
    }
    private final int data;
    private final int order;

    public IndexedInteger(int data, int order) {
        this.data = data;
        this.order = order;
    }

    public int getData() {
        return data;
    }

    public int getOrder() {
        return order;
    }
    @Override
    public String toString() {
        return "IndexedInteger{" +
                "data=" + data +
                ", order=" + order +
                '}';
    }
    @Override
    public int compareTo(IndexedInteger o) {
        return Integer.compare(data,o.data);
    }
    public static Comparator<IndexedInteger> getOrderComparator(){
        return new OrderComparator();
    }
    private static class OrderComparator implements Comparator<IndexedInteger>{
        @Override
        public int compare(IndexedInteger o1, IndexedInteger o2) {
            return Integer.compare(o1.order, o2.order);
        }
    }
}

class TickedInteger extends IndexedInteger{
    public TickedInteger(int data) {
        this(data, false);
    }
    public TickedInteger(int data, boolean isTicked) {
        super(data, isTicked?1:0);
    }

    public boolean isTicked(){
        return super.getOrder()!=0;
    }
    @Override
    @Deprecated
    public int getOrder() {
        throw new UnsupportedOperationException("banned method.");
    }

    @Override
    public String toString() {
        return "TickedInteger{" +
                "data=" + super.getData() +
                ", ticked=" + isTicked() +
                '}';
    }
}