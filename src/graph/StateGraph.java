package src.graph;

import java.util.*;
import java.util.function.*;

import src.decorate.InterfaceStateGraph;

public class StateGraph<T> implements InterfaceStateGraph<T> {
    private String name;
    private String description;
    private LinkedHashMap<String, Node<T, Object>> nodes;
    private Node<T, Object> initialNode;
    private List<Node<T, Object>> finalNodes;
    private List<Edge> edges;
    private LinkedHashMap<String, Predicate<T>> conditions;

    public StateGraph(String name, String description) {
        this.name = name;
        this.description = description;
        this.nodes = new LinkedHashMap<String, Node<T, Object>>();
        this.initialNode = null;
        this.finalNodes = new ArrayList<Node<T, Object>>();
        this.edges = new ArrayList<Edge>();
        this.conditions = new LinkedHashMap<String, Predicate<T>>();
    }
    /**
     * Obtiene el nombre del grafo de estado.
     * @return El nombre del grafo de estado.
     */
    public String getName() { return name; }

    public String getDescription() { return description; }

    public Node<T, Object> getInitial() { return initialNode; }

    public List<Node<T, Object>> getFinalNodes() { return Collections.unmodifiableList(finalNodes); }

    public Map<String, Predicate<T>> getConditions() { return Collections.unmodifiableMap(conditions); }

    public Node<T, Object> getNode(String name) { return nodes.get(name); }

    /**
     * Obtiene los nodos del grafo de estado.
     * @return Mapa deos nodos del grafo de estado.
     */
    @Override
    public Map<String, Node<T, Object>> getNodes() { return Collections.unmodifiableMap(nodes); }

    /**
     * Obtiene las aristas del grafo de estado.
     * @return Las aristas del grafo de estado.
     */
    @Override
    public List<Edge> getEdges() { return Collections.unmodifiableList(edges); }

    /**
     * Obtiene el nodo inicial del grafo de estado.
     * @return El nodo inicial del grafo de estado.
     */
    @Override
    public Node<T, Object> getInitialNode() { return initialNode; }

    /**
     * Establece el nodo inicial del grafo de estado.
     * @param name El nombre del nodo inicial.
     */
    @Override
    public void setInitial(String name) { initialNode = nodes.get(name); }

    /**
     * Comprueba si el nodo es el inicial.
     * @param name El nombre del nodo.
     * @return true si el nodo es el inicial, false en caso contrario.
     */
    @Override
    public boolean isInitial(String name) { return initialNode.getName().equals(name); }

    /**
     * Establece el nodo final del grafo de estado.
     * @param name El nombre del nodo final.
     */
    @Override
    public void setFinal(String name) {
        finalNodes.add(nodes.get(name));
        nodes.get(name).setFinalNode(true);
    }

    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        if (nodes.get(name) != null) throw new IllegalArgumentException("Node already exists");

        nodes.put(name, new Node<T, Object>(name, action, this));
        return this;
    }

    @Override
    public InterfaceStateGraph<T> replaceNode(Node<T, Object> node) {
        if (nodes.get(node.getName()) == null) throw new IllegalArgumentException("Node not found");
        nodes.put(node.getName(), node);
        return this;
    }

    @Override
    public <R> Node<T, R> addWfNode(String name, InterfaceStateGraph<R> workFlow) {
        if (nodes.get(name) != null) throw new IllegalArgumentException("Node already exists");
        
        Node<T, R> node = new Node<T, R>(name, this, workFlow);

        @SuppressWarnings("unchecked")
        Node<T, Object> node2 = (Node<T, Object>) node;
        nodes.put(name, node2);
        return node;
    }

    @Override   
    public InterfaceStateGraph<T> addEdge(String from, String to) {
        if (nodes.get(from) == null)                            throw new IllegalArgumentException("Node origin not found");
        if (nodes.get(to) == null)                              throw new IllegalArgumentException("Node destination not found");
        if (nodes.get(from).getChilds().contains(nodes.get(to))) throw new IllegalArgumentException("Edge already exists");

        getNode(from).addEdge(getNode(to));
        edges.add(new Edge(from, to));
        return this;
    }

    @Override
    public void addConditionalEdge(String from, String to, Predicate<T> condition) {
        addEdge(from, to);
        conditions.put(from + "-" + to, condition);
    }

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