package util.data_structure;

import org.junit.jupiter.api.Test;
import util.data_structure.InclusiveIntegerRange.SingleInclusiveRange;

import java.util.stream.IntStream;

class InclusiveIntegerRangeTest {

    @Test
    void includes() {
        System.out.println(new InclusiveIntegerRange(0, 100).includes(4));
    }

    public static void main(String[] args) {
        System.out.println(new SingleInclusiveRange(0, 10)._intersect(new SingleInclusiveRange(2, 11))
                .union(new SingleInclusiveRange(9, 100)));
        System.out.println(new SingleInclusiveRange(0, 100).
                _intersect(new SingleInclusiveRange(100, 120)).union(new SingleInclusiveRange(0, 1)));
        System.out.println(new SingleInclusiveRange(0,-1)._intersect(new SingleInclusiveRange(-2,3)
                ._intersect(new SingleInclusiveRange(-1, 5)._intersect(new SingleInclusiveRange(20, 100)))).isEmptySet());
        System.out.println(new SingleInclusiveRange(1, Integer.MAX_VALUE).complement());
        System.out.println(new SingleInclusiveRange(1,-1).complement());


    }
}