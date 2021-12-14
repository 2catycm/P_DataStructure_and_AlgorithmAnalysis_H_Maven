package DiscreteMath;

//import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Stack;

public class NumberRepresentation {
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
    public static String bitSetView(BitSet bitSet){
        Stack<Boolean> stack = new Stack<>();
        for (int i = 0; i < bitSet.length(); i++) {
            stack.push(bitSet.get(i));
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.empty()){
            stringBuilder.append(stack.pop()?"1":"0");
        }
        return stringBuilder.toString();
//        return bitSet.toString();
    }
    public static BitSet addBinary(BitSet binaryA, BitSet binaryB){
        BitSet result = new BitSet();
        int previousCarry = 0;
        int numberLength = Math.max(binaryA.length(), binaryB.length());
        for (int i = 0; i < numberLength; i++) {
            int Ai = binaryA.get(i)?1:0;
            int Bi = binaryB.get(i)?1:0;
            int currentCarry = (Ai+Bi+previousCarry)/2;
            if ((Ai+Bi+previousCarry-2*currentCarry)%2==1)
                result.set(i);
            previousCarry = currentCarry;
        }//O(n)。 每次循环需要用到两次“二进制加法”
        return result;
    }
    public static BitSet leftShift(BitSet binaryA, int numberOfShift){
        BitSet result = new BitSet();
        int j = 0;
        for (int i = 0; i < binaryA.cardinality(); i++) {
            j = binaryA.nextSetBit(j)+1;
            result.set(j-1+numberOfShift);
        }
        return result;
    }
    public static BitSet mulBinary(BitSet binaryA, BitSet binaryB){
        BitSet result = new BitSet();
        BitSet[] binaryCs = new BitSet[binaryB.length()];
        for (int i = 0; i < binaryB.length(); i++) {
            if (binaryB.get(i))
                binaryCs[i] = leftShift(binaryA, i);
            else
                binaryCs[i] = new BitSet();
        }//假设整体移位一格记作一次操作，那么需要0+1+...+b.length-1次以为
        for (int i = 0; i < binaryB.length(); i++) {
            result = addBinary(result, binaryCs[i]);
        }//长度为n+n-1的加法要做n次。
        return result;
    }
    public static boolean less(BitSet binaryA, BitSet binaryB){
        return true;
    }
//    public static Pair<BitSet,BitSet> quotientAndRemainderOf(BitSet binaryA, BitSet binaryB){
////        BitSet result = new BitSet();
////        while()
////        return result;
//        return null;
//    }
    public static int modularExponentiation(int base, int exponent, int modulus){
        int result = 1;
        base = base%modulus;//一次方
        var binaryExponent = binaryRepresentationOf(exponent);
        for (int i = 0; i < binaryExponent.length(); i++) {
            if (binaryExponent.get(i))
                result = (result*base)%modulus;
            base = (base*base)%modulus;//二次方、四次方、八次方
        }
        return result;
    }
    public static void main(String[] args) {
////        final var binaryA = binaryRepresentationOf(100632);
////        final var binaryB = binaryRepresentationOf(99);
//        final var binaryA = binaryRepresentationOf(10);
//        final var binaryB = binaryRepresentationOf(10);
//        System.out.println(bitSetView(binaryA));
////        System.out.println(binaryA.length());//correct
////        System.out.println(binaryA.cardinality());//data representation only
//        System.out.println(bitSetView(binaryB));
//        System.out.println(bitSetView(addBinary(binaryA,binaryB)));
//
//        System.out.println(bitSetView(leftShift(binaryB,1)));
//        System.out.println(bitSetView(mulBinary(binaryA, binaryB)));

//        System.out.println(modularExponentiation(3,100, 10));
//        System.out.println(modularExponentiation(26,0, 10007));
//        System.out.println(modularExponentiation(26,1, 10007));
//        System.out.println(modularExponentiation(26,2, 10007));
//        System.out.println(modularExponentiation(26,3, 10007));
//        System.out.println(modularExponentiation(26,4, 10007));
        System.out.println(modularExponentiation(26,12, 10007));
    }
}
