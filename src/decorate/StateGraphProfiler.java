package src.decorate;

import java.util.function.*;
import java.util.*;

import src.graph.*;

public class StateGraphProfiler<T> extends BaseDecorator<T> {
    private List<String> historic;

    public StateGraphProfiler(InterfaceStateGraph<T> wrappee) {
        super(wrappee);
        historic = new ArrayList<>();
    }

    @Override
    public T run(T input, boolean debug) {
        T result = super.run(input, debug);
        return result;
    }

    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        super.addNode(name, action);
        Node<T, Object> node = super.getNode(name);
        node = new NodeProfiler<>(node, this);
        super.replaceNode(node);
System.out.println(super.getNodes());
        return this;
    }
    
    public List<String> history() {
        return Collections.unmodifiableList(historic);
    }

    public void addHistory(String history) {
        List<String> reverse = new ArrayList<>();
        reverse.add(history);
        reverse.addAll(historic);
        historic = reverse;
    }

    @Override 
    public String toString(){
        return super.toString();
    }
}