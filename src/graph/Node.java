package src.graph;

import java.util.*;
import java.util.function.*;

public class Node<T> {
    private String name;
    private Consumer<T> action;
    private List<Node<T>> edges;
    private StateGraph<T> stateGraph;

    public Node(String name, Consumer<T> action, StateGraph<T> stateGraph) {
        this.name = name;
        this.action = action;
        this.stateGraph = stateGraph;
        this.edges = new ArrayList<Node<T>>();
    }

    public boolean run(T input, boolean debug, int i) {
        action.accept(input);
        if (debug) {
            System.out.println("Step " + i + " (" + stateGraph.getName() + ") - " +  name + " executed: " + input);
        }
        if (edges.isEmpty()) {
            return false;
        }
        for (Node<T> edge : edges)
            if (!edge.run(input, debug, i + 1))
                return false;
        return true;
    }

    public String getName() {
        return name;
    }

    public List<Node<T>> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public void addEdge(Node<T> edge) {
        if (edges.contains(edge)) {
            throw new IllegalArgumentException("Edge already exists");
        }
        edges.add(edge);
    }

    @Override
    public String toString() {
        return "Node: " + name + "(" + edges.size() + " output nodes)";
    }
}