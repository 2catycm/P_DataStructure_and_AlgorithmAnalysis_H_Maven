package lab9_graph;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

interface BreadthFirstSearch {
    BiPredicate<InterfaceGraphNode, InterfaceGraphNode> getShouldInQueue(); //parent and child
    BiConsumer<InterfaceGraphNode, Map<String, Object>> getNodeVisitor();
    Supplier<Map<String, Object>> getMapGenerator();
    default Map<String, Object> bfs(InterfaceGraphNode startingNode) {
        final var answerMap = getMapGenerator().get();
        Queue<InterfaceGraphNode> queue = new LinkedList<>();
        queue.offer(startingNode);
        while (!queue.isEmpty()) {
            //访问当前节点
            var current = queue.poll();
            getNodeVisitor().accept(current, answerMap);//对map的中的变量进行访问与更新。
            final var aBreak = answerMap.put("break", false);
            if( Boolean.TRUE.equals(aBreak) )
                break;
            //将子节点放入队列
            Iterable<InterfaceGraphNode> relatives = current.getAdjacencyList();
            for (var relative : relatives)
                if (getShouldInQueue().test(current, relative)) { //根据父子节点性质判断是否应该放到队列。
                    queue.offer(relative);
                }
        }
        return answerMap;
    }
}
