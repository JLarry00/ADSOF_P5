package src.data;

public class DoubleData {
    private double value;
    private double avg;

    public DoubleData(double value, double avg) {
        this.value = value;
        this.avg = avg;
    }

    public double getValue() {
        return value;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return String.format("%01.1f", value) + " (avg.= " + String.format("%01.3f", avg) + ')';
    }
}