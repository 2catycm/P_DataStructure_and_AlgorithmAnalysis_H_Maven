package lab8;

import java.util.Comparator;
import java.util.stream.IntStream;
 class IndexedData<Value extends Comparable<Value>> implements Comparable<IndexedData<Value>>{

    private final Value data;
    private final int order;

    public IndexedData(Value data, int order) {
        this.data = data;
        this.order = order;
    }

    public Value getData() {
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
    public int compareTo(IndexedData<Value> o) {
        return data.compareTo(o.data);
    }
    public static Comparator<IndexedData> getOrderComparator(){
        return new OrderComparator();
    }
    private static class OrderComparator implements Comparator<IndexedData>{
        @Override
        public int compare(IndexedData o1, IndexedData o2) {
            return Integer.compare(o1.order, o2.order);
        }
    }
}