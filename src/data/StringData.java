package src.data;

import java.util.LinkedHashMap;

public class StringData extends LinkedHashMap<String, String> {
    public StringData(String op1, String op2) {
        this.put("op1", op1);
        this.put("op2", op2);
        this.put("result", "");
    }
}