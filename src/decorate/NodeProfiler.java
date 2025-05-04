package src.decorate;
import java.util.List;
import src.graph.Node;

public class NodeProfiler<T, R> extends Node<T, R> {
    private final Node<T, R> wrappee;
    private final List<String> historic;
    private long startTime;
    private long endTime;
    private static final double NANOSECONDS_TO_SECONDS = 1000000000.0;

    //lg
    public NodeProfiler(Node<T, R> wrappee, List<String> historic) {
        super(wrappee.getName(), wrappee.getAction(), wrappee.getParentStateGraph());
        this.wrappee = wrappee;
        this.historic = historic;
    }

    @Override
    public boolean run(T input, boolean debug, int i) {
        String entrada = input.toString();
        long start = System.nanoTime();
        boolean result = wrappee.run(input, debug, i);
        long end = System.nanoTime();
        double elapsedMs = (end - start) / 1_000_000.0;
        historic.add("[" + getName() + " with: " + entrada + "] " + String.format("%.4f", elapsedMs) + " ms");
        return result;
    }
    
    public List<String> history() {
        return java.util.Collections.unmodifiableList(historic);
    }

    @Override 
    public String toString(){
        return super.toString();
    }
}