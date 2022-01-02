package lab4_linkedlist;

public class Problem5 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final int T = in.nextInt();
        for (int i = 0; i < T; i++) {
            final var nodes = BidirectionalLinkedNodes.readLinkedNodesFromOJ(in);
//            var nodesOfInterest = new BidirectionalLinkedNodes<BidirectionalLinkedNodes.BidirectionalNode<Integer>>();
//            nodes.getFirst().forThisAndEachNodesAfter(nodes, nodesOfInterest::pushAfter); //copy the nodes as an iterator list
//            while (!nodesOfInterest.isEmpty()){
//                for (var it = nodesOfInterest.getFirst();
//                     !BidirectionalLinkedNodes.BidirectionalNode.isEndOfList(it); it = it.next) {
//                    final var node = it.value;
//                    while (node.value<=node.next.value)
//                }
//            }
            newNewSolve(nodes);
//            bruteForceSolve(nodes);
            nodes.printlnToOJ(out);
//            nodes.printToOJ(out);
        }
    }
    //1
    //2
    //2 1 //运行错误1：删除之后没有退出 //运行错误2，打印空链表逻辑不统一，如果不存在first就会爆炸
    static void bruteForceSolve(BidirectionalLinkedNodes<Integer> nodes){
        for (; !nodes.isEmpty();) {
            var it = nodes.getFirst();
            it = it.advanceWhile(e -> e != nodes.getLast()/*getLast()*/ && e.value <= e.next.value); //现在it是第一个大于下一个的
            if (it == nodes.getLast())
                break;
            for (; it != nodes.getRightMargin(); ) {
                it = it.advanceWhile(e -> e != nodes.getLast()/*getLast()*/ && e.value <= e.next.value); //现在it是第一个大于下一个的
                if (it == nodes.getLast())
                    break; //不然下面会remove掉最后一个
                it = it.next;
                for (; it != nodes.getRightMargin() && it.value < it.previous.value; it = it.next) {
                    it.removeFront(nodes);
                } //现在，it在第一个比前一个大的
                it.removeFront(nodes);
            }
        }
    }
    static void newNewSolve(BidirectionalLinkedNodes<Integer> nodes){
        var nodesOfInterest = getNOI(nodes);
        //新版：从interest的每一个删，直到删干净了，然后再到下一个interest。如果下一个interest被删除过，那就跳到下一个interest
        while(!nodesOfInterest.isEmpty())
            //每次都减少减少一些兴趣点
        for (var it = nodesOfInterest.getFirst();
             it!= nodesOfInterest.getRightMargin(); ){
            var nodeIt = it.value;
            if (nodeIt.isDead() ||
                    nodeIt == nodes.getLeftMargin() || nodeIt.next==nodes.getRightMargin()||nodeIt.value <= nodeIt.next.value) {
                it = it.next;//先跑路防被杀
                it.killFront(nodesOfInterest);
                continue;
            }
            //当前这个点是有删除兴趣的。
            nodeIt = nodeIt.previous;//先逃跑防止被杀死
            var killer = nodeIt.next.next;            //每一步都要往后删干净，然后再到下一步
            for (;
                 killer!=nodes.getRightMargin()&&killer.previous.value> killer.value;killer = killer.next){
                killer.killFront(nodes);
            }
            killer.killFront(nodes);
            it.value = nodeIt; //逃跑后的nodeIt，是新的兴趣点
            it = it.next;
        }
    }
    //2 2 3 4 0 1 1 3 0 0失败原因
    //2 2 3 1 1 0时， 本来是把3 1 1 0 顺序删除
    //但是太贪心了，直接往左一走，把2 1 0一起删了
    @Deprecated
    static void newSolve(BidirectionalLinkedNodes<Integer> nodes){
        var nodesOfInterest = getNOI(nodes);
        //新版：从interest的每一个删，直到删干净了，然后再到下一个interest。如果下一个interest被删除过，那就跳到下一个interest
        for (var it = nodesOfInterest.getFirst();
             it!= nodesOfInterest.getRightMargin(); it = it.next){
            if (it.value.isDead())
                continue;
//            //当前往外扩展   错的，扩展是不对的，删不干净，比如0 2 3 1 1 2 0 0。  一次必须删掉所有的
//            for (var nodeIt = it.value;
//                 nodeIt!=nodes.getLeftMargin() && nodeIt.next!=nodes.getRightMargin() && nodeIt.next.value<nodeIt.value; ){
//                nodeIt.killAfter(nodes);
//                nodeIt = nodeIt.previous;//先移动，再杀
//                nodeIt.killAfter(nodes);
//            }
            for (var nodeIt = it.value;
                 nodeIt!=nodes.getLeftMargin() && nodeIt.next!=nodes.getRightMargin() && nodeIt.next.value<nodeIt.value; ){
                //每一步都要往后删干净，然后再到下一步
                nodeIt = nodeIt.previous;//先逃跑防止被杀死
                var killer = nodeIt.next.next;
                for (;
                     killer!=nodes.getRightMargin()&&killer.previous.value> killer.value;killer = killer.next){
                    killer.killFront(nodes);
                }
                killer.killFront(nodes);
            }
        }
    }

    private static BidirectionalLinkedNodes<BidirectionalLinkedNodes.BidirectionalNode<Integer>> getNOI(BidirectionalLinkedNodes<Integer> nodes) {
        var nodesOfInterest = new BidirectionalLinkedNodes<BidirectionalLinkedNodes.BidirectionalNode<Integer>>();
        //第一遍处理
//        for (; it!= nodes.getLast() && it.value<=it.next.value; it = it.next) ;
        for (var it = nodes.getFirst(); it != nodes.getRightMargin(); ) {
            it = it.advanceWhile(e -> e != nodes.getLast()/*getLast()*/ && e.value <= e.next.value); //现在it是第一个大于下一个的
            if (it== nodes.getLast())
                break; //不然下面会remove掉最后一个
            nodesOfInterest.pushAfter(it.previous);
            it = it.next;
            for (; it != nodes.getRightMargin() && it.value < it.previous.value; it = it.next) {
                it.killFront(nodes);
            } //现在，it在第一个比前一个大的
            it.killFront(nodes);
        }
        return nodesOfInterest;
    }

    //bug原理：这个版本的跳过的原理是记录删除组的第一个非删除组。
    //如果出现连续删除3 3 1 3 0 2 2 4      3 1后记录3， 3 0后记录3,3对2,删去2出现一个不该用的2
    //bug试图修复：如果记录删除组的首位。那么会有问题吗？
    //比如，3 0 的3记下来，3不会被垃圾回收，3的前面是1,
    //1被3记录下来了，所以也不被垃圾回收，所以能够得到1,
    //不对，删除1的时候，把3往第一个3上绑定
    //也有问题。
    //问题的实质是 通过已经死亡的指针去删除，以及相邻降序对的特殊处理是错误的。
    @Deprecated
    static void solve(BidirectionalLinkedNodes<Integer> nodes) {
        var nodesOfInterest = getNOI(nodes);
        //通用的处理步骤,每一步，通用都是
        while (!nodesOfInterest.isEmpty()) {
            for (var it = nodesOfInterest.getFirst();
                 it != nodesOfInterest.getRightMargin(); it = it.next) {
                var node = it.value;
                if (node == nodes.getLeftMargin() || node.next==nodes.getRightMargin()||node.value <= node.next.value) {
                    it.selfRemove(nodesOfInterest); //selfRemove依然可以往下走，依然可以垃圾回收
                    continue;//不是兴趣点，而且再也不是兴趣点了
                }
                node.removeAfter(nodes);
                node.selfRemove(nodes);
                it.value = it.value.previous; //转向左边那个
            }
        }
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "双向链表打包.java"
//#include "IndexedInteger.java"