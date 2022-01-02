package lab7_tree;

import util.judge.Judge;

class Problem7Test {
    public static void main(String[] args) {
        final var ints = Judge.nextRandomUnweightedTree(200, 3);
        System.out.println(ints.length);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i][0]+" "+ints[i][1]);
        }
    }
}