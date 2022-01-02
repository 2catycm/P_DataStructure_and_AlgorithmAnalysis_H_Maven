package lab4_linkedlist;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;


//interface BidirectionalPointable<Item> extends Iterable<Item> {
//    @Override
//    default BidirectionalPointer<Item> iterator() {
//        return bidirectionalPointer();
//    }
//    BidirectionalPointer<Item> bidirectionalPointer();
//}
//
//interface BidirectionalPointer<Item> extends Iterator<Item> {
//    boolean 还未指向虚无();//当前
//
//    void 前进();
//    void 后退();
//
//    Item 所指之物();
//
//
//    @Override
//    default boolean hasNext() {
//        return 还未指向虚无();
//    }
//
//    @Override
//    default Item next() {
//        final Item 所指之物 = 所指之物();
//        前进();
//        return 所指之物;
//    }
//}

class BidirectionalLinkedNodes<Item> /*implements BidirectionalPointable<Item>*/ {
    public static class BidirectionalNode<Item> { //内部静态类不能从外部类获得泛型，以及不能写成内部动态类
        public Item value;
        public BidirectionalNode<Item> previous;
        public BidirectionalNode<Item> next; //字段要不要public，对外部类和其他类的作用是什么？正在调查
        public BidirectionalNode(Item value, BidirectionalNode<Item> previous, BidirectionalNode<Item> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
        public BidirectionalNode<Item> linkFront(BidirectionalLinkedNodes<Item> list, Item newItem){
            BidirectionalNode<Item> newNode = new BidirectionalNode<Item>(newItem, this.previous, this);
            this.previous.next = newNode;
            this.previous = newNode;
            list.size++;
            return newNode;
        }
        public BidirectionalNode<Item> linkAfter(BidirectionalLinkedNodes<Item> list, Item newItem){
            BidirectionalNode<Item> newNode = new BidirectionalNode<Item>(newItem, this, this.next);
            this.next.previous = newNode;
            this.next = newNode;
            list.size++;
            return newNode;
        }
        @Deprecated
        public BidirectionalNode<Item> linkAfter(Item newItem){//not insert//develop for problem4 only
            BidirectionalNode<Item> newNode = new BidirectionalNode<Item>(newItem, this, null);
            this.next = newNode;
            return newNode;
        }
        public BidirectionalNode<Item> advanceWhile(Predicate<BidirectionalNode<Item>> condition) {
            var it = this;
            for (;condition.test(it); it = it.next) ;
            return it;
        }
        public BidirectionalNode<Item> retreatNoChecking(int stepCount) {
            if (stepCount<0)
                return advanceNoChecking(-stepCount);
            BidirectionalNode<Item> advanced = this;
            for (int i = 0; i < stepCount; i++) {
                try {
                    advanced = advanced.previous;
                }catch (NullPointerException e){
                    return null;
                }
            }
            return advanced;
        }
        public BidirectionalNode<Item> advanceNoChecking(int stepCount){ //超过的返回null
            if (stepCount<0)
                return retreatNoChecking(-stepCount);
            BidirectionalNode<Item> advanced = this;
            for (int i = 0; i < stepCount; i++) {
                try {
                    advanced = advanced.next;
                }catch (NullPointerException e){
                    return null;
                }
            }
            return advanced;
        }
        public BidirectionalNode<Item> retreat(int stepCount){
            if (stepCount<0)
                return advance(-stepCount);
            BidirectionalNode<Item> advanced = this;
            for (int i = 0; i < stepCount; i++) {
                if (advanced==null)
                    throw new IndexOutOfBoundsException(String.format("Going %d steps from node %s backward reaches the end ahead of schedule. Actually %d steps has retreated to reach null.", stepCount, this,i));
                advanced = advanced.previous;
            }
            return advanced;
        }
        public BidirectionalNode<Item> advance(int stepCount){ //java嘛，那还是有检查的
            if (stepCount<0)
                return retreat(-stepCount);
            BidirectionalNode<Item> advanced = this;
            for (int i = 0; i < stepCount; i++) {
                if (advanced==null)
                    throw new IndexOutOfBoundsException(String.format("Going %d steps from node %s forward reaches the end ahead of schedule. Actually %d steps has advanced to reach null.", stepCount, this,i));
                advanced = advanced.next;
            }
            return advanced;
        }
        public BidirectionalNode<Item> next(){
            return advance(1);
        }
        public BidirectionalNode<Item> previous(){
            return retreat(1);
        }
        @Override
        public String toString() {
            return "BidirectionalNode{" +
                    "value=" + value + '}';//不能递归
//                    ", previous=" + previous +
//                    ", next=" + next +
//                    '}';
        }
        public Item removeFront(BidirectionalLinkedNodes<Item> list){
            final BidirectionalNode<Item> newPrevious = this.previous.previous;
            newPrevious.next = this;
            final Item removed = this.previous.value;//至于它的value、next、front, 等Java垃圾回收吧
            this.previous = newPrevious;
            list.size--;
            return removed;
        }
        public Item removeAfter(BidirectionalLinkedNodes<Item> list){
            final BidirectionalNode<Item> newNext = this.next.next;
            newNext.previous = this;
            final Item removed = this.next.value;
            this.next = newNext;
            list.size--;
            return removed;
        }
        public boolean isDead(){
            return this.value==null && this.next==null && this.previous==null;
        }
        public Item killFront(BidirectionalLinkedNodes<Item> list){
            final BidirectionalNode<Item> newPrevious = this.previous.previous;
            newPrevious.next = this;
            final Item removed = this.previous.value;
                this.previous.value = null;
                this.previous.next = this.previous.previous = null; //kill the previous node
            this.previous = newPrevious;
            list.size--;
            return removed;
        }
        public Item killAfter(BidirectionalLinkedNodes<Item> list){
            final BidirectionalNode<Item> newNext = this.next.next;
            newNext.previous = this;
            final Item removed = this.next.value;
                this.next.value = null;
                this.next.next = this.next.previous = null; //kill the next node
            this.next = newNext;
            list.size--;
            return removed;
        }
//        @Deprecated //有bug，如果有的节点被selfRemove了，但是这个节点依然存在，通过这个节点的
        public Item selfRemove(BidirectionalLinkedNodes<Item> list){ //自己的节点斩杀，但是作为迭代器，依然可以向前向后
            this.next.previous = this.previous;
            this.previous.next = this.next;
            list.size--;
            return this.value;
//            Item va
//            this.value = null;
//            this.next = null;
//            this.previous = null;
        }

        public void forThisAndEachNodesAfter(BidirectionalLinkedNodes<Item> list, Consumer<? super BidirectionalNode<Item>> action) {
            Objects.requireNonNull(action);
            BidirectionalNode<Item> iterator = this;
            while (!list.isEndOfList(iterator)) {
                action.accept(iterator);
                iterator = iterator.next;
            }
        }
        public void forThisAndEachAfter(BidirectionalLinkedNodes<Item> list, Consumer<? super Item> action) {
            Objects.requireNonNull(action);
            BidirectionalNode<Item> iterator = this;
            while (!list.isEndOfList(iterator)) {
                action.accept(iterator.value);
                iterator = iterator.next;
            }
        }
        public void forThisAndEachBefore(BidirectionalLinkedNodes<Item> list, Consumer<? super Item> action) {
            Objects.requireNonNull(action);
            BidirectionalNode<Item> iterator = this;
            while (!list.isEndOfList(iterator)) {
                action.accept(iterator.value);
                iterator = iterator.previous;
            }
        }
        @Deprecated
        public boolean isEndOfList(){
            return this.next==null||this.previous==null;
        }
        public static<Item> boolean isEndOfList(BidirectionalNode<Item> ths){
            return ths==null||ths.isEndOfList();
        }
    }
    //left_margin和right_margin就像游标卡尺的两个针，中间卡着数据。无论数据是否为空，有多大，都是客观存在的。
    //而且每个游标卡尺有一个自己的margin，不能共享。
    private BidirectionalNode<Item> leftMargin = new BidirectionalNode<>(null, null, null);
    private BidirectionalNode<Item> rightMargin = new BidirectionalNode<>(null, null, null);

