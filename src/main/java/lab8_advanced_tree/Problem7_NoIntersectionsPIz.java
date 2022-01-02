package lab8_advanced_tree;

import java.util.Objects;

//# pragma OJ Main
public class Problem7_NoIntersectionsPIz {
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
                //性质1，必须是两两匹配，三三匹配违反题目条件（不相交）。
                //从小到大找最小的可以使得可以匹配一起的。
//                final var maxKey = setStoringBs.maxKey(); //不能写在这，最大键会变化的。
                while (!setStoringBs.isEmpty()){
                    final var minKey = setStoringBs.minKey();
                    assert (Objects.nonNull(minKey)); //因为树不是空的。
                    setStoringBs.remove(minKey); //不管匹配成不成功，禁止使用这个key了，防止后面那个查找是一样的。而且循环需要下一个minKey。
                    final var 能与树最小值匹配上的最小值 = setStoringBs.ceiling(mid - minKey); //后继。
                    if (能与树最小值匹配上的最小值==null) {
//                        if (minKey.equals(setStoringBs.maxKey())){ //问题，只剩一个节点的时候，新的maxKey是null. 没关系，树空了应该.
//                        break; //最大值都没有成功的后继，那自然是溜走了
                        if (setStoringBs.size()<=1){ //这是multiset。
                            // 情况1： 如果只有一堆相等的最大值，但是无法说大于mid，那么break。
                            // 情况2： 如果只有一个最大值，同理。（刚才最小值被删了。）留下最大值，作为maxKey，返回给递归上一层。
                            //情况3: 根节点为空，能够保证返回0. 这个时候刚才的最小值是刚才唯一的节点，但是被我们删除了，所以需要重新添加到树中在退出
                            if (setStoringBs.isEmpty())
                                setStoringBs.add(minKey);
                            break;
                        }//前面的值一定已经匹配过了，所以要么只剩这个值，要么存在更大的值。
                        continue;//后面更大的值有可能成功的。
                    }else{
                        大于等于mid的路径数[0]++;
                        setStoringBs.remove(能与树最小值匹配上的最小值);
                    }
                }

                final var maxKey = setStoringBs.maxKey();//有问题，最大键被我删了。
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