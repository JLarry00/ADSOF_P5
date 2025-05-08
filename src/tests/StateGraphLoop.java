package src.tests;

import java.util.function.*;

import src.graph.*;
import src.decorate.*;

public class StateGraphLoop<T> extends StateGraph<T> {
    /** El numero de veces que se ejecuta el grafo de estado */
    private int times;

    public StateGraphLoop(String name, String description, int times) {
        super(name, description);
        this.times = times;
    }

    @Override
    public T run(T input, boolean debug) {
        for (int i = 0; i < times; i++) {
            System.out.println("Running loop " + (i + 1) + " of " + times);
            super.run(input, debug);
        }
        return input;
    }

    public int getTimes() {
        return times;
    }

    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        super.addNode(name, action);
        return this;
    }

    @Override 
    public String toString(){
        return super.toString();
    }
}