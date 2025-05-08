package src.tests;

import java.util.LinkedHashMap;

public class PasswordData extends LinkedHashMap<String, String> {
    private boolean isCorrect;

    public PasswordData(String word) {
        this.put("answer", word);
        this.put("result", "");
        this.isCorrect = false;
    }

    public boolean isCorrect() {
        return this.isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "answer: " + (isCorrect ? this.get("answer") : "-") + ", isCorrect: " + this.isCorrect;
    }
}