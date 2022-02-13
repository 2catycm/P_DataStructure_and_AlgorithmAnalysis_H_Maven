package util.syntax;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.List;

public class OJWriter extends PrintWriter {
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