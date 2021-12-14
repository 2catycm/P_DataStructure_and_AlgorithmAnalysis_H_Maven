package lab4;//package lab4;
//
//import java.util.Iterator;
//import java.util.LinkedList;
//
///**
// * 用于debug，看看自己的链表和标准库的一不一样
// * @param <T>
// */
//public class 套壳LinkedList<T> extends LinkedList<T> implements Pointable<T>{
//    @Override
//    public Pointer<T> pointer() {
//        return new 套壳Iterator<T>(super.iterator());
//    }
//}
//class 套壳Iterator<E> implements Pointer<E>{
//    private Iterator<E> iterator;
//
//    public 套壳Iterator(Iterator<E> iterator) {
//        this.iterator = iterator;
//    }
//
//    @Override
//    public boolean 还未指向虚无() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void 前进() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public E 所指之物() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean hasNext() {
//        return iterator.hasNext();
//    }
//
//    @Override
//    public E next() {
//        return iterator.next();
//    }
//}