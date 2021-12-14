package lab8;

import util.algorithm.SortingHelper;

import java.util.ArrayList;
import java.util.Objects;

//# pragma OJ Main
public class Problem7ChangedSolve {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        int N = in.nextInt();
        int M = in.nextInt();
        final var tree = new VoidWeighedEdgeBasedTree(N); //不会超过int范围。
        tree.readEdgesFromOJ(in);
        out.println(solve(tree, M));
    }
    //任取M条不相交的简单路径，求M条中最短路径长度的最大值。
    static int solve(VoidWeighedEdgeBasedTree tree, int M) {
        int left = 0, right = (tree.size-1)*10000, mid; //right：可能的最长路径
        while(left<=right){
            mid = left+(right-left)/2;
            if (存在M条不相交简单路径使得每条路径长度都大于等于mid(mid, tree, M)){
                left = mid+1;
            }else{
                right = mid-1;//保守指针
            }
        }
        return right;//正确性证明：最后的情况left==right时，验证是否可行，如果可行，left++，right返回；如果不可行，right交错，right的上一个必定是可行的。
    }
    interface DepthFirstSearch{
        int dfs(int parent, int current); //返回的是b，留给外面使用的路径的长度（包括了自己头上的weight）
    }
    private static boolean 存在M条不相交简单路径使得每条路径长度都大于等于mid(int mid, VoidWeighedEdgeBasedTree tree, int M) {
        final int[] 大于等于mid的路径数 = {0};
        new DepthFirstSearch(){
            @Override
            public int dfs(int parent, int current) {
                int toParentWeight = 0;//特殊情况，root的是toParentWeight认为是0，反正没人用到。
                final var setStoringBs = new AVLMultiSet<Integer>();
                for (var relative : tree.adjacencyList[current]) {
                    if (relative.vertexVIndex == parent) {
                        toParentWeight = relative.weight;
                        continue;
                    }
                    final var b = dfs(current, relative.vertexVIndex);
                    if (b>=mid)
                        大于等于mid的路径数[0]++;
                    else {//小于的mid的放入树中。
                        setStoringBs.add(b); //排序.
                    }
                }
                //改一下匹配方法再对拍。
//                final var size = setStoringBs.size(); //不准
                ArrayList<Integer> array = new ArrayList<>(setStoringBs.size());
//                for (int i = 0; i < size; i++) {
//                    array.add(setStoringBs.minKey());
//                    setStoringBs.remove(array.get(i));
//                }
                while (!setStoringBs.isEmpty()){
                    array.add(setStoringBs.minKey());
                    setStoringBs.remove(array.get(array.size()-1));
                }
                assert (SortingHelper.检查数组是否有序(array));
                for (int i = 0; i < array.size(); i++) {
                    for (int j = i+1; j < array.size(); j++) {
                        if (array.get(i)+ array.get(j) >=mid) {
                            大于等于mid的路径数[0]++;
                            array.remove(j);
                            break;//跳出j的循环。
                        }
                    }
                }
                final var maxKey = array.isEmpty()?null:array.get(array.size()-1);
                return (maxKey ==null?0:maxKey)+toParentWeight;
            }
        }.dfs(0, tree.rootIndex);
        return 大于等于mid的路径数[0] >=M;
    }
}
//# include "OnlineJudgeIO.java"
//# include "OJTree.java"
//# include "AVLMultiSet.java"
//# include "符号表头文件.java"