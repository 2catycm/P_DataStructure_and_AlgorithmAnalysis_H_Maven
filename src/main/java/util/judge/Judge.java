package util.judge;

import util.algorithm.SortingHelper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;
//Data should be composite struct of Integers
//interface IntegerConstructable{
//    IntegerConstructable(int... ints);//卧槽，不允许这样写？
//}//算了，日后再写，告辞！
public abstract class Judge<Data> {
    public abstract void judge();
    protected int size;
    protected int range;
    public Judge(int size, int range) {
        this.size = size;
        this.range = range;
    }
    private Data[] generateData(){
//        final Field[] declaredFields = Data.class.getDeclaredFields();
//        for (int i = 0; i < ; i++) {
//
//        }
        return null;//算了，日后再写，告辞！
    }
    public static Random random = new Random();
    public static int[][] nextRandomUnweightedTree(int size, int heightRandomFactor){
        final var ints = new int[size-1][2];
        for (int i = 2; i <= size; i++) {
            ints[i-2][0] = i- (random.nextInt(Math.min(i-1, heightRandomFactor))+1);
            ints[i-2][1] = i;
//            printf("%d %d\n", i - (rand() % std::min(i - 1, w) + 1), i);
        }
        return ints;
    }
    public static int[][] nextRandomWeightedTree(int weightRange, int treeSize, int heightRandomFactor){
        final var ints = new int[treeSize-1][3];
        for (int i = 2; i <= treeSize; i++) {
            ints[i-2][0] = i- (random.nextInt(Math.min(i-1, heightRandomFactor))+1);
            ints[i-2][1] = i;
            ints[i-2][2] = random.nextInt(weightRange);
        }
        return ints;
    }
    public static String nextRandomAlphabetString(int size, int range){
        range = range%26;
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append((char)(random.nextInt(range)+'a'));
        }
        return stringBuilder.toString();
    }
    public static int[] nextRandomPermutation(int size){
        final var permutation = new int[size];
        for (int i = 0; i < size; i++) {
            permutation[i] = i+1;
        }
        SortingHelper.knuthShuffle(permutation);
        return permutation;
    }
    public static int[] nextRandomIntArray(int size, int range){
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = random.nextInt(range);
        }
        return result;
    }
    public static String ArraysToString(int[] array){
        final StringBuilder result = new StringBuilder();
        Arrays.stream(array).forEachOrdered(e-> result.append(e).append(" "));
        return result.toString();
    }
    public static <T> String ArraysToString(T[] array){
        return ArraysToString(array, " ");
    }
    public static <T> String ArraysToString(T[] array, String elementSeparator){
        final StringBuilder result = new StringBuilder();
        Arrays.stream(array).forEachOrdered(e-> result.append(e).append(elementSeparator));
        return result.toString();
    }
}
