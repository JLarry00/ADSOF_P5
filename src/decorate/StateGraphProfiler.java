package src.decorate;
import java.util.ArrayList;
import java.util.List;

public class StateGraphProfiler<T> extends BaseDecorator<T> {
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
    public T run(T input, boolean debug) {
        String traceAux;
        traceAux = input.toString();
        startTime = System.nanoTime();
        T result = super.run(input, debug);
        endTime = System.nanoTime();
        
        double elapsedMs = (endTime - startTime) / NANOSECONDS_TO_SECONDS;
        String trace = "[decrease with: " + traceAux + String.format("%.4f", elapsedMs) + " ms"+ "] " ;
        historic.add(trace);
        return result;
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
}