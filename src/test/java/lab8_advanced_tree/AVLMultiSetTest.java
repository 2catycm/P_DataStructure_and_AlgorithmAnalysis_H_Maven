package lab8_advanced_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLMultiSetTest {
    AVLMultiSet<Integer> set = new AVLMultiSet<>();
    @Test
    void add() {
        set.add(2);
        set.add(2);
        set.add(1);
        set.add(1);
        set.add(1);
        set.add(1);
        set.add(3);
        set.add(3);
        set.add(3);
        System.out.println(set);
    }

    @Test
    void remove() {
        set.add(2);
        set.add(2);
        set.add(1);
        set.add(1);
        set.add(1);
        set.add(1);
        set.add(3);
        set.add(3);
        set.add(3);
        set.remove(2);
        set.remove(1);
        set.remove(1);
        set.remove(0);
        set.remove(100);
        System.out.println(set);
        set.remove(2);
        System.out.println(set);
    }

    @Test
    void contains() {
        set.add(2);
        assertTrue(set.contains(2));
        assertFalse(set.contains(3));
    }

    @Test
    void minKey() {
        assertNull(set.minKey());
        set.add(1);
        set.add(10);
        System.out.println(set.minKey());
    }

    @Test
    void maxKey() {
        assertNull(set.maxKey());
        set.add(1);
        set.add(10);
        System.out.println(set.maxKey());
    }
}