package src.data;

import src.tests.*;

/**
 * Clase que representa un par de valores numéricos: un valor principal y su promedio.
 * Esta clase es útil para almacenar y formatear datos numéricos con su valor promedio asociado.
 *
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class StringData {
    /** El número de veces que se debe repetir la palabra */
    private int times;
    /** La palabra a repetir */
    private String word;
    /** El resultado de la repetición */
    private String result;

    /**
     * Constructor de la clase StringData.
     * @param word La palabra a repetir.
     * @param times El número de veces que se debe repetir la palabra.
     */
    public StringData(String word, int times) {
        if (times < 0) throw new IllegalArgumentException("Times must be equal to or greater than 0");
        this.word = word;
        this.times = times;
        this.result = "";
    }

    /**
     * Repite la palabra el número de veces especificado y devuelve el resultado.
     * @return El resultado de repetir la palabra.
     */
    public String replicate() {
        result += word;
        times--;
        return result;
    }

    /**
     * Obtiene el resultado de repetir la palabra.
     * @return El resultado de repetir la palabra.
     */
    public String getResult() {
        return this.result;
    }

    /**
     * Obtiene el número de veces que se repite la palabra.
     * @return El número de veces que se repite la palabra.
     */
    public int times() {
        return this.times;
    }

    /**
     * Obtiene la palabra a repetir.
     * @return La palabra a repetir.
     */
    public String getWord() {
        return this.word;
    }

    /**
     * Establece la palabra a repetir.
     * @param word La nueva palabra a repetir.
     * @return El objeto StringData actualizado.
     */
    public StringData setWord(String word) {
        this.word = word;
        return this;
    }

    /**
     * Concatena un carácter a la palabra.
     * @param c El carácter a concatenar.
     * @return El objeto StringData actualizado.
     */
    public StringData concat(char c) {
        this.word += c;
        return this;
    }

    /**
     * Convierte el objeto StringData en un objeto NumericData.
     * @return El objeto NumericData resultante.
     */
    public NumericData toNumericData() {
        NumericData nd = new NumericData(times, 0);
        nd.put("result", word.length());
        return nd;
    }

    /**
     * Convierte el objeto StringData en un objeto CharacterData.
     * @return El objeto CharacterData resultante.
     */
    public CharacterData toCharacterData() {
        if (word.length() == 0) return new CharacterData('_');
        CharacterData cd = new CharacterData(word.charAt(0));
        cd.setWord(word);
        return cd;
    }

    /**
     * Establece el número de veces que se repite la palabra.
     * @param times El nuevo número de veces que se repite la palabra.
     * @return El objeto StringData actualizado.
     */
    public StringData setTimes(int times) {
        this.times = times;
        return this;
    }

    /**
     * Devuelve una representación en cadena del objeto StringData.
     * @return Una cadena que representa la palabra, el número de veces que se repite y el resultado.
     */
    @Override
    public String toString() {
        return "word: " + word + ", times: " + times + ", result: " + result;
    }
}