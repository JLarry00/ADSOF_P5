package src.data;

import java.util.function.*;

public class CharacterData {
    private Character original;
    private int asciiValue;
    private Character encrypted;
    private int encryptedAsciiValue;

    public CharacterData(Character value) {
        this.original = value;
        this.asciiValue = (int) value;
        this.encrypted = null;
        this.encryptedAsciiValue = 0;
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

    @Override
    public String toString() {
        return "Original: " + (original==null?"none":original) + " (ASCII: " + asciiValue + "), Encrypted: " + (encrypted==null?"none":encrypted) + " (ASCII: " + encryptedAsciiValue + ")";
    }
}