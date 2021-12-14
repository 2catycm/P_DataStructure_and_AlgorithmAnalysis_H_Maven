package lab8;


interface SymbolTable<Key extends Comparable<Key>, Value>{
    int size();
    boolean isEmpty();
    Value get(Key key) ;
    Value put(Key key, Value value);
    default Value remove(Key key){return put(key, null);} //pend remove.  delay to delete.
}
interface OrderedSymbolTable<Key extends Comparable<Key>, Value> extends SymbolTable<Key, Value>{
    Key getPredecessorKey(Key key);
    Key getSuccessorKey(Key key);
    Key minKey();
    Key maxKey();
}
abstract class AbstractSymbolTable<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value>{
    public static interface Entry<K, V> {
        K getKey();
        V getValue();
    }
    @Override
    public Value get(Key key) {
        final var entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }
    //    不能保证，F题也不需要：The return Value is the replaced value if the key already existed before.
    @Override
    public Value put(Key key, Value value){
        final var entry = putEntry(key, value);
        return entry == null ? null:entry.getValue();
    }




    //需要子类实现。
    protected abstract Entry<Key, Value> getEntry(Key key);


    //Old entry.
    protected abstract Entry<Key, Value> putEntry(Key key, Value value);


}

//class LinkedBinaryTree<Entry>{
//   protected class BinaryNode<Entry>{
//       Entry data;
//       BinaryNode<Entry> leftChild;
//       BinaryNode<Entry> rightChild;
//       public BinaryNode(Entry data, BinaryNode<Entry> leftChild, BinaryNode<Entry> rightChild) {
//           this.data = data;
//           this.leftChild = leftChild;
//           this.rightChild = rightChild;
//       }
//   }
//   BinaryNode<Entry> root;
//}
abstract class AbstractOrderedSymbolTable<Key extends Comparable<Key>, Value> extends AbstractSymbolTable<Key, Value> implements OrderedSymbolTable<Key, Value>{
    @Override
    public Key getPredecessorKey(Key key) {
        final var predecessorEntry = getPredecessorEntry(key);
        return predecessorEntry == null ? null : predecessorEntry.getKey();
    }
    @Override
    public Key getSuccessorKey(Key key) {
        final var successorEntry = getSuccessorEntry(key);
        return successorEntry == null ? null : successorEntry.getKey();
    }



    //需要子类实现
    protected abstract Entry<Key, Value> getPredecessorEntry(Key key);
    protected abstract Entry<Key, Value> getSuccessorEntry(Key key);
}

//# include "AbstractBinarySearchTree.java"
//# include "BinarySearchTree.java"
//# include "AVLSearchTree.java"