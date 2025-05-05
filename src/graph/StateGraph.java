package src.graph;

import java.util.*;
import java.util.function.*;

import src.data.NumericData;
import src.graph.Node;
import src.decorate.InterfaceStateGraph;

public class StateGraph<T> implements InterfaceStateGraph<T> {
    private String name;
    private String description;
    private LinkedHashMap<String, Node<T, Object>> nodes;
    private Node<T, Object> initialNode;
    private List<Node<T, Object>> finalNodes;
    private List<Edge> edges;
    private LinkedHashMap<String, Predicate<T>> conditions;
    
    private List<NumericData> numericData;

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

    public List<NumericData> getNumericData() { return Collections.unmodifiableList(numericData); }



    public String getName() { return name; }

    public String getDescription() { return description; }

    public Node<T, Object> getInitial() { return initialNode; }

    public List<Node<T, Object>> getFinalNodes() { return Collections.unmodifiableList(finalNodes); }

    public Map<String, Predicate<T>> getConditions() { return Collections.unmodifiableMap(conditions); }

    public Node<T, Object> getNode(String name) { return nodes.get(name); }

    public Map<String, Node<T, Object>> getNodes() { return Collections.unmodifiableMap(nodes); }

    public List<Edge> getEdges() { return Collections.unmodifiableList(edges); }

    @Override
    public void setInitial(String name) { initialNode = nodes.get(name); }

    public void setFinal(String name) { finalNodes.add(nodes.get(name)); }

    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        if (nodes.get(name) != null) throw new IllegalArgumentException("Node already exists");

        nodes.put(name, new Node<T, Object>(name, action, this));
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
        T output = input;
        int i = 1;
        
        if (conditions.get(initialNode.getName()) == null || conditions.get(initialNode.getName()).test(output)) {
            if (debug) System.out.println("Step " + i + " (" + name + ") - " + "input: " + input);
            initialNode.run(output, debug, i + 1);
        }
        
        return output;
    }

    @Override
    public String getSuffixDecorators() {
        return "poiuygt";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Workflow '").append(name).append("' (").append(description).append("):");
        sb.append("\n- Nodes: {");
        boolean first = true;
        for (Map.Entry<String, Node<T, Object>> entry : nodes.entrySet()) {
            if (!first) sb.append(", ");
            first = false;
            String nodeName = entry.getKey();
            Node<T, Object> node = entry.getValue();
            sb.append(nodeName)
              .append("=")
              .append(node.toString())
              .append(" ")
              .append(getSuffixDecorators());
        }
        sb.append("}");
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