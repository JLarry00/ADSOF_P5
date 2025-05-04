package src.decorate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import src.graph.Node;
import src.graph.StateGraph;


/*public class StateGraphProfiler<T> extends BaseDecorator<T> {
    private long startTime;
    private long endTime;
    private List<String> historic;
    private static final double NANOSECONDS_TO_SECONDS = 1000000000.0;

    //lg
    public StateGraphProfiler(InterfaceStateGraph<T> wrappee) {
        super(wrappee);
        historic = new ArrayList<>();
    }

    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        Node<T, Object> baseNode = new Node<>(name, action, (StateGraph<T>) component);
        Node<T, Object> profiledNode = new NodeProfiler<>(baseNode, historic);
        ((StateGraph<T>) component).getNodes().put(name, profiledNode);
        return this;
    }   
    
    public List<String> history() {
        return java.util.Collections.unmodifiableList(historic);
    }

    @Override
    public String getSuffixDecorators() {
        return "[profiled]";
    }

    @Override 
    public String toString(){
        return super.toString();
    }
}*/