package lab8_advanced_tree;

import util.judge.Judge;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

class Problem6Test {

    static class Input{
        boolean isBunny;
        long property;
        static boolean GIRL = false;
        static boolean BUNNY = true;

        public Input(boolean isBunny, long property) {
            this.isBunny = isBunny;
            this.property = property;
        }

        @Override
        public String toString() {
            return "Input{" +
                    "isBunny=" + isBunny +
                    ", property=" + property +
                    '}';
        }
    }
    static Input[] nextRandomInput(){
        Input[] inputs = new Input[size];
        final var girlProperty = Judge.nextRandomPermutation(range);
        final var bunnyProperty = Judge.nextRandomPermutation(range);
        for (int i = 0; i < inputs.length; i++) {
            final var isBunny = Judge.random.nextBoolean();
            inputs[i] = new Input(isBunny, isBunny?bunnyProperty[i]:girlProperty[i]);
        }
        return inputs;
    }
    private static boolean correctCasesHint = true;
    private static int option = 4;
    /////////////////////////////0   1         2   3   4
    private static int[] sizes = {1, 1000000, 20, 888, 3};
    private static int[] ranges = {1, 1000000, 10000, 8888, 5}; //bigger than size
    private static int size = sizes[option];
    private static int range = ranges[option];
    private static int casePassed = 0;
    //    @Test
    public static void main(String[] args) {
        while (true){
//            final var inputs = nextRandomInput();
            final var inputs = new Input[]{new Input(false, 4), new Input(false, 1), new Input(true, 2)};
            long solveExpected = 0, solveActually = 0;
            try {
                solveExpected = referenceSolveZhangliyu(inputs);
//                solveExpected = referenceSolve(inputs);
//                solveExpected = standardSolve(inputs);
//                solveActually = standardSolve(inputs);
                solveActually = mySolve(inputs);
            }catch (Exception e){
                System.err.printf("expected:%d, \nactually:%s, \nat inputs:%s\n\n", solveExpected,e.getMessage(), Arrays.toString(inputs));
                continue;
            }
            if (solveExpected!=solveActually) {
                System.err.printf("expected:%d, \nactually:%d, \nnat inputs:%s\n\n", solveExpected, solveActually, Arrays.toString(inputs));
            }else if (correctCasesHint){
                assert (solveActually>=0);
                System.out.print(++casePassed+" cases has passed.\r");
            }
        }
    }
    //delegate using classmates' code
    static long referenceSolveZhangliyu(Input[] input) throws IOException {
        final var processBuilder = new ProcessBuilder("D:\\EnglishStandardPath\\Practice_File\\P_DataStructure_and_AlgorithmAnalysis_H_Maven\\src\\main\\java\\lab8\\ref\\avltree_lab06.exe");
        final var process = processBuilder.start();
        final var inputFromProcess = new Scanner(process.getInputStream());
        final var outputToProcess = new PrintWriter(process.getOutputStream());
        outputToProcess.println(input.length);
//        System.out.println(input.length);
        for (int i = 0; i < input.length; i++) {
            outputToProcess.println((input[i].isBunny?0:1)+" "+input[i].property);
//            System.out.println(input[i].isBunny?0:1+" "+input[i].property);
        }
        outputToProcess.flush();
        return inputFromProcess.nextLong();
    }
    static long referenceSolve(Input[] input){
        final TreeMap<Long, Void> tree = new ExtendedTreeMap<>();
        boolean flag = false;
        long earnSum = 0;
        int M = input.length;
        for (int i = 0; i < M; i++) {
            final var property = input[i].property;
            if (tree.isEmpty()){
                flag = input[i].isBunny;
                tree.put((long) property, null);
            }else{
                if (input[i].isBunny!=flag){
                    Long floorKey = tree.floorKey((long) property);
                    Long ceilingKey = tree.ceilingKey((long) property);
                    Long v;
                    if((ceilingKey==null)||(floorKey!=null && Math.abs(floorKey-property)>=Math.abs(ceilingKey-property))){
                        v = floorKey;
                    }else{
                        v = ceilingKey;
                    }
                    tree.remove(v);
                    long r = v-property;
                    if (r>=0) earnSum+=r;
                    else earnSum+=-r;

                }else{
                    tree.put((long) property, null);
                }
            }
        }
        return earnSum;
    }
    static long standardSolve(Input[] input){
        long earnSum = 0;
        final OrderedSymbolTable<Integer, Void> girls = new ExtendedTreeMap<>();
        final OrderedSymbolTable<Integer, Void> bunnies = new ExtendedTreeMap<>();
        for (int i = 0; i < input.length; i++) {
            if (input[i].isBunny==Input.GIRL){//girl
                earnSum = getEarnSum((int) input[i].property, earnSum, bunnies, girls);
            }else{//bunny
                earnSum = getEarnSum((int) input[i].property, earnSum, girls, bunnies);
            }
        }
        return earnSum;
    }
    static long mySolve(Input[] input){
        long earnSum = 0;
        final OrderedSymbolTable<Integer, Void> girls = new AVLSearchTree<>();
        final OrderedSymbolTable<Integer, Void> bunnies = new AVLSearchTree<>();
        for (int i = 0; i < input.length; i++) {
            if (input[i].isBunny==Input.GIRL){//girl
                earnSum = getEarnSum((int) input[i].property, earnSum, bunnies, girls);
            }else{//bunny
                earnSum = getEarnSum((int) input[i].property, earnSum, girls, bunnies);
            }
        }
        return earnSum;
    }
    static long getEarnSum(int bunnyProperty , long earnSum, OrderedSymbolTable<Integer, Void> girls, OrderedSymbolTable<Integer, Void> bunnies) {
        if (!girls.isEmpty()){
            final var floorKey = girls.getPredecessorKey(bunnyProperty);
            final var ceilingKey = girls.getSuccessorKey(bunnyProperty);
//            if ((ceilingKey==null) || floorKey!=null && Math.abs(floorKey-bunnyProperty)>=Math.abs(ceilingKey-bunnyProperty)) {//如果相等，应该走 floor key，因为 floor key更小。
            if ((ceilingKey==null) || floorKey!=null && Math.abs(floorKey-bunnyProperty)<=Math.abs(ceilingKey-bunnyProperty)) {//debug 小于等于才是有利可图。
                earnSum += Math.abs(floorKey - bunnyProperty);
                girls.remove(floorKey);
            }else{
                earnSum += Math.abs(ceilingKey - bunnyProperty);
                girls.remove(ceilingKey);
            }
        }else
            bunnies.put(bunnyProperty, null);
        return earnSum;
    }


    static class ExtendedTreeMap<Key extends Comparable<Key>, Value> extends TreeMap<Key,Value> implements OrderedSymbolTable<Key, Value>{
        @Override
        public Value get(Key key) {
            return super.get(key);
        }

        @Override
        public Value remove(Key key) {
            return super.remove(key);
        }

        @Override
        public Key getPredecessorKey(Key key) {
            return super.floorKey(key);
        }

        @Override
        public Key getSuccessorKey(Key key) {
            return super.ceilingKey(key);
        }

        @Override
        public Key minKey() {
            return super.firstKey();
        }

        @Override
        public Key maxKey() {
            return super.lastKey();
        }

        @Override
        public boolean isEmpty() {
            return super.isEmpty();
        }
    }
}