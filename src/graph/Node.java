package src.graph;

import java.util.*;
import java.util.function.*;

import src.decorate.*;

public class Node<T, R> {
    private String name;
    private Consumer<T> action;
    private boolean finalNode;
    private List<Node<T, R>> childs;
    private Node<T, R> previousNode;
    private StateGraph<T> parentStateGraph;
    private InterfaceStateGraph<R> workflowGraph;
    private Function<T, R> injector;
    private BiFunction<R, T, T> extractor;

    public Node(String name, Consumer<T> action, StateGraph<T> stateGraph) {
        this.name = name;
        this.action = action;
        this.parentStateGraph = stateGraph;
        this.childs = new ArrayList<Node<T, R>>();
        this.previousNode = null;
        this.workflowGraph = null;
        this.injector = null;
        this.extractor = null;
        this.finalNode = false;
    }

    public Node(String name, StateGraph<T> stateGraph, InterfaceStateGraph<R> workflowGraph) {
        this.name = name;
        this.action = null;
        this.parentStateGraph = stateGraph;
        this.childs = new ArrayList<Node<T, R>>();
        this.previousNode = null;
        this.workflowGraph = workflowGraph;
        this.injector = null;
        this.extractor = null;
        this.finalNode = false;
    }

    public String getName() { return name; }

    public Consumer<T> getAction() { return action; }

    public List<Node<T, R>> getChilds() { return Collections.unmodifiableList(childs); }

    public void setPreviousNode(Node<T, R> previousNode) { this.previousNode = previousNode; }

    public Node<T, R> getPreviousNode() { return previousNode; }

    public StateGraph<T> getParentStateGraph() { return parentStateGraph; }

    public InterfaceStateGraph<R> getWorkflowGraph() { return workflowGraph; }

    public Function<T, R> getInjector() { return injector; }

    public BiFunction<R, T, T> getExtractor() { return extractor; }

    public boolean isFinalNode() { return finalNode; }

    public void setFinalNode(boolean finalNode) { this.finalNode = finalNode; }

    public void addEdge(Node<T, R> nextNode) {
        if (childs.contains(nextNode)) throw new IllegalArgumentException("Edge already exists");
        childs.add(nextNode);
        nextNode.setPreviousNode(this);
    }

    public Node<T, R> withInjector(Function<T, R> injector) {
        if (workflowGraph == null) throw new IllegalArgumentException("Workflow graph is null");

        this.injector = injector;
        return this;
    }

    public Node<T, R> withExtractor(BiFunction<R, T, T> extractor) {
        if (workflowGraph == null) throw new IllegalArgumentException("Workflow graph is null");

        this.extractor = extractor;
        return this;
    }

    public boolean run(T input, boolean debug, int i) {
        if (workflowGraph != null) {
            R workflowInput = injector.apply(input);
            R workflowOutput = workflowGraph.run(workflowInput, debug);
            input = extractor.apply(workflowOutput, input);
        }

        if (!isEdgeAllowed(input)) return true;
        
        if (action != null) action.accept(input);

        if (debug) System.out.println("Step " + i + " (" + parentStateGraph.getName() + ") - " +  name + " executed: " + input);
        
        if (finalNode) return false;

        if (childs.isEmpty()) return true;

        for (Node<T, R> child : childs)
            if (child.run(input, debug, i + 1) == false)
                return false;
        
        return true;
    }

    public boolean isEdgeAllowed(T input) {
        Predicate<T> condition = null;
        if (previousNode != null) {
            condition = parentStateGraph.getConditions().get(previousNode.getName() + "-" + name);
        }
        if (condition != null && !condition.test(input)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Node: " + name + "(" + childs.size() + " output nodes)";
    }
}