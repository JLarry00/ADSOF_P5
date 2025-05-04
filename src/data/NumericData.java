package src.data;

public class NumericData {
    private int op1;    
    private int op2;
    private int result;

    public NumericData(int op1, int op2) {
        this.op1 = op1;
        this.op2 = op2;
        this.result = 0;
    }

    public int getOp1() {
        return op1;
    }

    public void setOp1(int op1) {
        this.op1 = op1;
    }

    public int getOp2() {
        return op2;
    }

    public void setOp2(int op2) {
        this.op2 = op2;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "{op1=" + op1 + ", op2=" + op2 + ", result=" + result + "}";
    }
}