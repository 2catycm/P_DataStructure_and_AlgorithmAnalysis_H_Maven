package lab4_linkedlist;
//#include "OJReader.java"
//#include "双向链表打包.java"
public class Problem2 {
    private static OJReader in = new OJReader();
    public static void main(String[] args) {
        int n = in.nextInt();//student count
        int m = in.nextInt();//advance step
        System.out.println(solve(n, m));
    }
    private static StringBuilder solve(int n, int m) {
        final BidirectionalLinkedNodes<Integer> students = new BidirectionalLinkedNodes<>();
        for (int i = 0; i < n; i++) {
            students.pushAfter(i+1);
        }
        final StringBuilder result = new StringBuilder();
        BidirectionalLinkedNodes.BidirectionalNode<Integer> it;
        while (!students.isEmpty()){
            for (it = students.getFirst();it!=null&&it.value!=null;it = it.advanceNoChecking(m))
                result.append(it.selfRemove(students)).append(" ");
        }
        return result;
    }
}
