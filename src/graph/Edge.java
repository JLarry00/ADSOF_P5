package src.graph;

/**
 * Clase que representa una arista en un grafo.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class Edge {
    /** El nodo de origen de la arista */
    private String from;
    /** El nodo de destino de la arista */
    private String to;

    /**
     * Constructor de la clase Edge.
     * @param from El nodo de origen de la arista.
     * @param to El nodo de destino de la arista.
     */
    public Edge(String from, String to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("From and to cannot be null");
        }
        this.from = from;
        this.to = to;
    }

    /**
     * Obtiene el nodo de origen de la arista.
     * @return El nodo de origen de la arista.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Obtiene el nodo de destino de la arista.
     * @return El nodo de destino de la arista.
     */
    public String getTo() {
        return to;
    }
}