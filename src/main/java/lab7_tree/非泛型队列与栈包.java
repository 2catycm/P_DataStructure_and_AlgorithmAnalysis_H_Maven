package lab7_tree;

class MaximumIntDeque extends MonoIntDeque{
    public MaximumIntDeque(int[] source, int windowLength) {
        super(source, windowLength);
    }
    @Override
    public void slide() {
        currentPosition++;
        //毕业退役老队员。
        if (!window.队空()&&window.队首()<currentPosition){//比如，队首的index是3， 现在currentPosition走到了4
            window.办理业务();//王牌老队员从前门荣誉退队。
        }
        //新生大闹集训队
        final int newStudentIndex = currentPosition+windowLength-1;//比如，cp = 0， 长度为2，那么新生是0+2-1。
        while (!window.队空()&&source[window.队尾()]<=source[newStudentIndex]){//集训队王牌学长还没被打败，或者当前最弱学长很菜。 <=0多杀一点。//<=0是必须的，因为学长容易退役，但是新生相同水平下更有价值。
            window.走后门();//学长从后门溜走了
        }
        window.排队(newStudentIndex);//新生入队。
    }
}

class MinimumIntDeque extends MonoIntDeque{
    public MinimumIntDeque(int[] source, int windowLength) {
        super(source, windowLength);
    }
    @Override
    public void slide() {
        currentPosition++;
        if (!window.队空()&&window.队首()<currentPosition){
            window.办理业务();
        }
        final int newStudentIndex = currentPosition+windowLength-1;
        while (!window.队空()&&source[window.队尾()]>=source[newStudentIndex]){//只有这里和maximum deque不同。
            window.走后门();
        }
        window.排队(newStudentIndex);
    }
}

abstract class MonoIntDeque{
    protected IntArrayDeque window;//存储的是编号。
    protected int[] source;
    protected int windowLength, currentPosition;
//    public MonoIntDeque(int[] source, int windowLength) {//public 但是不能被构造？
    protected MonoIntDeque(int[] source, int windowLength) {
        this.source = source;
        this.windowLength = windowLength;
        init();
    }
    private void init() {
        this.window = new IntArrayDeque(windowLength);
        for(currentPosition = -windowLength;currentPosition<-1;)
            slide();
    }
    public boolean canSlide(){//还是要用canSlide语义。没有slide之前，是-1的状态
        return currentPosition+windowLength<source.length;//比如，0 1 2 3这个数组，窗口2 3认为不能再滑动了，所以不会调用滑动方法。
    }
    public abstract void slide();
    public int getMax(){
        return source[window.队首()];
    }

//    public static void main(String[] args) {
//        new MonoIntDeque(new int[]{1},1);
//    } //父类可以有构造器，但是仍然不能被构造。
}

class IntArrayDeque extends IntArrayQueue{
    public IntArrayDeque() {
        super();
    }

    public IntArrayDeque(int capacity) {
        super(capacity);
    }

    public void 插队(int newItem) throws IndexOutOfBoundsException {
        front = (front - 1+modulus)%modulus;
        super.data[front] = newItem;
    }

    public int 走后门() throws IndexOutOfBoundsException { //pop out //dequeue
        rear = (rear - 1+modulus)%modulus;
        return data[rear];
    }

    public int 队尾() {
        return data[(rear - 1+modulus)%modulus];
    }
}

//定容队列
class IntArrayQueue{
    protected static final int defaultInitialCapacity = 10;
    protected int[] data;
    protected int capacity; //比数组的长度要小一个。
    protected int modulus; //与数组长度一致
    protected int rear = 0;//队尾
    protected int front = 0;//队首

    public IntArrayQueue() {
        this(defaultInitialCapacity);
    }

    public IntArrayQueue(int capacity) {
        this.capacity = capacity;
        modulus = capacity + 1;
        data = new int[modulus];
    }

    private IntArrayQueue(int[] data) {
        this.data = data;
        capacity = data.length - 1;
        modulus = data.length;
        front = 0;
        rear = capacity;//不能再减1。 rear是没有元素的一个位置。
    }

    public static IntArrayQueue readArrayQueueFromOJ(OJReader in, int length) {
        int[] array = new int[length + 1];
        for (int i = 0; i < length; i++) {
            array[i] = in.nextInt();
        }
        return new IntArrayQueue(array);
    }

    protected int inRangeMod(int maybeNegative, int positiveModulus) {
        return (maybeNegative+positiveModulus)%positiveModulus;//由于队列的特殊性。
    }

    public boolean 队空() {
        return front == rear;
    }

    public boolean 队满() {

        return (front - rear+modulus)%modulus == 1;
    }

    public int 队长() {
        return (rear - front+modulus)%modulus;//-1%6 == 5, 所以可以作差
    }

    public void 排队(int item) throws IndexOutOfBoundsException {
        data[rear] = item;
        rear = (rear + 1+modulus)%modulus;
    }

    public int 办理业务() throws IndexOutOfBoundsException {
        var oldValue = data[front];
        front = (front + 1+modulus)%modulus;
        return oldValue;
    }
    public int 队首() throws IndexOutOfBoundsException {
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

class IntArrayStack {
    private static final int defaultInitialCapacity = 10;
    private int[] data;
    private int capacity;
    private int top = -1;
    public IntArrayStack() {
        this(defaultInitialCapacity);
    }

    public IntArrayStack(int initialCapacity) {
        capacity = initialCapacity;
        data = new int[initialCapacity];
    }

    public void 可扩容入栈(int item) {
        try {
            入栈(item);
        } catch (ArrayIndexOutOfBoundsException e) {
            top--;
            capacity <<= 1;
            final var newArray = new int[capacity];
            System.arraycopy(data, 0, newArray, 0, data.length);
            data = newArray;
            入栈(item);
        }
    }

    public void 入栈(int item) throws ArrayIndexOutOfBoundsException {
        data[++top] = item;
    }

    public int 出栈() throws ArrayIndexOutOfBoundsException {
        return data[top--];
    }

    public int 栈顶() throws ArrayIndexOutOfBoundsException {
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