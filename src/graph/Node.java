package src.graph;

import java.util.*;
import java.util.function.*;

import src.decorate.*;

/**
 * Clase que representa un nodo en un grafo.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class Node<T, R> {
    /** El nombre del nodo */
    private String name;
    /** La acción a realizar en el nodo */
    private Consumer<T> action;
    /** Indica si el nodo es final */
    private boolean finalNode;
    /** Lista de nodos hijos */
    private List<Node<T, R>> childs;
    /** Nodo anterior */
    private Node<T, R> previousNode;
    /** Grafo de estado padre */
    private StateGraph<T> parentStateGraph;
    /** Grafo de estado hijo */
    private InterfaceStateGraph<R> workflowGraph;
    /** Inyector */
    private Function<T, R> injector;
    /** Extracto */
    private BiFunction<R, T, T> extractor;

    /**
     * Constructor de la clase Node.
     * @param name El nombre del nodo.
     * @param action La acción a realizar en el nodo.
     * @param stateGraph El grafo de estado al que pertenece el nodo.
     */
    public Node(String name, Consumer<T> action, StateGraph<T> stateGraph) {
        this.name = name;
        this.action = action;
        this.parentStateGraph = stateGraph;
        this.childs = new ArrayList<Node<T, R>>();
        this.previousNode = null;
        this.workflowGraph = null;
        this.injector = null;
        this.extractor = null;
        this.finalNode = false;
    }

    /**
     * Constructor de la clase Node.
     * @param name El nombre del nodo.
     * @param stateGraph El grafo de estado al que pertenece el nodo.
     * @param workflowGraph La interfaz grafo de estado hijo.
     */
    public Node(String name, StateGraph<T> stateGraph, InterfaceStateGraph<R> workflowGraph) {
        this.name = name;
        this.action = null;
        this.parentStateGraph = stateGraph;
        this.childs = new ArrayList<Node<T, R>>();
        this.previousNode = null;
        this.workflowGraph = workflowGraph;
        this.injector = null;
        this.extractor = null;
        this.finalNode = false;
    }

    /**
     * Obtiene el nombre del nodo.
     * @return El nombre del nodo.
     */ 
    public String getName() { return name; }

    /**
     * Obtiene la acción a realizar en el nodo.
     * @return La acción a realizar en el nodo.
     */
    public Consumer<T> getAction() { return action; }
    
    /**
     * Obtiene la lista de nodos hijos.
     * @return La lista de nodos hijos.
     */
    public List<Node<T, R>> getChilds() { return Collections.unmodifiableList(childs); }

    /**
     * Obtiene el nodo anterior.
     * @return El nodo anterior.
     */
    public Node<T, R> getPreviousNode() { return previousNode; }

    /**
     * Establece el nodo anterior.
     * @param previousNode El nodo anterior.
     */
    public void setPreviousNode(Node<T, R> previousNode) { this.previousNode = previousNode; }

    /**
     * Obtiene el grafo de estado padre.
     * @return El grafo de estado padre.
     */
    public StateGraph<T> getParentStateGraph() { return parentStateGraph; }
    
    /**
     * Obtiene el grafo de estado hijo.
     * @return El grafo de estado hijo.
     */
    public InterfaceStateGraph<R> getWorkflowGraph() { return workflowGraph; }

    /**
     * Obtiene el inyector.
     * @return El inyector.
     */
    public Function<T, R> getInjector() { return injector; }

    /**
     * Obtiene el extractor.
     * @return El extractor.
     */
    public BiFunction<R, T, T> getExtractor() { return extractor; }
    
    /**
     * Obtiene si el nodo es final.
     * @return true si el nodo es final, false en caso contrario.
     */ 
    public boolean isFinalNode() { return finalNode; }

    /**
     * Establece si el nodo es final.
     * @param finalNode true si el nodo es final, false en caso contrario.
     */ 
    public void setFinalNode(boolean finalNode) { this.finalNode = finalNode; } 

    /**
     * Añade una arista al nodo.
     * @param nextNode El nodo siguiente.
     */ 
    public void addEdge(Node<T, R> nextNode) {
        if (childs.contains(nextNode)) throw new IllegalArgumentException("Edge already exists");
        childs.add(nextNode);
        nextNode.setPreviousNode(this);
    }

    /**
     * Añade un inyector al nodo.
     * @param injector El inyector.
     * @return El nodo.
     */ 
    public Node<T, R> withInjector(Function<T, R> injector) {
        if (workflowGraph == null) throw new IllegalArgumentException("Workflow graph is null");

        this.injector = injector;
        return this;
    }

    /**
     * Añade un extractor al nodo.
     * @param extractor El extractor.
     * @return El nodo.
     */ 
    public Node<T, R> withExtractor(BiFunction<R, T, T> extractor) {
        if (workflowGraph == null) throw new IllegalArgumentException("Workflow graph is null");

        this.extractor = extractor;
        return this;
    }

    /**
     * Ejecuta el nodo.
     * @param input El input.
     * @param debug true si se debe ejecutar con debug, false en caso contrario.
     * @param i El índice.
     * @return true si el nodo se ha ejecutado correctamente, false en caso contrario.
     */ 
    public boolean run(T input, boolean debug, int i) {
        if (workflowGraph != null) {
            R workflowInput = injector.apply(input);
            R workflowOutput = workflowGraph.run(workflowInput, debug);
            input = extractor.apply(workflowOutput, input);
        }

        if (!isEdgeAllowed(input)) return true;
        
        if (action != null) action.accept(input);

        if (debug) System.out.println("Step " + i + " (" + parentStateGraph.getName() + ") - " +  name + " executed: " + input);
        
        if (finalNode) return false;

        if (childs.isEmpty()) return true;

        for (Node<T, R> child : childs)
            if (child.run(input, debug, i + 1) == false)
                return false;
        
        return true;
    }

    /**
     * Comprueba si la arista es permitida.
     * @param input El input.
     * @return true si la arista es permitida, false en caso contrario.
     */ 
    public boolean isEdgeAllowed(T input) {
        Predicate<T> condition = null;
        if (previousNode != null) {
            condition = parentStateGraph.getConditions().get(previousNode.getName() + "-" + name);
        }
        if (condition != null && !condition.test(input)) return false;
        return true;
    }

    /**
     * Devuelve una representación en cadena del nodo.
     * @return Una representación en cadena del nodo.
     */ 
    @Override
    public String toString() {
        return "Node: " + name + "(" + childs.size() + " output nodes)";
    }
}