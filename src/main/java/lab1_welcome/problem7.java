package lab1_welcome;

import java.util.Arrays;
import java.util.Scanner;

public class problem7 {
    //debug日记： 经过测试，去掉小数判断的话，4 24 得到的count确实是经典的结论1362种，说明程序问题在于小数判断
    private static final Scanner in = new Scanner(System.in);
    private static short numberCount; //n
    private static int target; //m
    private static int[] buckets = new int[13];//存放系数
    private static int sumOfBuckets = 0;
    private static int count = 0;
    private static boolean doExplain = true;//debug mode
    private static StringBuilder explanation = new StringBuilder();
    public static void main(String[] args) {
        numberCount = in.nextShort();
        target = in.nextInt();
        if (numberCount<=2) {
            System.out.println(0);
            return;
        }
        generateAndSolve();
        System.out.println(count);
//        testSingle();
    }

    public static void testSingle() {
        numberCount = 4;
//        target = 24;
//        分数[] numbers = {new 分数(5), new 分数(6), new 分数(3), new 分数(3)};
//        target = 159;
//        Rational[] numbers = {new Rational(2), new Rational(8), new Rational(12), new Rational(13)};
        target = 2;
        Rational[] numbers = {new Rational(1), new Rational(1), new Rational(1), new Rational(7)};

        这一次出现过小数 = false;
        这个组合尝试过 = false;
        这个组合已经失败了 = false;
        solve(numbers);
        System.out.println(count==1);
    }
    private static void generateAndSolve() {
        generateAndSolve(0);
    }
    private static void generateAndSolve(int state) {
        if (state==13)
            if (sumOfBuckets==numberCount)
                solve();
            else
                return;
        else{
            for (int i = 0; i <= numberCount; i++) {//注意numberCount取得到
                if (sumOfBuckets+i > numberCount)
                    break;
                buckets[state] = i;
                sumOfBuckets+=i;
                generateAndSolve(state+1);
                buckets[state] = 0;
                sumOfBuckets-=i;
            }
        }
    }

    private static void solve() {//因为已经那个得到一个bucket了.下面解析出四个数，然后判断是否符合要求。
        Rational[] numbers = new Rational[numberCount]; int k = 0;
        for (int i = 0; i < buckets.length; i++)
            for (int j = 0; j < buckets[i]; j++)
                numbers[k++] = new Rational(i + 1);
//        System.out.println(Arrays.toString(numbers));
//        count++;
        这一次出现过小数 = false;

        这个组合尝试过 = false;
        这个组合已经失败了 = false;

        solve(numbers);
        //debug mode
        if(doExplain && !这个组合已经失败了) {
            System.out.println(Arrays.toString(numbers)+"->");
            System.out.println(explanation);
            explanation = new StringBuilder();
        }
    }

    private static boolean 这一次出现过小数, 这个组合尝试过, 这个组合已经失败了;
    private static void solve(Rational[] numbers) {
        if (这个组合已经失败了)
            return;
        if(numbers.length==1){
            if (numbers[0].等于(target)) {
                if (这一次出现过小数)
                    if (!这个组合尝试过) {
                        这个组合尝试过 = true; //并且加过1
                        count++;
                    }
                    else ; //这个组合这一次成功，但是已经计过数了。
                else {
                    这个组合已经失败了 = true;
                    if (这个组合尝试过)//把那一次的1去掉
                        count--;
                }
            }
            return;
        }
        Rational[] reduced = new Rational[numbers.length-1];
        for (int i = 0; i <numbers.length; i++)
            for (int j = i+1; j < numbers.length; j++) {
                for (int k = 0; k < 6; k++) {//0,1,2,3 + - * /   4, 5 - 减法反过来    / 除法反过来  因为没有交换律！！
                    Rational result;
                    switch (k){
                        case 0:
                            result = numbers[i].加(numbers[j]);
                            break;
                        case 1:
                            result = numbers[i].减(numbers[j]);
                            break;
                        case 2:
                            result = numbers[i].乘(numbers[j]);
                            break;
                        case 3:
                            if (numbers[j].是零())
                                continue;//跳过这个情况
                            result = numbers[i].除(numbers[j]);
                            break;
                        case 4:
                            result = numbers[j].减(numbers[i]);
                            break;
                        case 5:
                            if (numbers[i].是零())
                                continue;//跳过这个情况
                            result = numbers[j].除(numbers[i]);
                            break;
                        default:
                            throw new IndexOutOfBoundsException("有问题");
                    }
//                    boolean 搜索修改过 = false;
                    boolean 记住这次状态 = 这一次出现过小数;
                    if (result.不是整数())
                        这一次出现过小数 = true;
                    //进一步搜索
                    int tempCnt = 0;
                    for (int l = 0; l < numbers.length; l++) {
                        if (l==i)
                            reduced[tempCnt++] = result;
                        else if (l==j) ; //i永远比j小，所以i插入新数组，j不插入新数组
                        else
                            reduced[tempCnt++] = numbers[l];
                    }
                    solve(reduced);
//                    if (搜索修改过)
//                        出现过小数 = false;
                    这一次出现过小数 = 记住这次状态;
                }
            }

    }
}

/**
 * @author 叶璨铭
 */
class Rational {
    //以下是本质决定数，也是互质的
    //numerator
    private int 分子;
    //denominator
    private int 分母;
    public int 取分子() {
        return 分子;
    }

