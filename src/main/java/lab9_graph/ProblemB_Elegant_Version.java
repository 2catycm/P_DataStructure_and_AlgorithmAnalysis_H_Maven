package lab9_graph;

import java.awt.*;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.*;

//# pragma OJ Main
public class ProblemB_Elegant_Version {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        int T = in.nextInt();
        for (int i = 0; i < T; i++) {
            out.println(solve(new CheckerBoardPoint(in.next()), new CheckerBoardPoint(in.next())));
        }
    }

    private static int solve(CheckerBoardPoint startPoint, CheckerBoardPoint endPoint) {
        final var solving = "Minimum number of steps needed to reach the end";
        return (int) new BreadthFirstSearch(){
            private BooleanMat isVisited = new BooleanMat(8+1, 8+1);
//            private Queue<Integer> depthQueue = new ArrayDeque<>();
//            {
//                depthQueue.offer(0);
//            }
//            private Integer depth;//                        depthQueue.offer(depth);
            @Override
            public BiPredicate<InterfaceGraphNode, InterfaceGraphNode> getShouldInQueue() {
//                depth = depthQueue.poll();
                return (parent, child) -> {
                    final var parentPoint = parent instanceof CheckerBoardPoint ? ((CheckerBoardPoint) parent) : null;
                    final var childPoint = child instanceof CheckerBoardPoint ? ((CheckerBoardPoint) child) : null;
                    if (Objects.isNull(parentPoint) || Objects.isNull(childPoint))
                        throw new UnsupportedOperationException();
                    final var should = childPoint.inBound() && !isVisited.get(childPoint);
                    if (should) {
                        isVisited.set(childPoint);
                        childPoint.setDepth(parentPoint.depth+1);
                    }
                    return should;
                };
            }

            @Override
            public BiConsumer<InterfaceGraphNode, Map<String, Object>> getNodeVisitor() {
                return (graphNode, answerMap) -> {
                    if(graphNode.equals(endPoint)) {
                        answerMap.put(solving, graphNode instanceof CheckerBoardPoint ? ((CheckerBoardPoint) graphNode).depth : null);
                        answerMap.put("break", true);
                    }
                };
            }

            @Override
            public Supplier<Map<String, Object>> getMapGenerator() {
                return TreeMap::new;
            }
        }.bfs(startPoint).get(solving);
    }

    private static class CheckerBoardPoint extends Point implements InterfaceGraphNode {
        private int depth = 0;

        public int getDepth() {
            return depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public CheckerBoardPoint(String ojInput) {
            super(ojInput.charAt(0) - 'a' + 1, ojInput.charAt(1) - '0');
        }

        public boolean inBound() {
            return 1 <= x && x <= 8 && 1 <= y && y <= 8;
        }

        public CheckerBoardPoint add(CheckerBoardPoint other) {
            return new CheckerBoardPoint(this.x + other.x, this.y + other.y);
        }

        public Iterable<InterfaceGraphNode> getAdjacencyList() {
            return () -> new Iterator<>() {
                private int i = 0;

                @Override
                public boolean hasNext() {
                    return i < 8;
                }

                @Override
                public CheckerBoardPoint next() {
                    return CheckerBoardPoint.this.add(step[i++]);
                }
            };
        }

        private static int[] step0 = {-2, -1, 2, 1, 2, 1, -2, -1};
        private static int[] step1 = {-1, -2, 1, 2, -1, -2, 1, 2};
        private static CheckerBoardPoint[] step = new CheckerBoardPoint[8];

        static {
            for (int i = 0; i < step.length; i++) {
                step[i] = new CheckerBoardPoint(step0[i], step1[i]);
            }
        }

        private CheckerBoardPoint(int x, int y) {
            super(x, y);
        }
    }


}
//# include "OnlineJudgeIO.java"
//# include "BreadthFirstSearch.java"
//# include "BooleanMat.java"