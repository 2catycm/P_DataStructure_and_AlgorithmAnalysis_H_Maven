package lab8_advanced_tree;

import org.junit.jupiter.api.Test;
import util.judge.Judge;

import java.util.Arrays;
import java.util.stream.IntStream;

class BinarySearchTreeTest {
    final BinarySearchTree<Integer, Integer> binarySearchTree = new BinarySearchTree<Integer, Integer>();
    @Test
    void test() {
        System.out.println(binarySearchTree.put(2, 200));
        System.out.println(binarySearchTree.put(1, 2));
        System.out.println(binarySearchTree.put(1, 3));
        System.out.println(binarySearchTree.put(4, 400));

        System.out.println(Arrays.toString(IntStream.range(1, 5).mapToObj(binarySearchTree::get).toArray()));
    }

    @Test
    void getPredecessorEntry() {
        System.out.println(binarySearchTree.put(2, 200));
        System.out.println(binarySearchTree.put(1, 2));
        System.out.println(binarySearchTree.put(1, 3));
        System.out.println(binarySearchTree.put(4, 400));

//        System.out.println(binarySearchTree.getPredecessor(3));
//        System.out.println(binarySearchTree.getSuccessor(0));
    }

//    @Test
//    void remove() {
//        binarySearchTree.clear();
//        final var ints = Judge.nextRandomPermutation(10);
//        for (int i = 0; i < ints.length; i++) {
//            binarySearchTree.put(ints[i], ints[i]*-100);
//        }
//        System.out.println(Arrays.toString(IntStream.range(1, 11).mapToObj(binarySearchTree::get).toArray()));
////        binarySearchTree.remove(5);
////        binarySearchTree.remove(8);
////        System.out.println(Arrays.toString(IntStream.range(1, 11).mapToObj(binarySearchTree::get).toArray()));
//    }

    @Test
    //passed, 与手写结果一致。
    void putEntry() {
        final var ints = Judge.nextRandomPermutation(10);
        for (int i = 0; i < ints.length; i++) {
            binarySearchTree.put(ints[i], ints[i]*-100);
        }
        System.out.println(Arrays.toString(ints));
        System.out.println(binarySearchTree);
    }


}