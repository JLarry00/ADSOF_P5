package src.data;

/**
 * Clase que representa un par de valores numéricos: un valor principal y su promedio.
 * Esta clase es útil para almacenar y formatear datos numéricos con su valor promedio asociado.
 *
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
import java.util.LinkedHashMap;

/**
 * Clase que representa un par de valores numéricos: un valor principal y su promedio.
 * Esta clase es útil para almacenar y formatear datos numéricos con su valor promedio asociado.
 *
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class NumericData extends LinkedHashMap<String, Integer> {

    /**
     * Constructor de la clase NumericData.
     * @param op1 El primer valor numérico.
     * @param op2 El segundo valor numérico.
     */
    public NumericData(int op1, int op2) {
        this.put("op1", op1);
        this.put("op2", op2);
        this.put("result", 0);
    }

    /**
     * Convierte el objeto NumericData en un objeto StringData.
     * @return Un nuevo objeto StringData con los valores de op1 y op2.
     */
    public StringData toStringData() {
        return new StringData(get("result").toString(), get("op1") + get("op2"));
    }

    /**
     * Devuelve una representación en cadena del objeto NumericData.
     * @return Una cadena que representa el valor principal y su promedio.
     */
    @Override
    public String toString() {
        return "{op1=" + this.get("op1") + ", op2=" + this.get("op2") + ", result=" + this.get("result") + "}";
    }
}