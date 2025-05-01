package src.graph;

import java.util.*;
import java.util.function.*;

public class StateGraph<T> {
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

    public String getName() { return name; }

    public String getDescription() { return description; }

    public Node<T, Object> getInitial() { return initialNode; }

    public List<Node<T, Object>> getFinalNodes() { return Collections.unmodifiableList(finalNodes); }

    public Map<String, Predicate<T>> getConditions() { return Collections.unmodifiableMap(conditions); }

    public Node<T, Object> getNode(String name) { return nodes.get(name); }

    public Map<String, Node<T, Object>> getNodes() { return Collections.unmodifiableMap(nodes); }

    public List<Edge> getEdges() { return Collections.unmodifiableList(edges); }

    public void setInitial(String name) { initialNode = nodes.get(name); }

    public void setFinal(String name) { finalNodes.add(nodes.get(name)); }

    public StateGraph<T> addNode(String name, Consumer<T> action) {
        if (nodes.get(name) != null) throw new IllegalArgumentException("Node already exists");

        nodes.put(name, new Node<T, Object>(name, action, this));
        return this;
    }

    public <R> Node<T, R> addWfNode(String name, StateGraph<R> workFlow) {
        if (nodes.get(name) != null) throw new IllegalArgumentException("Node already exists");
        
        Node<T, R> node = new Node<T, R>(name, this, workFlow);

        @SuppressWarnings("unchecked")
        Node<T, Object> node2 = (Node<T, Object>) node;
        nodes.put(name, node2);
        return node;
    }

    public StateGraph<T> addEdge(String from, String to) {
        if (nodes.get(from) == null)                            throw new IllegalArgumentException("Node origin not found");
        if (nodes.get(to) == null)                              throw new IllegalArgumentException("Node destination not found");
        if (nodes.get(from).getChilds().contains(nodes.get(to))) throw new IllegalArgumentException("Edge already exists");

        getNode(from).addEdge(getNode(to));
        edges.add(new Edge(from, to));
        return this;
    }

    public void addConditionalEdge(String from, String to, Predicate<T> condition) {
        addEdge(from, to);
        conditions.put(from + "-" + to, condition);
    }

    public T run(T input, boolean debug) {
        T output = input;
        int i = 1;
        
        if (conditions.get(initialNode.getName()) == null || conditions.get(initialNode.getName()).test(output)) {
            if (debug) System.out.println("Step " + i + " (" + name + ") - " + "input: " + input);
            initialNode.run(output, debug, i + 1);
        }
        
        return output;
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