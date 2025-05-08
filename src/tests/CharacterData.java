package src.tests;

import java.util.function.*;
import src.data.*;

/**
 * Clase que representa los datos de un carácter, incluyendo su valor original,
 * valor ASCII, versión encriptada y palabra asociada.
 * Esta clase es útil para manejar y transformar caracteres individuales.
 *
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class CharacterData {
    /** El carácter original */
    private Character original;
    /** El valor ASCII del carácter original */
    private int asciiValue;
    /** El carácter encriptado */
    private Character encrypted;
    /** El valor ASCII del carácter encriptado */
    private int encryptedAsciiValue;
    /** La palabra asociada al carácter */
    private String word;

    /**
     * Constructor de la clase CharacterData.
     * @param value El carácter inicial.
     */
    public CharacterData(Character value) {
        this.original = value;
        this.asciiValue = (int) value;
        this.encrypted = null;
        this.encryptedAsciiValue = 0;
        this.word = "";
    }

    /**
     * Encripta el carácter usando una función de encriptación.
     * @param encryptFunction La función de encriptación a aplicar.
     * @return El objeto CharacterData actualizado.
     */
    public CharacterData encryptChar(Function<Character, Character> encryptFunction) {
        this.encrypted = encryptFunction.apply(original);
        this.encryptedAsciiValue = (int) encrypted;
        return this;
    }

    /**
     * Encripta el carácter y reemplaza el original con el encriptado.
     * @param replaceFunction La función de reemplazo a aplicar.
     * @return El objeto CharacterData actualizado.
     */
    public CharacterData encryptAndReplaceChar(Function<Character, Character> replaceFunction) {
        encryptChar(replaceFunction);
        this.original = encrypted;
        this.asciiValue = encryptedAsciiValue;
        return this;
    }

    /**
     * Obtiene el carácter original.
     * @return El carácter original.
     */
    public Character getOriginal() {
        return original;
    }

    /**
     * Obtiene el valor ASCII del carácter original.
     * @return El valor ASCII.
     */
    public int getAsciiValue() {
        return asciiValue;
    }

    /**
     * Obtiene el carácter encriptado.
     * @return El carácter encriptado.
     */
    public Character getEncrypted() {
        return encrypted;
    }

    /**
     * Obtiene el valor ASCII del carácter encriptado.
     * @return El valor ASCII encriptado.
     */
    public int getEncryptedAsciiValue() {
        return encryptedAsciiValue;
    }

    /**
     * Obtiene la palabra asociada al carácter.
     * @return La palabra asociada.
     */
    public String getWord() {
        return word;
    }

    /**
     * Establece la palabra asociada al carácter.
     * @param word La nueva palabra a asociar.
     * @return El objeto CharacterData actualizado.
     */
    public CharacterData setWord(String word) {
        this.word = word;
        return this;
    }

    /**
     * Convierte el objeto CharacterData en un objeto NumericData.
     * @return Un nuevo objeto NumericData con el valor ASCII.
     */
    public NumericData toNumericData() {
        return new NumericData(asciiValue, 0);
    }

    /**
     * Convierte el objeto CharacterData en un objeto StringData.
     * @return Un nuevo objeto StringData con la palabra asociada.
     */
    public StringData toStringData() {
        return new StringData(word, 1);
    }

    /**
     * Convierte el objeto CharacterData en un objeto PasswordData.
     * @return Un nuevo objeto PasswordData con el carácter original.
     */
    public PasswordData toPasswordData() {
        return new PasswordData(original.toString());
    }

    /**
     * Convierte el objeto CharacterData en un objeto DoubleData.
     * @return Un nuevo objeto DoubleData con el valor ASCII.
     */
    public DoubleData toDoubleData() {
        return new DoubleData((double) asciiValue, 0.0);
    }

    /**
     * Devuelve una representación en cadena del objeto CharacterData.
     * @return Una cadena que representa el carácter original, su valor ASCII,
     * el carácter encriptado, su valor ASCII y la palabra asociada.
     */
    @Override
    public String toString() {
        return "Original: " + (original==null?"none":original) + " (ASCII: " + asciiValue + "), Encrypted: " + (encrypted==null?"none":encrypted) + " (ASCII: " + encryptedAsciiValue + "), Word: " + word;
    }
}