package src.decorate;

import java.util.function.Consumer;
import java.util.function.Predicate;
import src.graph.*;

public interface InterfaceStateGraph<T> {
    InterfaceStateGraph<T> addNode(String name, Consumer<T> action);

    InterfaceStateGraph<T> replaceNode(Node<T, Object> node);

    <R> Node<T, R> addWfNode(String name, InterfaceStateGraph<R> workFlow);

    InterfaceStateGraph<T> addEdge(String from, String to);

    void addConditionalEdge(String from, String to, Predicate<T> condition);

    T run(T input, boolean debug);

    void setInitial(String name);

    void setFinal(String name);

    String toString();

    Node<T, Object> getNode(String name);
}

