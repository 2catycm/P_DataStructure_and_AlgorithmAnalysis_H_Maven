package lab7_tree;

//bug1：
// 4
//1 2
//2 3
//3 4

//# pragma OJ Main
public class Problem7New {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final var tree =
                new VoidUnWeighedEdgeBasedTree(in.nextInt());//n in [1,200 000]
        tree.readEdgesFromOJ(in);
        final var solve = solve(tree);
//        out.printlnArray(solve, "\n");
        for (int i = 1; i < solve.length; i++) {
            out.println(solve[i]);
        }
    }
    public static class TwoOptionalIntegers{
        private final int requiredIntA;//necessary，essential， indispensable。
        private final Integer optionalIntB;
        public TwoOptionalIntegers(int requiredIntA) {
            this(requiredIntA, null);
        }
        public TwoOptionalIntegers(int requiredIntA, Integer optionalIntB) {
            this.requiredIntA = requiredIntA;
            this.optionalIntB = optionalIntB;
        }
        public boolean hasOptional(){return optionalIntB!=null;}
        public int getRequiredIntA() {
            return requiredIntA;
        }

        public Integer getOptionalIntB() {
            return optionalIntB;
        }

        @Override
        public boolean equals(Object o) {
            return toString().equals(o.toString());
        }

        @Override
        public int hashCode() {
            int result = requiredIntA;
            result = 31 * result + (optionalIntB != null ? optionalIntB.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
//            return  ((optionalIntB==null)?"":optionalIntB+" ") + requiredIntA+"";
//            return requiredIntA+""+ ((optionalIntB==null)?"":" "+optionalIntB) ;
            if (optionalIntB==null)
                return requiredIntA+"";
            else
                return Math.min(requiredIntA, optionalIntB)+" "+ Math.max(requiredIntA, optionalIntB);
        }
    }
//    static TwoOptionalIntegers[] bruteForceSolve(VoidUnWeighedEdgeBasedTree tree) {
//        TwoOptionalIntegers[] critical = new TwoOptionalIntegers[tree.size+1];
//        int[] size = new int[tree.size + 1];
//        int[] parents = new int[tree.size + 1];
//        postOrderInference(0, 1, tree, size, critical, parents);
//        critical = new TwoOptionalIntegers[tree.size+1];
//        for (int i = 1; i < critical.length; i++) {
//
//        }
//    }
//    private static void dfs(int parent, int current){
//
//    }
    static TwoOptionalIntegers[] solve(VoidUnWeighedEdgeBasedTree tree) {
        TwoOptionalIntegers[] critical = new TwoOptionalIntegers[tree.size+1];
        int[] size = new int[tree.size + 1];
        int[] parents = new int[tree.size + 1];
        postOrderInference(0, 1, tree, size, critical, parents);
        return critical;
    }

    private static void bruteForcePostOrderInference(int parent, int current, VoidUnWeighedEdgeBasedTree tree, int[] size, TwoOptionalIntegers[] critical, int[] parents) {

    }
    private static void postOrderInference(int parent, int current, VoidUnWeighedEdgeBasedTree tree, int[] size, TwoOptionalIntegers[] critical, int[] parents) {
        //把parent推断出来，目的是让后面重心可以向上移动。
        parents[current] = parent;
        //叶子结点的处理
        if (tree.vertices[current].size() == 1 && tree.vertices[current].get(0) == parent) {
            size[current] = 1;
//            distanceSum[current] = 0;
            critical[current] = new TwoOptionalIntegers(current); //自己就是唯一的重心。
            return;//忘记写了
        }
        for (var relative : tree.vertices[current]) {
            if (relative == parent)
                continue;
            postOrderInference(current, relative, tree, size, critical, parents);
            //先处理完子节点，再处理自己。
            size[current]+=size[relative];
        }
        //先处理完子节点，再处理自己。
        size[current]++;
        //下面，我们重头戏来了，critical的推理。
        for (var relative : tree.vertices[current]) {
            if (relative == parent)
                continue;
            final var doubledSize = size[relative] << 1;
            final var sizeOfCurrent = size[current];//现在是current作为子树，因此计算代价的时候，需要根据子树的根的大小而不是根据局部的父亲的大小。
            if (doubledSize < sizeOfCurrent)
                continue;//这个节点没有前途
            if (doubledSize== sizeOfCurrent){ //这样的节点若有，则至多有一个
                critical[current] = new TwoOptionalIntegers(current, relative);//细节，保证requiredInteger是较大的。
//                critical[current] = new TwoOptionalIntegers(relative, current);//细节，保证requiredInteger是较矮的。
                return;
            }
            //现在是最难的部分：doubledSize>size[current]
            //这样的节点若有，则至多有一个
            // 从原本重心中较 高 的值parent上溯
            //不过，可以证明：上一局当中，低位的重心的cost=0，那么这一局，一定是小于零的，所以低位重心不是可以的方案。
            for (var it = critical[relative].requiredIntA;it!=current; it = parents[it]){
//                int movingCost = size[it]<<1- sizeOfCurrent;
                int movingCost = (size[it]<<1)- sizeOfCurrent;
                if (movingCost>0){
                    critical[current] = new TwoOptionalIntegers(it);
                    return;
                }
                if (movingCost==0){
                    critical[current] = new TwoOptionalIntegers(parents[it], it);
//                    critical[current] = new TwoOptionalIntegers(it, parents[it]);
                    return;
                }
            }
        }
        //如果都小于，那么根就是新的重心。
        critical[current] = new TwoOptionalIntegers(current);
    }
}
//# include "OnlineJudgeIO.java"
//# include "OJTree.java"