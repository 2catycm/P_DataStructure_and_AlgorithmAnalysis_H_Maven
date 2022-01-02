package lab6_string;

public class Problem5 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final var stringS = in.next();
        out.println(solve(stringS));
    }

    private static int solve(String stringS) {
        StringBuilder markedString = new StringBuilder();
        for (int i = 0; i < stringS.length(); i++) {
            markedString.append(stringS.charAt(i)).append('*');
        }
        String stringMarked = markedString.toString();
        //奇数
//        int left = 1, right = stringS.length() - (1-stringS.length()%2), mid;
        int left = 1, right = stringMarked.length()-1, mid; //一定是偶数长度，(冗余一个*)
        while(left<=right){
            mid = left+((right-left)/2 - ((right-left)/2)%2);
            if (check(mid, stringMarked)){
                left = mid+2;
            }else{
                right = mid-2;
            }
        }
        return (right+1)/2;
    }

    private static boolean newCheck(int length, String stringS) {
        int[] RTLHashElements = new int[stringS.length()/*-length+1*/]; int RTLBase = 1;
        int[] LTRHashElements = new int[stringS.length()/*-length+1*/]; int LTRBase = (int) Math.pow(26, stringS.length()-1);
        for (int i = 0; i < stringS.length(); i++) {
            RTLHashElements[i] = stringS.charAt(i)*RTLBase;
            RTLBase*=26;
            LTRHashElements[i] = stringS.charAt(i)*LTRBase;
            LTRBase/=26;
        }
        IntPrefixSumTable RTLTable = new IntPrefixSumTable(RTLHashElements);
        IntPrefixSumTable LTRTable = new IntPrefixSumTable(LTRHashElements);
        for (int i = 0; i <= stringS.length() - length; i++) {
            final var RTLHash= RTLTable.sumAmong(i, i + length);
            final var LTRHash= LTRTable.sumAmong(i, i + length);
            if (RTLHash%LTRHash!=0&&LTRHash%RTLHash!=0)
                continue;
            final var substring = stringS.substring(i, i + length);
            boolean isPalindromic = true;
            for (int j = 0; j < (length - 1) / 2; j++) {
                if (substring.charAt(j)!=substring.charAt(length-1-j)){
                    isPalindromic = false;
                    break;
                }
            }
            if (isPalindromic)
                return true;
        }
        return false;
    }
    @Deprecated
    private static boolean check(int length, String stringS) {
        for (int i = 0; i <= stringS.length() - length; i++) {
            final var substring = stringS.substring(i, i + length);
//            final var hashLTF = new MyString(substring, false).hash(26, 100);
//            final var hashFTL = new MyString(substring, true).hash(26, 100);
            final var hashLTF = new MyString(substring, false).hash(26, 1000000007);
            final var hashFTL = new MyString(substring, true).hash(26, 1000000007);
            if (hashLTF!=hashFTL)
                continue;
            boolean isPalindromic = true;
            for (int j = 0; j < (length - 1) / 2; j++) {
                if (substring.charAt(j)!=substring.charAt(length-1-j)){
                    isPalindromic = false;
                    break;
                }
            }
            if (isPalindromic)
                return true;
        }
        return false;
    }
}

//#include "OJReader.java"
//#include "OJWriter.java"
//#include "IntPrefixSumTable.java"