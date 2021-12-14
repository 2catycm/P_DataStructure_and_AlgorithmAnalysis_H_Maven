package util.syntax;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.function.IntConsumer;

class GenericArray<Key extends Comparable<Key>>{
    Key[] heap;
    Comparable[] compArr;
    public GenericArray(Key[] heap, IntConsumer intConsumer) {
//        this(heap);
        this.heap = (Key[]) new Comparable[heap.length+1];
        System.arraycopy(heap, 0, this.heap, 1,  heap.length);
        intConsumer.accept(1);
    }
    public GenericArray(Key[] heap) {
//        this.heap = heap;//ok
        this.heap = (Key[]) new Comparable[heap.length+1];
        System.arraycopy(heap, 0, this.heap, 1,  heap.length);
        this.compArr = new Comparable[heap.length+1];
        System.arraycopy(heap, 0, this.compArr, 1,  heap.length);
    }
    public void print(){
        System.out.println(Arrays.toString(heap));
        System.out.println(Arrays.toString(compArr));
    }
}
class SubClass<Key extends Comparable<Key>> extends GenericArray<Key>{
    public SubClass(Key[] heap, IntConsumer intConsumer) {
        super(heap, intConsumer);
    }
}
public class Java泛型问题 {
    static OJReader in = new OJReader();
    public static void main(String[] args) {
        final var integers = new Integer[]{1, 2, 3};
        new GenericArray<Integer>(integers).print();
        new GenericArray<Integer>(integers, new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println("accepted");
            }
        }).print();
        new SubClass<Integer>(integers, new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println("accepted");
            }
        }).print();

//        TreeMap
    }
}
