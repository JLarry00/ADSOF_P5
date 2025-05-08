package src.graph;

import java.util.*;
import java.util.function.*;

public class StreamingStateGraph<T> extends StateGraph<T> {
    private List<T> history;

    public StreamingStateGraph(String name, String description) {
        super(name, description);
        this.history = new ArrayList<T>();
    }

    public List<T> history() {
        return Collections.unmodifiableList(history);
    }

    public void addHistory(T exec) {
        history.add(exec);
    }

    @Override
    public StateGraph<T> addNode(String name, Consumer<T> action) {
        super.addNode(name, action);
        return this;
    }

    @Override
    public T run(T input, boolean debug) {
        int i = 1;
        List<T> history = new ArrayList<T>(this.history);
        history.add(input);
        
        if ((super.getInitial() != null && super.getConditions().get(super.getInitial().getName()) == null) || super.getConditions().get(super.getInitial().getName()).test(input)) {
            if (debug) System.out.println("Step " + i + " (" + super.getName() + ") - " + "input: " + history);
            super.getInitial().run(input, debug, i + 1);
        }
        addHistory(input);
        return input;
    }
}