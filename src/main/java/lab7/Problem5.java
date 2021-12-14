package lab7;

import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.function.IntConsumer;
//知识点积累：1.树可以把任何节点（不只是原本的叶子结点）当做根，形成一个新的合法的树。
//2.除了根以外，是叶子节点等价于亲戚数为1. 注意根亲戚树可以为1可以为2. 根也可以是叶子（只有它的时候，注意叶子的定义是没有children）

//# pragma OJ Main
public class Problem5 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final var size = in.nextInt();
        final var tree = new VoidUnWeighedEdgeBasedTree(size);
        tree.readEdgesFromOJ(in);
//        in.whitespaceChars('\n','\n');
//        in.ordinaryChar(' ');
        IntArrayDeque path = new IntArrayDeque(size<<2);
        final int[] previousLeaf = {in.nextInt()};
        dfs(0, 1, tree, previousLeaf[0], path);
        in.nextEOFForEachInt(new IntConsumer() { //TT_EOL有问题
            @Override
            public void accept(int currentLeaf) {
                //现在是从上一个leaf（已经打印到Queue）开始，寻找下一个leaf，并且打印这个路径。
//                path.走后门();//释放掉上一个leaf的打印，因为我们还要打印。
//                bfs(previousLeaf[0], currentLeaf, tree, path);
                dfs(previousLeaf[0], tree.vertices[previousLeaf[0]].get(0), tree, currentLeaf, path);
                previousLeaf[0] = currentLeaf;
            }
        });
        //别忘了回根
        dfs(previousLeaf[0], tree.vertices[previousLeaf[0]].get(0), tree, 1, path);

        if (path.队长()==((size<<1)-1)){
            while (!path.队空()){
                out.print(path.办理业务());
                out.print(" ");
            }
        }else{
            out.print(-1);
        }
        out.println();
    }

    //经过证实，如果是queue\bfs搜索路径，最好的办法是把父亲推断出来。
    private static void bfs(int previousLeaf,  int currentLeaf, VoidUnWeighedEdgeBasedTree tree, IntArrayDeque path) {
//        int deadFront = 0;
//        final var bfsQueue = new IntArrayQueue(tree.size);
//        bfsQueue.排队(previousLeaf);
//        while (!bfsQueue.队空()){
//            var newFront = bfsQueue.办理业务();
//            path.排队(newFront);
//            if (newFront==currentLeaf)
//                return;
//            //TODO 如何回滚？ 如何找子节点
//            deadFront = newFront;
//        }
    }
    private static boolean dfs(int parent, int current, VoidUnWeighedEdgeBasedTree tree, int target, IntArrayDeque path) {
        path.排队(current);
        if (current==target) return true;
        if (tree.vertices[current].size()==1 && tree.vertices[current].get(0)==parent) {
            path.走后门();//消除影响
            return false;
        }
        for (var rel : tree.vertices[current])
        {
            if (rel==parent)
                continue;
            if(dfs(current, rel, tree, target, path)) return true;
        }
        path.走后门();//消除影响
        return false;
    }

}
//# include "OnlineJudgeIO.java"
//# include "OJTree.java"
//# include "非泛型队列与栈包.java"