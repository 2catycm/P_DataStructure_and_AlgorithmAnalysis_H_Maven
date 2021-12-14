package lab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.InputMismatchException;

class OJReader extends StreamTokenizer {
    public OJReader() {
        super(new BufferedReader(new InputStreamReader(System.in)));
    }
    public String next() {
        try {
            var t = super.nextToken();
            if (t==TT_NUMBER)
                return (int)super.nval+"";
            else if (t==TT_WORD)
                return super.sval;
            throw new InputMismatchException("Input is neither a string nor an integer while calling next(). ");
        } catch (IOException e) {
            throw new InputMismatchException("IO exception. ");
        }
    }
    public int nextInt() {
        try {
            var t = super.nextToken();
            if (t!=TT_NUMBER)
                throw new InputMismatchException("Input is not an integer while calling nextInt(). ");
        } catch (IOException e) {
            throw new InputMismatchException("IO exception.");
        }
        return (int) super.nval;
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
