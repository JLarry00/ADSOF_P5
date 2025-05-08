package src.decorate;

import java.util.function.Consumer;
import java.util.function.Predicate;

import src.graph.*;


public class BaseDecorator<T> implements InterfaceStateGraph<T> {
    protected InterfaceStateGraph<T> wrappee;


    public BaseDecorator(InterfaceStateGraph<T> wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public Node<T, Object> getNode(String name) {
        return wrappee.getNode(name);
    }

    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        return wrappee.addNode(name, action);
    }

    @Override
    public InterfaceStateGraph<T> replaceNode(Node<T, Object> node) {
        return wrappee.replaceNode(node);
    }

    @Override
    public <R> Node<T, R> addWfNode(String name, InterfaceStateGraph<R> workFlow) {
        return wrappee.addWfNode(name, workFlow);
    }
    
    @Override
    public InterfaceStateGraph<T> addEdge(String from, String to) {
        return wrappee.addEdge(from, to);
    }

    @Override
    public void addConditionalEdge(String from, String to, Predicate<T> condition) {
        wrappee.addConditionalEdge(from, to, condition);
    }   

    @Override
    public T run(T input, boolean debug) {
        return wrappee.run(input, debug);
    }

    @Override
    public void setInitial(String name) {
        wrappee.setInitial(name);
    }

    @Override
    public void setFinal(String name) {
        wrappee.setFinal(name);
    }

    @Override
    public String toString() {
        return wrappee.toString();
    }
}
