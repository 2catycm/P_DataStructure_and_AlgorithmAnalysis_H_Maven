package lab5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Scanner;
public class Problem5 {
//    private static OJReader in = new OJReader();
    private static Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        //好的习惯还是要限制作用域
//        int n, q, u,w, val;//因为不是并发，而且这些变量是单例（全题范围内有效，或者说一个operation中只能有一个。），所以main中声明。
        while (in.hasNext()){
            int n = in.nextInt();
            int q = in.nextInt();
//            ArrayDeque<Integer>[] arrayDeques = (ArrayDeque<Integer>[]) new Object[n];//报错
            ArrayDeque<Integer>[] arrayDeques = (ArrayDeque<Integer>[]) Array.newInstance(ArrayDeque.class, n);
//            LinkedDeque<Integer>[] arrayDeques = (LinkedDeque<Integer>[]) Array.newInstance(LinkedDeque.class, n);
//            for (var d:arrayDeques)
//                d = new ArrayDeque<Integer>(2*q/n); //bad initialization. Java foreach is read only!
            for (int i = 0; i < arrayDeques.length; i++) {
                arrayDeques[i] = new ArrayDeque<>(10*q/n);
//                arrayDeques[i] = new LinkedDeque<>();
            }
            for (int i = 0; i < q; i++) {
                int u, w;
                switch (in.nextInt()){
                    case 1:
                        u = in.nextInt();
                        w = in.nextInt();
                        int val = in.nextInt();
                        var dequeU1 = arrayDeques[u - 1];
                        if (w == 0) {
                            dequeU1.插队(val);
                        } else {
                            dequeU1.排队(val);
                        }
                        break;
                    case 2:
                        u = in.nextInt();
                        w = in.nextInt();
                        var dequeU2 = arrayDeques[u - 1];
                        if (dequeU2.队空())
                            out.println(-1);
                        else
                            out.println(w==0? dequeU2.办理业务(): dequeU2.走后门());
                        break;
                    case 3:
                        var dequeU = arrayDeques[in.nextInt()-1];
                        var dequeV = arrayDeques[in.nextInt()-1];
                        w = in.nextInt();
                        while (!dequeV.队空())
                            dequeU.排队(w==0?dequeV.办理业务():dequeV.走后门());
                        break;
                    default:
                        throw new RuntimeException("unknown operation.");
                }
            }
        }
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "StackAndQueue打包.java"