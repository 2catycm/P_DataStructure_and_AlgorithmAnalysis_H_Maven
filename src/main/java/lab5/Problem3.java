package lab5;

public class Problem3 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        in.wordChars('(','}');//StreamTokenizer 制定字符集。
        int T = in.nextInt();
        for (int i = 0; i < T; i++) {
            int n = in.nextInt();//n的值不需要其实。
            out.println(solve(in.next())?"YES":"NO");
        }
    }
//    private static enum LeftBrackets{
//
//    }
//private static String leftBrackets = "({[";
//    private static String rightBrackets = ")}]";
    private static char[] leftBrackets = {'(', '{', '['};
    private static char[] rightBrackets = {')', '}', ']'};
    private static int rankInLeftBrackets(char c){
        for (int i = 0; i < leftBrackets.length; i++) {
            if (leftBrackets[i]==c)
                return i;
        }
        return -1;
    }
    private static int rankInRightBrackets(char c){
        for (int i = 0; i < rightBrackets.length; i++) {
            if (rightBrackets[i]==c)
                return i;
        }
        return -1;
    }

    private static boolean solve(String string) {
        ArrayStack<Integer> stack = new ArrayStack<>(string.length());
        for (int i = 0; i < string.length(); i++) {
            final var item = string.charAt(i);
//            if (stack.栈空()) {
//                final var rank = rankInLeftBrackets(item);
//                if (rank !=-1)
//                    stack.入栈(rank);
//                else
//                    return false;
//            }
//            else{
//
//                if (rankInLeftBrackets(stack.出栈())!=rankInRightBrackets(item))
//                    return false;
//            }
            int rank = rankInLeftBrackets(item);
            if (rank!=-1)//左括号，那么继续入栈
                stack.入栈(rank);
            else{//右括号，需要找一个左括号和他进行battle
                if (stack.栈空())
                    return false;
                final var left = stack.出栈();
                if (item==rightBrackets[left])
                    continue;
                else
                    return false;
            }
        }
        return stack.栈空();
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "StackAndQueue打包.java"