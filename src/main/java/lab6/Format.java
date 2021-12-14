package lab6;

import java.util.Arrays;

final class Format {
    public static <T> String deepToString(T[][] array){
        return deepToString(array, " ");
    }
    public static <T> String deepToString(T[][] array, String elementSeparator){
        return deepToString(array, elementSeparator, "\n");
    }
    public static <T> String deepToString(T[][] array, String elementSeparator, String lineSeparator){
        StringBuilder result = new StringBuilder();
        for(var e:array) {
            int i;
            for (i = 0; i < e.length-1; i++) {
                result.append(e[i]).append(elementSeparator);
            }
            result.append(e[i]).append(lineSeparator);
        }
        return result.toString();
    }
    public static String deepToString(int[][] array){
        return deepToString(array, " ");
    }
    public static String deepToString(int[][] array, String elementSeparator){
        return deepToString(array, elementSeparator, "\n");
    }
    public static String deepToString(int[][] array, String elementSeparator, String lineSeparator){
        StringBuilder result = new StringBuilder();
        for(var e:array) {
            int i;
            for (i = 0; i < e.length-1; i++) {
                result.append(e[i]).append(elementSeparator);
            }
            result.append(e[i]).append(lineSeparator);
        }
        return result.toString();
    }
    public static int[][] transpose(int[][] array){
        int[][] result = new int[array[0].length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                result[j][i] = array[i][j];
            }
        }
        return result;
    }

    public static String arrayToString(int[] array, String elementSeparator) {
        return arrayToString(array, elementSeparator, "\n");
    }
    public static String arrayToString(int[] array, String elementSeparator, String lineSeparator) {
        StringBuilder result = new StringBuilder();
        int i;
        for (i = 0; i < array.length - 1; i++) {
            result.append(array[i]).append(elementSeparator);
        }
        result.append(array[i]).append(lineSeparator);
        return result.toString();
    }
}
