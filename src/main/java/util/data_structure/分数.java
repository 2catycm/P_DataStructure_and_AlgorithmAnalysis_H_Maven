package util.data_structure;

/**
 * @author 叶璨铭
 */
public class 分数 {
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

    public 分数(int 分子, int 分母) {
        this.分子 = 分子;
        this.分母 = 分母;
    }
    public 分数(int 整数) {
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
        return 显示(false);
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
    public 分数 加(分数 b) {
        int r1 = 0, r2 = 0;
        int p_ = b.分子;
        int q_ = b.分母;
        r2 = 分母 * q_;
        r1 = 分子 * q_ + p_ * 分母;
        return new 分数(r1, r2);
    }

    public static 分数 加(分数 a, 分数 b) {
        return a.加(b);
    }

    //    public void 自加(分数 b){
//        this = this.加(b);
//    }
    public 分数 减(分数 b) {
        int r1 = 0, r2 = 0;
        int p_ = b.分子;
        int q_ = b.分母;
        r2 = 分母 * q_;
        r1 = 分子 * q_ - p_ * 分母;
        return new 分数(r1, r2);
    }

    public static 分数 减(分数 a, 分数 b) {
        return a.减(b);
    }

    public 分数 乘(分数 b) {//times
        int r1 = 1, r2 = 1;
        r1 = b.分子 * 分子;
        r2 = b.分母 * 分母;
        return new 分数(r1, r2);
    }

    public static 分数 乘(分数 a, 分数 b) {
        return a.乘(b);
    }

    //version1
//    public 分数 除(分数 b){//times
//        int r1 = 1,r2 = 1;
//        r1= b.分子 *分母;
//        r2= b.分母 *分子;
//        return new 分数(r1,r2);
//    }
    public 分数 倒数() {
        if (this.分子==0)
            throw new ArithmeticException("欲生成一个倒数，但是新分数的分母不能是0。");
        return new 分数(this.分母, this.分子);
    }

    public static 分数 倒数(分数 b) {
        if (b.分子==0)
            throw new ArithmeticException("分母不能是0");
        return new 分数(b.分母, b.分子);
    }

    public 分数 除(分数 b) {
        return this.乘(b.倒数());
    }

    public static 分数 除(分数 a, 分数 b) {
        return a.除(b);
    }

    public static void main(String[] args) {
        分数 a = new 分数(0, 4);
        System.out.println("a= " + a);
        System.out.println(a.toDouble());
        System.out.println(a.显示(false));
        System.out.println();

        分数 b = new 分数(1, 16);
        System.out.println("b= " + b);
        System.out.println(b.toDouble());
        System.out.println(b.显示(false));
        System.out.println();

        System.out.println("a+b=" + 分数.加(a, b));
        System.out.println("a-b=" + 分数.减(a, b));
        System.out.println("a*b=" + 分数.乘(a, b));
        System.out.println("a/b=" + 分数.除(a, b));
    }
}
