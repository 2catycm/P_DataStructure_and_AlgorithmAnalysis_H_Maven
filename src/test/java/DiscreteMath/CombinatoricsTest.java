package DiscreteMath;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombinatoricsTest {

    @Test
    void main() {
        Combinatorics.main(null);
    }

    @Test
    void factorial() {
        assertEquals(6, Combinatorics.factorial(3));
        assertEquals(1, Combinatorics.factorial(0));
    }

    @Test
    void combination() {
        assertEquals(6, Combinatorics.combination(4, 2));
        assertEquals(1, Combinatorics.combination(4, 0));
    }
}