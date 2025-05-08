package src.decorate;
import src.graph.Node;

/**
 * Decorador que añade funcionalidad de profiling a un nodo.
 * Esta clase mide y registra el tiempo de ejecución de un nodo,
 * incluyendo el nombre del nodo y el input utilizado.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class NodeProfiler<T, R> extends NodeDecorator<T, R> {
    /** Factor de conversión de nanosegundos a milisegundos */
    private static final double NANOSECONDS_TO_MILLISECONDS = 1000000.0;
    /** El profiler del grafo de estado asociado */
    private StateGraphProfiler<T> stateGraphProfiler;

    /**
     * Constructor de la clase NodeProfiler.
     * @param wrappee El nodo a decorar.
     * @param stateGraphProfiler El profiler del grafo de estado.
     */
    public NodeProfiler(Node<T, R> wrappee, StateGraphProfiler<T> stateGraphProfiler) {
        super(wrappee);
        this.stateGraphProfiler = stateGraphProfiler;
    }

    /**
     * Ejecuta el nodo y mide su tiempo de ejecución.
     * @param input El input para la ejecución.
     * @param debug true si se debe ejecutar en modo debug, false en caso contrario.
     * @param i El índice de ejecución.
     * @return true si la ejecución fue exitosa, false en caso contrario.
     */
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

    /**
     * Devuelve una representación en cadena del nodo con profiling.
     * @return Una cadena que representa el nodo con indicación de profiling.
     */
    @Override 
    public String toString(){
        return super.toString() + " [profiled]";
    }
}