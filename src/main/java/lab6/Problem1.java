package lab6;

import java.util.ArrayList;

public class Problem1 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        String s = in.next();
        //不包含空集。 从长度为1到长度为3，需要查重。
        ArrayList<String> already; int count = 0;
        for (int i = 1; i <= s.length(); i++) {
            already = new ArrayList<>();
            for (int j = 0; j < s.length() - i + 1; j++) {
                final var substring = s.substring(j, j + i);
                if (!already.contains(substring))
                    already.add(substring);
            }
            count+= already.size();
        }
        out.println(count);
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"