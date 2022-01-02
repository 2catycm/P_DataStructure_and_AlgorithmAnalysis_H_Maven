package lab6_string;

public class Problem7 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        out.println(solve(in.next()));
    }

    private static String solve(String stringS) {
        final var length = stringS.length();
        final var nextArray = new KMPMatcher(stringS).getNextArray();
//        final var maxJ = nextArray[length - 2];
        int maxJ = 0;
        for (int i = 1; i < nextArray.length-1; i++) {
            maxJ = Math.max(maxJ, nextArray[i]);
        }
        for (int j = nextArray[length-1]; j > 0; j = nextArray[j-1]) {
            if (maxJ>=j)
                return stringS.substring(0, j);
        }
        return "Just a legend";
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "StringMatching.java"