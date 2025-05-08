package src.data;

/**
 * Clase que representa un par de valores numéricos: un valor principal y su promedio.
 * Esta clase es útil para almacenar y formatear datos numéricos con su valor promedio asociado.
 *
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class DoubleData {
    /** El valor principal almacenado */
    private double value;
    /** El promedio asociado al valor principal */
    private double avg;

    /**
     * Constructor de la clase DoubleData.
     * @param value El valor principal a almacenar.
     * @param avg El promedio asociado al valor principal.
     */
    public DoubleData(double value, double avg) {
        this.value = value;
        this.avg = avg;
    }

    /**
     * Obtiene el valor principal almacenado.
     * @return El valor principal.
     */
    public double getValue() {
        return value;
    }

    /**
     * Obtiene el promedio asociado al valor principal.
     * @return El promedio.
     */
    public double getAvg() {
        return avg;
    }

    /**
     * Establece el promedio asociado al valor principal.
     * @param avg El nuevo promedio a establecer.
     */ 
    public void setAvg(double avg) {
        this.avg = avg;
    }

    /**
     * Devuelve una representación en cadena del objeto DoubleData.
     * @return Una cadena que representa el valor principal y su promedio.
     */
    @Override
    public String toString() {
        return String.format("%01.1f", value) + " (avg.= " + String.format("%01.3f", avg) + ')';
    }
}