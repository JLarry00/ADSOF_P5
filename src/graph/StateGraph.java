package src.graph;

import java.util.*;
import java.util.function.*;

import src.data.NumericData;
import src.decorate.InterfaceStateGraph;

/**
 * Clase que representa un grafo de estado.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */     
public class StateGraph<T> implements InterfaceStateGraph<T> {
    /** El nombre del grafo de estado */
    private String name;
    /** La descripción del grafo de estado */
    private String description;
    /** Los nodos del grafo de estado */
    private LinkedHashMap<String, Node<T, Object>> nodes;
    /** El nodo inicial del grafo de estado */
    private Node<T, Object> initialNode;
    /** Los nodos finales del grafo de estado */
    private List<Node<T, Object>> finalNodes;
    /** Las aristas del grafo de estado */
    private List<Edge> edges;
    /** Las condiciones del grafo de estado */
    private LinkedHashMap<String, Predicate<T>> conditions;
    /** Los datos numéricos del grafo de estado */
    private List<NumericData> numericData;

    /**
     * Constructor de la clase StateGraph.
     * @param name El nombre del grafo de estado.
     * @param description La descripción del grafo de estado.
     */
    public StateGraph(String name, String description) {
        this.name = name;
        this.description = description;
        this.nodes = new LinkedHashMap<String, Node<T, Object>>();
        this.initialNode = null;
        this.finalNodes = new ArrayList<Node<T, Object>>();
        this.edges = new ArrayList<Edge>();
        this.conditions = new LinkedHashMap<String, Predicate<T>>();
        this.numericData = new ArrayList<NumericData>();
        numericData.add(new NumericData(2,2));
        numericData.add(new NumericData(3,3));
        numericData.add(new NumericData(4,4));
    }

    /**
     * Obtiene los datos numéricos del grafo de estado.
     * @return Los datos numéricos del grafo de estado.
     */
    public List<NumericData> getNumericData() { return Collections.unmodifiableList(numericData); }

    /**
     * Obtiene el nombre del grafo de estado.
     * @return El nombre del grafo de estado.
     */
    public String getName() { return name; }

    /**
     * Obtiene la descripción del grafo de estado.
     * @return La descripción del grafo de estado.
     */
    public String getDescription() { return description; }

    /**
     * Obtiene el nodo inicial del grafo de estado.
     * @return El nodo inicial del grafo de estado.
     */
    public Node<T, Object> getInitial() { return initialNode; }

    /**
     * Obtiene los nodos finales del grafo de estado.
     * @return Los nodos finales del grafo de estado.
     */
    public List<Node<T, Object>> getFinalNodes() { return Collections.unmodifiableList(finalNodes); }

    /**
     * Obtiene las condiciones del grafo de estado.
     * @return Las condiciones del grafo de estado.
     */
    public Map<String, Predicate<T>> getConditions() { return Collections.unmodifiableMap(conditions); }

    /**
     * Obtiene el nodo del grafo de estado.
     * @param name El nombre del nodo.
     * @return El nodo del grafo de estado.
     */
    public Node<T, Object> getNode(String name) { return nodes.get(name); }

    /**
     * Obtiene los nodos del grafo de estado.
     * @return Mapa deos nodos del grafo de estado.
     */
    public Map<String, Node<T, Object>> getNodes() { return Collections.unmodifiableMap(nodes); }

    /**
     * Obtiene las aristas del grafo de estado.
     * @return Las aristas del grafo de estado.
     */
    public List<Edge> getEdges() { return Collections.unmodifiableList(edges); }

    /**
     * Establece el nodo inicial del grafo de estado.
     * @param name El nombre del nodo inicial.
     */
    @Override
    public void setInitial(String name) { initialNode = nodes.get(name); }

    /**
     * Establece el nodo final del grafo de estado.
     * @param name El nombre del nodo final.
     */
    @Override
    public void setFinal(String name) {
        finalNodes.add(nodes.get(name));
        nodes.get(name).setFinalNode(true);
    }

    /**
     * Añade un nodo al grafo de estado.
     * @param name El nombre del nodo.
     * @param action La acción del nodo.
     * @return El grafo de estado.
     */
    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        if (nodes.get(name) != null) throw new IllegalArgumentException("Node already exists");

        nodes.put(name, new Node<T, Object>(name, action, this));
        return this;
    }

    /**
     * Reemplaza un nodo del grafo de estado.
     * @param node El nodo.
     * @return El grafo de estado.
     */
    @Override
    public InterfaceStateGraph<T> replaceNode(Node<T, Object> node) {
        if (nodes.get(node.getName()) == null) throw new IllegalArgumentException("Node not found");
        nodes.put(node.getName(), node);
        return this;
    }

    /**
     * Añade un nodo del grafo de estado.
     * @param name El nombre del nodo.
     * @param workFlow El grafo de estado hijo.
     * @return El grafo de estado.
     */
    @Override
    public <R> Node<T, R> addWfNode(String name, InterfaceStateGraph<R> workFlow) {
        if (nodes.get(name) != null) throw new IllegalArgumentException("Node already exists");
        
        Node<T, R> node = new Node<T, R>(name, this, workFlow);

        @SuppressWarnings("unchecked")
        Node<T, Object> node2 = (Node<T, Object>) node;
        nodes.put(name, node2);
        return node;
    }

    /**
     * Añade una arista al grafo de estado.
     * @param from El nodo de origen.
     * @param to El nodo de destino.
     * @return El grafo de estado.
     */
    @Override   
    public InterfaceStateGraph<T> addEdge(String from, String to) {
        if (nodes.get(from) == null)                            throw new IllegalArgumentException("Node origin not found");
        if (nodes.get(to) == null)                              throw new IllegalArgumentException("Node destination not found");
        if (nodes.get(from).getChilds().contains(nodes.get(to))) throw new IllegalArgumentException("Edge already exists");

        getNode(from).addEdge(getNode(to));
        edges.add(new Edge(from, to));
        return this;
    }

    /**
     * Añade una arista condicional al grafo de estado.
     * @param from El nodo de origen.
     * @param to El nodo de destino.
     * @param condition La condición.
     */
    @Override
    public void addConditionalEdge(String from, String to, Predicate<T> condition) {
        addEdge(from, to);
        conditions.put(from + "-" + to, condition);
    }

    /**
     * Ejecuta el grafo de estado.
     * @param input El input.
     * @param debug true si se debe ejecutar con debug, false en caso contrario.
     * @return El input.
     */
    @Override
    public T run(T input, boolean debug) {
        int i = 1;
        
        if (initialNode != null) {
            if (conditions.get(initialNode.getName()) == null || conditions.get(initialNode.getName()).test(input)) {
                if (debug) System.out.println("Step " + i + " (" + name + ") - " + "input: " + input);
                initialNode.run(input, debug, i + 1);
            }
        }
        
        return input;
    }

    /**
     * Devuelve una representación en cadena del grafo de estado.
     * @return Una representación en cadena del grafo de estado.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Workflow '").append(name).append("' (").append(description).append("):");
        sb.append("\n- Nodes: " + nodes.toString());
        sb.append("\n- Initial: " + (initialNode != null ? initialNode.getName() : "null"));
        sb.append("\n- Final: ");
        if (finalNodes.size() == 1) {
            sb.append(finalNodes.get(0).getName());
        } else if (finalNodes.size() == 0) {
            sb.append("None");
        } else {
            sb.append(finalNodes.toString());
        }
        return sb.toString();
    }
}