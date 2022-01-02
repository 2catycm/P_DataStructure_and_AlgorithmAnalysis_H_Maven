package lab8_advanced_tree;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Predicate;

//文件组织格式：下面的代码实现上面的代码
abstract class AbstractBinarySearchTree<Key extends Comparable<Key>, Value> extends AbstractOrderedSymbolTable<Key, Value>{
    protected Predicate<Entry<Key, Value>> getLegalTreeEntryCondition(){return entry -> true;} //如果不写成方法，就不是子类的condition。
    protected boolean isLegalTree(){
        final var legalTreeEntryCondition = getLegalTreeEntryCondition(); //多态的。
        Queue<Entry<Key, Value>> queue = new ArrayDeque<>(size());
        queue.offer(root);
        for (;!queue.isEmpty();){
            final var entry = queue.poll();
            if(!legalTreeEntryCondition.test(entry)) return false;
            if (entry.left!=null)
                queue.offer(entry.left);
            if (entry.right!=null)
                queue.offer(entry.right);
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }

    protected static Entry noNewNodeCreated = new Entry<>("no new node created", null, null, null, null, 0, -1);
    /**
     * 帮助子类实现了子类root的情况，也就是从空树到不是空树的过程。子类只需要实现不是root的情况，这在abstract中给出。
     * 同时，帮助子类更新了树的高度和节点数量，这主要是为了性能考虑，防止子类去更新。
     * @param key
     * @param value
     * @return 如果是新的节点，返回放置的新节点。否则返回 noNewNodeCreated。 noNewNodeCreated的key和value，目前可以保证是子类的返回是对的，但不能保证线程安全。
     */
    @Override
    protected Entry<Key, Value> putEntry(Key key, Value value) {
        assert(key!=null);
//        root = putEntry(root, key, value);//为了进行一波递归
//        return root;
        if (root == null) {
            root = new Entry<>(key, value, null, null, null, 1, 0);
            return root;
        }
        final var putEntry = putEntry(root, key, value);
        if (putEntry!=noNewNodeCreated)
            for (var upIt = putEntry.parent;upIt!=null;upIt = upIt.parent){
                upIt.size = newSizeOf(upIt);
                upIt.height = newHeightOf(upIt);
            }
        return putEntry;
    }

    /**
     * 帮助子类定位了要删除的位置，子类再根据其知识进行判断。这是很耦合的，容易有内部bug。
     * @param key
     * @return key被删除之前在树中的值，如果没有值，返回null。
     */
    @Override
    public Value remove(Key key) {
        final var entry = getEntry(key);
        if (entry==null)
            return null;
        else {
            removeEntry(entry);
            return entry.getValue();
        }
    }
    /**
     * @param entry
     * @return entry被删除之前再tree中的值，如果没有值，返回null
     */
    protected abstract Entry<Key, Value> removeEntry(Entry<Key, Value> entry);
    protected static Entry noNewNodeDeleted = new Entry<>("no new node deleted", null, null, null, null, 0, -1);



    @Override
    protected Entry<Key, Value> getEntry(Key key) {
        assert(key!=null);
        if (root==null) return null;
        return getEntry(root, key);
    }

    @Override
    protected Entry<Key, Value> getPredecessorEntry(Key key) {
        assert(key!=null);
        if (root==null)
            return null;
        return getPredecessorEntry(root, key);
    }

    @Override
    protected Entry<Key, Value> getSuccessorEntry(Key key) {
        assert(key!=null);
        if (root==null)
            return null;
        return getSuccessorEntry(root, key);
    }

    protected static final class Entry<K, V> implements AbstractSymbolTable.Entry<K, V> { //不继承，子类（平衡树）可以继承LinkingInformation进行多态。
        K key;
        V value;
        Entry<K, V> left;
        Entry<K, V> right;

        Entry<K, V> parent;
        int size;
        int height;

        public Entry(K key, V value, Entry<K, V> left, Entry<K, V> right, Entry<K, V> parent, int size, int height) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.size = size;
            this.height = height;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        public Entry<K, V> getLeft() {
            return left;
        }

        public Entry<K, V> getRight() {
            return right;
        }

        public Entry<K, V> getParent() {
            return parent;
        }

        public int getSize() {
            return size;
        }

        public int getHeight() {
            return height;
        }

        @Override
        public String toString() {//ToString只打印别人的key
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    ", left=" + keyOf(left) +
                    ", right=" + keyOf(right) +
                    ", parent=" + keyOf(parent) +
                    ", size=" + size +
                    ", height=" + height +
                    '}';
        }
    }
    protected Entry<Key, Value> root = null;

    /**
     * null 也认为是leaf的一种.
     * @param node
     * @param <Key>
     * @param <Value>
     * @return
     */
    public static <Key, Value> boolean isLeaf(Entry<Key, Value> node) {
//        assert (node!=null);
        return  node==null || (node.left == null && node.right == null);
    }
    public static <Key, Value> Key keyOf(Entry<Key, Value> node) {
        return node==null ? null:node.key;
    }
    public static <Key, Value> Value valueOf(Entry<Key, Value> node) {
        return node==null ? null:node.value;
    }
    public static <Key, Value> Entry<Key, Value> leftOf(Entry<Key, Value> node) {
        return node==null ? null:node.left;
    }
    public static <Key, Value> Entry<Key, Value> rightOf(Entry<Key, Value> node) {
        return node==null ? null:node.right;
    }
    public static <Key, Value> Entry<Key, Value> parentOf(Entry<Key, Value> node) {
        return node==null ? null:node.parent;
    }

    public static <Key, Value> int sizeOf(Entry<Key, Value> node) {
        return node == null  ? 0 : node.size;
    }

    public static <Key, Value> int heightOf(Entry<Key, Value> node) {
        return node == null  ? -1 : node.height;
    }

    protected static <Key, Value> int newHeightOf(Entry<Key, Value> x){
        return Math.max(heightOf(x.left), heightOf(x.right))+1;
    }
    protected static <Key, Value> int newSizeOf(Entry<Key, Value> x){
        return sizeOf(x.left)+sizeOf(x.right)+1;
    }

    @Override
    public int size() {
        return sizeOf(root);
    }

    @Override
    public String toString() {
        if (root==null)
            return "Empty Tree";
        StringBuilder builder = new StringBuilder();
        Queue<Entry<Key, Value>> queue = new ArrayDeque<>(size());
        queue.offer(root);
        for (;!queue.isEmpty();){
            final var entry = queue.poll();
            builder.append(entry).append("\n");
            if (entry.left!=null)
                queue.offer(entry.left);
            if (entry.right!=null)
                queue.offer(entry.right);
        }
        return builder.toString();
    }

    @Override
    public Key minKey() {
        if (root==null)
            return null;
        final var minKey = minEntry(root);
        return minKey == null ? null : minKey.getKey();
    }
    @Override
    public Key maxKey() {
        if (root==null)
            return null;
        final var maxKey = maxEntry(root);
        return maxKey == null ? null : maxKey.getKey();
    }


    //要求子类实现
    protected abstract Entry<Key, Value> putEntry(Entry<Key, Value> subTree, Key key, Value value);

    protected abstract Entry<Key, Value> getEntry(Entry<Key, Value> subTree, Key key);

    protected abstract Entry<Key, Value> getPredecessorEntry(Entry<Key, Value> subTree, Key key);

    protected abstract Entry<Key, Value> getSuccessorEntry(Entry<Key, Value> subTree, Key key);

    protected abstract Entry<Key, Value> minEntry(Entry<Key, Value> subTree);
    protected abstract Entry<Key, Value> maxEntry(Entry<Key, Value> subTree);
}