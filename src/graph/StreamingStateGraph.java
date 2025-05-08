package src.graph;

import java.util.*;
import java.util.function.*;

/**
 * Clase que representa un grafo de estado de streaming.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class StreamingStateGraph<T> extends StateGraph<T> {
    /** La historia del grafo de estado de streaming */
    private List<T> history;

    /**
     * Constructor de la clase StreamingStateGraph.
     * @param name El nombre del grafo de estado de streaming.
     * @param description La descripción del grafo de estado de streaming.
     */
    public StreamingStateGraph(String name, String description) {
        super(name, description);
        this.history = new ArrayList<T>();
    }

    /**
     * Obtiene la historia del grafo de estado de streaming.
     * @return La historia del grafo de estado de streaming.
     */
    public List<T> history() {
        return Collections.unmodifiableList(history);
    }

    /**
     * Añade la ejecución al historial.
     * @param exec La ejecución.
     */
    public void addHistory(T exec) {
        history.add(exec);
    }

    /**
     * Añade un nodo al grafo de estado de streaming.
     * @param name El nombre del nodo.
     * @param action La acción del nodo.
     * @return El grafo de estado de streaming.
     */
    @Override
    public StateGraph<T> addNode(String name, Consumer<T> action) {
        super.addNode(name, action);
        return this;
    }

    /**
     * Ejecuta el grafo de estado de streaming.
     * @param input El input.
     * @param debug true si se debe ejecutar con debug, false en caso contrario.
     * @return El input.
     */
    @Override   
    public T run(T input, boolean debug) {
        T output = input;
        int i = 1;
        List<T> history = new ArrayList<T>(this.history);
        history.add(input);
        
        if ((super.getInitial() != null && super.getConditions().get(super.getInitial().getName()) == null) || super.getConditions().get(super.getInitial().getName()).test(output)) {
            if (debug) System.out.println("Step " + i + " (" + super.getName() + ") - " + "input: " + history);
            super.getInitial().run(output, debug, i + 1);
        }
        addHistory(output);
        return output;
    }
}