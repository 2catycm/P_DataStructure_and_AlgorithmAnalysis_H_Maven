package lab3_sorting;

import java.util.Scanner;

public class Problem1Bad {
    public static void main(String[] args) {
//        QReader in = new QReader();
        Scanner in = new Scanner(System.in);
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
            System.out.println(solve(arrayA,arrayB));
        }
    }

    private static String solve(int[] arrayA, int[] arrayB) {
        StringBuffer result = new StringBuffer();
        result.append(count(arrayA,arrayB));
        result.append("\n");
        for (int e:arrayA) {
            result.append(e).append(" ");
        }
        for (int e:arrayB) {
            result.append(e).append(" ");
        }
        return result.toString();
    }

    private static int count(int[] arrayA, int[] arrayB) {
        int count = 0;
        for (int i = 0; i < arrayA.length; i++) {
            final int i1 = arrayA[i];
            for (int j = 0; j < arrayB.length ; j++) {
                if (i1<=j)
                    break;
                count++;
            }
        }
        return count;
    }
}
//class QReader {
//    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//    private StringTokenizer tokenizer = new StringTokenizer("");
//
//    private String innerNextLine() {
//        try {
//            return reader.readLine();
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    public boolean hasNext() {
//        while (!tokenizer.hasMoreTokens()) {
//            String nextLine = innerNextLine();
//            if (nextLine == null) {
//                return false;
//            }
//            tokenizer = new StringTokenizer(nextLine);
//        }
//        return true;
//    }
//
//    public String nextLine() {
//        tokenizer = new StringTokenizer("");
//        return innerNextLine();
//    }
//
//    public String next() {
//        hasNext();
//        return tokenizer.nextToken();
//    }
//
//    public int nextInt() {
//        return Integer.parseInt(next());
//    }
//
//    public long nextLong() {
//        return Long.parseLong(next());
//    }
//}
//
//class QWriter implements Closeable {
//    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
//
//    public void print(Object object) {
//        try {
//            writer.write(object.toString());
//        } catch (IOException e) {
//            return;
//        }
//    }
//
//    public void println(Object object) {
//        try {
//            writer.write(object.toString());
//            writer.write("\n");
//        } catch (IOException e) {
//            return;
//        }
//    }
//
//    @Override
//    public void close() {
//        try {
//            writer.close();
//        } catch (IOException e) {
//            return;
//        }
//    }
//}