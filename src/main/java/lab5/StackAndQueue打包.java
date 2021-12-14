package lab5;

import java.util.Comparator;

//class MaximumDeque<Item extends Comparable<Item>> extends ArrayDeque<Item> {
//    public MaximumDeque(int windowLength) {
//        super(windowLength);
//    }
//    public Item getMax(){
//        return super.队首();
//    }
//
//
//}
class MaximumDeque<Item>{
    private ArrayDeque<Integer> window;//存储的是编号。
//    private LinkedDeque<Integer> window;//存储的是编号。
    private Item[] source;
    private int windowLength, currentPosition;
    private Comparator<Item> itemComparator;
    public MaximumDeque(Item[] source, int windowLength, Comparator<Item> itemComparator) {
        this.source = source;
        this.windowLength = windowLength;
        this.itemComparator =itemComparator;
        init();
    }

    private void init() {
        this.window = new ArrayDeque<>(windowLength);
//        this.window = new LinkedDeque<>();
        for(currentPosition = -windowLength;currentPosition<-1;)
            slide();
    }
    //错误的init！ init的时候，并不是只有王牌队员和新生有作用。大二、大三学生也有可能成为强者。
//    private void init() {
////        this.currentPosition = 0;
//        this.currentPosition = -1;
//        this.window = new ArrayDeque<>(windowLength);
//        int tempMaxIndex = 0;
//        //根据 canSlide语义，初始化的时候只考虑了前面-1到进入到0后才有的前一个。
//        //比如，0 1 2 3 窗口长为3， 指针在-1， 窗口已经拥有了0和1中的最大值，但不拥有2； slide一次之后才拥有2，同时没有人退役。
////        int i;
//        for (int i = tempMaxIndex+1; i <= windowLength-2; i++) {
//            if (itemComparator.compare(source[i],source[tempMaxIndex])>0)
//                tempMaxIndex = i;
//        }
//        window.排队(tempMaxIndex);//初始化时不用考虑新生必定入队这个定理，因为现在这个不是真的新生。
////        if (itemComparator.compare(source[i], source[tempMaxIndex])>=0)//>=也好，减少一个名额。
////            window.排队(i);//新生就是最强的
////        else {
////            window.排队(i);//潜力新生
////            window.排队(tempMaxIndex);//最强学长
////        }
//    }

//    public boolean valid(){//不应该命名成canSlide，会有歧义。valid表示最大值可以取的状态。
//        return currentPosition+windowLength<=source.length;//比如，0 1 2 3这个数组，窗口长2, 2 3是最后一个，但还是合法的，认为还能滑动，下一个3 null才是不能滑动了。1+2<4, 2+2=4， 3+2！<=4
//    }
    public boolean canSlide(){//还是要用canSlide语义。没有slide之前，是-1的状态
        return currentPosition+windowLength<source.length;//比如，0 1 2 3这个数组，窗口2 3认为不能再滑动了，所以不会调用滑动方法。
    }
    public void slide(){
        currentPosition++;
        //毕业退役老队员。
        if (!window.队空()&&window.队首()<currentPosition){//比如，队首的index是3， 现在currentPosition走到了4
            window.办理业务();//王牌老队员从前门荣誉退队。
        }
        //新生大闹集训队
        final int newStudentIndex = currentPosition+windowLength-1;//比如，cp = 0， 长度为2，那么新生是0+2-1。
        while (!window.队空()&&itemComparator.compare(source[window.队尾()], source[newStudentIndex])<=0){//集训队王牌学长还没被打败，或者当前最弱学长很菜。 <=0多杀一点。//<=0是必须的，因为学长容易退役，但是新生相同水平下更有价值。
            window.走后门();//学长从后门溜走了
        }
        window.排队(newStudentIndex);//新生入队。
    }
    public Item getMax(){
        return source[window.队首()];
    }
}
interface OJStack<Item> {
    void 入栈(Item item);