    public BidirectionalLinkedNodes() {
        //互认
        leftMargin.next = rightMargin;
        rightMargin.previous = leftMargin;
//        //自指。防止advance过度即使过度也可以停下来。
//        leftMargin.previous = leftMargin;
//        rightMargin.next = rightMargin;
    }
    void pushAfter(Item newItem){
        rightMargin.linkFront(this, newItem);
    }
    void pushFront(Item newItem){
        leftMargin.linkAfter(this, newItem);
    }
    BidirectionalNode<Item> getFirst(){
        return leftMargin.next;
    }
    BidirectionalNode<Item> getLast(){
        return rightMargin.previous;
    }

    public BidirectionalNode<Item> getLeftMargin() {
        return leftMargin;
    }

    public BidirectionalNode<Item> getRightMargin() {
        return rightMargin;
    }

    @Deprecated
    public boolean isEndOfList(BidirectionalNode<Item> iterator){
        return iterator==leftMargin || iterator==rightMargin;
    }

    private int size = 0;
    public int size() {
        return size;
    }
    public boolean isEmpty(){return size<=0;}

    public static BidirectionalLinkedNodes<Integer> readLinkedNodesFromOJ(OJReader in){
        return readLinkedNodesFromOJ(in,in.nextInt());
    }
    public static BidirectionalLinkedNodes<Integer> readLinkedNodesFromOJ(OJReader in, int length){
        final BidirectionalLinkedNodes<Integer> integers = new BidirectionalLinkedNodes<>();
        for (int i = 0; i < length; i++) {
            integers.pushAfter(in.nextInt());
        }
        return integers;
    }
    public void printlnToOJ(OJWriter out){
        this.printToOJ(out);
        out.println();
    }

    public void printToOJ(OJWriter out){
//        this.getFirst().forThisAndEachAfter(this, e->{
//            out.print(e);
//            if (e != this.getLast())
//                out.print(" ");
//        });
        if (this.isEmpty())return;
        var it = this.getFirst();
        for (;it!=this.getLast();it = it.next)
            out.print(it.value+" ");
        out.print(it.value);
    }
    public static void main(String[] args) {
        final BidirectionalLinkedNodes<Object> objects = new BidirectionalLinkedNodes<>();
        objects.pushAfter(1);
        objects.pushAfter(2);
        objects.pushAfter(" test");
        final BidirectionalNode<Object> first = objects.getFirst();
        first.forThisAndEachAfter(objects, System.out::println);
        System.out.println(objects);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder().append("{");
        final BidirectionalNode<Item> first = this.getFirst();
        first.forThisAndEachAfter(this, e->result.append(e).append(" "));
        return result.append("}").toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BidirectionalLinkedNodes<?> that = (BidirectionalLinkedNodes<?>) o;
        if (size != that.size)
            return false;
        var itThis = this.getFirst();
        var itThat = that.getFirst();
        for(;itThis!=this.getRightMargin();itThis = itThis.next, itThat = itThat.next)
            if (itThis.value!=itThat.value)
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}