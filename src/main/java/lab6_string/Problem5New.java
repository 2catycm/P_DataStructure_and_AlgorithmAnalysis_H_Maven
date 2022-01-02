package lab6_string;

import java.util.BitSet;

//错误总结：bug1，二分答案，或者说马拉车可行的原理理解错了。
//错误总结2：bug2， 取模、long、范围内取模、快速模指数有问题
//错误总结3：bug3：MyString搞了半天居然构造器是错的，没有构造reversed的值。
public class Problem5New {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        out.println(solve(in.next()));
    }
    //Note：charAt快于toCharArray。 stringBuilder也需要拷贝、扩容，只是没有新对象的创立。
    static int solve(String stringS) {
        return binarySearchAnswer(preProcess(stringS));
    }
//    private static char symbol = 'z'+1; //$
    private static char symbol = 'a'-1; //$
    private static String preProcess(String stringS){
        final var stringBuilder = new StringBuilder(stringS.length() * 2 + 1);
        for (int i = 0; i < stringS.length(); i++) {
            stringBuilder.append(symbol).append(stringS.charAt(i));
        }
        stringBuilder.append(symbol);
        return stringBuilder.toString();
    }
    // $a$b$c$b$
    private static int binarySearchAnswer(String stringProcessed) {
//        calculatePrefixHash(stringProcessed);
        int left = 3, right = stringProcessed.length(), mid; //比如 3 5 7为答案区间。
                                                            // 排除1是因为串$没有原来的意义。而3是必然成立的。
        while (left<=right){
            mid = left+(right-left)/2;
            if (mid%2==0) mid--;
            if (newHasPalindrome(mid, stringProcessed))
                left = mid+2; //$a$是对的，推出a是对的。  //错误：$a$a$是对的，推出$a$是对的。 正确：推出a$a是回文串
            else
                right= mid-2;
        }
        return right/2;  //比如$a$是对的，实际长度为1. 但是$a$a$是对的，实际长度为2.

        //bug1： 二分答案的基础有问题。 如果检查只检查 #a#a#而不检查a#a， 那么7就退不出5
        //解决，处理后的字符串找最长回文串，同时，因为是最大的，所以如果找到了a#a， 必定能找到#a#a#， 所以只要二分往上找一定能找到。
        //要改hasPalindrome方法。
    }

//    private static void calculatePrefixHash(String stringProcessed) {//多算了一些没用的。
//        for (int i = 0; i < stringProcessed.length(); i++) {
//
//        }
//    }
//    private static int[] LTRSum, RTLSum;
    //需要保证O(n)

//    private static int modulus = 10007, alphabetLen = 26;
//    private static long modulus = BigInteger.probablePrime(31, new Random()).longValue();
    private static long modulus = 2059356049;
    private static int alphabetLen = 256;
    private static long[] getHashArray(int subStringLength, MyString stringS){
        final var subStringCnt = stringS.length() - subStringLength+1;
        long highestBase = modularExponentiation(alphabetLen, subStringLength-1, modulus);
        final var LTR = new long[subStringCnt];
        for (int i = 0; i < subStringLength; i++) {
            LTR[0]*=alphabetLen;
            LTR[0]+=stringS.charAt(i)/*-'a'+1*/;
            LTR[0]%=modulus;
        }
        for (int i = 1; i < subStringCnt; i++) {
            LTR[i] = inRangeMod(LTR[i-1] - (long) stringS.charAt(i - 1) *highestBase%modulus, modulus);
            LTR[i]*= alphabetLen;
            LTR[i]+= stringS.charAt(i+subStringLength-1)/*-'a'+1*/;
            LTR[i]%= modulus;
        }
        return LTR;
    }
    private static boolean newHasPalindrome(int subStringLength, String stringProcessed) {
        final var subStringCnt = stringProcessed.length() - subStringLength+1;
        final var LTR = getHashArray(subStringLength, new MyString(stringProcessed, false));
        final var RTL = getHashArray(subStringLength, new MyString(stringProcessed, true));
        for (int i = 0; i < subStringCnt; i++) {//每个字串都要尝试。
            if (LTR[i]==RTL[subStringCnt-i-1])
                return true;// 不，还是不能只判断hash //蒙特卡洛方法。
//                return new StringBuilder(stringProcessed.substring())
        }
        return false;
    }
