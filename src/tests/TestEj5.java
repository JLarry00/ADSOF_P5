package src.tests;

import src.data.*;

import java.time.*;

/**
 * Clase que contiene las pruebas del ejercicio 5.
 * Esta clase implementa tests para probar diferentes aspectos
 * de los grafos de estado de streaming con datos de caracteres y cadenas.
 *
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class TestEj5 {
    /**
     * Método principal que ejecuta la prueba del ejercicio 5.
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        NumericData input = new NumericData(0, 0);

        StateGraphLoop<NumericData> loop = new StateGraphLoop<>("loop-down", "Get a number, and decrease if positive", 10);
        StateGraphTimer<NumericData> g = new StateGraphTimer<>(loop, Duration.ofMillis(5000));

        loop.addNode("increase", (NumericData mo) -> {
            mo.put("op1", mo.get("op1") + 1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted, failed to complete operation");
            }
        });
        
        g.setInitial("increase");
        
        System.out.println(g + "\ninput = " + input);
        NumericData output = g.run(input, true);
        System.out.println("result = " + output);
    }
}