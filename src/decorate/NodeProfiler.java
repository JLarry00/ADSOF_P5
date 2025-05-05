package src.decorate;
import src.graph.Node;

public class NodeProfiler<T, R> extends NodeDecorator<T, R> {
    private static final double NANOSECONDS_TO_SECONDS = 1000000000.0;
    private StateGraphProfiler<T> stateGraphProfiler;

    public NodeProfiler(Node<T, R> wrappee, StateGraphProfiler<T> stateGraphProfiler) {
        super(wrappee);
        this.stateGraphProfiler = stateGraphProfiler;
    }

    @Override
    public boolean run(T input, boolean debug, int i) {
        String entrada = input.toString();
        long startTime = System.nanoTime();
        boolean result = super.run(input, debug, i);
        long endTime = System.nanoTime();
        double elapsedMs = (endTime - startTime) / NANOSECONDS_TO_SECONDS;
        stateGraphProfiler.addHistory("[" + getName() + " with: " + entrada + "] " + String.format("%.4f", elapsedMs) + " s");
        return result;
    }

    @Override 
    public String toString(){
        return super.toString() + " [profiled]";
    }
}