//    @Deprecated
//    private static boolean hasPalindrome(int subStringLength, String stringProcessed) {
//        //1.预处理。
//        final var subStringCnt = stringProcessed.length() - subStringLength+1;
////        int highestBase = (int) Math.pow(alphabetLen, subStringLength-1)%modulus;
//        int highestBase = inRangeMod(modularExponentiation(alphabetLen, subStringLength-1, modulus), modulus);
//        int[] LTR = new int[subStringCnt];
//        for (int i = 0; i < subStringLength; i++) {
//            LTR[0]*=alphabetLen;
//            LTR[0]%=modulus;
//            LTR[0]+=stringProcessed.charAt(i);
//        }
//        for (int i = 1; i < subStringCnt; i++) {
//            LTR[i] = (int) (LTR[i-1] - (long) stringProcessed.charAt(i - 1) *highestBase%modulus);
//            LTR[i]*=alphabetLen;
//            LTR[i]+=stringProcessed.charAt(i+subStringLength-1);
////            LTR[i]%=modulus;
//            LTR[i] = inRangeMod(LTR[i], modulus);//有可能减出负数了，规整一下。
//        }
//        int[] RTL = new int[subStringCnt];
//        for (int i = 0; i < subStringLength; i++) {
//            RTL[0]=(RTL[0]*alphabetLen)%modulus+stringProcessed.charAt(stringProcessed.length()-i-1);//错误写法是+=
//        }
//        for (int i = 1; i < subStringCnt; i++) {
//            RTL[i] = inRangeMod(((RTL[i-1] - (long)stringProcessed.charAt(stringProcessed.length()-i)*
//                    highestBase%modulus)*alphabetLen+stringProcessed.charAt(subStringCnt-i-1)),modulus);
////            RTL[i]%=modulus;
////            RTL[i] = RTL[i], );//有可能减出负数了，规整一下。
//        }
//
//
//        //2.比对。
////        //注意遍历，一定要从每一个$开始遍历，否则即使是回文串，也不是原本字符串的回文串。
////        // （长度会有问题，如果不这么做，就不符合二分答案中的定义：
////        // 任何时候，字串与处理串的的映射是子串两边加上$，且内部加上$）
////        for (int i = 0; i < subStringCnt; i+=2) {//跳着遍历字符串，这样i是子字符串开头在源字符串的位置。 $a$a$ 3个子字符串，遍历第一个第三个； $a$ 遍历第一个
////            if (LTR[i]==RTL[subStringCnt-i-1])
////                return true;//蒙特卡洛方法。
////        }
//        for (int i = 0; i < subStringCnt; i++) {//每个字串都要尝试。
//            if (LTR[i]==RTL[subStringCnt-i-1])
//                return true;//蒙特卡洛方法。
//        }
//        return false;
//    }
    public static long modularExponentiation(int base, int exponent, long modulus){
        long result = 1;
        long baseLong = base%modulus;//一次方
        var binaryExponent = binaryRepresentationOf(exponent);
        for (int i = 0; i < binaryExponent.length(); i++) {
            if (binaryExponent.get(i))
                result = (result*baseLong)%modulus;
            baseLong = (baseLong*baseLong)%modulus;//二次方、四次方、八次方
        }
        return result;
    }
    public static BitSet binaryRepresentationOf(int decimal){
        int dividend = decimal;
        BitSet result = new BitSet(); int bitPosition =0;
        while(dividend!=0){
            if (dividend%2==1)
                result.set(bitPosition);
            dividend/=2;
            bitPosition++;
        }
        return result;
    }
    public static long inRangeMod(long maybeNegative, long positiveModulus) {
        if (maybeNegative < 0)
            return (int) (positiveModulus - (-maybeNegative) % positiveModulus);
        else
            return (int) (maybeNegative % positiveModulus);
    }
}
class MyString{
    private String data;
    private boolean reversed;
    public MyString(String data, boolean reversed) {
        this.data = data;
        this.reversed = reversed;
    }

    public int length(){
        return data.length();
    }
    public char charAt(int index){
        return reversed?data.charAt(data.length()-1-index):data.charAt(index);
    }
    public int hash(int base, int modulus){
        int hashSum = 0;
//        for (int i = 0; i < this.data.length(); i++) {
//            hashSum+=this.data.charAt(i)*Math.pow(base, this.data.length()-i-1);
//            hashSum%=modulus;
//        }
        //写一个秦九韶多项式算法
        hashSum = data.charAt(0)-'a';//假设为小写字母
        for (int i = 1; i < data.length(); i++) {
            hashSum = (hashSum*base%modulus+(charAt(i)-'a'))%modulus;
        }
        return hashSum;
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
