//package lab8;
//
//
//class BinarySearchTree<Key extends Comparable<Key>, Value> extends AbstractOrderedSymbolTable<Key, Value> {
//    protected static final class Entry<K, V> implements AbstractSymbolTable.Entry<K, V> { //不继承，子类（平衡树）可以继承LinkingInformation进行多态。
//        K key;
//        V value;
//        LinkingInformation<K, V> links;
//
//        public Entry(K key, V value, LinkingInformation<K, V> links) {
//            this.key = key;
//            this.value = value;
//            this.links = links;
//        }
//
//        @Override
//        public K getKey() {
//            return key;
//        }
//
//        @Override
//        public V getValue() {
//            return value;
//        }
////        public V selfRemove(){
////            assert (links.isLeaf());
////            final var parent = links.parent;
////            this.links = null;
////            if (parent.links.left==this)
////                parent.links.left = null;
////            else
////                parent.links.right = null;
////            return this.value;
////        }
//    }
//
//    protected static class LinkingInformation<K, V> {//继承很难搞，干脆把可能用到的属性都添加上。
//        Entry<K, V> left;
//        Entry<K, V> right;
////        Entry<K, V> parent; //有了parent 一切都变复杂了.
////        int size;
//
//        public LinkingInformation(Entry<K, V> left, Entry<K, V> right/*, Entry<K, V> parent*//*, int size*/) {
//            this.left = left;
//            this.right = right;
////            this.parent = parent;
////            this.size = size;
//        }
//
//        public boolean isLeaf() {
//            return left == null && right == null;
//        }
//    }
//
//    protected Entry<Key, Value> root = null;
//
////    public int size() {
////        return size(root);
////    }
////
////    private int size(Entry<Key, Value> node) {
////        return (node == null || node.links == null) ? 0 : node.links.size;
////    }
//
//    @Override
//    protected Entry<Key, Value> getEntry(Key key) {
//        Entry<Key, Value> curr;
//        for (curr = root; curr != null; ) {
//            final var comparison = curr.key.compareTo(key);
//            if (comparison == 0)
//                return curr;
//            else if (comparison > 0) {//key往左走
//                curr = curr.links.left;
//            } else {
//                curr = curr.links.right;
//            }
//        }
//        return /*curr != null ? curr.value :*/ null;
//    }
//
//    @Override
//    public AbstractSymbolTable.Entry<Key, Value> putEntry(Key key, Value value) {
//        return null;
//    }
//
////    @Override
////    public Value put(Key key, Value value) {
////        if (root == null) {
////            root = new Entry<>(key, value, new LinkingInformation<>(null, null/*, null*//*, 1*/));
////            return value;
////        }
////        return put(root, key, value);
////    }
////
////    //put the value by dfs the tree.
////    private Value put(Entry<Key, Value> node, Key key, Value value) {
////        assert (node != null);//不能到达叶子。
////        final var comparison = node.key.compareTo(key);
////        Value ret;
////        if (comparison == 0) {
////            final var oldValue = node.value;
////            node.value = value;
////            ret = oldValue;
////        } else if (comparison > 0) { //node比较大，走左边。
////            if (node.links.left == null) {
////                node.links.left = new Entry<>(key, value, new LinkingInformation<>(null, null/*, node*//*,1*/));
////                ret = value;
////            } else {
////                ret = put(node.links.left, key, value);
////            }
////        } else {
////            if (node.links.right == null) {
////                node.links.right = new Entry<>(key, value, new LinkingInformation<>(null, null/*, node*//*, 1*/));
////                ret = value;
////            } else {
////                ret = put(node.links.right, key, value);
////            }
////        }
//////        node.links.size = size(node.links.left) + size(node.links.right) + 1;
////        return ret;
////    }
//
//    @Override
//    protected Entry<Key, Value> getPredecessorEntry(Key key) {
//        Entry<Key, Value> predecessor = null, curr;
//        for (curr = root; curr != null; ) {
//            final var comparison = curr.key.compareTo(key);
//            if (comparison == 0) {
//                predecessor = curr;
//                break;
//            } else if (comparison > 0) {//curr.key is bigger, so it is never the predecessor.
//                curr = curr.links.left;
//            } else {//curr.key may be the predecessor, but there may be a more close one.
//                predecessor = curr;
//                curr = curr.links.right;
//            }
//        }
//        return predecessor;
//    }
//
//    @Override
//    protected Entry<Key, Value> getSuccessorEntry(Key key) {
//        return getSuccessorEntry(root, key);
//    }
//
//    protected Entry<Key, Value> getSuccessorEntry(Entry<Key, Value> subtree, Key key) {
//        Entry<Key, Value> successor = null, curr;
//        for (curr = subtree; curr != null; ) {
//            final var comparison = key.compareTo(curr.key);
//            if (comparison == 0) {
//                successor = curr;
//                break;
//            } else if (comparison > 0) {//curr.key is smaller, so it is never the successor.
//                curr = curr.links.right;
//            } else {//curr.key may be the successor, but there may be a more close one.
//                successor = curr;
//                curr = curr.links.left;
//            }
//        }
//        return successor;
//    }
//
//    //return the new subtree root.
//    public static <K, V> Entry<K, V> rotateToRight(Entry<K, V> leftDown, Entry<K, V> rightUp) {
//        //可以不动parent的情况下完成这个操作。
//        assert (rightUp.links.left == leftDown);
//        final var rightDown = new Entry<K, V>(rightUp.key, rightUp.value, new LinkingInformation<>(leftDown.links.right, rightUp.links.right/*, rightUp*/));
//        rightUp.links.right = rightDown;
//        rightUp.key = leftDown.key;
//        rightUp.value = leftDown.value;
//        rightUp.links.left = leftDown.links.left;
//        return rightUp;
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return false;
//    }
//
//    @Override
//    public Value remove(Key key) {
//        throw new UnsupportedOperationException("太复杂了，需要改成没有parent的版本。");
////        final var predecessorEntry = getPredecessorEntry(key);
////        assert (predecessorEntry.key.compareTo(key)==0);
////        final var ret = predecessorEntry.value;
////        if (predecessorEntry.links.right!=null){
////            final var successorEntry = getSuccessorEntry(predecessorEntry.links.right, key);
////            assert (successorEntry!=null);
////            predecessorEntry.key = successorEntry.key;
////            predecessorEntry.value = successorEntry.value;
////            if (successorEntry.links.isLeaf()){
////                successorEntry.selfRemove();
////            }else{
////                //now successor entry has a right child, we want the right child to replace its place.
////                final var rightChild = successorEntry.links.right;
////                assert (rightChild !=null && successorEntry.links.left==null);
////                final var newParent = successorEntry.links.parent;
////                rightChild.links.parent = newParent;
////                if(newParent.links.left == successorEntry)
////                    newParent.links.left = rightChild;
////                else
////                    newParent.links.right = rightChild;
////            }
////        }else if (predecessorEntry.links.left!=null){ //no right child, but there is left child.
////
////        }else{//leaf case
////           predecessorEntry.selfRemove();
////        }
////        return ret;
//    }
//
//    public Value clear() {
//        if (root == null) return null;
//        final var oldValue = root.value;
//        root = null;
//        return oldValue;
//    }
//}
