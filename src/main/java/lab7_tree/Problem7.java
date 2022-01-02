package lab7_tree;

//# pragma OJ Main
public class Problem7 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final var tree =
                new VoidUnWeighedEdgeBasedTree(in.nextInt());//n in [1,200 000]
        tree.readEdgesFromOJ(in);
        out.printlnIntArray(solve(tree), "\n");
    }
    static int[] solve(VoidUnWeighedEdgeBasedTree tree) {
        int[] critical = new int[tree.size + 1];
        int[] size = new int[tree.size + 1];
//        int[] distanceSum = new int[tree.size + 1]; //也就是k

        int[] parents = new int[tree.size + 1];
        postOrderInference(0, 1, tree, size, /*distanceSum,*/ critical, parents);
        return critical;
    }

    private static void postOrderInference(int parent, int current, VoidUnWeighedEdgeBasedTree tree, int[] size,/* int[] distanceSum,*/ int[] critical, int[] parents) {
        //把parent推断出来，目的是让后面重心可以向上移动。
        parents[current] = parent;
        //叶子结点的处理
        if (tree.vertices[current].size() == 1 && tree.vertices[current].get(0) == parent) {
            size[current] = 1;
//            distanceSum[current] = 0;
            critical[current] = current; //自己就是重心。
        }
        for (var relative : tree.vertices[current]) {
            if (relative == parent)
                continue;
            postOrderInference(current, relative, tree, size, /*distanceSum,*/ critical, parents);
            //先处理完子节点，再处理自己。
            size[current]+=size[relative];
        }
        //先处理完子节点，再处理自己。
        size[current]++;
/*        //处理distance sum
        for (var relative : tree.vertices[current]) {
            if (relative == parent)
                continue;
            distanceSum[current] = distanceSum[relative] + size[relative]<<1-size[current];
            break;//只要其中一个子树就可以求了。
        }
        !distanceSum要重新定义。size[current]是新的，不能根据以往的去求！
        int[] distanceSum = new int[tree.size+1];
        distanceSumInference(parent, current, tree, size, distanceSum);
        不要真的求出来，一定很慢。
        求解critical[current]. 此时已经有前面的critical。
        int bestNewCriticalPoint = -1;
        for (var relative : tree.vertices[current]) {
            if (relative == parent)
                continue;
            int possibleNewCriticalPoint;
            for(possibleNewCriticalPoint = critical[relative]; ; possibleNewCriticalPoint = parents[possibleNewCriticalPoint]){
                int cost = size[possibleNewCriticalPoint]<<1 - size[parents[possibleNewCriticalPoint]];
                if (cost>0)
                    break;
            }
            if ()
        }*/
        //求解critical[current]. 此时已经有前面的critical。
        //只用搞最重的子树就行了
        final var heaviestSubTree = heaviestSubTreeOfSubTree(parent, current, tree, size);

    }
    private static int heaviestSubTreeOfSubTree(int parent, int inputSubTreeIndex, VoidUnWeighedEdgeBasedTree tree, int[] size){
//        !逻辑错了，万一adj[0]就是parent呢
//        final var adjacencyList = tree.vertices[inputSubTreeIndex];
//        int heaviest = 0;
//        final var degree = adjacencyList.size();
//        for (int i = 1; i < degree; i++) {
//            final var relative = adjacencyList.get(i);
//        }
        int heaviest = -1;
        boolean firstTime = true;
        for (var relative:tree.vertices[inputSubTreeIndex]){
            if (relative==parent)
                continue;
            if (firstTime){
                firstTime = false;
                heaviest = relative;
                continue;
            }
            if (size[relative]>size[heaviest])
                heaviest = relative;
        }
        return heaviest;
    }
    @Deprecated
    private static void distanceSumInference(int parent, int current, VoidUnWeighedEdgeBasedTree tree, final int[] size, int[] distanceSum) {//size是已知的。
        //叶子结点的处理
        if (tree.vertices[current].size() == 1 && tree.vertices[current].get(0) == parent) {
            distanceSum[current] = 0;
        }
        for (var relative : tree.vertices[current]) {
            if (relative == parent)
                continue;
            distanceSum[current] = distanceSum[relative] + size[relative]<<1-size[current];
            break;//只要其中一个子树就可以求了。
        }
    }

}
//# include "OnlineJudgeIO.java"
//# include "OJTree.java"