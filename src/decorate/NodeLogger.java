package src.decorate;

import src.graph.*;

/**
 * Decorador que añade funcionalidad de logging a un nodo.
 * Esta clase registra la ejecución de un nodo, incluyendo timestamp,
 * nombre del nodo y el resultado de la ejecución.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class NodeLogger<T, R> extends NodeDecorator<T, R> {
    /** El logger del grafo de estado asociado */
    private StateGraphLogger<T> stateGraphLogger;

    /**
     * Constructor de la clase NodeLogger.
     * @param wrappee El nodo a decorar.
     * @param stateGraphLogger El logger del grafo de estado.
     */
    public NodeLogger(Node<T, R> wrappee, StateGraphLogger<T> stateGraphLogger) {
        super(wrappee);
        this.stateGraphLogger = stateGraphLogger;
    }

    /**
     * Ejecuta el nodo y registra la ejecución.
     * @param input El input para la ejecución.
     * @param debug true si se debe ejecutar en modo debug, false en caso contrario.
     * @param i El índice de ejecución.
     * @return true si la ejecución fue exitosa, false en caso contrario.
     */
    @Override
    public boolean run(T input, boolean debug, int i) {
        String inputString = input.toString();
        String str = "node " + getName() + " executed,";
        String timestamp = new java.text.SimpleDateFormat("[dd/MM/yyyy - HH:mm:ss]").format(new java.util.Date());
        str = timestamp + " " + str;
        str = str + " with output: " + inputString;
        boolean result = super.run(input, debug, i);
        if (i > 2) stateGraphLogger.addRegister(str);

        return result;
    }

    /**
     * Devuelve una representación en cadena del nodo con logging.
     * @return Una cadena que representa el nodo con indicación de logging.
     */
    @Override
    public String toString(){
        return super.toString() + " [logged]";
    }
}