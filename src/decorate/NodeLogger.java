package src.decorate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import src.graph.Node;
import java.util.function.Consumer;
import src.graph.StateGraph;

/*public class NodeLogger<T> extends Node<T, Object> {
    private String fileName;

    public NodeLogger(Node<T, Object> wrappee, String fileName) {
        super(wrappee.getName(), wrappee.getAction(), wrappee.getParentStateGraph());
        this.fileName = fileName;
    }

    @Override
    public boolean run(T input, boolean debug, int i) {
        guardar("node decrease executed,");
        boolean result = super.run(input, debug, i);
        guardar(" with output: " + input.toString());
        return result;
    }

    private void guardar(String mensaje) {
        String timestamp = new java.text.SimpleDateFormat("[dd/MM/yyyy - HH:mm:ss]").format(new java.util.Date());
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(timestamp + " " + mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return super.toString();
    }

    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        StateGraph<T> baseGraph = getBaseGraph();
        Node<T, Object> baseNode = new Node<>(name, action, baseGraph);
        Node<T, Object> profiledNode = new NodeProfiler<>(baseNode, historic);
        baseGraph.getNodes().put(name, profiledNode);
        return this;
    }
}*/
