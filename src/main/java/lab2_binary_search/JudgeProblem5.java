package lab2_binary_search;

import org.apache.commons.lang3.time.StopWatch;

import java.util.Arrays;
import java.util.Random;

public class JudgeProblem5 {
    //cmd 命令
//    "D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\bin\java.exe" -javaagent:D:\开发工具\JetBr
//    ains\apps\IDEA-U\ch-0\211.7628.21\lib\idea_rt.jar=54351:D:\开发工具\JetBrains\apps\IDEA-U\ch-0\211.7628.21\bin -Dfile.encoding=UTF-8 -classpath "D:\EnglishStandardPath\DevelopEnvironme
//    nt\jdks\jkd 1.8\jre\lib\charsets.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\deploy.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\ext\access
//-bridge-64.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\ext\cldrdata.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\ext\dnsns.jar;D:\EnglishSt
//    andardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\ext\jaccess.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\ext\jfxrt.jar;D:\EnglishStandardPath\DevelopEnvironmen
//    t\jdks\jkd 1.8\jre\lib\ext\localedata.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\ext\nashorn.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\
//    ext\sunec.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\ext\sunjce_provider.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\ext\sunmscapi.jar;D:
//            \EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\ext\sunpkcs11.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\ext\zipfs.jar;D:\EnglishStandardPath\Devel
//    opEnvironment\jdks\jkd 1.8\jre\lib\javaws.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\jce.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\jfr.
//            jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\jfxswt.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\jsse.jar;D:\EnglishStandardPath\DevelopEnvi
//    ronment\jdks\jkd 1.8\jre\lib\management-agent.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\plugin.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\l
//    ib\resources.jar;D:\EnglishStandardPath\DevelopEnvironment\jdks\jkd 1.8\jre\lib\rt.jar;D:\EnglishStandardPath\Practice_File\P_DataStructure_and_AlgorithmAnalysis_H\out\production\P_Dat
//            aStructure_and_AlgorithmAnalysis_H;D:\EnglishStandardPath\Practice_File\P_DataStructure_and_AlgorithmAnalysis_H\lib\commons-lang3-3.12.0.jar" lab2.JudgeProblem5 >a.txt
    private static StopWatch watch = new StopWatch();
    public static void main(String[] args) {
//        System.out.println(timeTrail(100000));//出现-1问题
//        System.out.println(timeTrail(10000));//出现-1问题
//        System.out.println(timeTrail(100);//出现-1问题
//        System.out.println(timeTrail(100, 65,88));
        //出现-1问题是因为左右缩小错误
//        System.out.println(timeTrail(5));
//        for (int i = 0; i < 87; i++) {
//            System.out.println(timeTrail(100, i+1,88));
//        }
        System.out.println(timeTrail(10));//解决了一个bug： completeReport 的lastIndex有问题。

    }

    private static String timeTrail(int n) {
        return timeTrail(n,1,n);
    }
    private static String timeTrail(int n, int l, int r) {
        int[] data1 = generateData(n);
        final Integer[] data1_boxed = Arrays.stream(data1).boxed().toArray(Integer[]::new);
        int[] data2 = generateData(n);
        final Integer[] data2_boxed = Arrays.stream(data2).boxed().toArray(Integer[]::new);
        watch.reset();
        watch.start();
        final int solveBruteForce = Problem5.solve_only_merge(data1, data2, l, r);
        watch.stop();
        System.out.println(watch.formatTime());
        watch.reset();
        watch.start();
//        watch.split();
        final int solve = Problem5New.solve(data1_boxed, data2_boxed, l, r);
        watch.stop();
        if (solve!=solveBruteForce)
            System.out.println("expected: " + solveBruteForce + "\nactually: " + solve
                +"\nat: ArrayA = "+Arrays.toString(data1)+"\n    ArrayB = "+Arrays.toString(data2));

            return watch.formatTime();
    }

    private static int[] generateData(int n) {
        int[] result = new int[n];
        final Random random = new Random();
        for (int i = 0; i < n; i++) {
//            result[i] = random.nextInt((int)1e9); //坏处：随机数不会重复，但应该允许重复
            result[i] = i>n/2?2:1;
        }
        Arrays.sort(result);
        return result;
    }
}
