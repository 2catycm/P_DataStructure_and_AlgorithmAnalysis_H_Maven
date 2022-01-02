package lab4_linkedlist;

import java.util.ArrayList;
import java.util.stream.IntStream;

interface SolvableList {
    void insert(char chIn, int position);
    char find(int position);
    void transform(int leftInclusive, int rightInclusive);
}
public class Problem6 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final String string = in.next();
        final int T = in.nextInt();
//        SolvableList solutionList = new BruteForceSolutionList(string);
        SolvableList solutionList = new SolutionList(string);
        for (int i = 0; i < T; i++) {
            switch (in.nextInt()){
                case 1:
                    solutionList.insert(in.next().charAt(0), in.nextInt());
                    break;
                case 2:
                    out.println(solutionList.find(in.nextInt()));
                    break;
                case 3:
                    solutionList.transform(in.nextInt(), in.nextInt());
                    break;
            }
        }
    }
}
//第一个问题，是数组套数组，还是数组套链表，还是链表套数组，还是链表套链表？
//我们的目的是查询、插入、翻转都快
//查询快在于有跳过，插入快在于能插入。
//所以，数组的链表，和链表的链表是可以选择的。
class SolutionList implements SolvableList {
    private BidirectionalLinkedNodes<TickedArrayList<TickedCharacter>> nodes = new BidirectionalLinkedNodes<>();
    private int size = 0;
    private int sqrtN = 0;
    public void updateSqrtN(){
        sqrtN = (int) Math.sqrt(size);
    }

    public SolutionList(String string) {
        size = string.length();updateSqrtN();
        int i = 0;//对string的指针
        //前面sqrtN**2个 //根号15*根号15 = 9, 所以剩下的也不少
        for (int j = 0; j < sqrtN; j++) {
            var block = new TickedArrayList<TickedCharacter>(new ArrayList<>(sqrtN), false);
            for (int k = 0; k < sqrtN; k++) {
                block.getList().add(new TickedCharacter(string.charAt(i++), false));
            }
            nodes.pushAfter(block);
        }
        //剩下的, i此时等于sqrt**2
        var block = new TickedArrayList<TickedCharacter>(new ArrayList<>(size-sqrtN*sqrtN), false); //最好一次申请到位。sqrtN可能多了，也可能少了
        for (int j = i; j < size; j++) {
            block.getList().add(new TickedCharacter(string.charAt(j), false));
        }
        nodes.pushAfter(block);
    }
    @Override
    public void insert(char chIn, int position) {
        final Position foundPosition = findPosition(position);
        final var block = foundPosition.blockNode.value;
        final int blockSize = block.getList().size();
        int innerPosition = foundPosition.innerPosition;

        block.getList().add(innerPosition, new TickedCharacter(chIn, block.isTicked())); //插入之后要变成1   //插入的时候没有被tick
        this.size++;updateSqrtN();
        if (blockSize >=2*sqrtN-1){
//            final List<TickedCharacter> newBlockList = block.getList().subList(sqrtN, block.getList().size());
//            it.value = new TickedArrayList<TickedCharacter>(new ArrayList<TickedCharacter>(block.getList().subList(0, sqrtN)), block.isTicked());
//            block.subList(sqrtN, block.size())
            final var newBlock = new ArrayList<TickedCharacter>();
            IntStream.range(sqrtN, blockSize).forEachOrdered(i->{
                newBlock.add(block.getList().remove(sqrtN));
            });
            foundPosition.blockNode.linkAfter(nodes, new TickedArrayList<>(newBlock, block.isTicked()));//元素移动，然后块属性不变
        }
    }