    Item 出栈();

    Item 栈顶();

    boolean 栈空();

    int 栈深();

    String 栈可视化();
}

interface OJQueue<Item> {
    boolean 队空();

    boolean 队满();

    int 队长();

    void 排队(Item item);

    Item 办理业务();

    String 队列可视化();
}

class ArrayStack<Item> implements OJStack<Item> {
    private static final int defaultInitialCapacity = 10;
    private Item[] data;
    private int capacity;
    private int top = -1;//top指向当前最后一个元素的索引。

    //    private ArrayStack(Item[] data) {
//        this.data = data.clone();
//    }
    public ArrayStack() {
        this(defaultInitialCapacity);
    }

    public ArrayStack(int initialCapacity) {
        capacity = initialCapacity;
        data = (Item[]) new Object[initialCapacity];
    }

    public void 可扩容入栈(Item item) {
        try {
            入栈(item);
        } catch (ArrayIndexOutOfBoundsException e) {
            top--;
            capacity <<= 1;
            final var newArray = (Item[]) new Object[capacity];
            System.arraycopy(data, 0, newArray, 0, data.length);
            data = newArray;
            入栈(item);
        }
    }

    public void 入栈(Item item) throws ArrayIndexOutOfBoundsException {
        data[++top] = item;
    }

    public Item 出栈() throws ArrayIndexOutOfBoundsException {
        return data[top--];
    }

    public Item 栈顶() throws ArrayIndexOutOfBoundsException {
        return data[top];
    }

    public boolean 栈空() {
        return top < 0;//不能等于0
    }

    public int 栈深() {
        return top + 1;
    }

    @Override
    public String toString() {
        return 栈可视化();
    }

    public String 栈可视化() {//按照下压栈的格式打印
        StringBuilder result = new StringBuilder();
        result.append("|\tStack Top\t|").append("\n");
        for (int i = data.length - 1; i >= 0; i--) {
            result.append("|---------------|").append("\n");
            result.append(String.format("|\t\t%s\t\t|", data[i]));
            if (i == top)
                result.append("<- top");
            result.append("\n");
            result.append("|---------------|").append("\n");
        }
        return result.toString();
    }
}

//定容队列
class ArrayDeque<Item> extends ArrayQueue<Item> {
    public ArrayDeque() {
        super();
    }

    public ArrayDeque(int capacity) {
        super(capacity);
    }

    public void 插队(Item newItem) throws IndexOutOfBoundsException {
//        if (队满()) {
//            throw new IndexOutOfBoundsException("定容队列毕竟不是希尔伯特大饭店，想插队也不能在队满的情况啊。");
//        }
//        front = super.inRangeMod(front - 1, modulus);
        front = (front - 1+modulus)%modulus;
        super.data[front] = newItem;
    }

    public Item 走后门() throws IndexOutOfBoundsException { //pop out //dequeue
//        if (队空()) {
//            throw new IndexOutOfBoundsException("队空，没有人要走后门。");
//        }
//        rear = super.inRangeMod(rear - 1, modulus);
        rear = (rear - 1+modulus)%modulus;
        return data[rear];
    }

    public Item 队尾() {
//        if (队空()) {
//            throw new IndexOutOfBoundsException("队空，没有人要走后门。");
//        }
        return data[(rear - 1+modulus)%modulus];
    }

}

//定容队列
class ArrayQueue<Item> implements OJQueue<Item> {
    protected static final int defaultInitialCapacity = 10;
    protected Item[] data;
    protected int capacity; //比数组的长度要小一个。
    protected int modulus; //与数组长度一致
    protected int rear = 0;//队尾
    protected int front = 0;//队首

