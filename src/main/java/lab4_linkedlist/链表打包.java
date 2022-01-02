package lab4_linkedlist;

import java.util.Iterator;


interface Pointable<Item> extends Iterable<Item> {
    @Override
    default Pointer<Item> iterator() {
        return pointer();
    }

    Pointer<Item> pointer();
}

interface Pointer<Item> extends Iterator<Item> {
    boolean 还未指向虚无();//当前

    void 前进();

    Item 所指之物();

    @Override
    default boolean hasNext() {
        return 还未指向虚无();
    }

    @Override
    default Item next() {
        final Item 所指之物 = 所指之物();
        前进();
        return 所指之物;
    }
}

class LinkedNodes<Item> implements Pointable<Item> {
    public static LinkedNodes<Integer> readLinkedNodesFromOJ(OJReader in){
        return readLinkedNodesFromOJ(in,in.nextInt());
    }
    public static LinkedNodes<Integer> readLinkedNodesFromOJ(OJReader in, int length){
        final LinkedNodes<Integer> integers = new LinkedNodes<>();
        for (int i = 0; i < length; i++) {
            integers.add(in.nextInt());
        }
        return integers;
    }
    public LinkedNodes() {
        this.size = 0;
        head = tail = null;
    }

    private int size;

    public int size() {
        return size;
    }

    public void add(Item nextElement) {
        Node<Item> oldTail = tail;
        Node<Item> newNode = new Node<>(nextElement, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
        size++;
    }

    private Node<Item> head;//head取得到
    private Node<Item> tail;//tail是最后一个旧的

    private static class Node<Item> {
        public Item val;
        public Node<Item> next;

        public Node(Item val, Node<Item> next) {
            this.val = val;
            this.next = next;
        }
    }

    @Override
    public Pointer<Item> pointer() {
        return new Pointer<Item>() {
//            private Node<Item> current = new Node<Item>(null, head);
            private Node<Item> current = head;

            @Override
            public boolean 还未指向虚无() { //也就是所指之物不会异常
//                return current.next != null;
                return current != null;
            }

            @Override
            public void 前进() {
                current = current.next;
            }

            @Override
            public Item 所指之物() {
                return current.val;
            }
        };
    }
    public static void main(String[] args) { //有问题。需要假头
        final LinkedNodes<Object> objects = new LinkedNodes<>();
        objects.add(1);
        objects.add(2);
        objects.add(" test");
        for (Object e : objects)
            System.out.println(e);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder().append("{");
        for (Object e:this)
            result.append(e).append(", ");
        return result.append(")").toString();
    }
}