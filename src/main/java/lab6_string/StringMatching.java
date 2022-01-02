package lab6_string;
//#include "AlphaBet.java"
abstract class StringMatcher {
    protected String pattern;
    protected int index = -1;

    public StringMatcher(String pattern) {
        this.pattern = pattern;
    }

    abstract public void match(String text);

    public int matchedIndex() {
        return index;
    }
    public void reset() {
        index = 0;
    }
}

class BruteForceMatcher extends StringMatcher {
    public BruteForceMatcher(String pattern) {
        super(pattern);
    }

    @Override
    public void match(String text) {
        int j = 0;//当前匹配到pattern的哪一个字符（的结尾）
        int i = index + 1;//当前匹配到text的哪一个位置（的结尾）。
        while (true) {
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
                i++;
            } else {
                j = 0;//j从头开始匹配
                i -= j;//j表示的是已经匹配的数量，所以i倒车，以前的匹配都是作废的。
            }
            if (i >= text.length()){
                index = -1;
                break;
            }
            if (j >= pattern.length()) {
                index = i - pattern.length();
                break;
            }
        }
    }
}
class FSAMatcher extends StringMatcher{
    protected int[][] dfa;
    //例如 ababc
    //X:    0
    //dfa:   0 1 2 3 4     5
    //a      1   3
    //b        2   4
    //c              5
    public FSAMatcher(String pattern) {
        this(pattern, ContinuousAlphabet.LOWERCASE);
    }
    public FSAMatcher(String pattern, ContinuousAlphabet alphabet) {
        super(pattern);
        dfa = new int[alphabet.length()][pattern.length()];
        dfa[alphabet.indexOf(pattern.charAt(0))][0] = 1;
        int restartState = 0;
        for (int j = 1; j < pattern.length(); j++) {
            //失败情况下，把前面的去掉0的[1到i-1]拿去跑，或者是空集。
            for (int c = 0; c < alphabet.length(); c++) {
                dfa[c][j] = dfa[c][restartState];
            }
            //成功的一个情况
            final var jSuccessChar = alphabet.indexOf(pattern.charAt(j));
            dfa[jSuccessChar][j] = j+1;
            //更新X
            restartState = dfa[jSuccessChar][restartState];
        }
    }

    @Override
    public void match(String text) {
        for (int i = index+1, j = 0; i < text.length(); i++) {
            j = dfa[text.charAt(i)][j]; //状态转移。
            if (j==pattern.length()){
                index = i+1-pattern.length();
                return;
            }
        }
        index = -1;
    }

    public int[][] getDfa() {
        return dfa;
    }
}
class KMPMatcher extends StringMatcher {
    protected int[] nextArray;

    public KMPMatcher(String pattern) {
        super(pattern);
        nextArray = new int[pattern.length()];
        for (int i = 1, j = 0; i < nextArray.length; ) {
            //求next[i]. 当前pattern[0,i]
            if (pattern.charAt(j) == pattern.charAt(i)) {
                nextArray[i] = j + 1;
                j++;
                i++;
            } else if (j == 0) {
                nextArray[i] = 0;
                i++;
            } else {
                j = nextArray[j - 1];
            }
        }
    }

    public int[] getNextArray() {
        return nextArray;
    }

    @Override
    public void match(String text) {
        int j = 0;//当前匹配到pattern的哪一个字符（的结尾）
        int i = index + 1;//当前匹配到text的哪一个位置（的结尾）。
        while (true) {
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
                i++;
            } else if (j == 0) {
                i++;//j=0，那么j不动，i动
            } else {
                j = nextArray[j - 1]; //i不用动，i永远不后退。
            }
            if (j >= pattern.length()) {
                index = i - pattern.length();
                break;
            }
            if (i >= text.length()) {
                index = -1;
                break;
            }
        }
    }
}