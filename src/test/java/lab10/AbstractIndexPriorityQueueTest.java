package lab10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractIndexPriorityQueueTest {

    @Test
    void offer() {
        final var integerIndexPriorityQueue = new IndexPriorityQueue<Integer>();
        integerIndexPriorityQueue.offer(1, 10);
        System.out.println(integerIndexPriorityQueue.peekIndex());
        integerIndexPriorityQueue.offer(2, 90);
        System.out.println(integerIndexPriorityQueue.peekIndex());
        integerIndexPriorityQueue.updateKey(1, 1000);
        System.out.println(integerIndexPriorityQueue.peekIndex());
        integerIndexPriorityQueue.offer(11, 100000);
        System.out.println(integerIndexPriorityQueue.peekIndex());
        System.out.println(integerIndexPriorityQueue.pollIndex());
    }

    @Test
    void poll() {
    }

    @Test
    void updateKey() {
    }
}