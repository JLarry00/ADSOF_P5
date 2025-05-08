package src.decorate;

import java.util.function.*;
import java.util.*;
import src.graph.*;

/**
 * Clase base para implementar el patrón Decorator en nodos de grafo.
 * Esta clase proporciona una implementación base que delega todas las operaciones
 * al nodo decorado (wrappee).
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class NodeDecorator<T, R> extends Node<T, R> {
    /** El nodo decorado */
    private Node<T, R> wrappee;

    /**
     * Constructor de la clase NodeDecorator.
     * @param wrappee El nodo a decorar.
     */
    public NodeDecorator(Node<T, R> wrappee) {
        super(wrappee.getName(), wrappee.getParentStateGraph(), wrappee.getWorkflowGraph());
        this.wrappee = wrappee;
    }

    /**
     * Añade una arista al nodo.
     * @param nextNode El nodo siguiente.
     */
    @Override
    public void addEdge(Node<T, R> nextNode) {
        wrappee.addEdge(nextNode);
    }

    /**
     * Obtiene el nodo anterior.
     * @return El nodo anterior.
     */
    @Override
    public Node<T, R> getPreviousNode() {
        return wrappee.getPreviousNode();
    }

    /**
     * Obtiene el grafo de estado padre.
     * @return El grafo de estado padre.
     */
    @Override
    public InterfaceStateGraph<T> getParentStateGraph() {
        return wrappee.getParentStateGraph();
    }

    /**
     * Obtiene el grafo de estado hijo.
     * @return El grafo de estado hijo.
     */
    @Override
    public InterfaceStateGraph<R> getWorkflowGraph() {
        return wrappee.getWorkflowGraph();
    }

    /**
     * Obtiene la acción del nodo.
     * @return La acción del nodo.
     */
    @Override
    public Consumer<T> getAction() {
        return wrappee.getAction();
    }

    /**
     * Obtiene el inyector del nodo.
     * @return El inyector del nodo.
     */
    @Override
    public Function<T, R> getInjector() {
        return wrappee.getInjector();
    }

    /**
     * Obtiene el extractor del nodo.
     * @return El extractor del nodo.
     */
    @Override
    public BiFunction<R, T, T> getExtractor() {
        return wrappee.getExtractor();
    }

    /**
     * Obtiene el nombre del nodo.
     * @return El nombre del nodo.
     */
    @Override
    public String getName() {
        return wrappee.getName();
    }

    /**
     * Obtiene los nodos hijos.
     * @return La lista de nodos hijos.
     */
    @Override
    public List<Node<T, R>> getChilds() {
        return wrappee.getChilds();
    }

    /**
     * Añade un inyector al nodo.
     * @param injector El inyector a añadir.
     * @return El nodo actualizado.
     */
    @Override
    public Node<T, R> withInjector(Function<T, R> injector) {
        wrappee.withInjector(injector);
        return this;
    }

    /**
     * Añade un extractor al nodo.
     * @param extractor El extractor a añadir.
     * @return El nodo actualizado.
     */
    @Override
    public Node<T, R> withExtractor(BiFunction<R, T, T> extractor) {
        wrappee.withExtractor(extractor);
        return this;
    }

    /**
     * Ejecuta el nodo.
     * @param input El input para la ejecución.
     * @param debug true si se debe ejecutar en modo debug, false en caso contrario.
     * @param i El índice de ejecución.
     * @return true si la ejecución fue exitosa, false en caso contrario.
     */
    @Override
    public boolean run(T input, boolean debug, int i) {
        return wrappee.run(input, debug, i);
    }

    /**
     * Verifica si una arista está permitida.
     * @param input El input para la verificación.
     * @return true si la arista está permitida, false en caso contrario.
     */
    @Override
    public boolean isEdgeAllowed(T input) {
        return wrappee.isEdgeAllowed(input);
    }

    /**
     * Establece el nodo anterior.
     * @param previousNode El nodo anterior.
     */
    @Override
    public void setPreviousNode(Node<T, R> previousNode) {
        wrappee.setPreviousNode(previousNode);
    }

    /**
     * Devuelve una representación en cadena del nodo.
     * @return Una cadena que representa el nodo.
     */
    @Override
    public String toString() {
        return wrappee.toString();
    }
}
