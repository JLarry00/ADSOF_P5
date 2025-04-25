package src;

public class NumericData extends Data<Number> {
    public NumericData(Number op1, Number op2) {
        super(op1, op2);
        put("result", 0);
    }
}