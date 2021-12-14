package util.syntax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OJReaderTest {
    private static OJReader in = new OJReader();
    @Test
    void next() {
        System.out.println(in.next());//JUnit 不行
    }

    @Test
    void nextInt() {
    }

    public static void main(String[] args) {
        System.out.println(in.next());
        System.out.println(in.next());
    }
}