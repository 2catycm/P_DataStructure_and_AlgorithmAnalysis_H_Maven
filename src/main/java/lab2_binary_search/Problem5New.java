package lab2_binary_search;

import java.util.Scanner;

public class Problem5New {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int n = in.nextInt(), T = in.nextInt();
        Integer[] arrayA = new Integer[n];
        Integer[] arrayB = new Integer[n];
        for (int i = 0; i < n; i++) {
            arrayA[i] = in.nextInt();
        }
        for (int i = 0; i < n; i++) {
            arrayB[i] = in.nextInt();
        }
        for (int i = 0; i < T; i++) {
            int lowerBound = in.nextInt();
            int upperBound = in.nextInt();
            System.out.println(solve(arrayA, arrayB, lowerBound, upperBound));
        }
    }

    static int solve(Integer[] arrayA, Integer[] arrayB, int lowerBound, int upperBound) { //传进来的bound是错的，大1
        //中位数的性质： 左边有 upperBound-lowerBound 个， 右边多一个
        lowerBound--;upperBound--;
        int targetLeftElementCount = upperBound - lowerBound;
        //用arrayB的元素去搜索arrayA
        int left = lowerBound, right = upperBound+1, mid;//arrayB 的元素下标
//        boolean changedLeft = false;
        while (left<right) {   //比如0,1 只有一个
            mid = left + (right - left) / 2;
            final BinarySearch_for_problem5.BinarySearchResult binarySearchResult = BinarySearch_for_problem5.completeSearchingReportOf(arrayA, lowerBound, upperBound+1,arrayB[mid]);
            if (binarySearchResult.hasFoundTarget()) {// 那么我可以知道第一个是多少，第二个是多少
                //分析： 插入我这个mid之后，在arrayB看来，我左边有 mid个元素，
                //在arrayA里面，我左边可以选择，我至少有first index个元素, 至多有last index个元素。
                //注意，lowerBound前面的元素不算，所以要减掉两个lowerBound
                int lessLeftElementCount = binarySearchResult.firstIndexOfTarget() + mid -2*lowerBound;
                int mostLeftElementCount = binarySearchResult.lastIndexOfTarget() + mid  -2*lowerBound;
                if (targetLeftElementCount < lessLeftElementCount) {
                    right = mid;
//                    changedLeft = false;
                }
                else if (targetLeftElementCount > mostLeftElementCount) {
                    left = mid + 1;
//                    changedLeft = true;
                }
                else
                    return arrayB[mid];

            } else { //那么找到的是appropriate index
                //分析： 插入我这个mid之后，在arrayB里面，我左边有 mid个元素，
                //在arrayA里面，我左边有appropriate index个元素
                int leftElementCount = binarySearchResult.appropriateIndexOfTarget() + mid-2*lowerBound;
                if (leftElementCount == targetLeftElementCount)
                    return arrayB[mid];
                else if (leftElementCount > targetLeftElementCount) {
                    right = mid;
//                    changedLeft = true;
                }
                else {
                    left = mid + 1;
//                    changedLeft = false;
                }
            }
        }
//        //如果找不到，那么搜索不是徒劳的，我已经知道arrayB的相邻两数，之间必定是中位数。
//        //此刻，left==right, 可能是又缩到左，也有可能是左缩到右, 所以增加一个boolean记录
//        if (changedLeft) {
//            left--;
//        } else {
//            right++;
//        }
//        //left+1=right
//        BinarySearch.completeSearchingReportOf(arrayA, )




        //重来
        left = lowerBound;
        right = upperBound+1;//arrayA 的元素下标
        while (left < right) {   //比如0,1 只有一个
            mid = left + (right - left) / 2;
            final BinarySearch_for_problem5.BinarySearchResult binarySearchResult = BinarySearch_for_problem5.completeSearchingReportOf(arrayB, lowerBound, upperBound+1,arrayA[mid]);
            if (binarySearchResult.hasFoundTarget()) {// 那么我可以知道第一个是多少，第二个是多少
                //分析： 插入我这个mid之后，在arrayB看来，我左边有 mid个元素，
                //在arrayA里面，我左边可以选择，我至少有first index个元素, 至多有last index个元素。
                int lessLeftElementCount = binarySearchResult.firstIndexOfTarget() + mid-2*lowerBound;
                int mostLeftElementCount = binarySearchResult.lastIndexOfTarget() + mid-2*lowerBound;
                if (targetLeftElementCount < lessLeftElementCount)
                    right = mid;
                else if (targetLeftElementCount > mostLeftElementCount)
                    left = mid + 1;
                else
                    return arrayA[mid];

            } else { //那么找到的是appropriate index
                //分析： 插入我这个mid之后，在arrayB里面，我左边有 mid个元素，
                //在arrayA里面，我左边有appropriate index个元素
                int leftElementCount = binarySearchResult.appropriateIndexOfTarget() + mid-2*lowerBound;
                if (leftElementCount == targetLeftElementCount)
                    return arrayA[mid];
                else if (leftElementCount > targetLeftElementCount)
                    right = mid;
                else
                    left = mid + 1;

            }
        }
        return -1;
    }
}

