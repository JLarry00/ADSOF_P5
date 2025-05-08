package src;

import java.util.List;
import src.data.*;
import src.decorate.*;
import src.graph.*;

/**
 * Clase principal que ejecuta los ejercicios.
 * 
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class Main {

    /**
     * Método principal que ejecuta los ejercicios.
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        print("Ejercicio 1");
        mainEj1(args);
        print("Ejercicio 2");
        mainEj2(args);
        print("Ejercicio 3");
        mainEj3(args);
        print("Ejercicio 4");
        mainEj4(args);
        print("Ejercicio 5");
        mainEj5(args);
    }

    /*
     * Ejercicio 5
     */
    /**
     * Método principal que ejecuta el ejercicio 5.
     * @param args Los argumentos de la línea de comandos.
     */
    public static void mainEj5(String[] args) {
        StateGraph<NumericData> g = new StateGraph<>("loop-down", "Get a number, and decrease if positive");
        StateGraphLogger<NumericData> lg = new StateGraphLogger<>(g, "traces.txt");
        StateGraphProfiler<NumericData> sg = new StateGraphProfiler<>(lg);

        sg.addNode("decrease", (NumericData mo) -> mo.put("op1", mo.get("op1") - 1));
        sg.addConditionalEdge("decrease", "decrease", (NumericData mo) -> mo.get("op1") > 0);
        sg.setInitial("decrease");

        NumericData input = new NumericData(3, 0);
        System.out.println(sg + "\ninput = " + input);
        NumericData output = sg.run(input, true);
        System.out.println("result = " + output);
        System.out.println("history = " + sg.history());
    }

    /*
     * Ejercicio 4
     */
    /**
     * Método principal que ejecuta el ejercicio 4.
     * @param args Los argumentos de la línea de comandos.
     */
    public static void mainEj4(String[] args) {
        StreamingStateGraph<DoubleData> sg = buildWorkflowEj4(); // el método construye el workflow
        System.out.println(sg);
        List
          .of(1, 5, 2, 4)
          .forEach( d->{ DoubleData wfInput = new DoubleData(d, 0);
            System.out.println("Workflow input = "+wfInput);
            sg.run(wfInput, true);
          });
        System.out.println("History="+sg.history());
    }

    /**
     * Método que construye el workflow del ejercicio 4.
     * @return El workflow del ejercicio 4.
     */
    private static StreamingStateGraph<DoubleData> buildWorkflowEj4() {
        StreamingStateGraph<DoubleData> sg = new StreamingStateGraph<>("average", "Calculates the average of an incoming data");

        sg.addNode("average", (DoubleData mo) -> {
            double sum = mo.getValue();
            for (DoubleData nd: sg.history()) {
                sum += nd.getValue();
            }
            double average = sum / (sg.history().size() + 1);
            mo.setAvg(average);
        });

        sg.setInitial("average");

        return sg;
    }

    /*
     * Ejercicio 3
     */
    /**
     * Método principal que ejecuta el ejercicio 3.
     * @param args Los argumentos de la línea de comandos.
     */
    private static void mainEj3(String[] args) {
        StateGraph<NumericData> wfNumeric = buildWorkflowEj2();
        StateGraph<StringData> sg = buildWorkflowEj3(wfNumeric);
        System.out.println(sg);

        StringData input = new StringData("jamon", 2);
        System.out.println("input = " + input);
        StringData output = sg.run(input, true); // ejecución con debug
        System.out.println("result = " + output);
    }

    /**
     * Método que construye el workflow del ejercicio 3.
     * @param wfNumeric El workflow del ejercicio 2.
     * @return El workflow del ejercicio 3.
     */
    public static StateGraph<StringData> buildWorkflowEj3(StateGraph<NumericData> wfNumeric) {
        StateGraph<StringData> sg = new StateGraph<>("replicate", "Replicates a given word");

        sg.addWfNode("calculate", wfNumeric)
          .withInjector((StringData sd) -> sd.toNumericData())
          .withExtractor((NumericData nd, StringData sd) -> sd.setTimes(nd.get("result")));

        sg.addNode("replicate", sd -> sd.replicate());

        sg.addEdge("calculate", "replicate");
        sg.addConditionalEdge("replicate", "replicate", sd -> sd.times() > 0);

        sg.setInitial("calculate");

        return sg;
    }

    /*
     * Ejercicio 2
     */
    /**
     * Método principal que ejecuta el ejercicio 2.
     * @param args Los argumentos de la línea de comandos.
     */
    private static void mainEj2(String[] args) {
        StateGraph<NumericData> sg = buildWorkflowEj2();
        System.out.println(sg);

        NumericData input = new NumericData(2, 3);
        System.out.println("input = " + input);
        NumericData output = sg.run(input, true); // ejecución con debug
        System.out.println("result = " + output);

        NumericData input2 = new NumericData(2, 2);
        System.out.println("input = " + input2);
        NumericData output2 = sg.run(input2, true); // ejecución con debug
        System.out.println("result = " + output2);
    }

    /**
     * Método que construye el workflow del ejercicio 2.
     * @return El workflow del ejercicio 2.
     */
    private static StateGraph<NumericData> buildWorkflowEj2() {
        StateGraph<NumericData> sg = new StateGraph<>("math2", "Add two numbers, and then square if the sum is even");

        sg.addNode("sum", (NumericData mo) -> mo.put("result", mo.get("op1") + mo.get("op2")))
          .addNode("square", (NumericData mo) -> mo.put("result", mo.get("result") * mo.get("result")));

        sg.addConditionalEdge("sum", "square", (NumericData mo) -> mo.get("result") % 2 == 0);

        sg.setInitial("sum");
        sg.setFinal("square");

        return sg;
    }

    /*
     * Ejercicio 1
     */
    /**
     * Método principal que ejecuta el ejercicio 1.
     * @param args Los argumentos de la línea de comandos.
     */
    private static void mainEj1(String[] args) {
        StateGraph<NumericData> sg = buildWorkflowEj1();

        System.out.println(sg);

        NumericData input = new NumericData(2, 3);
        System.out.println("input = " + input);
        NumericData output = sg.run(input, true); // ejecución con debug
        System.out.println("result = " + output);
    }

    /**
     * Método que construye el workflow del ejercicio 1.
     * @return El workflow del ejercicio 1.
     */
    private static StateGraph<NumericData> buildWorkflowEj1() {
        StateGraph<NumericData> sg = new StateGraph<>("math2", "Add two numbers, and then square");

        sg.addNode("sum", (NumericData mo) -> mo.put("result", mo.get("op1") + mo.get("op2")))
          .addNode("square", (NumericData mo) -> mo.put("result", mo.get("result") * mo.get("result")));

        sg.addEdge("sum", "square");
        
        sg.setInitial("sum");
        sg.setFinal("square");

        return sg;
    }

    /**
     * Método que imprime un mensaje.
     * @param message El mensaje a imprimir.
     */
    private static void print(String message) {
        System.out.println("-------------------------------------------------");
        System.out.println(message);
        System.out.println("-------------------------------------------------");
    }
}