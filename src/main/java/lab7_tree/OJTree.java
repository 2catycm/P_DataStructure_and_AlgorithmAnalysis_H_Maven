package lab7_tree;

import java.util.*;

abstract class ParentImplicitTree<Item>{
    public abstract class ParentImplicitTreeIterator<Item>{
        public abstract void next();
        public boolean hasNext(){
            return getIndex()<ParentImplicitTree.this.size;
        }
        public abstract int getIndex();
    }
    public abstract ParentImplicitTreeIterator<Item> root();
    protected int size = 0; //空树不是null。
    public ParentImplicitTree() {
        this(0);
    }
    public ParentImplicitTree(int size) {
        this.size = size;
    }
    /**
     * @return cardinality of set Vertices.
     */
    public int size(){return size;}
    public int edgesSize(){return size-1;}
    public boolean isEmpty(){return size==0;}
}

class UnWeighedEdgeBasedTree<Item> extends ParentImplicitTree<Item>{
    //TODO
    @Override
    public ParentImplicitTree<Item>.ParentImplicitTreeIterator<Item> root() {
        throw new UnsupportedOperationException();
    }
}
class VoidUnWeighedEdgeBasedTree extends ParentImplicitTree<Void>{
    public ArrayList<Integer>[] vertices;
//    public LinkedList<Integer>[] vertices;
    public int rootIndex;

    @Override
    public String toString() {
        return "VoidUnWeighedEdgeBasedTree{" +
                "vertices=" + Arrays.toString(vertices) +
                ", rootIndex=" + rootIndex +
                '}';
    }

    public VoidUnWeighedEdgeBasedTree(int[][] edges) {
        this(edges.length+1);//边的数量加一才是节点大小
        for (int i = 0; i < edges.length; i++) {
            addEdge(edges[i][0], edges[i][1]);
        }
    }
    public VoidUnWeighedEdgeBasedTree(int size) {
        this(size, 1);
    }
    public VoidUnWeighedEdgeBasedTree(int size, int rootIndex) {
        super(size);
        this.rootIndex = rootIndex;
        vertices = new ArrayList[size+1];
//        vertices = new LinkedList[size+1];
        for (int i = 0; i < vertices.length; i++) {
//            vertices[i] = new ArrayList<>(size-1); //会导致内存超限
            vertices[i] = new ArrayList<>();
//            vertices[i] = new LinkedList<>(); //差不多，不但没有省内存，还慢了一些。
        }

    }
    public void readEdgesFromOJ(OJReader in){
        for (int i = 0; i < size - 1; i++) {
            addEdge(in.nextInt(), in.nextInt());
        }
    }
    public void addEdge(int vertexU, int vertexV){
        vertices[vertexU].add(vertexV);
        vertices[vertexV].add(vertexU);
    }
    //实验性功能，难写
    public ArrayDeque<Integer> depthFirstSearch(int parent, int current, int target, int capacity){
        return depthFirstSearch(parent, current, new int[]{target}, capacity);
    }
    public ArrayDeque<Integer> depthFirstSearch(int parent, int current, int[] targets){
        return depthFirstSearch(parent, current, targets, size);
    }
    public ArrayDeque<Integer> depthFirstSearch(int parent, int current, int[] targets, int capacity){
        Arrays.sort(targets);//支持二分搜索
        final var arrayDeque = new ArrayDeque<Integer>(capacity);
        depthFirstSearch(parent, current, targets, arrayDeque);
        return arrayDeque;
    }
    private boolean depthFirstSearch(int parent, int current, int[] targets, ArrayDeque<Integer> path){
        path.offerLast(current);
        if (targets[Arrays.binarySearch(targets, current)]==current) return true; //binarySearch返回的是插入的位置，所以需要叠一层判断。
        if (this.vertices[current].size()==1 && this.vertices[current].get(0)==parent) {
            path.pollLast();//消除影响
            return false;
        }
        for (var rel : this.vertices[current])
        {
            if (rel==parent)
                continue;
            if(depthFirstSearch(current, rel, targets, path)) return true;
        }
        path.pollLast();//消除影响
        return false;
    }
    @Override
    public ParentImplicitTree<Void>.ParentImplicitTreeIterator<Void> root() {
        throw new UnsupportedOperationException();
    }
}



















