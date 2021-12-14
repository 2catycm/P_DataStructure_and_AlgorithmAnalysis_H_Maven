package DiscreteMath;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class GreatestCommonDivisor {
    public static int findGreatestCommonDivisor(int positiveA, int positiveB){
        assert positiveA>0 && positiveB>0;
        int dividend, divisor;
        if(positiveA>positiveB){
            dividend = positiveA; divisor = positiveB;
        }else {
            dividend = positiveB; divisor = positiveA;
        }
        while(divisor!=0){
            int temp = divisor;
            divisor = dividend%divisor;
            dividend = temp;
        }
        assert dividend>0;
        return dividend;
    }
//    public static Pair<Integer, Integer> findBezoutPairFor(int positiveA, int positiveB){
//        assert positiveA>0 && positiveB>0;
//        int gcdAB = findGreatestCommonDivisor(positiveA, positiveB);
//        int resultA, resultB;
//
//        assert resultA*positiveA+resultB+positiveB==gcdAB;
//        return new MutablePair<>(resultA, resultB);
//    }

    public static void main(String[] args) {
        System.out.println(findGreatestCommonDivisor(561, 234));
    }
}
