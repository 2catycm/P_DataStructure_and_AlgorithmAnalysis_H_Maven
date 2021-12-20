package lab9;

import java.awt.*;

//# pragma OJ Main
public class ProblemD_Sum_New {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        int T = in.nextInt();
        for (int i = 0; i < T; i++) {
            int N = in.nextInt();
            int M = in.nextInt();
            final var intMat = new Matrix<Integer>(N, M);
            Matrix.readDataFromOJ(intMat, in);
            final var greatestSum = new GreatestSum(intMat);
            out.println(greatestSum.getGreatestSum());
        }
    }
    static class GreatestSum {
        public long getGreatestSum() {
            return greatestSum;
        }
        private long greatestSum = Long.MIN_VALUE;
        private final Matrix<Integer> intMat;
        private BooleanMat selected;
        public GreatestSum(Matrix<Integer> intMat) {
            this.intMat = intMat;
            selected = new BooleanMat(intMat.getRows(), intMat.getCols());
            dfs(new Point(0,0));
        }
        private void dfs(Point currentPoint){
            //base case
            if (!selected.inBound(currentPoint)){
                greatestSum = Math.max(greatestSum, sum(selected, intMat));
                return;
            }
            //
            final var nextPoint = nextPoint((Point) currentPoint.clone());
            if (adjacentToPrevious(currentPoint)){
                selected.clear(currentPoint);
                dfs(nextPoint);
            }else{
                selected.set(currentPoint);
                dfs(nextPoint);
                selected.clear(currentPoint);
                dfs(nextPoint);
            }
        }

        private Point nextPoint(Point currentPoint) {
            if (++currentPoint.y==selected.getCols()) {
                currentPoint.x++;
                currentPoint.y = 0;
            }
            return currentPoint;
        }

        private static int[] step0 = {-1, -1, -1, 0, 0, 1, 1, 1};
        private static int[] step1 = {-1, 0, 1, -1, 1, -1, 0, 1};
        private boolean adjacentToPrevious(Point currentPoint){
            for (int i = 0; i < 8; i++) {
                final var point = new Point(currentPoint.x + step0[i], currentPoint.y + step1[i]);
                if (!selected.inBound(point))
                    continue;
                if (selected.get(point))
                    return true;
            }
            return false;
        }
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
}
//# include "OnlineJudgeIO.java"
//# include "BooleanMat.java"
//# include "Matrix.java"