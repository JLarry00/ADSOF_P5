package src.decorate;

import src.graph.Node;

public class NodeDecorator<T, R> extends Node<T, R> {

    private Node<T, R> wrappee;

    public NodeDecorator(Node<T, R> wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public void addEdge(Node<T, R> nextNode) {
        wrappee.addEdge(nextNode);
    }

    @Override
    public void withInjector(Function<T, R> injector) {
        wrappee.withInjector(injector);
    }

    @Override
    public void withExtractor(BiFunction<R, T, T> extractor) {
        wrappee.withExtractor(extractor);
    }

    @Override
    public boolean run(T input, boolean debug, int i) {
        return wrappee.run(input, debug, i);
    }

    @Override
    public void setPreviousNode(Node<T, R> previousNode) {
        wrappee.setPreviousNode(previousNode);
    }
    
    



}
