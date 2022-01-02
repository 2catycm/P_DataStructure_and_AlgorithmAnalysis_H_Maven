package lab7_tree;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * Rooted Ordered Tree.
 * Tree is a connected acyclic graph (Vertices, Edges).
 * @param <Item> Besides structure, data of type Item is stored in every vertex.
 */
abstract class Tree<Item> /*implements Graph*/{
    protected int size;
    public Tree() {
        this(0);//空树不是null。
    }
    public Tree(int size) {
        this.size = size;
    }
    /**
     * @return cardinality of set Vertices.
     */
    public int size(){return size;}
    public int edgesSize(){return size-1;}
    public boolean isEmpty(){return size==0;}

    /**
     * Must implement this in the tree method, but not the iterator method root();
     * @return the iterator for the root of the tree.
     */
    public abstract TreeNodeIterator<Item> root();
    public abstract Tree<Item> subTree(TreeNodeIterator<Item> newRoot);
    public abstract Tree<Item> combinedTree(Item newRootData, LinkedList<Tree<Item>> subTrees);
    public int height(){return root().height();}
}

/**
 * 1.Iterator is not data. Data representation is the duty of Tree class.
 * 2.Iterator knows its Tree T. Tree T gives iterator of itself.
 * @param <Item>
 */
abstract class TreeNodeIterator<Item>{
    protected Tree<Item> master;
    public TreeNodeIterator(Tree<Item> master) {
        this.master = master;
    }
    //Basic data getter and setter.
    public abstract Item getData();
    public abstract void setData();
    //Basic interface for tree structure.
    public abstract TreeNodeIterator<Item> parent();
    public abstract TreeNodeIterator<Item> firstChild();
    public abstract TreeNodeIterator<Item> nextSibling();
    public TreeNodeIterator<Item> root(){return master.root();}//This method is master dependent.

    /**
     * Note that this method is master tree dependent.
     * @return the depth of the node, which is the size of pathToRoot(node).
     */
    public int depth(){ //This method is master dependent. The size of pathToRoot.
        int depth = 0;
        for(var it = this;it!=root();it = it.nextSibling(), depth++) ;
        return depth;
    }
    public int degree(){
        int degree = 0;
        for(var it = this;it!=null;it = it.nextSibling(), degree++) ;
        return degree;
    }

    /**
     *
     * @return the height of the node, or the height of subtree(node).
     */
    public int height(){
        int height = -1;
        for(var it = this;it!=null;it = it.firstChild(), height++) ;
        return height;
    }
    public ArrayList<TreeNodeIterator<Item>> pathToRoot(){
        final var path = new ArrayList<TreeNodeIterator<Item>>();
        for(var it = this;it!=null;it = it.parent()) {
            path.add(it);
        }
        return path;
    }
    public Tree<Item> subTree(){return master.subTree(this);}//This depends on the master data representation.
}


///**
// * Left Child Right Sibling Representation of Rooted Ordered Tree
// */
//class LCRSTree<Item> extends Tree<Item>{
//
//}
//class LCRSTreeIterator extends TreeNodeIterator{
//
//}