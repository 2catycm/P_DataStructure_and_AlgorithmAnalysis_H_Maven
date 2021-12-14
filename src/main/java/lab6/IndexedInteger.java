package lab6;

import java.util.Comparator;
import java.util.stream.IntStream;

public class IndexedInteger implements Comparable<IndexedInteger>{
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