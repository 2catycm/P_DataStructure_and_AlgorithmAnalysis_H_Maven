package lab4_linkedlist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BidirectionalLinkedNodesTest {

    @org.junit.jupiter.api.Test
    void pushAfter() {
    }

    @org.junit.jupiter.api.Test
    void pushFront() {
    }

    @org.junit.jupiter.api.Test
    void getFirst() {
    }

    @org.junit.jupiter.api.Test
    void getLast() {
    }

    @org.junit.jupiter.api.Test
    void getLeftMargin() {
    }

    @org.junit.jupiter.api.Test
    void getRightMargin() {
    }

    @org.junit.jupiter.api.Test
    void isEndOfList() {
    }

    @org.junit.jupiter.api.Test
    void readLinkedNodesFromOJ() {
    }

    @org.junit.jupiter.api.Test
    void testReadLinkedNodesFromOJ() {
    }

    @Test
    void testEquals() {
        final BidirectionalLinkedNodes<Object> nodes1 = new BidirectionalLinkedNodes<>();
        final BidirectionalLinkedNodes<Object> nodes2 = new BidirectionalLinkedNodes<>();
        for (int i = 0; i < 5; i++) {
            nodes1.pushAfter(i);
            nodes2.pushFront(5-i-1);
        }
        assertEquals(nodes1, nodes2);
    }

    @Test
    void testHashCode() {
    }
}