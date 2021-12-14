package lab5;

public class Problem4 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    private static final long modulus = 514329;//被模数
    public static void main(String[] args) {
        in.wordChars('(',')');
        out.println(newSolve(in.next()));
    }
    //思路: 括号是一种等待计算的层次。 关键的是，如果要退出这个层次，那么这个层次下创造的更深的层次必定已经计算完成。
    private static long newSolve(String string){
        ArrayStack<Long> stack = new ArrayStack<>(string.length());
        stack.入栈(0L);//base sum
        for (int i = 0; i < string.length(); i++) {
            final var c = string.charAt(i);
            if (c=='('){
                stack.入栈(0L);
            }else{
                final var topElement = stack.出栈();
                if (topElement==0)//新进的一层
                    stack.入栈((stack.出栈()+1)%modulus);//退出新进的一层，把上一层的累计值加1。因为必定存在base sum，所以必定存在上一层
                else{
                    stack.入栈((stack.出栈()+topElement*2)%modulus);//这一层已经计算结束，让上一层（加法层）加上这个数值。
                }
            }
        }
        return stack.出栈();//因为括号是匹配的，所以这个必定是base sum层。
    }
    //bug1
    //(()(()))
    //bug2
    //(()(())(()))
    //()(())() 错因：退出层的时候，不只是左边的要被加一，而是整个层都要被加一
    private static char[] bracket = {'(',')'};
    private static long solve(String string) {
        ArrayStack<Object> stack = new ArrayStack<>(string.length());
        for (int i = 0; i < string.length(); i++) {
            final var item = string.charAt(i);
            if (item==bracket[0])//左括号
                stack.入栈(item);
            else {//右括号
                final var topElement = stack.出栈();
                if (topElement instanceof Character){
                    final char ch = (Character) topElement;
                    if (ch==bracket[1])
                        throw new RuntimeException("不可能");
                    else if (ch==bracket[0]) {
                        if (!stack.栈空() && stack.栈顶() instanceof Long)
                            stack.入栈(((long)stack.出栈()+1)%modulus);
                        else
                            stack.入栈((long)1);
                    }
                }else if (topElement instanceof Long){
//                    stack.出栈();//左括号消掉 wrong! 左括号可能在很远的的地方出现，这之间的gap是数字，要把数字加起来然后把左括号崩了。
                    Long sum = (Long) topElement;
                    while(stack.栈顶() instanceof Long){
                        sum+=(Long) stack.出栈();
                        sum%=modulus;
                    }
                    stack.出栈();//最后一个
                    stack.入栈((Long)sum*2%modulus);
                }else
                    throw new RuntimeException("不可能的。");
            }
        }
        return (long) stack.栈顶();
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "StackAndQueue打包.java"