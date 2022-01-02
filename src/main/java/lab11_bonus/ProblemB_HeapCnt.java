package lab11_bonus;

//# pragma OJ Main
public class ProblemB_HeapCnt {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    private static long modulus = 998244353;
    public static void main(String[] args) {
        out.println(solve(in.nextInt()));
    }

    static long solve(int n) {
        if (n==0) throw new RuntimeException("有问题");
        if (n==1) return 1;
        if (n==2) return 1;
        if (n==3) return 2;
        int h = (int) (Math.log(n)/Math.log(2));
        int left, right;
        final int baseN = 1<<(h-2);
        final int newLineHalf = 1<<(h-1);
        final var newLine = n-1 - baseN * 2;
        if (newLine > newLineHalf){
            left = baseN+newLineHalf;
            right = baseN+ 2*newLineHalf- newLine;
        }else{
            left = baseN+ newLine;
            right = baseN;
        }
        return solve(left) * solve(right) * combination(n-1, right);
    }
    static long combination(int m, int n){
        int  i;
        if(n>m-n) n=m-n;
        double s1=0.0;
        double s2=0.0;
        for (int j = m-n+1; j <=m; j++) {
            s1+=Math.log(j);
        }
        for (int j = 1; j <=n; j++) {
            s2+=Math.log(j);
        }
        return (long) Math.exp(s1-s2);
    }
}
//# include "OnlineJudgeIO.java"