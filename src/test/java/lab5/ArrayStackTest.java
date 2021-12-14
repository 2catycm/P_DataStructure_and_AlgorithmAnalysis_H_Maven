package lab5;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ArrayStackTest {
    private static ArrayStack<String> stack1;
@BeforeAll
static void prepare(){
    stack1= new ArrayStack<>(3);
    stack1.入栈("123455");
    stack1.入栈("155");
    stack1.入栈("123");
}
    @Test
    void 可扩容入栈() {
        stack1.可扩容入栈(1+"扩容");
    }

    @Test
    void 入栈() {
        stack1.入栈(1+"非扩容");
    }

    @Test
    void 出栈() {
        System.out.println(stack1.toString());
        System.out.println(stack1.出栈());
        System.out.println(stack1.出栈());
        System.out.println(stack1.出栈());
        System.out.println(stack1.出栈());
        System.out.println(stack1.出栈());
        System.out.println(stack1.出栈());
    }

    @Test
    void 栈空() {
        System.out.println(stack1.栈空());
    }

    @Test
    void 当前容纳量() {
        System.out.println(stack1.栈深());
    }
}