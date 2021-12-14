package util.syntax;

public class 再看短路问题 {
    public static void main(String[] args) {
        int i = 0;
        var b = (i != 0) && ((i = 2) == 2);
        System.out.println(i);
        b = !((i != 0) && ((i = 2) == 2));
        System.out.println(i);
        b = ((i != 0) || ((i = 2) == 2));
        System.out.println(i);
    }
}
