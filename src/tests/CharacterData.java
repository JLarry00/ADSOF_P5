package src.tests;

import java.util.function.*;
import src.data.*;

public class CharacterData {
    private Character original;
    private int asciiValue;
    private Character encrypted;
    private int encryptedAsciiValue;
    private String word;

    public CharacterData(Character value) {
        this.original = value;
        this.asciiValue = (int) value;
        this.encrypted = null;
        this.encryptedAsciiValue = 0;
        this.word = "";
    }

    public CharacterData encryptChar(Function<Character, Character> encryptFunction) {
        this.encrypted = encryptFunction.apply(original);
        this.encryptedAsciiValue = (int) encrypted;
        return this;
    }

    public CharacterData encryptAndReplaceChar(Function<Character, Character> replaceFunction) {
        encryptChar(replaceFunction);
        this.original = encrypted;
        this.asciiValue = encryptedAsciiValue;
        return this;
    }

    public Character getOriginal() {
        return original;
    }

    public int getAsciiValue() {
        return asciiValue;
    }

    public Character getEncrypted() {
        return encrypted;
    }

    public int getEncryptedAsciiValue() {
        return encryptedAsciiValue;
    }

    public String getWord() {
        return word;
    }

    public CharacterData setWord(String word) {
        this.word = word;
        return this;
    }

    public NumericData toNumericData() {
        return new NumericData(asciiValue, 0);
    }

    public StringData toStringData() {
        return new StringData(word, 1);
    }

    public PasswordData toPasswordData() {
        return new PasswordData(original.toString());
    }

    public DoubleData toDoubleData() {
        return new DoubleData((double) asciiValue, 0.0);
    }

    @Override
    public String toString() {
        return "Original: " + (original==null?"none":original) + " (ASCII: " + asciiValue + "), Encrypted: " + (encrypted==null?"none":encrypted) + " (ASCII: " + encryptedAsciiValue + "), Word: " + word;
    }
}