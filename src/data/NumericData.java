package src.data;

import java.util.LinkedHashMap;

public class NumericData extends LinkedHashMap<String, Integer> {
    public NumericData(int op1, int op2) {
        this.put("op1", op1);
        this.put("op2", op2);
        this.put("result", 0);
    }

    public StringData toStringData() {
        return new StringData(get("result").toString(), get("op1") + get("op2"));
    }

    @Override
    public String toString() {
        return "{op1=" + this.get("op1") + ", op2=" + this.get("op2") + ", result=" + this.get("result") + "}";
    }
}