/**
 * @author 叶璨铭
 */
class BinarySearch_for_problem5 {
    public static <T extends Comparable<T>> int lastIndexOf(T[] arrayA, T target) {
        return lastIndexOf(arrayA, 0, arrayA.length, target);
    }

    public static <T extends Comparable<T>> int lastIndexOf(T[] arrayA, int startIndex, T target) {
        return lastIndexOf(arrayA, startIndex, arrayA.length, target);
    }

    public static <T extends Comparable<T>> int lastIndexOf(T[] arrayA, int startIndex, int endIndex, T target) {
        int index = _binarySearch(arrayA, startIndex, endIndex, target);
        if (index == -1) return -1;        //至少有一个可能
        int previousIndex = index;
        while ((index = _binarySearch(arrayA, index + 1, endIndex, target)) != -1) {
            previousIndex = index;
        }
        return previousIndex;
    }

    public static <T extends Comparable<T>> int firstIndexOf(T[] arrayA, T target) {
        return firstIndexOf(arrayA, 0, arrayA.length, target);
    }

    public static <T extends Comparable<T>> int firstIndexOf(T[] arrayA, int startIndex, T target) {
        return firstIndexOf(arrayA, startIndex, arrayA.length, target);
    }

    public static <T extends Comparable<T>> int firstIndexOf(T[] arrayA, int startIndex, int endIndex, T target) {
        int index = _binarySearch(arrayA, startIndex, endIndex, target);
        if (index == -1) return -1;        //至少有一个可能
        int previousIndex = index;
        while ((index = _binarySearch(arrayA, startIndex, index, target)) != -1) {
            previousIndex = index;
        }
        return previousIndex;
    }

    public static <T extends Comparable<T>> boolean contains(T[] arrayA, T target) {
        return contains(arrayA, 0, arrayA.length, target);
    }

    public static <T extends Comparable<T>> boolean contains(T[] arrayA, int startIndex, T target) {
        return contains(arrayA, startIndex, arrayA.length, target);
    }

    public static <T extends Comparable<T>> boolean contains(T[] arrayA, int startIndex, int endIndex, T target) {
        return _binarySearch(arrayA, startIndex, endIndex, target) != -1;
    }

    private static <T extends Comparable<T>> int _binarySearch(T[] arrayA, T target) {
        return _binarySearch(arrayA, 0, arrayA.length, target);
    }

    private static <T extends Comparable<T>> int _binarySearch(T[] arrayA, int startIndex, T target) {
        return _binarySearch(arrayA, startIndex, arrayA.length, target);
    }

    private static <T extends Comparable<T>> int _binarySearch(T[] arrayA, int startIndex, int endIndex, T target) {
        int left = startIndex, right = endIndex, mid;//不取右边
        while (left < right) {   //比如0,1 只有一个
            mid = left + (right - left) / 2;
            final int comparison = arrayA[mid].compareTo(target);
            if (comparison == 0)
                return mid;
            else if (comparison < 0)
                left = mid + 1;    //比如，变成1
            else
                right = mid;  //注意， right不取
        }
        return -1;
    }

    /**
     * 上面的情况都是假定查找目标是存在的，而且可能存在多个，我们需要尽可能找到查找目标的范围。
     * 而下面的情况则是假定目标本来就不存在， 需要查找到能插入的地方，这样的地方必定存在。
     *
     * @param arrayA     arrayA is asserted that it does not contain target number.
     * @param startIndex
     * @param endIndex
     * @param target
     * @param <T>
     * @return the appropriate index of the array, for example, search {1,2,2,2,4,4,6,9,100} for target 3, we will not return -1, but
     * return index 4 instead                                     order 0 1 2 3 4 5 6 7 8
     * since index 4 is the index that make arrayList.add(4, 3) appropriate
     * index element
     */
    public static <T extends Comparable<T>> int appropriateIndexOf(T[] arrayA, int startIndex, int endIndex, T target) {
        int left = startIndex, right = endIndex, mid;//不取右边
        while (left < right) {   //比如0,1 只有一个
            mid = left + (right - left) / 2;
            final int comparison = arrayA[mid].compareTo(target);
            if (comparison == 0)
                //存疑，应该返回-1还是mid
//                return mid;
                return -1; //错误地发现了，方法返回认为调用不合适。问题是，你怎么知道存不存在，还有，你
            else if (comparison < 0)
                left = mid + 1;    //比如，变成1
            else
                right = mid;  //注意， right不取
        }
        return left;
    }

