package lab7;

import util.judge.Judge;

import static org.junit.jupiter.api.Assertions.*;

class Problem7Test {
    public static void main(String[] args) {
        final var ints = Judge.nextRandomUnweightedTree(200, 3);
        System.out.println(ints.length);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i][0]+" "+ints[i][1]);
        }
    }
}