package lab6_string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KMPMatcherTest {

    @Test
    void getNextArray() {
        final var match1 = new KMPMatcher("abaabcac", true);
        final var match2 = new KMPMatcher("abaabcac", false);
        assertArrayEquals(match1.getNextArray(), match2.getNextArray());
    }
}