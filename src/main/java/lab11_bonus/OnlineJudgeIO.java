package lab11_bonus;

import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.function.IntConsumer;

class OJReader extends StreamTokenizer {
    public OJReader() {
        super(new BufferedReader(new InputStreamReader(System.in)));
    }

    public String next() {
        try {
            var t = super.nextToken();
            if (t == TT_NUMBER)
                return (int) super.nval + "";
            else if (t == TT_WORD)
                return super.sval;
            throw new InputMismatchException("Input is neither a string nor an integer while calling next(). ");
        } catch (IOException e) {
            throw new InputMismatchException("IO exception. ");
        }
    }

    public int nextInt() {
        try {
            var t = super.nextToken();
            if (t != TT_NUMBER)
                throw new InputMismatchException("Input is not an integer while calling nextInt(). ");
        } catch (IOException e) {
            throw new InputMismatchException("IO exception.");
        }
        return (int) super.nval;
    }

    public void nextEOFForEachInt(IntConsumer consumer) {
        try {
            for (var t = super.nextToken(); t!=TT_EOF; t = super.nextToken()) {
                consumer.accept((int)super.nval);
            }
        } catch (IOException e) {
            throw new InputMismatchException("IO exception.");
        }
    }

    public int[] nextIntArray() {
        return nextIntArray(this.nextInt());
    }

    public int[] nextIntArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = this.nextInt();
        }
        return array;
    }

    public Integer[] nextIntegerArray() {
        return nextIntegerArray(this.nextInt());
    }

    public Integer[] nextIntegerArray(int length) {
        Integer[] array = new Integer[length];
        for (int i = 0; i < length; i++) {
            array[i] = this.nextInt();
        }
        return array;
    }
}

class OJWriter extends PrintWriter {
    public <T> void printlnList(List<T> list) {
        printlnList(list, " ");
    }

    public <T> void printlnList(List<T>  list, String elementSeparator) {
        for (var e : list) {
            this.print(e + elementSeparator);
        }
        this.println();
    }

    public void printlnIntArray(int[] array) {
        printlnIntArray(array, " ");
    }
    public void printlnIntArray(int[] array, String elementSeparator) {
        for (int i = 0; i < array.length; i++) {
            this.print(array[i] + " ");
        }
        this.println();
    }

    public <T> void printlnArray(T[] array) {
        printlnArray(array, " ");
    }
    public <T> void printlnArray(T[] array, String elementSeparator) {
        for (int i = 0; i < array.length; i++) {
            this.print(array[i] + " ");
        }
        this.println();
    }

    public OJWriter() {
        super(new BufferedOutputStream(System.out), true);
    }
}