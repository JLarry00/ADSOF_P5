package src.decorate;

import java.util.*;
import java.util.function.*;

import src.graph.*;

/**
 * Clase base para implementar el patrón Decorator en grafos de estado.
 * Esta clase proporciona una implementación base que delega todas las operaciones
 * al objeto decorado (wrappee).
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class BaseDecorator<T> implements InterfaceStateGraph<T> {
    /** El objeto decorado que implementa la interfaz InterfaceStateGraph */
    protected InterfaceStateGraph<T> wrappee;

    /**
     * Constructor de la clase BaseDecorator.
     * @param wrappee El objeto a decorar que implementa InterfaceStateGraph.
     */
    public BaseDecorator(InterfaceStateGraph<T> wrappee) {
        this.wrappee = wrappee;
    }

    /**
     * Obtiene las condiciones del grafo de estado.
     * @return Un mapa con las condiciones del grafo de estado.
     */
    @Override
    public Map<String, Predicate<T>> getConditions() {
        return wrappee.getConditions();
    }

    /**
     * Obtiene el nombre del grafo de estado.
     * @return El nombre del grafo de estado.
     */
    @Override
    public String getName() {
        return wrappee.getName();
    }

    /**
     * Obtiene las aristas del grafo de estado.
     * @return La lista de aristas del grafo de estado.
     */
    @Override
    public List<Edge> getEdges() {
        return wrappee.getEdges();
    }

    /**
     * Obtiene el nodo inicial del grafo de estado.
     * @return El nodo inicial del grafo de estado.
     */
    @Override
    public Node<T, Object> getInitialNode() {
        return wrappee.getInitialNode();
    }

    /**
     * Verifica si un nodo es el nodo inicial.
     * @param name El nombre del nodo a verificar.
     * @return true si el nodo es inicial, false en caso contrario.
     */
    @Override
    public boolean isInitial(String name) {
        return wrappee.isInitial(name);
    }

    /**
     * Obtiene un nodo por su nombre.
     * @param name El nombre del nodo a obtener.
     * @return El nodo con el nombre especificado.
     */
    @Override
    public Node<T, Object> getNode(String name) {
        return wrappee.getNode(name);
    }

    /**
     * Obtiene todos los nodos del grafo de estado.
     * @return Un mapa con todos los nodos del grafo de estado.
     */
    @Override
    public Map<String, Node<T, Object>> getNodes() {
        return wrappee.getNodes();
    }

    /**
     * Añade un nodo al grafo de estado.
     * @param name El nombre del nodo a añadir.
     * @param action La acción a realizar en el nodo.
     * @return El grafo de estado actualizado.
     */
    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        return wrappee.addNode(name, action);
    }

    /**
     * Reemplaza un nodo en el grafo de estado.
     * @param node El nuevo nodo que reemplazará al existente.
     * @return El grafo de estado actualizado.
     */
    @Override
    public InterfaceStateGraph<T> replaceNode(Node<T, Object> node) {
        return wrappee.replaceNode(node);
    }

    /**
     * Añade un nodo de flujo de trabajo al grafo de estado.
     * @param name El nombre del nodo a añadir.
     * @param workFlow El grafo de estado del flujo de trabajo.
     * @return El nodo añadido.
     */
    @Override
    public <R> Node<T, R> addWfNode(String name, InterfaceStateGraph<R> workFlow) {
        return wrappee.addWfNode(name, workFlow);
    }
    
    /**
     * Añade una arista al grafo de estado.
     * @param from El nombre del nodo de origen.
     * @param to El nombre del nodo de destino.
     * @return El grafo de estado actualizado.
     */
    @Override
    public InterfaceStateGraph<T> addEdge(String from, String to) {
        return wrappee.addEdge(from, to);
    }

    /**
     * Añade una arista condicional al grafo de estado.
     * @param from El nombre del nodo de origen.
     * @param to El nombre del nodo de destino.
     * @param condition La condición que debe cumplirse para seguir la arista.
     */
    @Override
    public void addConditionalEdge(String from, String to, Predicate<T> condition) {
        wrappee.addConditionalEdge(from, to, condition);
    }   

    /**
     * Ejecuta el grafo de estado.
     * @param input El input para la ejecución.
     * @param debug true si se debe ejecutar en modo debug, false en caso contrario.
     * @return El resultado de la ejecución.
     */
    @Override
    public T run(T input, boolean debug) {
        return wrappee.run(input, debug);
    }

    /**
     * Establece un nodo como inicial.
     * @param name El nombre del nodo a establecer como inicial.
     */
    @Override
    public void setInitial(String name) {
        wrappee.setInitial(name);
    }

    /**
     * Establece un nodo como final.
     * @param name El nombre del nodo a establecer como final.
     */
    @Override
    public void setFinal(String name) {
        wrappee.setFinal(name);
    }

    /**
     * Devuelve una representación en cadena del grafo de estado.
     * @return Una cadena que representa el grafo de estado.
     */
    @Override
    public String toString() {
        return wrappee.toString();
    }
}