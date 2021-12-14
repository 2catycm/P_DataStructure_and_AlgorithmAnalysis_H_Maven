package lab4;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;

class OJWriter extends PrintWriter {
    public void printIntArray(int[] array){
        for (int i = 0; i < array.length; i++) {
            this.print(array[i]+" ");
        }
        this.println();
    }
    public <T> void printArray(T[] array){
        for (int i = 0; i < array.length; i++) {
            this.print(array[i]+" ");
        }
        this.println();
    }
    public OJWriter() {
        super(new BufferedOutputStream(System.out), true);
    }


}
