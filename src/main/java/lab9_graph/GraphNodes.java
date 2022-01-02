package lab9_graph;

import java.util.LinkedList;

interface InterfaceGraphNode {
    Iterable<InterfaceGraphNode> getAdjacencyList();
}
class GraphNode implements InterfaceGraphNode {
    LinkedList<InterfaceGraphNode> adjacencyTable;
    @Override
    public Iterable<InterfaceGraphNode> getAdjacencyList() {
        return adjacencyTable;
    }
}
class ColoredGraphNode extends GraphNode{
    public enum Color{
        WHITE, YELLOW, RED;
    }

}