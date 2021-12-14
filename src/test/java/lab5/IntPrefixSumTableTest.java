package lab5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntPrefixSumTableTest {

    @Test
    void sumAmong() {
        int[] a = {1, 2, 3, 4, 5};
        final var intPrefixSumTable = new IntPrefixSumTable(a);
        assertEquals(1, intPrefixSumTable.sumAmong(0, 1));
        assertEquals(15, intPrefixSumTable.sumAmong(0, 5));
        assertEquals(7, intPrefixSumTable.sumAmong(2, 4));
    }
}