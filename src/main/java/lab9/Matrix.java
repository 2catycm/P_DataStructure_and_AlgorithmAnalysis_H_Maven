package lab9;

import java.awt.*;
import java.util.Arrays;

class Matrix<T> {
    //形状逻辑
    public int getRows() {
        return roi.height;
    }

    public int getCols() {
        return roi.width;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Matrix: {\n");
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                stringBuilder.append(get(new Point(i, j))).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.append("}").toString();
    }

    public void reshape(int rows, int cols){
        if (getRows()*getCols()!=rows*cols)
            throw new IllegalArgumentException("bad reshape size");
        roi.height = rows;
        roi.width = cols;
        originalCols = cols;//step发生改变。
    }
    //赋值逻辑
    public void fill(T newValue){
        Arrays.fill(this.data, newValue); //不带roi的情况下好用。
    }
    public void set(Point point, T newValue) {
        if (!inBound(point))
            throw new IndexOutOfBoundsException();
        data[(point.x+roi.x) * originalCols + (point.y+roi.y)] = newValue;
    }
    //取值逻辑
    public long sum(){
        //反射获取泛型的场景
        //
        //在Java中可以通过反射获取泛型信息的场景有如下三个：
        //
        //    （1）成员变量的泛型
        //    （2）方法参数的泛型
        //    （3）方法返回值的泛型
        //
        //在Java中不可以通过反射获取泛型信息的场景有如下两个：
        //
        //    （1）类或接口声明的泛型
        //    （2）局部变量的泛型
//        if (!this.getClass().getGenericInterfaces()[0].equals(Integer.class))
//            throw new UnsupportedOperationException();
        return Arrays.stream(data).mapToLong((e)->(Integer)e).sum();
    }
    public T get(Point point) {
        if (!inBound(point))
            throw new IndexOutOfBoundsException();
        return data[(point.x+roi.x) * originalCols + (point.y+roi.y)];
    }
    boolean inBound(Point point) {
        return 0 <= point.x && point.x < getRows() && 0 <= point.y && point.y < getCols();
    }

    public Matrix(int rows, int cols) {
        this(rows, cols, (T[]) new Object[rows * cols], new Rectangle(0, 0, cols, rows));
    }
    public Matrix<T> roi(Rectangle roi){
        if (!inBound(roi.getLocation())|| !inBound(new Point(roi.x+roi.height, roi.y+roi.width)))
            throw new IndexOutOfBoundsException("roi cannot be created.");
        return new Matrix<T>(originalRows, originalCols, data, roi);
    }
    private Matrix(int originalRows, int originalCols, T[] data, Rectangle roi) {
        this.originalRows = originalRows;
        this.originalCols = originalCols;
        this.data = data;
        this.roi = roi;
    }
    private int originalRows, originalCols;
    private final T[] data;
    private Rectangle roi;

    public static void readDataFromOJ(Matrix<Integer> intMat, OJReader in) {
        final var point = new Point(0, 0);
        for (int i = 0; i < intMat.getRows(); i++) {
            for (int j = 0; j < intMat.getCols(); j++) {
                point.x = i;
                point.y = j;
                intMat.set(point, in.nextInt());
            }
        }
    }
}
