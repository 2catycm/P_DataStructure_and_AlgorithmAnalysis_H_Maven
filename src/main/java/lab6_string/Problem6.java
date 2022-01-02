package lab6_string;
//这道题的正确性和思路有待证明。
public class Problem6 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final var cipherTable = new CipherTable(in);
        final var stringS = in.next();
        out.println(solve(stringS, cipherTable));
//        out.println(fake(stringS,cipherTable));
    }

    private static int solve(String stringS, CipherTable cipherTable) {
        //二分答案来一波
        //首先我们知道这道题不可以二分答案。因为没有单调性。 ab c  ed 成功不能推出 a bce  d成功。
        int minimumLeftLength = (stringS.length()+1)/2;//例如，0 1 2 左边长度至少为2； 0 1 2 3左边长度至少为2
        StringBuilder preProcessed = new StringBuilder(stringS.length());
        preProcessed.append(stringS, 0, minimumLeftLength).append('*');
        preProcessed.append(cipherTable.encrypt(stringS.substring(minimumLeftLength)));
        //下一步关键了
        //假如加密是+2
        //0 1 2 4->   0 1 * 0 1
        //这个时候，取到了最长。  其他不取最长的原因是，0 1 * 123 12412 0 1 阻隔了。（当然这里长度也不满足）
        //所以我们需要前缀最长（原文最长），也就需要一个和它相等的后缀最长，同时他们井水不犯河水。
        final var nextArray = new KMPMatcher(preProcessed.toString()).getNextArray();
        return stringS.length()-nextArray[nextArray.length-1];
    }
    private static int fake(String stringS, CipherTable cipherTable){
//        int left = (stringS.length()+1)/2, right = stringS.length(), mid;
//        while (left<=right){
//            mid = left+(right-left)/2;
//            if (check(mid, stringS, cipherTable))
//
//        }
        for (int i = 0; i < stringS.length(); i++) {
            System.out.println(check(i, stringS, cipherTable));
        }
        return 0;
    }
    private static boolean check(int startIndexOfOrigin, String stringS, CipherTable cipherTable){
        return stringS.startsWith(cipherTable.decrypt(stringS.substring(startIndexOfOrigin)));
    }
}
class CipherTable{
    //线性代数复习。 置换矩阵P，有性质P的转置等于P的逆。
    private char[] table;
    private char[] inverseTable;
    public CipherTable(OJReader in) {
        table = new char[26];
        inverseTable = new char[26];
        char ch;
        for (int i = 0; i < table.length; i++) {
            ch = in.next().charAt(0);
            table[i] = ch;
            inverseTable[ch-'a'] = (char) ('a'+i);
        }
    }
    public String encrypt(String message){
        StringBuilder result = new StringBuilder(message.length());
        for (int i = 0; i < message.length(); i++) {
            result.append(encrypt(message.charAt(i)));
        }
        return result.toString();
    }
    public String decrypt(String ciphertext){
        StringBuilder result = new StringBuilder(ciphertext.length());
        for (int i = 0; i < ciphertext.length(); i++) {
            result.append(decrypt(ciphertext.charAt(i)));
        }
        return result.toString();
    }
    public char encrypt(char message){
        return table[message-'a'];
    }
    public char decrypt(char ciphertext){
        return inverseTable[ciphertext-'a'];
    }
    //test
//    System.out.println(cipherTable.encrypt('a'));
//    System.out.println(cipherTable.decrypt('a'));
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "StringMatching.java"