package src.decorate;

import java.util.function.*;
import java.util.*;

import src.graph.*;

/**
 * Decorador que añade funcionalidad de profiling a un grafo de estado.
 * Esta clase mide y registra el tiempo de ejecución de los nodos del grafo,
 * incluyendo el nombre del nodo y el input utilizado.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class StateGraphProfiler<T> extends BaseDecorator<T> {
    /** La lista de registros de tiempo de ejecución */
    private List<String> historic;

    /**
     * Constructor de la clase StateGraphProfiler.
     * @param wrappee El grafo de estado a decorar.
     */
    public StateGraphProfiler(InterfaceStateGraph<T> wrappee) {
        super(wrappee);
        historic = new ArrayList<>();
    }

    /**
     * Ejecuta el grafo de estado.
     * @param input El input para la ejecución.
     * @param debug true si se debe ejecutar en modo debug, false en caso contrario.
     * @return El resultado de la ejecución.
     */
    @Override
    public T run(T input, boolean debug) {
        T result = super.run(input, debug);
        return result;
    }

    /**
     * Añade un nodo al grafo de estado y lo decora con profiling.
     * @param name El nombre del nodo a añadir.
     * @param action La acción a realizar en el nodo.
     * @return El grafo de estado actualizado.
     */
    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        super.addNode(name, action);
        Node<T, Object> node = super.getNode(name);
        node = new NodeProfiler<>(node, this);
        super.replaceNode(node);
        return this;
    }
    
    /**
     * Obtiene el historial de tiempos de ejecución.
     * @return Una lista inmutable con los registros de tiempo de ejecución.
     */
    public List<String> history() {
        return Collections.unmodifiableList(historic);
    }

    /**
     * Añade un nuevo registro de tiempo de ejecución al principio de la lista.
     * @param history El registro de tiempo a añadir.
     */
    public void addHistory(String history) {
        List<String> reverse = new ArrayList<>();
        reverse.add(history);
        reverse.addAll(historic);
        historic = reverse;
    }

    /**
     * Devuelve una representación en cadena del grafo de estado.
     * @return Una cadena que representa el grafo de estado.
     */
    @Override 
    public String toString(){
        return super.toString();
    }
}