    public ArrayQueue() {
        this(defaultInitialCapacity);
    }

    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        modulus = capacity + 1;
        data = (Item[]) new Object[modulus];
    }

    private ArrayQueue(Item[] data) {
        this.data = data;
        capacity = data.length - 1;
        modulus = data.length;
        front = 0;
//        rear = capacity-1;//还要再减1。  比如数组0,1,2,3， 长度为4， capacity为3， rear指针在2。
        rear = capacity;//不能再减1。 rear是没有元素的一个位置。
    }

    public static ArrayQueue<Integer> readArrayQueueFromOJ(OJReader in, int length) {
        Integer[] array = new Integer[length + 1];
        for (int i = 0; i < length; i++) {
            array[i] = in.nextInt();
        }
        return new ArrayQueue<Integer>(array);
    }

    protected int inRangeMod(int maybeNegative, int positiveModulus) {
//        if (maybeNegative < 0)
//            return positiveModulus - (-maybeNegative) % positiveModulus;
//        else
//            return maybeNegative % positiveModulus;
        return (maybeNegative+positiveModulus)%positiveModulus;//由于队列的特殊性。
    }

    public boolean 队空() {
        return front == rear;
    }

    public boolean 队满() {
//        return (rear+1)%moder == front%moder;
//        return inRangeMod(front - rear, modulus) == 1;
        return (front - rear+modulus)%modulus == 1;
    }

    public int 队长() {
//        return (front-rear)%moder;//-1%6 == 5, 所以可以作差
//        return (rear - front+modulus)%modulus;//-1%6 == 5, 所以可以作差
        return (rear - front+modulus)%modulus;//-1%6 == 5, 所以可以作差
    }

    public void 排队(Item item) throws IndexOutOfBoundsException {
//        if (队满()) {
//            throw new IndexOutOfBoundsException("队列满，无法入队。");
//        }
        data[rear] = item;
//        rear = inRangeMod(rear + 1, modulus);
        rear = (rear + 1+modulus)%modulus;
    }

    public Item 办理业务() throws IndexOutOfBoundsException {
//        if (队空()) {
//            throw new IndexOutOfBoundsException("队空，无法出队。");
//        }
        var oldValue = data[front];
//        front = inRangeMod(front + 1, modulus);
        front = (front + 1+modulus)%modulus;
        return oldValue;
    }
    public Item 队首() throws IndexOutOfBoundsException {
//        if (队空()) {
//            throw new IndexOutOfBoundsException("队空，无法出队。");
//        }
        return data[front];
    }

    public String toString() {
        return 队列可视化();
    }

    public String 队列可视化() { //上面是办事窗口
        StringBuilder result = new StringBuilder();
        result.append("|\tQueue Front\t|").append("\n");
        for (int i = front; i != rear; i++, i = inRangeMod(i, modulus)) { //在rear之前还是可以遍历的最后一个元素。
            result.append("|---------------|").append("\n");
            result.append(String.format("|\t\t%s\t\t|", data[i])).append("\n");
            result.append("|---------------|").append("\n");
        }
        result.append("|\tQueue Back\t|").append("\n");
        return result.toString();
    }
}

//#include "BidirectionalLinkedNodes.java"
class LinkedStack<Item> implements OJStack<Item> {
    private BidirectionalLinkedNodes<Item> nodes = new BidirectionalLinkedNodes<>();

    public LinkedStack() {
    }

    public LinkedStack(BidirectionalLinkedNodes<Item> nodes) {
        this.nodes = nodes;
    }

    public void 入栈(Item newItem) { //push in
        nodes.pushAfter(newItem);
    }

    public Item 出栈() { //pop out
        final Item value = nodes.getLast().value;
        nodes.getRightMargin().removeFront(nodes);
        return value;
    }

    public Item 安全出栈() throws IndexOutOfBoundsException { //pop out
        if (栈空())//自己队空，而不是nodes空，这样如果后续isEmpty有修改就不用改这里
            throw new IndexOutOfBoundsException("Stack is empty!");
        return 出栈();
//        final Item value = nodes.getLast().value;
////        try {
////            nodes.getRightMargin().removeFront(nodes);
////        }catch (NullPointerException ) //？异常好还是判断好
//        nodes.getRightMargin().removeFront(nodes);
//        return value;
    }

