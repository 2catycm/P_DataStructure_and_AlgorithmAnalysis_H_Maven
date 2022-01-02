package lab6_string;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class FSAMatcherTest {

    @Test
    void match() {
        final var aabaabc = new FSAMatcher("aabaabc");
        aabaabc.match(" aabaabc aabaabc aabaabc");
        System.out.println(aabaabc.matchedIndex());
        aabaabc.match(" aabaabc aabaabc aabaabc");
        System.out.println(aabaabc.matchedIndex());
    }

    @Test
    void getDfa() {
        System.out.println(Arrays.deepToString(new FSAMatcher("aabaabab").getDfa()));
    }
}