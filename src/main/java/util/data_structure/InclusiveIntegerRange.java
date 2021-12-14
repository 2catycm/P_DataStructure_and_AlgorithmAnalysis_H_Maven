package util.data_structure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * 1.不考虑实数，这是整数的范围.
 * 2.提供交并补运算，支持多段的复杂范围。
 * 3.用Integer.MAX_VALUE和Integer.MIN_VALUE来表示正负无穷的概念
 * 4.由于是整数，所以没必要有()开区间的概念，一切用闭区间表示
 * 5.为lab4 F题特殊准备的是集合的 对称差分 运算
 * 6.空集的概念：ranges没有范围
 */
public class InclusiveIntegerRange /*implements Cloneable*/{
    public static InclusiveIntegerRange emptySet(){
        return new InclusiveIntegerRange(new TreeSet<>());
    }
    public static InclusiveIntegerRange universalSet(){
        return new InclusiveIntegerRange(new SingleInclusiveRange(Integer.MIN_VALUE, Integer.MAX_VALUE));
    }
    public InclusiveIntegerRange(int lowerBound, int upperBound) {
        ranges = new TreeSet<>();
        ranges.add(new SingleInclusiveRange(lowerBound, upperBound));
    }
    private InclusiveIntegerRange(SingleInclusiveRange range) {
        ranges = new TreeSet<>();
        if (!range.isEmptySet())
            ranges.add(range);
    }

    private TreeSet<SingleInclusiveRange> ranges;//不变量：n个ranges之间没有交集

    private InclusiveIntegerRange(TreeSet<SingleInclusiveRange> ranges) {
        this.ranges = ranges;
    }
    public boolean includes(int testedNumber){
        return ranges.parallelStream().anyMatch(e->e.includes(testedNumber));
    }
    public boolean isEmptySet(){
        return ranges.isEmpty();
    }
    public boolean isUniversalSet(){
        return ranges.size()==1&&ranges.toArray()[0].equals(new SingleInclusiveRange(Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    public void intersect(InclusiveIntegerRange other){
        if (isEmptySet()||other.isEmptySet()) {
            this.ranges.clear();
            return;
        }
        if (isUniversalSet()) {
            this.ranges = other.ranges;  //面向oj的假设，我们只有一个可变对象，所以不克隆了，浪费时间
            return;
        }
        if (other.isUniversalSet())
            return;
//        var it = ranges.iterator();
//        var previous = it.next();
//        while (it.hasNext()){
//            var current = it.next();
//
//        }
        ranges.addAll(other.ranges); //暴力做法
    }
    public void union(InclusiveIntegerRange other){

    }
//    public void complement(){
//        if (isEmptySet())
//    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
//        int i;
//        for (i = 0; i < ranges.size()-1; i++) {
//            stringBuilder.append(ranges.get(i)).append("∪");
//        }
//        stringBuilder.append(ranges.get(i));
        final int size = ranges.size(); int i = 0;
        for (var e:ranges){
            stringBuilder.append(e);
            if (++i<size)
                stringBuilder.append("∪");
        }
        return stringBuilder.toString();
    }

    //空集封装为单例?
    public static class SingleInclusiveRange implements Comparable<SingleInclusiveRange>{//这个class当中的都还是O(1)操作
//        public static SingleInclusiveRange emptySet = new SingleInclusiveRange(0,-1);
//        public static SingleInclusiveRange universalSet = new SingleInclusiveRange(Integer.MIN_VALUE,Integer.MAX_VALUE);
        private final int lowerBound;
        private final int upperBound;

        public int getLowerBound() {
            return lowerBound;
        }

        public int getUpperBound() {
            return upperBound;
        }

        public SingleInclusiveRange(int lowerBound, int upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
//            if (this.isEmptySet())
//                this.lowerBound = 1;
        }
        public boolean includes(int testedNumber){
            return lowerBound<=testedNumber&&testedNumber<=upperBound;
        }
        public boolean isEmptySet(){
            return lowerBound>upperBound;
        }
        public InclusiveIntegerRange intersect(SingleInclusiveRange other){
            return new InclusiveIntegerRange(new SingleInclusiveRange(
                    Math.max(this.lowerBound, other.lowerBound),
                    Math.min(this.upperBound, other.upperBound)));
        }
        public SingleInclusiveRange _intersect(SingleInclusiveRange other){
            return new SingleInclusiveRange(
                    Math.max(this.lowerBound, other.lowerBound),
                    Math.min(this.upperBound, other.upperBound));
        }
        public InclusiveIntegerRange union(SingleInclusiveRange other){
            final TreeSet<SingleInclusiveRange> ranges = new TreeSet<>();
            if (this._intersect(other).isEmptySet()){
                ranges.add(this);ranges.add(other);
            }else {
                ranges.add(new SingleInclusiveRange(
                        Math.min(this.lowerBound, other.lowerBound),
                        Math.max(this.upperBound, other.upperBound)));
            }
//            ranges.add(new SingleInclusiveRange(Math.min(this.lowerBound, other.lowerBound), Math.max(this.lowerBound, other.lowerBound)));
//            ranges.add(new SingleInclusiveRange(Math.min(this.upperBound, other.upperBound), Math.max(this.upperBound, other.upperBound)));
            return new InclusiveIntegerRange(ranges);
        }
        public InclusiveIntegerRange complement(){
            final TreeSet<SingleInclusiveRange> ranges = new TreeSet<>();
            if (lowerBound!=Integer.MIN_VALUE)
                ranges.add(new SingleInclusiveRange(Integer.MIN_VALUE, /*lowerBound==Integer.MIN_VALUE?Integer.MIN_VALUE:*/lowerBound-1));

            if (upperBound!=Integer.MAX_VALUE)
                ranges.add(new SingleInclusiveRange(/*upperBound==Integer.MAX_VALUE?Integer.MAX_VALUE:*/upperBound+1, Integer.MAX_VALUE));
            return new InclusiveIntegerRange(ranges);
        }
        @Override
        public String toString() {
            return String.format("[%d, %d]", lowerBound, upperBound);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SingleInclusiveRange that = (SingleInclusiveRange) o;

            if (lowerBound != that.lowerBound) return false;
            return upperBound == that.upperBound;
        }

        @Override
        public int hashCode() {
            int result = lowerBound;
            result = 31 * result + upperBound;
            return result;
        }

        @Override
        public int compareTo(SingleInclusiveRange o) {
            return Comparator.comparingInt(SingleInclusiveRange::getLowerBound).thenComparing(SingleInclusiveRange::getUpperBound).compare(this, o);
        }
    }

}
