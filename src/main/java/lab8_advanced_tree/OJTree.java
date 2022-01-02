package lab8_advanced_tree;
//lab7是无权树，这里是有权树。

import java.util.ArrayList;
import java.util.Arrays;

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

class VoidWeighedEdgeBasedTree extends ParentImplicitTree<Void>{
    public ArrayList<Edge>[] adjacencyList;//包括parent和children
    public static class Edge{
        public int vertexVIndex;
        public int weight;
        public Edge(int vertexVIndex, int weight) {
            this.vertexVIndex = vertexVIndex;
            this.weight = weight;
        }
        @Override
        public String toString() {
            return "Edge{" +
                    "vertexVIndex=" + vertexVIndex +
                    ", weight=" + weight +
                    '}';
        }
    }
    public int rootIndex;
    public VoidWeighedEdgeBasedTree(int[][] edges) {
        this(edges.length+1);//边的数量加一才是节点大小
        for (int i = 0; i < edges.length; i++) {
            addEdge(edges[i][0], edges[i][1], edges[i][2]);
        }
    }
    public VoidWeighedEdgeBasedTree(int size) {
        this(size, 1);
    }
    public VoidWeighedEdgeBasedTree(int size, int rootIndex) {
        super(size);
        this.rootIndex = rootIndex;
        adjacencyList = new ArrayList[size+1];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }
    public void readEdgesFromOJ(OJReader in){
        for (int i = 0; i < size - 1; i++) {
            addEdge(in.nextInt(), in.nextInt(), in.nextInt());
        }
    }
    public void addEdge(int vertexU, int vertexV, int weight){
        adjacencyList[vertexU].add(new Edge(vertexV, weight));
        adjacencyList[vertexV].add(new Edge(vertexU, weight));
    }

    @Override
    public ParentImplicitTree<Void>.ParentImplicitTreeIterator<Void> root() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "VoidWeighedEdgeBasedTree{" +
                "adjacencyList=" + Arrays.toString(adjacencyList) +
                ", rootIndex=" + rootIndex +
                '}';
    }
}