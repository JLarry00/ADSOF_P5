package src.decorate;

import java.util.function.*;
import java.util.*;
import src.graph.*;

public class NodeDecorator<T, R> extends Node<T, R> {

    private Node<T, R> wrappee;

    public NodeDecorator(Node<T, R> wrappee) {
        super(wrappee.getName(), wrappee.getParentStateGraph(), wrappee.getWorkflowGraph());
        this.wrappee = wrappee;
    }

    @Override
    public void addEdge(Node<T, R> nextNode) {
        wrappee.addEdge(nextNode);
    }

    @Override
    public Node<T, R> getPreviousNode() {
        return wrappee.getPreviousNode();
    }

    @Override
    public StateGraph<T> getParentStateGraph() {
        return wrappee.getParentStateGraph();
    }

    @Override
    public InterfaceStateGraph<R> getWorkflowGraph() {
        return wrappee.getWorkflowGraph();
    }

    @Override
    public Consumer<T> getAction() {
        return wrappee.getAction();
    }

    @Override
    public Function<T, R> getInjector() {
        return wrappee.getInjector();
    }

    @Override
    public BiFunction<R, T, T> getExtractor() {
        return wrappee.getExtractor();
    }

    @Override
    public String getName() {
        return wrappee.getName();
    }

    @Override
    public List<Node<T, R>> getChilds() {
        return wrappee.getChilds();
    }

    @Override
    public Node<T, R> withInjector(Function<T, R> injector) {
        wrappee.withInjector(injector);
        return this;
    }

    @Override
    public Node<T, R> withExtractor(BiFunction<R, T, T> extractor) {
        wrappee.withExtractor(extractor);
        return this;
    }

    @Override
    public boolean run(T input, boolean debug, int i) {
        return wrappee.run(input, debug, i);
    }

    @Override
    public boolean isEdgeAllowed(T input) {
        return wrappee.isEdgeAllowed(input);
    }

    @Override
    public void setPreviousNode(Node<T, R> previousNode) {
        wrappee.setPreviousNode(previousNode);
    }

    @Override
    public String toString() {
        return wrappee.toString();
    }
}
