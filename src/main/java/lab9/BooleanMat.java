package lab9;

import java.awt.*;
import java.util.BitSet;

class BooleanMat {
    private int rows, cols;
    private BitSet data;
    public BooleanMat(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new BitSet(rows*cols);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void clear(Point point) {
        if (!inBound(point))
            throw new IndexOutOfBoundsException();
        data.clear(point.x * cols + point.y);
    }
    public void set(Point point) {
        if (!inBound(point))
            throw new IndexOutOfBoundsException();
        data.set(point.x * cols + point.y);
    }

    public boolean get(Point point) {
        if (!inBound(point))
            throw new IndexOutOfBoundsException();
        return data.get(point.x * cols + point.y);
    }
    public boolean get(int index){
        return data.get(index);
    }
    public boolean inBound(Point point) {
        return 0 <= point.x && point.x < rows && 0 <= point.y && point.y < cols;
    }
}