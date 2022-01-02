package lab1_welcome;
import java.util.LinkedList;
import java.util.Scanner;

public class judgeProblem4 {
    //由judge知，大概率不是我算法错了，而是因为 sumA，sumB溢出了，nh最大是10的14次方
    private static final Scanner in = new Scanner(System.in);
    private static int n,h,sumA, sumB;
    private static Integer minA, minB, maxA, maxB;
    public static void main(String[] args) {
//        int cnt = 1;
        while (/*cnt-->0*/true) {
//            takeIn();
            generate();
            final String solve3 = solve3();
//            final String solve1 = solve1();
//            if (!solve1.equals(solve3)) {
//                System.err.println("expected: " + solve3 + "\nactually: " + solve1
//                +"\nat: "+String.format("n=%d, h=%d, sumA=%d, sumB=%d, minA=%d, maxA=%d, minB=%d, maxB=%d",n, h, sumA, sumB, minA, maxA, minB, maxB));
//            }
//            else
//                System.out.println("correct!");
            if (!solve3.equals("IMPOSSIBLE")) {

                for (int i = 0; i < queue.size(); i++) {
                    System.out.print(queue.get(i) + " ");
                }
                System.out.println("\n" + solve3);
            }
        }
//        System.out.println("correct!");
    }
    //王梓安
    public static void insert(int[] t, int n){
        Scanner in = new Scanner(System.in);

        for (int i = 0; i < n-1; i++){
            t[i] = queue.remove(0);
//            t[i] = in.nextInt();
        }
    }
    public static long sum(int[] t){
        long tot = 0;
        for (int i = 0; i < t.length; i++){
            tot = tot + t[i];
        }
        return tot;
    }
    public static int min_num(int[] t){
        if (t.length == 0){
            return 0;
        }
        int min = t[0];
        for (int i = 1; i < t.length; i++){
            if (t[i] != 0 && min > t[i]){
                min = t[i];
            }
        }
        return min;
    }
    public static int max_num(int[] t){
        if (t.length == 0){
            return 0;
        }
        int max = t[0];
        for (int i = 1; i < t.length; i++){
            if (t[i] != 0 && max < t[i]){
                max = t[i];
            }
        }
        return max;
    }
    private static String solve3(){
        Scanner in = new Scanner(System.in);
        boolean flag = true;
        boolean flag_in = true;
        int n = judgeProblem4.n;
        int h = judgeProblem4.h;//max
        int[] a = new int[n];
        int[] b = new int[n];
        insert(a, n);
        insert(b, n);
        for (int ins_a = h; ins_a > 0; ins_a--){
            a[n-1] = ins_a;
            int min_a = min_num(a);
            int max_a = max_num(a);
            long tot_a = sum(a) - min_a - max_a;
            for (int ins_b = 0; ins_b <= h && flag_in; ins_b++){
                b[n-1] = ins_b;
                int min_b = min_num(b);
                int max_b = max_num(b);
                long tot_b = sum(b) - min_b - max_b;
                if (tot_a > tot_b){
                    if (ins_b == h){
                        flag_in = false;
                    }
                    continue;
                }
                else{
                    flag_in = false;
                    if (ins_b <= 1){
                        flag = false;
                        break;
                    }
                    else{
                        b[n-1] = ins_b - 1;
                        break;
                    }
                }
            }
            int min_b = min_num(b);
            int max_b = max_num(b);
            long tot_b = sum(b) - min_b - max_b;
            if (tot_a > tot_b){
                continue;
            }
            else{
                a[n-1] = ins_a + 1;
                break;
            }
        }
        if (flag){
            return ""+(a[n-1] - b[n-1]);
        }
        else{
            return "IMPOSSIBLE";
        }
    }
//    private static String solve2() {
//        Integer min = null;
//        for (int a = 1; a < h+1; a++) {
//            for (int b = 1; b < h+1; b++) {
//                int realSumA = realSum(sumA, minA, maxA, a);
////                assert realSumA!=-1;
//                int realSumB = realSum(sumB, minB, maxB, b);
//                if (realSumA > realSumB)
//                    min = min==null? (a-b): Math.min(a-b, min);
//            }
//        }
//        return min==null?"IMPOSSIBLE":min+"";
//    }
    private static int realSum(int sumA, int minA, int maxA, int a) {
        if (a<=minA)
            return sumA-maxA;
        else if (a<maxA)
            return sumA-maxA-minA+a;
        else if (a>=maxA)
            return sumA-minA;
        return -1;
    }

