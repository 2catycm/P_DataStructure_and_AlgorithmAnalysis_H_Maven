package lab1_welcome;

import java.util.Scanner;

public class problem4failed {
    private static final Scanner in = new Scanner(System.in);
    private static int n,h,sumA, sumB, minA, minB, maxA, maxB;
    private static int[] bucketA,bucketB;
    public static void main(String[] args) {
        takeIn();
//        System.out.println(sumA);
//        System.out.println(maxA);
//        System.out.println(minB);
        discuss();
    }

    private static void discuss() {
//        if (minA>1 && minB >1)
//            if ( (sumA-maxA*bucketA[maxA]) > (sumB-maxB*bucketB[maxB]) ){
//                System.out.println(0);
//                return;
//            }
//        if (maxA<h && maxB<h)
//            if ( (sumA-minA*bucketA[minA]) > (sumB-minB*bucketB[minB]) ){
//                System.out.println(0);
//                return;
//            }
        Integer min = null;
        for (int a = 1; a < h+1; a++) {
            for (int b = 1; b < h+1; b++) {
                int realSumA = realSum(sumA, bucketA, minA, maxA, a);
//                assert realSumA!=-1;
                int realSumB = realSum(sumB, bucketB, minB, maxB, b);
                if (realSumA > realSumB)
                    min = min==null? (a-b): Math.min(a-b, min);
//                System.out.println(a+" "+b);
//                System.out.println(min);
            }
        }
        System.out.println(min==null?"IMPOSSIBLE":min);
    }

    private static int realSum(int sumA, int[] bucketA, int minA, int maxA, int a) {
        if (a<=minA)
            return sumA-maxA;
        else if (a<maxA)
            return sumA-maxA-minA+a;
        else if (a>=maxA)
            return sumA-minA;
        return -1;
    }
    //理解有误
    private static int realSum2(int sumA, int[] bucketA, int minA, int maxA, int a) {
        if (a<minA)
            return sumA-maxA*bucketA[maxA];
        else if (a==minA)
            return sumA-maxA*bucketA[maxA]-minA*bucketA[minA];
        else if (a<maxA)
            return sumA-maxA*bucketA[maxA]-minA*bucketA[minA]+a;
        else if (a==maxA)
            return sumA-maxA*bucketA[maxA]-minA*bucketA[minA];
        else if (a>maxA)
            return sumA-minA*bucketA[minA];
        return -1;
    }

    private static void takeIn() {
        n = in.nextInt();
        h = in.nextInt();
        bucketA = new int[h+1]; //自动都是0
        bucketB = new int[h+1]; //自动都是0
        sumA = 0;sumB = 0;
        for (int i = 0; i < n - 1; i++) {
            final int i1 = in.nextInt();
            bucketA[i1]++;
            sumA+=i1;
        }
        for (int i = 0; i < n - 1; i++) {
            final int i1 = in.nextInt();
            bucketB[i1]++;
            sumB+=i1;
        }
        for ( minA = 1; minA <= h; minA++) {
            if (bucketA[minA]!=0)
                break;
        }
        for ( minB = 1; minB <= h; minB++) {
            if (bucketB[minB]!=0)
                break;
        }

        for ( maxA = h; maxA >=1; maxA--) {
            if (bucketA[maxA]!=0)
                break;
        }

        for ( maxB = h; maxB >=1; maxB--) {
            if (bucketB[maxB]!=0)
                break;
        }
    }
}
