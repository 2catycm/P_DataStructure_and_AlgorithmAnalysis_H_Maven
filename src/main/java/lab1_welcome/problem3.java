package lab1_welcome;

import java.util.Scanner;

public class problem3 {
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            doOnce();
        }
//        String[] result = new String[1000];//nulls 表
    }

    private static void doOnce() {
        int m = in.nextInt() - 1; //-1
        //1.确定位数
        //q位数, q-1位数, …… 1位数。 共有
        //这一题，相同位数的一组，是完整的三进制。
        int q;
        for (q = 1; q < 100; q++)
            if (2*m<(Math.pow(3, q)-3))
                break;
        q--;
        //2.确定在该位数的第几个
        //第x个。 比如 2是1位数的第2个, 0是1位数的第0个, 3是2位数中的第0个
        int x = (int) (m - (Math.pow(3, q)-3)/2);

        //3.转换为 三进制，不足位数补零
        StringBuilder stringBuilder = new StringBuilder();
        String ternary = Integer.toString(x, 3);
        for (int i = 0; i < q-ternary.length(); i++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(ternary);
        //4.转换为 236
        System.out.println(stringBuilder.toString().replace('2', '6').replace('0', '2').replace('1', '3'));
    }

}
