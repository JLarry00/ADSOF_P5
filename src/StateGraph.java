package src;

import java.util.*;

public class StateGraph {
    private Map<String, State> states;
    private State currentState;

    public StateGraph() {
        this.states = new HashMap<>();
    }

    public void addState(String name, State state) {
        states.put(name, state);
    }
    
}