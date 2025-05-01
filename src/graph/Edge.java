package src.graph;

public class Edge {
    private String from;
    private String to;

    public Edge(String from, String to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("From and to cannot be null");
        }
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}