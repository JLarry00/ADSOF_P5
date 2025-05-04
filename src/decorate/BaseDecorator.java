package src.decorate;

import java.util.function.Consumer;
import java.util.function.Predicate;
import src.graph.Node;

public class BaseDecorator<T> implements InterfaceStateGraph<T> {
    protected InterfaceStateGraph<T> component;


    public BaseDecorator(InterfaceStateGraph<T> component) {
        this.component = component;
    }

    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        return component.addNode(name, action);
    }

    @Override
    public <R> Node<T, R> addWfNode(String name, InterfaceStateGraph<R> workFlow) {
        return component.addWfNode(name, workFlow);
    }
    
    @Override
    public InterfaceStateGraph<T> addEdge(String from, String to) {
        return component.addEdge(from, to);
    }

    @Override
    public void addConditionalEdge(String from, String to, Predicate<T> condition) {
        component.addConditionalEdge(from, to, condition);
    }   

    @Override
    public T run(T input, boolean debug) {
        return component.run(input, debug);
    }

    @Override
    public void setInitial(String name) {
        component.setInitial(name);
    }

    @Override
    public String getSuffixDecorators() {
        return component.getSuffixDecorators();
    }

    @Override
    public String toString() {
        return component.toString();
    }
}
