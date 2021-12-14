package util.algorithm;

/**
 * @author 叶璨铭
 */
public class BinarySearch {
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
                        lastIndexOf(arrayA, mid + 1, endIndex, target)
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

        System.out.println(BinarySearch.completeSearchingReportOf(test, 4));
        System.out.println(BinarySearch.completeSearchingReportOf(test, 0));//准确
        System.out.println(BinarySearch.completeSearchingReportOf(test, 10));//准确
        System.out.println(BinarySearch.completeSearchingReportOf(test, 3));//报告完美

//        Arrays.binarySearch()
    }
}
