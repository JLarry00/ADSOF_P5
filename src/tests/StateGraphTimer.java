package src.tests;

import java.util.*;
import java.util.function.*;
import java.time.*;

import src.decorate.*;

/**
 * Clase que representa un grafo de estado.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */     
public class StateGraphTimer<T> extends BaseDecorator<T> {
    /** El tiempo maximo de ejecucion */
    private Duration time;

    /**
     * Constructor de la clase StateGraph.
     * @param name El nombre del grafo de estado.
     * @param description La descripción del grafo de estado.
     */
    public StateGraphTimer(InterfaceStateGraph<T> wrappee, Duration time) {
        super(wrappee);
        this.time = time;
    }

    /**
     * Añade un nodo al grafo de estado.
     * @param name El nombre del nodo.
     * @param action La acción del nodo.
     * @return El grafo de estado.
     */
    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        super.addNode(name, action);
        return this;
    }

    /**
     * Ejecuta el grafo de estado.
     * @param input El input.
     * @param debug true si se debe ejecutar con debug, false en caso contrario.
     * @return El input.
     */
    @Override
    public T run(T input, boolean debug) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Execution time exceeded the limit. Terminating program.");
                System.exit(0);
            }
        }, time.toMillis());

        try {
            input = super.run(input, debug);
        } finally {
            timer.cancel();
        }
        return input;
    }

    /**
     * Devuelve una representación en cadena del grafo de estado.
     * @return Una representación en cadena del grafo de estado.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}