package src.decorate;
import src.graph.Node;

public class NodeProfiler<T, R> extends NodeDecorator<T, R> {
    private static final double NANOSECONDS_TO_MILLISECONDS = 1000000.0;
    private StateGraphProfiler<T> stateGraphProfiler;

    public NodeProfiler(Node<T, R> wrappee, StateGraphProfiler<T> stateGraphProfiler) {
        super(wrappee);
        this.stateGraphProfiler = stateGraphProfiler;
    }

    @Override
    public boolean run(T input, boolean debug, int i) {
        boolean allowed = super.isEdgeAllowed(input);
        String entrada = input.toString();
        long startTime = System.nanoTime();
        boolean result = super.run(input, debug, i);
        long endTime = System.nanoTime();
        double elapsedMs = (endTime - startTime) / NANOSECONDS_TO_MILLISECONDS;
        if (allowed) stateGraphProfiler.addHistory("[" + getName() + " with: " + entrada + "] " + String.format("%.4f", elapsedMs) + " ms");
        return result;
    }

    @Override 
    public String toString(){
        return super.toString() + " [profiled]";
    }
}