package src.decorate;

import java.util.function.*;
import src.graph.*;
import java.util.*;
import java.io.*;

/**
 * Decorador que añade funcionalidad de logging a un grafo de estado.
 * Esta clase registra la ejecución de los nodos del grafo en un archivo,
 * incluyendo timestamp, nombre del nodo y el resultado de la ejecución.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class StateGraphLogger<T> extends BaseDecorator<T> {
    /** El nombre del archivo donde se guardarán los registros */
    private String fileName;
    /** La lista de registros de ejecución */
    private List<String> registers;

    /**
     * Constructor de la clase StateGraphLogger.
     * @param wrappee El grafo de estado a decorar.
     * @param fileName El nombre del archivo donde se guardarán los registros.
     */
    public StateGraphLogger(InterfaceStateGraph<T> wrappee, String fileName) {
        super(wrappee);
        this.fileName = fileName;
        this.registers = new ArrayList<String>();
    }

    /**
     * Obtiene la lista de registros de ejecución.
     * @return Una lista inmutable con los registros de ejecución.
     */
    public List<String> getRegisters() {
        return Collections.unmodifiableList(registers);
    }

    /**
     * Añade un nuevo registro de ejecución al principio de la lista.
     * @param register El registro a añadir.
     */
    public void addRegister(String register) {
        List<String> reverse = new ArrayList<>();
        reverse.add(register);
        reverse.addAll(registers);
        registers = reverse;
    }

    /**
     * Ejecuta el grafo de estado y registra las ejecuciones.
     * @param input El input para la ejecución.
     * @param debug true si se debe ejecutar en modo debug, false en caso contrario.
     * @return El resultado de la ejecución.
     */
    @Override
    public T run(T input, boolean debug) {
        T result = super.run(input, debug);
        registerExecutions();
        return result;
    }

    /**
     * Registra las ejecuciones en el archivo especificado.
     * Los registros se escriben en orden cronológico inverso.
     */
    public void registerExecutions() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            for (String register : registers) writer.println(register);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Añade un nodo al grafo de estado y lo decora con logging.
     * @param name El nombre del nodo a añadir.
     * @param action La acción a realizar en el nodo.
     * @return El grafo de estado actualizado.
     */
    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        super.addNode(name, action);
        Node<T, Object> node = super.getNode(name);
        node = new NodeLogger<>(node, this);
        super.replaceNode(node);
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
