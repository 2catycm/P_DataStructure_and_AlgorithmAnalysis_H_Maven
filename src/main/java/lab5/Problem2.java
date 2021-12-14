package lab5;

import java.time.temporal.Temporal;
//问题1：
//5 5 5
//1 2 3 4 5
//1 2 3 4 5
public class Problem2 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final int n = in.nextInt();
        final int p = in.nextInt();
        final int q = in.nextInt();
//        final var line1 = new LinkedQueue<Integer>(BidirectionalLinkedNodes.readLinkedNodesFromOJ(in, p));
//        final var line2 = new LinkedQueue<Integer>(BidirectionalLinkedNodes.readLinkedNodesFromOJ(in, q));
        final var line1 = ArrayQueue.readArrayQueueFromOJ(in, p);
        final var line2 = ArrayQueue.readArrayQueueFromOJ(in, q);
//        final var teamMateSuccess = new boolean[n+1]; //队友有无成功. //初始化为false
//        int timeTot = 0;
//        for (;!line1.队空() && !line2.队空();){
//            int line1Current = 0, line2Current = 0;
//            if (!line1.队空()){
//                line1Current = line1.办理业务();
//                if (!teamMateSuccess[line1Current])
//                    timeTot++;
//            }
//            if (!line2.队空()){
//                line2Current = line2.办理业务();
//                if (!teamMateSuccess[line2Current])
//                    timeTot++;
//            }
//            teamMateSuccess[line1Current] = true;//0可以做，但是不读取
//            teamMateSuccess[line2Current] = true;
//        }
//        out.println(timeTot);
        final var teams = new int[n + 1];//每个team的等待时间  //默认是0， 0是不合法的
        for (int t = 1; !line1.队空() || !line2.队空(); t++) { //时间累计
            int element1 = 0;
            while (!line1.队空()){
                element1 = line1.办理业务();
                if (teams[element1]==0)
                    teams[element1] = t;
                else
                    continue;//这些人不存在，要到达第一个存在的人，然后时间再继续
                break;
            }
            while (!line2.队空()){
                int element2 = line2.办理业务();
                if (teams[element2]==0)
                    teams[element2] = t;
                else {
                    if (element2==element1)
                        break;
                    continue; //这个人有可能存在。
                }
                break;
            }
        }
        for (int i = 1; i <= n; i++) {
            out.print(teams[i]);
            out.print(" ");
        }
        out.println();
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//头文件。 还没有实现递归include，所以要按两次BakeAll
//#include "StackAndQueue打包.java"