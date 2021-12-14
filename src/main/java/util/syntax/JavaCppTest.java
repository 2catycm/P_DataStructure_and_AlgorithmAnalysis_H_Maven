package util.syntax;

public class JavaCppTest {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        int i = 1/0; //
//        Exception in thread "main" java.lang.ArithmeticException: / by zero
//        at util.JavaCppTest.main(JavaCppTest.java:5)
        //C++的异常无法捕获。
    }

}
