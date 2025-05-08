package src.decorate;

import java.util.function.Consumer;
import java.util.function.Predicate;
import src.graph.*;
import java.util.*;

/**
 * Interfaz que define las operaciones básicas de un grafo de estado.
 * Esta interfaz proporciona los métodos necesarios para manipular y ejecutar
 * un grafo de estado con nodos y aristas.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public interface InterfaceStateGraph<T> {
    /**
     * Obtiene el nombre del grafo de estado.
     * @return El nombre del grafo de estado.
     */
    String getName();

    /**
     * Obtiene las condiciones del grafo de estado.
     * @return Un mapa con las condiciones del grafo de estado.
     */
    Map<String, Predicate<T>> getConditions();

    /**
     * Obtiene el nodo inicial del grafo de estado.
     * @return El nodo inicial del grafo de estado.
     */
    Node<T, Object> getInitialNode();

    /**
     * Verifica si un nodo es el nodo inicial.
     * @param name El nombre del nodo a verificar.
     * @return true si el nodo es inicial, false en caso contrario.
     */
    boolean isInitial(String name);

    /**
     * Obtiene las aristas del grafo de estado.
     * @return La lista de aristas del grafo de estado.
     */
    List<Edge> getEdges();

    /**
     * Añade un nodo al grafo de estado.
     * @param name El nombre del nodo a añadir.
     * @param action La acción a realizar en el nodo.
     * @return El grafo de estado actualizado.
     */
    InterfaceStateGraph<T> addNode(String name, Consumer<T> action);

    /**
     * Reemplaza un nodo en el grafo de estado.
     * @param node El nuevo nodo que reemplazará al existente.
     * @return El grafo de estado actualizado.
     */
    InterfaceStateGraph<T> replaceNode(Node<T, Object> node);

    /**
     * Añade un nodo de flujo de trabajo al grafo de estado.
     * @param name El nombre del nodo a añadir.
     * @param workFlow El grafo de estado del flujo de trabajo.
     * @param <R> El tipo de datos del grafo que este nodo ejecutará.
     * @return El nodo añadido.
     */
    <R> Node<T, R> addWfNode(String name, InterfaceStateGraph<R> workFlow);

    /**
     * Añade una arista al grafo de estado.
     * @param from El nombre del nodo de origen.
     * @param to El nombre del nodo de destino.
     * @return El grafo de estado actualizado.
     */
    InterfaceStateGraph<T> addEdge(String from, String to);

    /**
     * Añade una arista condicional al grafo de estado.
     * @param from El nombre del nodo de origen.
     * @param to El nombre del nodo de destino.
     * @param condition La condición que debe cumplirse para seguir la arista.
     */
    void addConditionalEdge(String from, String to, Predicate<T> condition);

    /**
     * Ejecuta el grafo de estado.
     * @param input El input para la ejecución.
     * @param debug true si se debe ejecutar en modo debug, false en caso contrario.
     * @return El resultado de la ejecución.
     */
    T run(T input, boolean debug);

    /**
     * Establece un nodo como inicial.
     * @param name El nombre del nodo a establecer como inicial.
     */
    void setInitial(String name);

    /**
     * Establece un nodo como final.
     * @param name El nombre del nodo a establecer como final.
     */
    void setFinal(String name);

    /**
     * Devuelve una representación en cadena del grafo de estado.
     * @return Una cadena que representa el grafo de estado.
     */
    String toString();

    /**
     * Obtiene un nodo por su nombre.
     * @param name El nombre del nodo a obtener.
     * @return El nodo con el nombre especificado.
     */
    Node<T, Object> getNode(String name);

    /**
     * Obtiene todos los nodos del grafo de estado.
     * @return Un mapa con todos los nodos del grafo de estado.
     */
    Map<String, Node<T, Object>> getNodes();
}

