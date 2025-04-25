package src;

import java.util.*;

public abstract class Data<T extends Object> extends LinkedHashMap<String, T> {
    public Data(T op1, T op2) {
        this.put("op1", op1);
        this.put("op2", op2);
    }
}