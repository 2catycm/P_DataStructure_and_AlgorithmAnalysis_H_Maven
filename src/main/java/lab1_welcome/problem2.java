package lab1_welcome;

import java.util.Scanner;
import java.util.TreeSet;

public class problem2 {
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int n = in.nextInt();
        final TreeSet<Integer> treeSet = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            treeSet.add(in.nextInt());
        }
        final int T = in.nextInt();
        for (int i = 0; i < T; i++) {
            System.out.println(treeSet.contains(in.nextInt())?"yes":"no");
        }
    }
}

//解法2：桶排，这题的书在10的五次方

