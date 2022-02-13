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
            if (i >= text.length()) {
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

class FSAMatcher extends StringMatcher {
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
            dfa[jSuccessChar][j] = j + 1;
            //更新X
            restartState = dfa[jSuccessChar][restartState];
        }
    }

    @Override
    public void match(String text) {
        for (int i = index + 1, j = 0; i < text.length(); i++) {
            j = dfa[text.charAt(i)][j]; //状态转移。
            if (j == pattern.length()) {
                index = i + 1 - pattern.length();
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

    public KMPMatcher(String pattern, boolean method2) {
        super(pattern);
        if (method2) {
            nextArray = new int[pattern.length()];
            int j = 0;
            for (int i = 1; i < nextArray.length; i++) {
                while (j > 0 && pattern.charAt(j) != pattern.charAt(i))
                    j = nextArray[j - 1];
                if (pattern.charAt(j) == pattern.charAt(i))
                    j++;
                nextArray[i] = j;
            }
        }else{
            //以下代码从0开始下标。
            nextArray = new int[pattern.length()];
            //对于某一时刻的i， j表示P[0...i-1]中最长公共前后缀 的前缀 的下一个；j因此也正是最长公共前后缀的长度。
            //比如，01234
            //     aabaa，    i = 5时， j应该等于2。
            //     一方面因为0-4这个长度为i=5的字符串的最长公共前后缀长度=2，
            //    另一方面因为前缀的下一个是b，也就是下标为2.
            int j = 0;
            //for从左往右 求next[i]. 当前处理的是P[0...i]， 从自动机观点来看，求解的是pattern[1...i]放入自动机得到的状态.
            for (int i = 1; i < nextArray.length; ) {
                if (pattern.charAt(j) == pattern.charAt(i)) {//如果正在匹配的新的match，那么当前就确定了，整下一个。
                    nextArray[i] = j + 1;
                    j++;
                    i++;
                } else if (j == 0) { //特殊处理，如果回退到0，那么next[i]确定是0, 可以计算下一个了。
                    nextArray[i] = 0;
                    i++;
                } else {
                    j = nextArray[j - 1];//j进行一次回退。
                }
            }
        }
    }

    public KMPMatcher(String pattern) {
        this(pattern, false);
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