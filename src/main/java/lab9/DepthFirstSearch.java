package lab9;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

interface DepthFirstSearch {
    BiPredicate<InterfaceGraphNode, InterfaceGraphNode> getShouldInStack(); //parent and child
    BiConsumer<InterfaceGraphNode, Map<String, Object>> getNodeVisitor();
    default Supplier<Map<String, Object>> getMapGenerator(){
        return HashMap::new;
    }
    default Map<String, Object> dfs(InterfaceGraphNode startingNode) {
        final var answerMap = getMapGenerator().get();
        Stack<InterfaceGraphNode> stack = new Stack<>();
        stack.push(startingNode);
        while (!stack.isEmpty()) {
            //访问当前节点
            var current = stack.pop();
            getNodeVisitor().accept(current, answerMap);//对map的中的变量进行访问与更新。
            final var aBreak = answerMap.put("break", false);
            if( Boolean.TRUE.equals(aBreak) )
                break;
            //将子节点放入队列
            Iterable<InterfaceGraphNode> relatives = current.getAdjacencyList();
            for (var relative : relatives)
                if (getShouldInStack().test(current, relative)) { //根据父子节点性质判断是否应该放到队列。
                    stack.push(relative);
                }
        }
        return answerMap;
    }
}
