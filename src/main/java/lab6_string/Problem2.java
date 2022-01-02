package lab6_string;

public class Problem2 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        System.out.println(Format.deepToString(Format.transpose(new FSAMatcher(in.next(), ContinuousAlphabet.LOWERCASE).getDfa())));
//        System.out.println(Format.deepToString(new FSAMatcher(in.next(), Alphabet.LOWERCASE).getDfa()));
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "Format.java"
//#include "StringMatching.java"