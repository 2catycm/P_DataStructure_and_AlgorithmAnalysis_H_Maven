package lab7_tree;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.BitSet;

//# pragma OJ Main
public class Problem6 {
    private static OJReader in = new OJReader();
//    private static Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
//        try {
        int T = in.nextInt();
        for (int i = 0; i < T; i++) {
            int n = in.nextInt();
            int k = in.nextInt();
            //罪魁祸首找到了！这里跳过了输入
//            if (k == 1) {
//                out.println(0);
//                continue;
//            }
            VoidUnWeighedEdgeBasedTree tree;
            int[] friends;
            //这部分问题排除.
//            try {
                tree = new VoidUnWeighedEdgeBasedTree(n);
                tree.readEdgesFromOJ(in);
                friends = in.nextIntArray(k);
//            }catch(Exception e){
//                out.println(-1);
//                continue;
//            }
//            for (int j = 0; j < n - 1; j++) {
//                tree.addEdge(in.nextInt(), in.nextInt());
//            }
//            int[] friends = new int[k];
//            for (int j = 0; j < k; j++) {
//                friends[i] = in.nextInt();
//            }
//            out.println(solve(tree, friends));
//            out.println(newSolve(tree, friends));
//            try {
                final var x = newNewSolve(tree, friends);
                out.println(x);
//            }catch(Exception e){
//                out.println(-1);
//                continue;
//            }
        }
//        }catch(Exception e){
//                out.println(-1);
//                return;
//        }
    }

    //方法3
    static int newNewSolve(VoidUnWeighedEdgeBasedTree tree, final int[] friends) {
//        Arrays.sort(friends);
        int arbitraryFriendAIndex = 0;
//        try {
        var depthArrayForAll = new int[tree.size+1]; //所有人距离任取的A的深度
        newDepthInference(depthArrayForAll, tree, friends[arbitraryFriendAIndex]);
        final var friendBIndex = maxIndexOf(depthArrayForAll, friends);

        depthArrayForAll = new int[tree.size+1]; //上一次的得去掉
        newDepthInference(depthArrayForAll, tree, friends[friendBIndex]);//wrong! 没有清零
        final var friendCIndex = maxIndexOf(depthArrayForAll, friends);

        return (depthArrayForAll[friends[friendCIndex]]+1)/2;
//    }
//        catch (Exception e){}
//        return -1;
    }
    //返回即是friend，又是最深的在friend中的下标。
    private static int maxIndexOf(int[] depthArrayForAll, final int[] friends) {
        int maxFriendIndex = 0;
        for (int i = 1; i < friends.length; i++) {
            if (depthArrayForAll[friends[i]]>depthArrayForAll[friends[maxFriendIndex]])
                maxFriendIndex = i;
        }
        return maxFriendIndex;
    }

    //栈溢出了，所以这里写个bfs试试
    private static void newDepthInference(int[] depthArrayForAll, VoidUnWeighedEdgeBasedTree tree, int root){
        ArrayDeque<Integer> queue = new ArrayDeque<>(tree.size);
        queue.offerLast(root);
//        int parent = 0;//that is wrong bfs
//        boolean[] isVisited = new boolean[tree.size+1];
        BitSet isVisited = new BitSet(tree.size+1);
        while (!queue.isEmpty()){
            final var front = queue.pollFirst();
            isVisited.set(front);
            for (var relative:tree.vertices[front]){
//                if (relative==parent)
//                    continue;//that is wrong bfs
                if (isVisited.get(relative))
                    continue;
                depthArrayForAll[relative] = depthArrayForAll[front]+1;
                queue.offerLast(relative);
            }
//            parent = front;//that is wrong bfs
        }
    }

    //方法2
    static int newSolve(VoidUnWeighedEdgeBasedTree tree, int[] friends) {
        Arrays.sort(friends);
        int arbitraryFriendAIndex = 0;
        final var depth = new int[friends.length]; //这些人距离任取的A的深度
        depthInference(depth, tree, 0, friends[arbitraryFriendAIndex], 0, friends);
        final var friendBIndex = maxIndexOf(depth);
        depthInference(depth, tree, 0, friends[friendBIndex], 0, friends);
        final var friendCIndex = maxIndexOf(depth);
        return (depth[friendCIndex]+1)/2;
    }

    private static int maxIndexOf(int[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i]> array[maxIndex])
                maxIndex = i;
        }
        return maxIndex;
    }
    private static void depthInference(int[] depthArray, VoidUnWeighedEdgeBasedTree tree, int parent, int current, int currentDepth, int[] friends) {
        //如果搜索到朋友，就把朋友的深度记下来。 不要return，还有别的朋友要经过这个朋友
        final var searchFriendIndex = Arrays.binarySearch(friends, current);
//        if (friends[searchFriendIndex]==current) {
        if (searchFriendIndex>=0) {
            depthArray[searchFriendIndex] = currentDepth;
        }
        //如果已经到了叶子节点，这个分支搜索完成
        if (tree.vertices[current].size()==1 && tree.vertices[current].get(0)==parent) {
            return;
        }
        for (var rel : tree.vertices[current])
        {
            if (rel==parent)
                continue;
            depthInference(depthArray, tree, current, rel, currentDepth+1, friends);
        }
    }

    //方法1
    static int solve(VoidUnWeighedEdgeBasedTree tree, int[] friends) {
        int arbitraryFriendAIndex = 0;
        final var depth = new int[friends.length]; //这些人距离任取的A的深度
//        final var path = tree.depthFirstSearch(0, A, friends);
        //求解B
        int indexB = arbitraryFriendAIndex; //which is farthestFromAIndex
        depth[arbitraryFriendAIndex] = 0; //自动是0
        for (int i = 1; i < friends.length; i++) {
            if ((depth[i] = depthFirstSearch(tree, 0, friends[arbitraryFriendAIndex], friends[i]))
                    > depth[indexB])
                indexB = i;
        }
        //求解C. 我们重用depth数组来节省内存。
        int indexC = indexB; //which is farthestFromBIndex
        depth[indexC] = 0;
        for (int i = 0; i < friends.length; i++) {
            if (i==indexC)
                continue;
            if ((depth[i] = depthFirstSearch(tree, 0, friends[indexB], friends[i]))
                    > depth[indexB])
                indexC = i;
        }
        return (depth[indexC]+1)/2;
    }

    //返回target的深度。 负一表示没找到。
    private static int depthFirstSearch(VoidUnWeighedEdgeBasedTree tree, int parent, int current, int target) {
        if (target == current) return 0;
        if (tree.vertices[current].size() == 1 && tree.vertices[current].get(0) == parent) {
            return -1;
        }
        for (var rel : tree.vertices[current]) {
            if (rel == parent)
                continue;
            final var d = depthFirstSearch(tree, current, rel, target);
            if (d != -1) return d + 1;
        }
        return -1;
    }
}
//# include "OnlineJudgeIO.java"
//# include "OJTree.java"