/**
 * Edge based tree is friendly to common OJ.
 * The input from OJ is usually undecided unordered n-1 edges.
 *
 * @param <Item> the item is on vertex. can be noting.
 */
@Deprecated
class OldEdgeBasedTree<Item> extends ParentImplicitTree<Item> {
    protected Vertex<Item>[] vertices;
    protected static class Vertex<Item> {
        public Item data;
        public LinkedList<Edge> closedRelatives;//包括parent和children
        public static class Edge{
            public int vertexVIndex;
            public int weight;
            public Edge(int vertexVIndex, int weight) {
                this.vertexVIndex = vertexVIndex;
                this.weight = weight;
            }
        }
        public Vertex(Item data, LinkedList<Edge> closedRelatives) {
            this.data = data;
            this.closedRelatives = closedRelatives;
        }
    }
    public int rootIndex;

    public OldEdgeBasedTree() {
        this(0);//空树不是null。
    }
    public OldEdgeBasedTree(int size) {
        this(size, 1);
    }
    public OldEdgeBasedTree(int size, int rootIndex) {
        super(size);
        this.vertices = new Vertex[size + 1];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex<>(null, new LinkedList<>());
        }
        this.rootIndex = rootIndex;
    }
    //overloading in java for default value is not elegant. I can't reuse the achievement of constructor overloading in reading overloading.
    public static <Item> OldEdgeBasedTree<Item> readEdgeBasedTreeFromOJ(OJReader in) {
        return readEdgeBasedTreeFromOJ(in, in.nextInt());
    }
    public static <Item> OldEdgeBasedTree<Item> readEdgeBasedTreeFromOJ(OJReader in, int size) {
        return readEdgeBasedTreeFromOJ(in, size, 1);
    }
    public static <Item> OldEdgeBasedTree<Item> readEdgeBasedTreeFromOJ(OJReader in, int size, int rootIndex) {
        final var newTree = new OldEdgeBasedTree<Item>(size, rootIndex);
        for (int i = 0; i < size-1; i++) {//edgeCnt is n-1.
            newTree.addEdge(in.nextInt(), in.nextInt(), in.nextInt());
        }
        return newTree;
    }
    public static <Item> OldEdgeBasedTree<Item> readUnweightedEdgeBasedTreeFromOJ(OJReader in) {
        return readUnweightedEdgeBasedTreeFromOJ(in, in.nextInt());
    }
    public static <Item> OldEdgeBasedTree<Item> readUnweightedEdgeBasedTreeFromOJ(OJReader in, int size) {
        return readUnweightedEdgeBasedTreeFromOJ(in, size, 1);
    }
    public static <Item> OldEdgeBasedTree<Item> readUnweightedEdgeBasedTreeFromOJ(OJReader in, int size, int rootIndex) {
        return readUnweightedEdgeBasedTreeFromOJ(in, size, rootIndex, 0);
    }
    public static <Item> OldEdgeBasedTree<Item> readUnweightedEdgeBasedTreeFromOJ(OJReader in, int size, int rootIndex, int weight) {
        final var newTree = new OldEdgeBasedTree<Item>(size, rootIndex);
        for (int i = 0; i < size-1; i++) {//edgeCnt is n-1.
            newTree.addEdge(in.nextInt(), in.nextInt(), weight);
        }
        return newTree;
    }
    public Item getVertexData(int vertexU){
        return vertices[vertexU].data;
    }
    public void setVertexData(int vertexU, Item data){
        vertices[vertexU].data = data;
    }
    public void addEdge(int vertexU, int vertexV, int edgeWeight){
        vertices[vertexU].closedRelatives.add(new Vertex.Edge(vertexV, edgeWeight));
        vertices[vertexV].closedRelatives.add(new Vertex.Edge(vertexU, edgeWeight));
    }
    @Override
    public EdgeBasedTreeIterator<Item> root() {
        return null;
    }
    public class EdgeBasedTreeIterator<Item> extends ParentImplicitTreeIterator<Item> {
        @Override
        public void next() {

        }
        @Override
        public int getIndex() {
            return 0;
        }
    }
}
