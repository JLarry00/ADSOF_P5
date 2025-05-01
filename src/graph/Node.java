package src.graph;

import java.util.*;
import java.util.function.*;

public class Node<T> {
    private String name;
    private Consumer<T> action;
    private List<Node<T>> edges;
    private Node<T> previousNode;
    private StateGraph<T> stateGraph;

    public Node(String name, Consumer<T> action, StateGraph<T> stateGraph) {
        this.name = name;
        this.action = action;
        this.stateGraph = stateGraph;
        this.edges = new ArrayList<Node<T>>();
        this.previousNode = null;
    }

    public boolean run(T input, boolean debug, int i) {
        Predicate<T> condition = null;
        if (previousNode != null) {
            condition = stateGraph.getConditions().get(previousNode.getName() + "-" + name);
        }
        if (previousNode == null || condition == null || condition.test(input)) {
            action.accept(input);
            if (debug) {
                System.out.println("Step " + i + " (" + stateGraph.getName() + ") - " +  name + " executed: " + input);
            }
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

    public void addEdge(Node<T> nextNode) {
        if (edges.contains(nextNode)) {
            throw new IllegalArgumentException("Edge already exists");
        }
        edges.add(nextNode);
        nextNode.setPreviousNode(this);
    }

    public void setPreviousNode(Node<T> previousNode) {
        this.previousNode = previousNode;
    }

    public Node<T> getPreviousNode() {
        return previousNode;
    }

    @Override
    public String toString() {
        return "Node: " + name + "(" + edges.size() + " output nodes)";
    }
}