package lab11_bonus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProblemB_DPTest {

    @Test
    void combination() {
        final var combination = new Combination(25);
        System.out.println(combination.combination(25,12));
    }
    private static long[][] combination;
}