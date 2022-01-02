package lab6_string;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class StringMatcherTest {

    @Test
    void getNextArray() {
        var kmpMatcher = new KMPMatcher("abaabab");
        System.out.println(Arrays.toString(kmpMatcher.getNextArray()));
        kmpMatcher = new KMPMatcher("");
        System.out.println(Arrays.toString(kmpMatcher.getNextArray()));
        kmpMatcher = new KMPMatcher("a");
        System.out.println(Arrays.toString(kmpMatcher.getNextArray()));

        kmpMatcher = new KMPMatcher("abacababaab");
        System.out.println(Arrays.toString(kmpMatcher.getNextArray()));
    }

    @Test
    void kmpMatch() {
        var kmpMatcher = new KMPMatcher("abaabab");
        kmpMatcher.match("abaabab abaabab abaabab");
        System.out.println(kmpMatcher.matchedIndex());
        kmpMatcher.match("abaabab abaabab abaabab");

        System.out.println(kmpMatcher.matchedIndex());
        kmpMatcher.match("abaabab abaabab abaabab");

        System.out.println(kmpMatcher.matchedIndex());

    }

}