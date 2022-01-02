package lab4_linkedlist;


import java.util.Iterator;

public class Problem1 {
    private static OJReader in = new OJReader();
    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        final LinkedListProblem1<Term> list1 = new LinkedListProblem1<>();
        final LinkedListProblem1<Term> list2 = new LinkedListProblem1<>();
        for (int i = 0; i < n; i++) {
            list1.add(new Term(in.nextInt(), in.nextInt()));
        }
        for (int i = 0; i < m; i++) {
            list2.add(new Term(in.nextInt(), in.nextInt()));
        }//从大到小
        final Iterator<Term> it1 = list1.iterator();
        final Iterator<Term> it2 = list2.iterator();
        int k = 0;
        final StringBuilder stringBuilder = new StringBuilder();
        for (Term term1 = it1.next(),
             term2 = it2.next();it1.hasNext()&&it2.hasNext();){
            int comparison = term1.compareTo(term2);
            if (comparison==0)
                stringBuilder.append(term1.add(term2));
            else if (comparison<0){//term1小，term2大。消耗term2
                stringBuilder.append(term2);
                term2 = it2.next();
            }else{
                stringBuilder.append(term1);
                term1 = it1.next();
            }
            stringBuilder.append("\n");
            k++;
        }
        while (it1.hasNext()) {
            stringBuilder.append(it1.next());
            k++;
        }
        while (it2.hasNext()) {
            stringBuilder.append(it2.next());
            k++;
        }
        System.out.println(k);
        System.out.println(stringBuilder.toString());
    }
}

//理解：没有递归，只是存了下一个的地址
//链表先改谁，后改谁，需要注意
class LinkedListProblem1<Item> implements Iterable<Item>{ //注意，不是Iterable<LinkedList<E>>
    public LinkedListProblem1() {
        this.size = 0;
        head = tail = null;
    }
//    public LinkedList(int capacity) {
//        this.capacity = capacity;
//        head = tail = null;
//    }
//    private int capacity;//禁止这个概念
    private int size;
    public int getSize() {
        return size;
    }
    public void add(Item nextElement) { //加到tail上
//        if (size==0){
//            head = new Node<>(nextElement, null);
//            tail = head;
//        }else if (size==1) {
//            Node<Item> newNode = new Node<>(nextElement, null);
//            tail.next = newNode;
//            tail = newNode;
//            head.next = tail;
//        }else {
//                Node<Item> newNode = new Node<>(nextElement, null);
//                tail.next = newNode;
//                tail = newNode;
//        }
        Node<Item> oldTail = tail;
        Node<Item> newNode = new Node<>(nextElement, null);
        tail = newNode;
        if (oldTail==null)
            head = newNode;
        else
            oldTail.next = newNode;
        size++;
    }
    private Node<Item> head;//head取得到
    private Node<Item> tail;//tail是最后一个旧的

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private Node<Item> current = head;
            private int currentPlace = 0;
            @Override
            public boolean hasNext() {
//                return current.next!=null;
//                return currentPlace<size;
                return current!=null;
            }

            @Override
            public Item next() {
                Item prev = current.val;
//                currentPlace++;
                current = current.next;
                return prev;
            }
        };
    }

    private static class Node<Item>{
        public Item val;
        public Node<Item> next;
        public Node(Item val, Node<Item> next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        final LinkedListProblem1<Object> objects = new LinkedListProblem1<>();
        objects.add(1);
        objects.add(2);
        objects.add(" test");
        for(Object e:objects)
            System.out.println(e);
    }
}