    public void 令分子为(int 分子) {//此方法不会自动约分，约分是惰性的
        this.分子 = 分子;
        已经被约分 = false;
    }

    public int 取分母() {
        return 分母;
    }

    public void 令分母为(int 分母) {
        this.分母 = 分母;
        已经被约分 = false;
    }

    private static int gcd(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        //如果不是0的话，有如下的数学原理
        //If p > q, the gcd of p and q is the same as the gcd of q and p % q.
        return gcd(Math.min(a, b), Math.max(a, b) % Math.min(a, b));
    }

    private boolean 已经被约分 = false;
    public void 约分() {
        //0的问题
        if (分母 == 0)
            throw new ArithmeticException("分母不能是0");// 与java api一致，不需要写throws在方法上
        //符号规范：分子含有负号，分母不要有
        int sign = 1;
        if (Math.signum(分子) * Math.signum(分母) < 0)
            sign = -1;
        分子 = Math.abs(分子) * sign;
        分母 = Math.abs(分母);
        //gcd约分
        int temp = gcd(分子, 分母);
        分母 /= temp;
        分子 /= temp;
        已经被约分 = true;
    }

    public Rational(int 分子, int 分母) {
        this.分子 = 分子;
        this.分母 = 分母;
    }
    public Rational(int 整数) {
        this.分子 = 整数;
        this.分母 = 1;
        已经被约分 =true;
    }

    public boolean 是正数() {
        return 分子 > 0;
    }

    public boolean 是假分数() {//真正的分数应该小于1；假分数可以以真分数形式显示
        return 分子 > 分母;
    }

    public boolean 不是整数(){
        return 分子%分母!=0;
    }
    public boolean 是整数(){
        return 分子%分母==0;
    }
    public boolean 是零(){
        if (分母==0)
            throw new ArithmeticException("分母不能是0");// 与java api一致，不需要写throws在方法上
        return 分子==0;
    }

    public String 显示(boolean 以带分数形式) {
        if(!已经被约分)
            约分();
        String r = "";
        if (以带分数形式)
            if (是假分数()) {
                int 带整数 = 分子 / 分母;//无需考虑符号,因为都一样
                int 真子 = Math.abs(分子 % 分母);//符号在带整数里面,这里隐式使用了括号
                r = 带整数 + "_" + 真子 + "|" + 分母;
            } else r = 分子 + "|" + 分母;
        else r = 分子 + "|" + 分母;
        return r;
    }

    public String toString() {
        return 显示(true);
    }
    public double toDouble(){return ((double)分子/(double)分母);}

    public boolean 等于(int 整数){
        if (this.不是整数())
            return false;
        return  分子/分母==整数;
    }
    //运算工具包
//    public 分数 被加上(分数 b) {
//
//    }
    public Rational 加(Rational b) {
        int r1 = 0, r2 = 0;
        int p_ = b.分子;
        int q_ = b.分母;
        r2 = 分母 * q_;
        r1 = 分子 * q_ + p_ * 分母;
        return new Rational(r1, r2);
    }

    public static Rational 加(Rational a, Rational b) {
        return a.加(b);
    }

    //    public void 自加(分数 b){
//        this = this.加(b);
//    }
    public Rational 减(Rational b) {
        int r1 = 0, r2 = 0;
        int p_ = b.分子;
        int q_ = b.分母;
        r2 = 分母 * q_;
        r1 = 分子 * q_ - p_ * 分母;
        return new Rational(r1, r2);
    }

    public static Rational 减(Rational a, Rational b) {
        return a.减(b);
    }

    public Rational 乘(Rational b) {//times
        int r1 = 1, r2 = 1;
        r1 = b.分子 * 分子;
        r2 = b.分母 * 分母;
        return new Rational(r1, r2);
    }

    public static Rational 乘(Rational a, Rational b) {
        return a.乘(b);
    }

    //version1
//    public 分数 除(分数 b){//times
//        int r1 = 1,r2 = 1;
//        r1= b.分子 *分母;
//        r2= b.分母 *分子;
//        return new 分数(r1,r2);
//    }
    public Rational 倒数() {
        if (this.分子==0)
            throw new ArithmeticException("欲生成一个倒数，但是新分数的分母不能是0。");
        return new Rational(this.分母, this.分子);
    }

    public static Rational 倒数(Rational b) {
        if (b.分子==0)
            throw new ArithmeticException("分母不能是0");
        return new Rational(b.分母, b.分子);
    }

    public Rational 除(Rational b) {
        return this.乘(b.倒数());
    }

    public static Rational 除(Rational a, Rational b) {
        return a.除(b);
    }

    public static void main(String[] args) {
        Rational a = new Rational(0, 4);
        System.out.println("a= " + a);
        System.out.println(a.toDouble());
        System.out.println(a.显示(false));
        System.out.println();

        Rational b = new Rational(1, 16);
        System.out.println("b= " + b);
        System.out.println(b.toDouble());
        System.out.println(b.显示(false));
        System.out.println();

        System.out.println("a+b=" + Rational.加(a, b));
        System.out.println("a-b=" + Rational.减(a, b));
        System.out.println("a*b=" + Rational.乘(a, b));
        System.out.println("a/b=" + Rational.除(a, b));
    }
}
