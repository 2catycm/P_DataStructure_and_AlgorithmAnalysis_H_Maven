package lab9_graph;

import org.junit.jupiter.api.Test;

import java.awt.*;

class MatrixTest {

    @Test
    void testToString() {
        Matrix<Integer> mat = new Matrix<>(6,6);
        mat.fill(10);
        System.out.println(mat);
        final var roi = mat.roi(new Rectangle(2, 2, 3, 3));
        System.out.println(roi);
        roi.reshape(1, 9);
        System.out.println(roi);
        System.out.println(mat.sum());
    }
}