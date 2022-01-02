package lab6_string;

public class Problem3 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
//        in.next();
//        System.out.println(StandardCharsets.UTF_8.toString());
        System.out.print(Format.arrayToString(new KMPMatcher(in.next()).getNextArray(), "\n"));
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "Format.java"
//#include "StringMatching.java"