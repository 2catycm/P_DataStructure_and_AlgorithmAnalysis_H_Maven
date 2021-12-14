package lab6;

abstract class Alphabet {
    //可以想象成一个 {a,b,c,d,e}这样的表，是char的集合
    public abstract int indexOf(char ch);
    public abstract char charOfIndex(int index);
    public abstract int length();
}
//class CipherTable extends Alphabet{
//    //{b c d e}  第'a'-'a' 个字母就是
//    private char[] table;
//
//    public CipherTable(char[] table) {
//        this.table = table;
//    }
//
//    @Override
//    public int indexOf(char ch) {
//        return 0;
//    }
//
//    @Override
//    public char charOfIndex(int index) {
//        return 0;
//    }
//
//    @Override
//    public int length() {
//        return 0;
//    }
//}
class ContinuousAlphabet extends Alphabet {
    public static final ContinuousAlphabet LOWERCASE = new ContinuousAlphabet('a', 'z');
    public static final ContinuousAlphabet UPPERCASE = new ContinuousAlphabet('A', 'Z');
    private char lowInclusive;
    private char highInclusive;

    public ContinuousAlphabet(char lowInclusive, char highInclusive) {
        if (lowInclusive > highInclusive) {
            this.lowInclusive = highInclusive;
            this.highInclusive = lowInclusive;
        } else {
            this.lowInclusive = lowInclusive;
            this.highInclusive = highInclusive;
        }
    }
    @Override
    public int indexOf(char ch) {
        return ch - lowInclusive;
    }

    @Override
    public char charOfIndex(int index) {
        return (char) (lowInclusive+index);
    }
    @Override
    public int length() {
        return highInclusive - lowInclusive + 1;
    }
}

