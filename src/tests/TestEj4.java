package src.tests;

import src.data.*;
import src.graph.*;

import java.util.Scanner;

/**
 * Clase que contiene las pruebas del ejercicio 4.
 * Esta clase implementa tests para probar diferentes aspectos
 * de los grafos de estado de streaming con datos de caracteres y cadenas.
 *
 * @author Juan Larrondo y Abril Palanco
 * @version 1.0
 */
public class TestEj4 {
    
    /**
     * Método principal que ejecuta la prueba del ejercicio 4.
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        StreamingStateGraph<NumericData> sgc = charCounter();

        System.out.println(sgc);

        NumericData input;
        do {
            input = new NumericData(0, 0);
            System.out.println("Workflow input = " + input);
            input = sgc.run(input, true);
            System.out.println("Workflow output = " + input);
        } while (input.get("result") != 0);
        System.out.println("History="+sgc.history());
    }

    /**
     * Construye un flujo de trabajo para contar caracteres en una palabra.
     * @return El grafo de estado de streaming configurado.
     */
    private static StreamingStateGraph<NumericData> charCounter() {
        StreamingStateGraph<NumericData> sg = new StreamingStateGraph<>("char counter", "A workflow to count the number of characters in a word.");
        StreamingStateGraph<StringData> sgs = askWord();

        sg.addWfNode(sgs.getName(), sgs)
        .withInjector((NumericData mo) -> mo.toStringData())
        .withExtractor((StringData mo, NumericData mo2) -> {
            mo2.put("result", mo.getWord().length());
            return mo2;
        });

        sg.setInitial(sgs.getName());

        return sg;
    }

    /**
     * Construye un flujo de trabajo para solicitar una palabra al usuario.
     * @return El grafo de estado de streaming configurado.
     */

    private static StreamingStateGraph<StringData> askWord() {
        StreamingStateGraph<StringData> sg = new StreamingStateGraph<>("ask word", "A workflow to get a word from the user.");
        StateGraph<CharacterData> sgc = wordGetter();

        sg.addNode("ask word", (StringData mo) -> {
            mo.setWord("");
            System.out.println("Enter a word (finish with \"/\"): ");
        })
        .addWfNode(sgc.getName(), sgc)
        .withInjector((StringData mo) -> mo.toCharacterData())
        .withExtractor((CharacterData mo, StringData mo2) -> mo2.setWord(mo.getWord()));

        sg.addNode("end", (StringData mo) -> {
            mo.setWord(mo.getWord().substring(0, mo.getWord().length() - 1));
        });

        sg.addEdge("ask word", sgc.getName());
        sg.addEdge(sgc.getName(), "end");

        sg.setInitial("ask word");
        sg.setFinal("end");

        return sg;
    }

    /**
     * Construye un flujo de trabajo para obtener caracteres del usuario.
     * @return El grafo de estado configurado.
     */
    
    private static StateGraph<CharacterData> wordGetter() {
        StateGraph<CharacterData> sg = new StateGraph<>("word getter", "A workflow to get a word from the user.");

        sg.addNode("ask letter", (CharacterData mo) -> {
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            if (userInput.length() > 0) {
                mo.encryptAndReplaceChar(c -> userInput.charAt(0));
                mo.setWord(mo.getWord() + userInput.charAt(0));
            }
        })
        .addNode("end", (CharacterData mo) -> {});

        sg.addConditionalEdge("ask letter", "ask letter", (CharacterData mo) -> mo.getOriginal() != '/');
        sg.addEdge("ask letter", "end");

        sg.setInitial("ask letter");
        sg.setFinal("end");

        return sg;
    }
}