    public static <T extends Comparable<T>> int appropriateIndexOf(T[] arrayA, T target) {
        return appropriateIndexOf(arrayA, 0, arrayA.length, target);
    }

    public static <T extends Comparable<T>> int appropriateIndexOf(T[] arrayA, int startIndex, T target) {
        return appropriateIndexOf(arrayA, startIndex, arrayA.length, target);
    }

    /**
     * 本类的精华方法，设计本类的目的所在。
     *
     * @param arrayA
     * @param startIndex
     * @param endIndex
     * @param target
     * @param <T>
     * @return BinarySearchResult: 如果hasFoundTarget是false，那么 firstIndex和lastIndex都是appropriateIndex
     */
    public static <T extends Comparable<T>> BinarySearchResult completeSearchingReportOf(T[] arrayA, int startIndex, int endIndex, T target) {
        int left = startIndex, right = endIndex, mid;//不取右边
        while (left < right) {   //比如0,1 只有一个
            mid = left + (right - left) / 2;
            final int comparison = arrayA[mid].compareTo(target);
            if (comparison == 0) {//关键部分
                //确定了存在性, mid 是存在的， 但是不排除左边left的左边有没有
                return new BinarySearchResult(true,
                        firstIndexOf(arrayA, startIndex, mid, target),
//                        lastIndexOf(arrayA, mid + 1, endIndex, target)//mid的后面也有可能一个都没有啊，所以mid开始而不是mid+1开始
                        lastIndexOf(arrayA, mid, endIndex, target)
                );
            } else if (comparison < 0)
                left = mid + 1;    //比如，变成1
            else
                right = mid;  //注意， right不取
        }
        return new BinarySearchResult(false, left, left);
    }

    public static <T extends Comparable<T>> BinarySearchResult completeSearchingReportOf(T[] arrayA, int startIndex, T target) {
        return completeSearchingReportOf(arrayA, startIndex, arrayA.length, target);
    }

    public static <T extends Comparable<T>> BinarySearchResult completeSearchingReportOf(T[] arrayA, T target) {
        return completeSearchingReportOf(arrayA, 0, arrayA.length, target);
    }

    public static class BinarySearchResult {
        private final boolean hasFoundTarget;
        private final int firstIndexOfTarget;
        private final int lastIndexOfTarget;
        public BinarySearchResult(boolean hasFoundTarget, int firstIndexOfTarget, int lastIndexOfTarget) {
            this.hasFoundTarget = hasFoundTarget;
            this.firstIndexOfTarget = firstIndexOfTarget;
            this.lastIndexOfTarget = lastIndexOfTarget;
        }
        public boolean hasFoundTarget() {
            return hasFoundTarget;
        }
        public int firstIndexOfTarget() {
            return firstIndexOfTarget;
        }
        public int lastIndexOfTarget() {
            return lastIndexOfTarget;
        }
        public int appropriateIndexOfTarget() {
            return firstIndexOfTarget;
        }
        @Override
        public String toString() {
            return "BinarySearchResult{" +
                    "hasFoundTarget=" + hasFoundTarget +
                    ", firstIndexOfTarget=" + firstIndexOfTarget +
                    ", lastIndexOfTarget=" + lastIndexOfTarget +
                    '}';
        }
    }


    public static void main(String[] args) {
//            assert Integer.valueOf(1)==null;//assert默认情况下不会打开
        Integer[] test = {1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 5, 6, 9};
        //                0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19
//        System.out.println(BinarySearch.firstIndexOf(test, 0, test.length, 3));//correct!
//        System.out.println(BinarySearch.firstIndexOf(test, 3));//correct!
////        System.out.println(BinarySearch.lastIndexOf(test, 0, test.length, 3));//correct!
//        System.out.println(BinarySearch.lastIndexOf(test, 3));//correct!
//        System.out.println(BinarySearch._binarySearch(test, 0, test.length, 3));

//        System.out.println(BinarySearch.appropriateIndexOf(test, 4));
//        System.out.println(BinarySearch.appropriateIndexOf(test, 0));//准确
//        System.out.println(BinarySearch.appropriateIndexOf(test, 10));//准确
//        System.out.println(BinarySearch.appropriateIndexOf(test, 3));//-1

        System.out.println(BinarySearch_for_problem5.completeSearchingReportOf(test, 4));
        System.out.println(BinarySearch_for_problem5.completeSearchingReportOf(test, 0));//准确
        System.out.println(BinarySearch_for_problem5.completeSearchingReportOf(test, 10));//准确
        System.out.println(BinarySearch_for_problem5.completeSearchingReportOf(test, 3));//报告完美
    }
}
