package src.data;

import src.tests.*;

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

    public String getWord() {
        return this.word;
    }

    public StringData setWord(String word) {
        this.word = word;
        return this;
    }

    public StringData concat(char c) {
        this.word += c;
        return this;
    }

    public NumericData toNumericData() {
        NumericData nd = new NumericData(times, 0);
        nd.put("result", word.length());
        return nd;
    }

    public CharacterData toCharacterData() {
        if (word.length() == 0) return new CharacterData('_');
        CharacterData cd = new CharacterData(word.charAt(0));
        cd.setWord(word);
        return cd;
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