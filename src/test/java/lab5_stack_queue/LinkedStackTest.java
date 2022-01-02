package lab5_stack_queue;

import org.junit.jupiter.api.Test;

class LinkedStackTest {
    private static LinkedStack<Integer> stack = new LinkedStack<>();
    @Test
    void push() {
        stack.入栈(123);
        stack.入栈(456);
        System.out.println(stack.toString());
    }

    @Test
    void pop() {
        System.out.println(stack.安全出栈());
    }

    @Test
    void peek() {
        System.out.println(stack.栈顶());
    }

    @Test
    void isEmpty() {
        System.out.println(stack.栈空());
    }

    @Test
    void testToString() {
        System.out.println(stack.toString());
    }
}