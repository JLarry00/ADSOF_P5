package src.tests;

import java.util.function.*;

import src.graph.*;
import src.decorate.*;

/**
 * Clase que extiende un grafo de estado para permitir su ejecución múltiple.
 * Esta clase permite ejecutar el grafo un número específico de veces,
 * manteniendo el mismo input entre ejecuciones.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class StateGraphLoop<T> extends StateGraph<T> {
    /** El número de veces que se ejecutará el grafo de estado */
    private int times;

    /**
     * Constructor de la clase StateGraphLoop.
     * @param name El nombre del grafo de estado.
     * @param description La descripción del grafo de estado.
     * @param times El número de veces que se ejecutará el grafo.
     */
    public StateGraphLoop(String name, String description, int times) {
        super(name, description);
        this.times = times;
    }

    /**
     * Ejecuta el grafo de estado el número de veces especificado.
     * @param input El input para la ejecución.
     * @param debug true si se debe ejecutar en modo debug, false en caso contrario.
     * @return El resultado de la última ejecución.
     */
    @Override
    public T run(T input, boolean debug) {
        for (int i = 0; i < times; i++) {
            System.out.println("Running loop " + (i + 1) + " of " + times);
            super.run(input, debug);
        }
        return input;
    }

    /**
     * Obtiene el número de veces que se ejecutará el grafo.
     * @return El número de ejecuciones configurado.
     */
    public int getTimes() {
        return times;
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
     * Devuelve una representación en cadena del grafo de estado.
     * @return Una cadena que representa el grafo de estado.
     */
    @Override 
    public String toString(){
        return super.toString();
    }
}