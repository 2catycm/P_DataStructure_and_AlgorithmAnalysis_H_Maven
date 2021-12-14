package lab3;



import java.io.*;
import java.util.InputMismatchException;

class MyScanner extends StreamTokenizer{
    public MyScanner(Reader r) {
        super(r);
    }
    public int nextInt(){
        try {
            super.nextToken();
        } catch (IOException e) {
            throw new InputMismatchException("");
        }
        return (int) super.nval;
    }
}
//oj测试 从2096降低到712，能够与c++赛跑了
//可以再次降到676
public class Problem1FastIO {
    private static PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
//    private static BufferedWriter printWriter = new BufferedWriter(new OutputStreamWriter(System.out));
    private static MyScanner in = new MyScanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) throws IOException {
        int T = in.nextInt(), n, m;
        int[] arrayA, arrayB;
        for (int i = 0; i < T; i++) {
            n = in.nextInt(); m = in.nextInt();
            arrayA = new int[n]; arrayB = new int[m];
            for (int j = 0; j < n; j++) {
                arrayA[j] = in.nextInt();
            }
            for (int j = 0; j < m; j++) {
                arrayB[j] = in.nextInt();
            }
            solve(arrayA, arrayB);
            printWriter.flush();
        }
        printWriter.close();
    }
    private static void solve(int[] arrayA, int[] arrayB) throws IOException {
        final StringBuilder result = new StringBuilder();
//        int inversionCount = 0, previousICount = 0;
        long inversionCount = 0, previousICount = 0; //易错点： 逆序对可能有n^2个！！
        int comparison;
        int i,j;
        for (i = 0, j = 0; i < arrayA.length && j < arrayB.length;) { //i指向arrayA还没有归并的最小值。j同理
            comparison = arrayA[i] - arrayB[j];
            if (comparison>0){
                previousICount++;//符合逆序对的情况
                result.append(arrayB[j]).append(" ");//B更小，消耗掉这个最小值
                j++; //最小值更新
            }else{ //不符合逆序对的情况
                result.append(arrayA[i]).append(" ");//可以认为A更小，相等也可以把它消耗掉
                i++;
                inversionCount+=previousICount;//战绩继承
//                previousICount = 0; //不会清零
            }
        }
        //某个数组已经被消耗完了。
        if (j==arrayB.length) //j消耗完了，i还没有
            for (; i < arrayA.length; i++) {
                inversionCount+=previousICount;
                result.append(arrayA[i]).append(" ");
            }
        else
            for (; j < arrayB.length; j++) {
//                inversionCount+=previousICount;
                result.append(arrayB[j]).append(" ");
            }
        printWriter.println(inversionCount);
        printWriter.println(result.toString());
//        printWriter.write(inversionCount+"\n");
//        printWriter.write(result.toString());
    }
}
