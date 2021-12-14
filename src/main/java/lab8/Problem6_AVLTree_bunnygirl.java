package lab8;

import java.util.Objects;

//# pragma OJ Main
public class Problem6_AVLTree_bunnygirl {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
//        out.println(solveWithLibrary());
        out.println(solve());
    }

    static long solve() {
        long earnSum = 0;
        int M = in.nextInt();
        final OrderedSymbolTable<Long, Void> girls = new AVLSearchTree<>();
        final OrderedSymbolTable<Long, Void> bunnies = new AVLSearchTree<>();
//        final OrderedSymbolTable<Integer, Void> girls = new ExtendedTreeMap<>();
//        final OrderedSymbolTable<Integer, Void> bunnies = new ExtendedTreeMap<>();
        for (int i = 0; i < M; i++) {
            if (in.nextInt()==1){//girl
                earnSum = getEarnSum(earnSum, bunnies, girls);
            }else{//bunny
                earnSum = getEarnSum(earnSum, girls, bunnies);
            }
        }
        return earnSum;
    }

     static long getEarnSum(long earnSum, OrderedSymbolTable<Long, Void> girls, OrderedSymbolTable<Long, Void> bunnies) {
         final var bunnyProperty = in.nextInt(); //上一个是0，这一个是5
         if (!girls.isEmpty()){ //有很多girls
             final var floorKey = girls.getPredecessorKey((long) bunnyProperty);//最接近的是4
             final var ceilingKey = girls.getSuccessorKey((long) bunnyProperty);//最接近的是6
             if (ceilingKey==null){//用floorKey
                 assert(Objects.nonNull(floorKey));
                 girls.remove(floorKey);
                 earnSum += Math.abs(floorKey - bunnyProperty);
                 return earnSum;
             }else if (floorKey==null){
                 girls.remove(ceilingKey);
                 earnSum += Math.abs(ceilingKey - bunnyProperty);
                 return earnSum;
             } else {
                 final var floorProfit = Math.abs(floorKey - bunnyProperty);
                 final var ceilingProfit = Math.abs(ceilingKey - bunnyProperty);
//                 if (floorProfit>=ceilingProfit){
                 if (floorProfit<=ceilingProfit){//理解反了
                     girls.remove(floorKey);
                     earnSum += Math.abs(floorKey - bunnyProperty);
                     return earnSum;
                 }else{
                     girls.remove(ceilingKey);
                     earnSum += Math.abs(ceilingKey - bunnyProperty);
                     return earnSum;
                 }
             }
         }else {//没有女孩的情况下，兔子才不会被秒杀。
             bunnies.put((long) bunnyProperty, null);
             return earnSum; //原样返回
         }
     }
//     static long getEarnSum(long earnSum, OrderedSymbolTable<Integer, Void> girls, OrderedSymbolTable<Integer, Void> bunnies) {
//        final var bunnyProperty = in.nextInt();
//        if (!girls.isEmpty()){
//            final var floorKey = girls.getPredecessorKey(bunnyProperty);
//            final var ceilingKey = girls.getSuccessorKey(bunnyProperty);
//            if ((ceilingKey==null) || floorKey!=null && Math.abs(floorKey-bunnyProperty)>=Math.abs(ceilingKey-bunnyProperty)) {//如果相等，应该走 floor key，因为 floor key更小。
//                earnSum += Math.abs(floorKey - bunnyProperty);
//                girls.remove(floorKey);
//            }else{
//                earnSum += Math.abs(ceilingKey - bunnyProperty);
//                girls.remove(ceilingKey);
//            }
//        }else //没有女孩的情况下，兔子才不会被秒杀。
//            bunnies.put(bunnyProperty, null);
//        return earnSum;
//    }
//    static class ExtendedTreeMap<Key extends Comparable<Key>, Value> extends TreeMap<Key,Value> implements OrderedSymbolTable<Key, Value>{
//        @Override
//        public Value get(Key key) {
//            return super.get(key);
//        }
//
//        @Override
//        public Value remove(Key key) {
//            return super.remove(key);
//        }
//
//        @Override
//        public Key getPredecessorKey(Key key) {
//            return super.floorKey(key);
//        }
//
//        @Override
//        public Key getSuccessorKey(Key key) {
//            return super.ceilingKey(key);
//        }
//
//        @Override
//        public Key minKey() {
//            return null;
//        }
//    }
}
//# include "OnlineJudgeIO.java"
//# include "符号表头文件.java"