package lab6;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

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