    public Item 栈顶() { //peek
        return nodes.getLast().value;
    }

    public boolean 栈空() {
        return nodes.isEmpty();
    }

    public int 栈深() {
        return nodes.size();
    }

    @Override
    public String toString() {
        return 栈可视化();
    }

    public String 栈可视化() {//按照下压栈的格式打印
        StringBuilder result = new StringBuilder();
        result.append("|\tStack Top\t|").append("\n");
        for (var it = nodes.getLast(); it != nodes.getLeftMargin(); it = it.previous) {
            result.append("|---------------|").append("\n");
            result.append(String.format("|\t\t%s\t\t|", it.value.toString())).append("\n");
            result.append("|---------------|").append("\n");
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedStack<?> that = (LinkedStack<?>) o;

        return nodes != null ? nodes.equals(that.nodes) : that.nodes == null;
    }

    @Override
    public int hashCode() {
        return nodes != null ? nodes.hashCode() : 0;
    }
}

class LinkedDeque<Item> extends LinkedQueue<Item> {
    public void 插队(Item newItem) { //push in //enqueue
        nodes.pushFront(newItem);
    }

    public Item 带检查的走后门() throws IndexOutOfBoundsException { //pop out //dequeue
        if (队空())
            throw new IndexOutOfBoundsException("队空！上天无路，入地无门。");
        return 走后门();
    }

    public Item 走后门() { //pop out //dequeue
        final Item value = nodes.getLast().value;
        nodes.getRightMargin().removeFront(nodes);
        return value;
    }

    public Item 队尾() {
        return nodes.getLast().value;
    }
}

class LinkedQueue<Item> implements OJQueue<Item> {
    protected BidirectionalLinkedNodes<Item> nodes = new BidirectionalLinkedNodes<>();

    public LinkedQueue() {
    }

    //nodes的尾为队尾，nodes的头为队头
    public LinkedQueue(BidirectionalLinkedNodes<Item> nodes) {
        this.nodes = nodes;
    }

    public void 排队(Item newItem) { //push in //enqueue
        nodes.pushAfter(newItem);
    }

    public Item 带检查的办理业务() throws IndexOutOfBoundsException { //pop out //dequeue
        if (队空())
            throw new IndexOutOfBoundsException("队空！没有人前来买瓜。");
        return 办理业务();
    }

    public Item 预处理业务() { //peek
        return nodes.getFirst().value;
    }

    public Item 办理业务() { //pop out //dequeue
        final Item value = nodes.getFirst().value;
        nodes.getLeftMargin().removeAfter(nodes);
        return value;
    }

    public Item 队首() {
        return nodes.getFirst().value;
    }

    public boolean 队空() {
        return nodes.isEmpty();
    }

    @Override
    public boolean 队满() {
        return false; //永远不会满
    }

    public int 队长() {//不是队长 是队chang
        return nodes.size();
    }

    @Override
    public String toString() {
        return 队列可视化();
    }

    public String 队列可视化() { //上面是办事窗口
        StringBuilder result = new StringBuilder();
        result.append("|\tQueue Front\t|").append("\n");
        for (var it = nodes.getFirst(); it != nodes.getRightMargin(); it = it.next) {
            result.append("|---------------|").append("\n");
            result.append(String.format("|\t\t%s\t\t|", it.value.toString())).append("\n");
            result.append("|---------------|").append("\n");
        }
        result.append("|\tQueue Back\t|").append("\n");
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedQueue<?> that = (LinkedQueue<?>) o;

        return nodes != null ? nodes.equals(that.nodes) : that.nodes == null;
    }

    @Override
    public int hashCode() {
        return nodes != null ? nodes.hashCode() : 0;
    }
}
