package lab4_linkedlist;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class SolutionListTest {

    @Test
    void insert() {
    }

    @Test
    void find() {
    }

    @Test
    void transform() {
    }

    public static void main(String[] args) {
        new SolutionList("01234567890123456789");
        var a = new ArrayList<Integer>();
        a.add(2);
        a.add(0, 1);//前插
        System.out.println(a);
    }
}