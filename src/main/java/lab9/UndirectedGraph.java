package lab9;

import java.util.*;

//代码组织，上面的代码依赖于下面的实现。
class UndirectedGraph {
//    public boolean hasCycle() {
//        int time = 0;
////        Stack<Integer> stack = new Stack<>();
////        stack.push(1);
////        BitSet isVisited = new BitSet();//red or yellow.
////        isVisited.set(1);
////        do
////        while(!stack.empty()){
////            final var top = stack.peek();
////            for(var relative:relativesOf(top)){
////                if (!isVisited.get(relative))
////                    stack.push(top);
////
////            }
////            stack.pop();
////        }
////        while(new Predicate<UndirectedGraph>(){
////            @Override
////            public boolean test(UndirectedGraph graph) {
////                for (int i = 1; i < graph.verticesCnt+1; i++) {
////                    if (!isVisited.get(i))
////                        return true;//continue to dfs
////                }
////                return false;
////            }
////        }.test(this));
//    }
    private static Random random = new Random();
    /**
     * 1. 这是 无向图 的判环方法，不是有环图。
     * 2. 这个图允许自环、平行边。
     * 3. 路径的定义。简单路径的定义。（不允许有重复顶点的路径） 可以重复边？（不可以重复边，否则存在顶点重复） u-v-u不是简单路径
     * 4. 环的定义。 简单环的定义。（不允许重复顶点和边， 除了起点和终点这两个顶点是重复的。）  u-v-u（u和v之间有两条边）
     * （特别地，因为起点和终点可以重复，所以规定不允许重复边）
     * (特别地，自环是简单环，因为没有重复顶点和边)
     * @return
     */
    /*public boolean hasCycle2(){
        if (edgesCnt>=verticesCnt)
            return true;
        BitSet isVisited = new BitSet(verticesCnt);
        final int parent = 0; isVisited.set(parent);
//        int dfsStart = random.nextInt(verticesCnt)+1;
        int dfsStart = 1;
        dw: do {
            int[] ev = new int[2];
            inferEVOfSubGraph(parent, dfsStart, ev, isVisited);
            if(ev[0]>=ev[1])
                return true;
            for (dfsStart = 1; dfsStart <= verticesCnt; dfsStart++) {
                if (!isVisited.get(dfsStart))
                    continue dw;
            }
            break;
        }while(true);
        return false;
    }
    private void inferEVOfSubGraph(int parent, int current, int[] ev, BitSet isVisited) {
        ev[1]++;
        isVisited.set(current);
        for(var relative:relativesOf(current)){
            if (relative==current)
                ev[0]++; //自环算一个边，但是不要走过去。
            else if (isVisited.get(relative)) //
                continue; //当时已经算过一次了
            else {
                ev[0]++; //算它
                inferEVOfSubGraph(current, relative, ev, isVisited);
            }
        }
    }*/
    public boolean hasCycle(){
        BitSet isVisited = new BitSet(verticesCnt);
        final int parent = 0; isVisited.set(parent);
//        int dfsStart = random.nextInt(verticesCnt)+1;
        int dfsStart = 1;
//        dw: do {
//            if(hasCycleDFS(parent, dfsStart, isVisited))
//                return true;
//            for (dfsStart = 1; dfsStart <= verticesCnt; dfsStart++) {
//                if (!isVisited.get(dfsStart))
//                    continue dw;
//            }
//            break;
//        }while(true);
        for (int i = 1; i <= verticesCnt; i++) {
            if (!isVisited.get(i))
                if (hasCycleDFS(parent, i, isVisited))
                    return true; //已经找到了环。
                //else continue;
            //else continue; 继续找下一个没有访问过的。
            //不会遗漏，因为i能够到这一步，小于当前i的所有节点都已经遍历过。
        }
        return false;//所有的不连通子图都没有环，总体没有环。
    }
    //边没有显式定义。
    private boolean hasCycleDFS(int parent, int current, BitSet isVisited){
        assert (isVisited.get(parent));
//        if (isVisited.get(current))
//            return true;
        isVisited.set(current);
        boolean parentPathNotAvailable = true; //与这个顶点连接的边，要去掉一条来自顶点的边
        for(var relative:relativesOf(current)){
            if (relative==current)
                return true; //自环显然是环
            if (parentPathNotAvailable && (relative == parent)) {
                parentPathNotAvailable = false;
                continue;
            } //如果到父节点有至少两条边，那么一定有环。这在下一句有判断到
            if (isVisited.get(relative)) //
                return true; //如果对方被访问过，那么一定是有环造成的。
            //现在的情况是，子图没有被访问，访问子图。
//            return hasCycleDFS(current, relative, isVisited);//错误
            //上面的是错误的，因为如果子图没有环，不代表当前节点的其他子节点没有环。
            //正确代码如下：
            if (hasCycleDFS(current, relative, isVisited))
                return true;
            //else continue;隐含的是，继续检查其他亲戚（其他邻接顶点）
        }
        return false;//如果没有relative，就没有环
    }
    public int getVerticesCnt() {
        return verticesCnt;
    }

    public int getEdgesCnt() {
        return edgesCnt;
    }


    //支持1-v的节点下标
    public void addEdge(int vertexU, int vertexV){
        adjacencyTables[vertexU].add(vertexV);
        adjacencyTables[vertexV].add(vertexU);
        edgesCnt++;
    }
    //支持1-v的节点下标
    public Iterable<Integer> relativesOf(int vertexV){
        return adjacencyTables[vertexV];
    }
    public UndirectedGraph(int verticesCnt, int edgesCnt, OJReader in){
        this(verticesCnt);
        for (int i = 0; i < edgesCnt; i++) {
            addEdge(in.nextInt(), in.nextInt());
        }
    }
    public UndirectedGraph(int verticesCnt) {
        this.verticesCnt = verticesCnt;
        this.adjacencyTables = new LinkedList[verticesCnt+1];
        for (int i = 1; i < adjacencyTables.length; i++) {
            adjacencyTables[i] = new LinkedList<>();
        }
    }
    private int verticesCnt;//支持1-v的节点下标
    private int edgesCnt;
    private LinkedList<Integer>[] adjacencyTables;

    @Override
    public String toString() {
        return "UndirectedGraph{" +
                "verticesCnt=" + verticesCnt +
                ", edgesCnt=" + edgesCnt +
                ", adjacencyTables=" + Arrays.toString(adjacencyTables) +
                '}';
    }
}
