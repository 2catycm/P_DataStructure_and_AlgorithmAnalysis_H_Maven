package lab9_graph;

import java.awt.*;

//# pragma OJ Main
public class ProblemD_Sum {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

//    public static void main(String[] args) {
//        int T = in.nextInt();
//        for (int i = 0; i < T; i++) {
//            int N = in.nextInt();
//            int M = in.nextInt();
//            final var intMat = new Matrix<Integer>(N, M);
//            out.println(solve(intMat));
//        }
//    }
    //solving：The max sum of any not adjacent elements in matrix.
//    static long solve(Matrix<Integer> intMat) {
////        long maxSum = Long.MIN_VALUE;
//        BooleanMat selected = new BooleanMat(intMat.getRows(), intMat.getCols());
////        dfs(new Point(0, 0), selected, intMat,  maxSum);
////        return 0;
//        dfs(new Point(0, 0), selected, intMat);
//        return currentMaxSum;
//    }
//    private static long currentMaxSum = Long.MIN_VALUE;
//    private static void dfs(Point point, BooleanMat selected, Matrix<Integer> intMat) {
//        //到达终点状态
//
//        //扩展方式1: 我自己要选。
//        selected.set(point);
//        Iterable<Point> pointNext = () -> iterator(point);
//        for(var i:pointNext){
//
//        }
//        //扩展方式2：我自己不选
//        selected.clear(point);
//    }

    private static int[] step0 = {0, 0,-2,2,-2,2,2,-2, -2, -1, 2, 1, 2, 1, -2, -1};
    private static int[] step1 = {2, -2, 0, 0, -2,2,-2,2, -1, -2, 1, 2, -1, -2, 1, 2};
//    private static Iterator<Point> iterator(Point point) {
//        return new Iterator<>() {
//            private int i = 0;
//            @Override
//            public boolean hasNext() {
//                return i < 16;
//            }
//
//            @Override
//            public Point next() {
//                return new Point(point.x+step0[i], point.y+step1[i]);
//            }
//        };
//    }

    //dfs的目的是，枚举所有可能的selected mat

//    private static void dfs(Point point, BooleanMat selected, Matrix<Integer> intMat, long maxSum) {
////        if (point.x== intMat.getRows()-1 && point.y== intMat.getCols()-1)
////            maxSum = Math.max(maxSum, sum(selected, intMat));
////        //第一种情况， 这一步被选中
////        selected.set(point);
////        //下一步有无选择空间
////        if ()
////        //第二种情况，这一步没有选中（虽然上一步给了这一步选择的自由空间。）
////        selected.clear(point);
//        //不对
//    }

    private static long sum(BooleanMat selected, Matrix<Integer> intMat) {
        long result = 0;
        for (int i = 0; i < intMat.getRows(); i++) {
            for (int j = 0; j < intMat.getCols(); j++) {
                final var point = new Point(i, j);
                if (selected.get(point))
                    result+=intMat.get(point);
            }
        }
        return result;
    }

}
//# include "OnlineJudgeIO.java"