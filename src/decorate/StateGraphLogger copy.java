package src.decorate;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import src.graph.Node;
import src.graph.StateGraph;


/*public class StateGraphLogger<T> extends BaseDecorator<T> {
    private String fileName;
    private List<String> historic;

    public StateGraphLogger(InterfaceStateGraph<T> wrappee, String fileName) {
        super(wrappee);
        this.fileName = fileName;
    }

   @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        Node<T, Object> baseNode = new Node<T, Object>(name, action, (StateGraph<T>) component);    
        Node<T, Object> profiledNode = new NodeLogger<>(baseNode, fileName);
        // Añade el nodo decorado al grafo base
        ((StateGraph<T>) component).getNodes().put(name, profiledNode);
        return this;
    }

    public List<String> history() {
        return Collections.unmodifiableList(historic);
    }

    @Override
    public String getSuffixDecorators() {
        return "[logged]";
    }

    @Override
    public String toString(){
        return super.toString();
    }
}*/
