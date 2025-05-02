package src.data;

public class StringData {
    private int times;
    private String word;
    private String result;

    public StringData(String word, int times) {
        if (times < 0) throw new IllegalArgumentException("Times must be equal to or greater than 0");
        this.word = word;
        this.times = times;
        this.result = "";
    }

    public String replicate() {
        result += word;
        times--;
        return result;
    }

    public String getResult() {
        return this.result;
    }

    public int times() {
        return this.times;
    }

    public NumericData toNumericData() {
        return new NumericData(times, 0);
    }

    public StringData setTimes(int times) {
        this.times = times;
        return this;
    }

    @Override
    public String toString() {
        return "word: " + word + ", times: " + times + ", result: " + result;
    }
}