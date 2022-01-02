package lab5_stack_queue;

import org.junit.jupiter.api.Test;

class LinkedDequeTest {
    private static LinkedDeque<Integer> deque = new LinkedDeque<>();
//    @BeforeAll
//    void prepare(){
//    }

    @Test
    void 排队() {
        deque.排队(123);
        deque.排队(456);
        System.out.println(deque);
    }

    @Test
    void 插队() {
        deque.插队(999);
        System.out.println(deque);
    }

    @Test
    void 办理业务() {
        System.out.println(deque.办理业务());
    }

    @Test
    void 走后门() {
        System.out.println(deque.走后门());
    }

    @Test
    void 队首() {
        System.out.println(deque.队首());
    }

    @Test
    void 队尾() {
        System.out.println(deque.队尾());
    }

    @Test
    void 队空() {
        System.out.println(deque.队空());
    }

    @Test
    void testToString() {
        System.out.println(deque);
    }
}