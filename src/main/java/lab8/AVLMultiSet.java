package lab8;

import java.util.*;
interface OrderedSet<Key extends Comparable<Key>>{
    int size();
    boolean isEmpty();
    Key minKey();
    Key maxKey(); //这个和predecessor、successor是不同的。有些类型没有最大值最小值，就不能调用前继查找和后继查找来做min、max。
    Key floor(Key key);
    Key ceiling(Key key);
    boolean add(Key key);
    boolean remove(Key key);
    boolean contains(Key key);
}
//需要单独include
class AVLMultiSet<Key extends Comparable<Key>> implements OrderedSet<Key> {
    AVLSearchTree<Key, Integer> tree = new AVLSearchTree<>();
    @Override
    public boolean remove(Key key) {
        final var entry = tree.getEntry(key);
        if (entry==null)
            return false; //找不到删不了
        else{
            if (--entry.value<=0) //这个entry是指针引用，可以直接修改值。
                tree.removeEntry(entry);
        }
        return true;
    }
    @Override
    public boolean add(Key key) {
        final var entry = tree.getEntry(key);
        if (entry==null)
            tree.put(key, 1);
        else{
            tree.putEntry(entry, key, entry.value+1);
        }
        return true;
    }


    @Override
    public Key minKey() {
        return tree.minKey();
    }

    @Override
    public Key maxKey() {
        return tree.maxKey();
    }





    @Override
    public boolean contains(Key key) {
        return tree.get(key)!=null; //bst保证了查不到就返回null.
    }


    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public Key floor(Key key) {
        return tree.getPredecessorKey(key);
    }

    @Override
    public Key ceiling(Key key) {
        return tree.getSuccessorKey(key);
    }

    @Override
    public String toString() {
        return "AVLMultiSet{" +
                "tree=" + tree +
                '}';
    }
}
