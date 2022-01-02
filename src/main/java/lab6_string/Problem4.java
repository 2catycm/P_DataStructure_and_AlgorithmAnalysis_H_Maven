package lab6_string;
//Bug1
//abcde
//acbde   //match 第一个if写错了是pattern = text
public class Problem4 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final var stringA = in.next();
        final var stringB = in.next();
        final KMPMatcher ringMatcher = new KMPMatcher(stringB) {
            @Override
            public void match(String text) {
                if (text.length()!=pattern.length()){
                    index = -1;
                    return;
                }
                for (int i = 0, j = 0; i < text.length()*2;) {
                    if (text.charAt(i%text.length())==pattern.charAt(j)){
                        i++;j++;
                        if (j==pattern.length()){
                            index = i-j;
                            return;
                        }
                    }else{
                        if (j==0)
                            i++;
                        else{
                            j = nextArray[j-1];
                        }
                    }
                }
                index = -1;
            }
        };
        ringMatcher.match(stringA);
        out.println(ringMatcher.matchedIndex()!=-1?"Yes":"No");
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "Format.java"
//#include "StringMatching.java"