    @Override
    public char find(int position) { //传入的position从1开始计数， 我们的ArrayList和Bidirectional都是从0开始
        final Position foundPosition = findPosition(position);
        final var block = foundPosition.blockNode.value;
        int innerPosition = foundPosition.innerPosition;
        final TickedCharacter tickedCharacter = block.getList().get(innerPosition);
        return (block.isTicked()^tickedCharacter.isTicked())?transform(tickedCharacter.getCharacter()):tickedCharacter.getCharacter();
    }
    public char transform(char ch){
        return (char)('a'+'z'-ch);
    }
    @Override
    public void transform(int leftInclusive, int rightInclusive) {
        //找到块位置与内部位置
        final Position leftPosition = findPosition(leftInclusive);
        final Position rightPosition = findPosition(rightInclusive);
        if (leftPosition.blockNode==rightPosition.blockNode){        //特殊执行: 同块
            var list = leftPosition.blockNode.value.getList();
            for (int i = leftPosition.innerPosition; i <= rightPosition.innerPosition; i++) {
//                final TickedCharacter characterToChange = list.get(i);
//                list.set(i, new TickedCharacter(characterToChange.getCharacter(), characterToChange.isTicked()));
                list.get(i).flip();
            }
        }
        else{
            //分成三部分考虑：左元素所在块策略，右元素所在块策略，中间块的策略
            //left
            var list = leftPosition.blockNode.value.getList();
            for (int i = leftPosition.innerPosition; i < list.size(); i++) {
                list.get(i).flip();
            }
            //right
            list = rightPosition.blockNode.value.getList();
            for (int i = 0; i <= rightPosition.innerPosition; i++) {
                list.get(i).flip();
            }
            //middle
            for (var it = leftPosition.blockNode.next; it!= rightPosition.blockNode; it = it.next)
                it.value.flip();
        }
    }
    private Position findPosition(int position){
        var it = nodes.getLeftMargin();
        while (position>0){
            it = it.next;
            position-=it.value.getList().size();
        }
        position+=it.value.getList().size()-1;
        return new Position(it, position);
    }
//    private Position findPosition(int position){
////        final int[] sumSize = {0};
////        var it = nodes.getFirst();
////        it = it.advanceWhile(i->i!=nodes.getRightMargin()&&(sumSize[0] +=i.value.getList().size())<position).previous;//=也不行
////        it = it.advanceWhile(i->i!=nodes.getRightMargin()&&(sumSize[0] +=i.value.getList().size())<position).previous;//=也不行
////        it = it.advanceWhile(i->(sumSize[0] +=i.value.getList().size())<=position);//=必须可以。 比如块1 2， 找第二个，在第一个而非第二个，情况为等于。
////        int sumSize = 0;
////        for (var it = nodes.getFirst();
////        it!=nodes.getRightMargin();it = it.next){
////
////        }
////        int diff;var it = nodes.getFirst();
////        for (;it!= nodes.getLast(); it = it.next) {
////            diff = position - sumSize[0];  //position是 按照个数来计数的
////            if (diff < 0) {
////                it = it.previous;//太多了
////                break;
////            }
////            sumSize[0]+=it.value.getList().size();
////        }
////        int innerPosition = position - (sumSize[0]- it.value.getList().size())-1;//比如说块1， 我们要找第2个  //2-0-1 = 1
////        return new Position(it, innerPosition);
//    }
    private static class Position{
        public final BidirectionalLinkedNodes.BidirectionalNode<TickedArrayList<TickedCharacter>> blockNode;
        public final int innerPosition;
        public Position(BidirectionalLinkedNodes.BidirectionalNode<TickedArrayList<TickedCharacter>> blockNode, int innerPosition) {
            this.blockNode = blockNode;
            this.innerPosition = innerPosition;
        }
    }
}
class BruteForceSolutionList implements SolvableList {
    private BidirectionalLinkedNodes<Character> nodes;
    public BruteForceSolutionList(String string) {
        nodes = new BidirectionalLinkedNodes<>();
        string.chars().forEachOrdered(c-> nodes.pushAfter((char)c));
    }

    @Override
    public void insert(char chIn, int position) {
        nodes.getLeftMargin().advance(position).linkFront(nodes, chIn);
    }

    @Override
    public char find(int position) {
        return nodes.getLeftMargin().advance(position).value;
    }

    @Override
    public void transform(int leftInclusive, int rightInclusive) {
        var it = nodes.getLeftMargin().advance(leftInclusive);
        for (int i = leftInclusive; i <= rightInclusive; i++, it = it.next) {
            it.value = (char)('a'+'z'-it.value);
        }
    }
}
class TickedArrayList<T> /*extends ArrayList<T>*/ {
    private ArrayList<T> list;
    private boolean ticked;

    public boolean isTicked() {
        return ticked;
    }

    public void flip() {
        this.ticked = !ticked;
    }
    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }

    public TickedArrayList(ArrayList<T> list, boolean ticked) {
        this.list = list;
        this.ticked = ticked;
    }

    @Override
    public String toString() {
        return "TickedArrayList{" +
                "list=" + list +
                ", ticked=" + ticked +
                '}';
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }
}
class TickedCharacter{
    private char character;
    private boolean ticked;
    public TickedCharacter(char ch, boolean ticked) {
        this.character = ch;
        this.ticked = ticked;
    }

    @Override
    public String toString() {
        return "TickedCharacter{" +
                "ch=" + character +
                ", ticked=" + ticked +
                '}';
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public boolean isTicked() {
        return ticked;
    }

    public void flip() {
        this.ticked = !ticked;
    }
    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "双向链表打包.java"