    private static String solve1() {
        final Integer discuss = discuss1();
        return discuss==null?"IMPOSSIBLE":discuss+"";
    }
    private static Integer discuss1() {
        Integer min = null;
        //1,1 情况
        if ((sumA-maxA)>(sumB-maxB))
            min = 1-minB;
        //1,2 情况, 此情况并没有被上一情况排除，因为b未必是被增强了; 注意：情况不能互相排除，因为a-b不是说绝对值最小，而是负数最小。
//        int b = minB+1; //我们选择b的最小
        if ((sumA-maxA)>(sumB-maxB+1)) {  //先确定可能性，再确保极大性
            int 差 = 1 - Math.min(maxB - 1, ((sumA - maxA) - (sumB - maxB - minB) - 1));
            if (min == null || min>差)
                min = 差;
        }
        //1,3 情况
        if ((sumA-maxA)>(sumB-minB)){
            int 差 = 1 - h; //和与a，b都无关， 因此取最远的值
            if (min == null || min>差)
                min = 差;
        }
        //2,1 情况
        //sumA-maxA-minA+a, 令a = maxA-1
        if ((sumA-1-minA)>(sumB-maxB)){ //a先是要可能盖过（足够大），然后再往小去取, 但是又不能小过minA
            int 差 =Math.max(minA+1, (sumB-maxB)-(sumA-maxA-minA)+1) -minB;
            if (min == null || min>差)
                min = 差;
        }
        //2,2 情况
        //sumA-maxA-minA+a, 令a = maxA-1
        //sumB-maxB-minB+b, 令b = minB+1
        if ((sumA-1-minA)>(sumB-maxB+1)){
            int 差 = Math.max( ((sumB-maxB-minB)-(sumA-maxA-minA)+1), (minA+1)-(maxB-1));  //不能比 它小，否则不符情况
            if (min == null || min>差)
                min = 差;
        }
        //2,3 情况
        //sumA-maxA-minA+a, 令a = maxA-1
        if ((sumA-1-minA)>(sumB-minB)){
            int 差 = Math.max(minA+1, (sumB-minB)-(sumA-maxA-minA)+1) - h;
            if (min == null || min>差)
                min = 差;
        }
        //3,1 情况
        if ((sumA-minA)>(sumB-maxB)){
            int 差 = maxA - minB;
            if (min == null || min>差)
                min = 差;
        }
        //3,2 情况
        //sumB-maxB-minB+b, 令b = minB+1
        if ((sumA-minA)>(sumB-maxB+1)){
            int 差 = maxA - Math.min(maxB-1, (sumA-minA)-(sumB-maxB-minB)-1);      //b要选的尽可能大，但是又不能太大
            if (min == null || min>差)
                min = 差;
        }
        //3,3 情况
        if ((sumA-minA)>(sumB-minB)){
            int 差 = maxA-h;
            if (min == null || min>差)
                min = 差;
        }

        return min;
    }
    private static final LinkedList<Integer> queue = new LinkedList<>();
    private static void generate() {
        n = 4;
        h = 100;
        System.out.print(n+" "+h+" ");
        for (int i = 0; i < n-1; i++) {
            final int ia =  1+ (int) (Math.random() * (h - 1));
            queue.add(ia);
            sumA+=ia;
            minA = minA==null?ia:Math.min(minA, ia);
            maxA = maxA==null?ia:Math.max(maxA, ia);
        }
        for (int i = 0; i < n-1; i++) {
            final int ib = 1+ (int) (Math.random() * (h - 1));
            queue.add(ib);
            sumB+=ib;
            minB = minB==null?ib:Math.min(minB, ib);
            maxB = maxB==null?ib:Math.max(maxB, ib);
        }


    }
    private static void takeIn() {
        n = in.nextInt();
        h = in.nextInt();
        for (int i = 0; i < n-1; i++) {
            final int ia = in.nextInt();
            sumA+=ia;
            minA = minA==null?ia:Math.min(minA, ia);
            maxA = maxA==null?ia:Math.max(maxA, ia);
        }
        for (int i = 0; i < n-1; i++) {
            final int ib = in.nextInt();
            sumB+=ib;
            minB = minB==null?ib:Math.min(minB, ib);
            maxB = maxB==null?ib:Math.max(maxB, ib);
        }
    }
}
