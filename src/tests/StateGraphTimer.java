package src.tests;

import java.util.*;
import java.util.function.*;
import java.time.*;

import src.decorate.*;

/**
 * Decorador que añade funcionalidad de temporizador a un grafo de estado.
 * Esta clase permite establecer un tiempo máximo de ejecución para el grafo,
 * terminando la ejecución si se excede el límite.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */     
public class StateGraphTimer<T> extends BaseDecorator<T> {
    /** El tiempo máximo de ejecución permitido */
    private Duration time;

    /**
     * Constructor de la clase StateGraphTimer.
     * @param wrappee El grafo de estado a decorar.
     * @param time La duración máxima permitida para la ejecución.
     */
    public StateGraphTimer(InterfaceStateGraph<T> wrappee, Duration time) {
        super(wrappee);
        this.time = time;
    }

    /**
     * Añade un nodo al grafo de estado.
     * @param name El nombre del nodo a añadir.
     * @param action La acción a realizar en el nodo.
     * @return El grafo de estado actualizado.
     */
    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        super.addNode(name, action);
        return this;
    }

    /**
     * Ejecuta el grafo de estado con un límite de tiempo.
     * Si la ejecución excede el tiempo máximo establecido,
     * el programa se termina automáticamente.
     * 
     * @param input El input para la ejecución.
     * @param debug true si se debe ejecutar en modo debug, false en caso contrario.
     * @return El resultado de la ejecución.
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
     * @return Una cadena que representa el grafo de estado.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}