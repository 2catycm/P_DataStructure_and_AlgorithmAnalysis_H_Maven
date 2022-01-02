package lab8_advanced_tree;

import org.junit.jupiter.api.Test;
import util.judge.Judge;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
//本测试文件组织关系：上面的方法测试成功依赖于下面的方法测试成功。
class AVLSearchTreeTest {
    private AVLSearchTree<Integer, Integer> tree = new AVLSearchTree<>();
    private int testSize = 10;
    private final int[] ints = Judge.nextRandomPermutation(testSize);

    @Test
    void removeEntry() {
        while (true) {
            prepare();
            assert(!tree.isEmpty());
            for (int i = 0; i < ints.length; i++) {
                final var testKey = ints[i];
                assertEquals(testKey*-100, tree.get(testKey));
                tree.remove(testKey);
                assertNull(tree.get(testKey));
            }
            assert(tree.isEmpty());
        }
    }
    @Test
    void getPredecessorEntry() {
        int testSize = 100;
        while (true) {
            final var ints = Judge.nextRandomPermutation(testSize);
            for (int i = 0; i < ints.length; i++) {
                try {
                    tree.put(ints[i]*2, ints[i]*2 * -100); //稀疏，应该是 2, 4, 6  ... 200
                } catch (AssertionError e) {
                    e.printStackTrace();
                    throw new RuntimeException("ints:" + Arrays.toString(ints) + "putting " + ints[i] + " into\n" + tree);
                }
            }
            for (int i = 0; i < ints.length; i++) {
                //查找1, 3, 5, 7, ... 199的后继，应该是放进去的键。
                final var i1 = 2 * i + 1;
                assertEquals(i1+1, tree.getSuccessorKey(i1));

                assertEquals(i1+1, tree.getPredecessorKey(i1+2));

                assertNull(tree.getPredecessorKey(1-i));
            }
            break;
        }
    }
    @Test
    void getEntry() {
        while (true) {
            prepare();
            for (int i = 0; i < ints.length; i++) {
                assertEquals((i+1)*-100, tree.get(i+1));
                assertEquals(null, tree.get(i+1+testSize));
            }
            break;
        }
    }
    @Test
    void singleTestPutEntry() {
//        final var ints = new int[]{1, 3, 4, 9};
//        final var ints = new int[]{12, 23, 24, 20, 13, 16, 8};
        final var ints = new int[]{1, 4, 8, 5, 9, 10, 2, 7, 3, 6};
        for (int i = 0; i < ints.length; i++) {
            tree.put(ints[i], ints[i] * -100);
        }
    }

    @Test
    void putEntry() {
        while (true) {
//            for (int i = 0; i < ints.length; i++) {
//                try {
//                    tree.put(ints[i], ints[i] * -100);
//                } catch (AssertionError e) {
//                    e.printStackTrace();
//                    throw new RuntimeException("ints:" + Arrays.toString(ints) + "putting " + ints[i] + " into\n" + tree);
//                }
//            }
            prepare();
            System.out.println(Arrays.toString(ints));
            System.out.println(tree);
            System.out.println(tree.isLegalTree());
            break;
        }
    }
//    @BeforeAll
    void prepare(){
        for (int i = 0; i < ints.length; i++) {
            try {
                tree.put(ints[i], ints[i] * -100);
            } catch (AssertionError e) {
                e.printStackTrace();
                throw new RuntimeException("ints:" + Arrays.toString(ints) + "putting " + ints[i] + " into\n" + tree);
            }
        }
    }

    //小语法测试：
    public static void main(String[] args) {
        assert(false); //assert再JUnit下会开。
    }
}