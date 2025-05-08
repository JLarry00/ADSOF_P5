package src.tests;

import java.util.LinkedHashMap;

/**
 * Clase que representa los datos de una contraseña, incluyendo la respuesta correcta
 * y el estado de verificación.
 * Esta clase es útil para manejar y verificar contraseñas.
 *
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class PasswordData extends LinkedHashMap<String, String> {
    /** Indica si la contraseña es correcta */
    private boolean isCorrect;

    /**
     * Constructor de la clase PasswordData.
     * @param word La contraseña inicial.
     */
    public PasswordData(String word) {
        this.put("answer", word);
        this.put("result", "");
        this.isCorrect = false;
    }

    /**
     * Verifica si la contraseña es correcta.
     * @return true si la contraseña es correcta, false en caso contrario.
     */
    public boolean isCorrect() {
        return this.isCorrect;
    }

    /**
     * Establece si la contraseña es correcta.
     * @param isCorrect true si la contraseña es correcta, false en caso contrario.
     */
    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    /**
     * Devuelve una representación en cadena del objeto PasswordData.
     * @return Una cadena que representa la contraseña y su estado de verificación.
     */
    @Override
    public String toString() {
        return "answer: " + (isCorrect ? this.get("answer") : "-") + ", isCorrect: " + this.isCorrect;
    }
}