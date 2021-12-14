package lab2;

import java.util.Scanner;

public class Problem5Classical {//专门针对中位数的排除法
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int n = in.nextInt(), T = in.nextInt();
        Integer[] arrayA = new Integer[n];
        Integer[] arrayB = new Integer[n];
        for (int i = 0; i < n; i++) {
            arrayA[i] = in.nextInt();
        }
        for (int i = 0; i < n; i++) {
            arrayB[i] = in.nextInt();
        }
        for (int i = 0; i < T; i++) {
            int lowerBound = in.nextInt();
            int upperBound = in.nextInt();
            System.out.println(solve(arrayA, arrayB, lowerBound, upperBound));
        }
    }
    static int solve(Integer[] arrayA, Integer[] arrayB, int lowerBound, int upperBound) {
        lowerBound--;upperBound--;
        int leftA = lowerBound, rightA = upperBound, midA;
        int leftB = lowerBound, rightB = upperBound, midB;
        int comparison;boolean isOddCase;
        while (rightA>leftA /*&& rightB>leftB*/) {//当前数组slice的长度，  注意这里保持一个不变量，就是每次执行slice, rightA-leftA = rightB - leftB
            midA = leftA+(rightA-leftA)/2; //比如， 0和1的中间是0， 0和2的中间是1（而不是0！因为2取得到）
            midB = leftB+(rightB-leftB)/2;
            comparison = arrayA[midA]-arrayB[midB];
            isOddCase = (rightA-leftA+1)%2==1;
            if (comparison==0){
                return arrayA[midA];
            }else if (comparison<0) { //A不如B，   比如 0 1 2 不如  0 3 4
                // 剪切，变成  1 2 和 0 3
                if (isOddCase) {
                    leftA = midA;
                    rightB = midB;
                }else{                //看来，我们需要分奇偶讨论      0 1 2 3     0 3 4 5       1不如3  但是，我们应该怎么排除才对呢？
                    //我们应该维持不变量 left把假中位数自己也排除了。
                    leftA = midA+1;
                    rightB= midB;
                }
            }
            else {
                if (isOddCase) {
                    leftB = midB;
                    rightA = midA;
                }else{ //B不如A    比如， 1 2 比 0 3 大  // 问题来了，这是偶数局， 你怎么看
                    //剪切，左边把2排除掉， 右边就把0排除掉。
                    rightA = midA;
                    leftB = midB+1;
                }
            }
        }
        //此刻，rightA==leftA !==（表示不一定等于） rightB==leftB
        return Math.min(arrayA[rightA], arrayB[rightB]); //注意，我们拒绝垃圾java规范， 我们必须允许上界参与到我们的计算中来，否则，我们无法得知，左界是否被加一到异常界
    }
        //如果是取右边，那么排除算法是有bug的
//    static int solve2(Integer[] arrayA, Integer[] arrayB, int lowerBound, int upperBound) { //传进来的bound是错的，大1
//        lowerBound--;upperBound--;
//        int leftA = lowerBound, rightA = upperBound+1, midA;
//        int leftB = lowerBound, rightB = upperBound+1, midB;
//        do {   //比如0,1 只有一个
//            if (leftA+1==rightA&&leftB+1==rightB){
//                return Math.min(arrayA[leftA], arrayB[leftB]);
//            }
//            midA = leftA + (rightA - leftA) / 2;
//            midB = leftB + (rightB - leftB) / 2;
//            int comparison = arrayA[midA]-arrayB[midB];
//            if (comparison == 0)
//                return arrayA[midA];
//            else if (comparison < 0) { //A的中值比B的小， A中值以下被排除， B中值以上被排除
//                leftA = midA + 1;
//                rightB = midB;
//            }
//            else {
//                leftB = midB + 1;
//                rightA = midA;
//            }
//        }while (true);
//
////        return Math.min(arrayA[leftA], arrayB[leftB]);
////        return -1;
//    }
}

