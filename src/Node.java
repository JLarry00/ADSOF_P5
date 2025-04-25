package src;

import java.util.function.Function;

public class Node<T extends Data<Object>, R extends Data<Object>> {
    private String name;
    private Function<T,R> action;

    public Node(String name, Function<T,R> action) {
        this.name = name;
        this.action = action;
    }

    public R run(T input) {
        return action.apply(input);
    }

    public String getName() {
        return name;
    }
}