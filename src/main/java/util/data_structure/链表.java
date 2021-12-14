package util.data_structure;


import java.util.Iterator;

interface 可浏览的<Item> extends Iterable<Item>{
    @Override
    default 浏览指针<Item> iterator(){return 浏览指针();}
    浏览指针<Item> 浏览指针();
}
interface 浏览指针<Item> extends Iterator<Item>{
    boolean 还未指向虚无();//当前
    void 前进();
    Item 所指之物();
    @Override
    default boolean hasNext(){
        return 还未指向虚无();
    }
    @Override
    default Item next(){
        前进();
        return 所指之物();
    }
}
public class 链表<Item> implements 可浏览的<Item>{
    public 链表() {
        this.size = 0;
        head = tail = null;
    }
    private int size;
    public int getSize() {
        return size;
    }
    public void add(Item nextElement) {
        节点<Item> oldTail = tail;
        节点<Item> newNode = new 节点<>(nextElement, null);
        tail = newNode;
        if (oldTail==null)
            head = newNode;
        else
            oldTail.next = newNode;
        size++;
    }
    private 节点<Item> head;//head取得到
    private 节点<Item> tail;//tail是最后一个旧的
    private static class 节点<Item>{
        public Item val;
        public 节点<Item> next;
        public 节点(Item val, 节点<Item> next) {
            this.val = val;
            this.next = next;
        }
    }
    @Override
    public 浏览指针<Item> 浏览指针() {
        return new 浏览指针<Item>() {
            private 节点<Item> current = new 节点<Item>(null, head);
            @Override
            public boolean 还未指向虚无() {
                return current.next!=null;
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
        final 链表<Object> objects = new 链表<>();
        objects.add(1);
        objects.add(2);
        objects.add(" test");
        for(Object e:objects)
            System.out.println(e);
    }
}
