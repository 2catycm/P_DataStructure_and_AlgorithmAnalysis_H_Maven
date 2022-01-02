package lab7_tree;

public class Problem4 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        int T = in.nextInt();String preOrder, postOrder;
        for (int i = 0; i < T; i++) {
            preOrder = in.next();
            postOrder = in.next();
            out.println(solve(preOrder.substring(1), postOrder.substring(0,postOrder.length()-1)));
//            out.println(newSolve(preOrder, postOrder));
        }
    }

    static int newSolve(String preOrder, String postOrder) {
        int singleNodeCount = 0;
        final var length = preOrder.length();
        for (int i = 0; i < length-1 /*- 1*/; i++) {////不需要-1
            for (int j = 1/*i+1*/; j < length; j++) {//不是从i+1开始找，而是找到节点对应的位置
                if (preOrder.charAt(i)==postOrder.charAt(j) && preOrder.charAt(i+1)==postOrder.charAt(j-1))
                    singleNodeCount++;
            }
        }
        return 1<<singleNodeCount;
    }

    //ABCD的时候, BCD能够推出A = 2B， CD能够推出B=2C， 但是需要CD才能推出C=2D， 出现了逻辑漏洞与不统一。
    //为什么不对？
    static int solve(String preOrder, String postOrder) {
        final var length = postOrder.length();
//        if (length ==1) return 1;
        if (length==2){
            return preOrder.charAt(0)==postOrder.charAt(0)?1:4;
        }
        final var maybeLeftChild = preOrder.charAt(0);
        if (maybeLeftChild ==postOrder.charAt(length-1))
            return solve(preOrder.substring(1), postOrder.substring(0, length-1))<<1;
        final var leftChildInPostAddOne = postOrder.indexOf(maybeLeftChild)+1;
        return solve(preOrder.substring(0, leftChildInPostAddOne), postOrder.substring(0, leftChildInPostAddOne))
            * solve(preOrder.substring(leftChildInPostAddOne), postOrder.substring(leftChildInPostAddOne));
    }
}
//#include "OnlineJudgeIO.java"