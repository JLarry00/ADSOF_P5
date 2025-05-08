package src.tests;

import src.graph.*;

public class TestEj1 {

    public static void main(String[] args) {
        print("Test: CharacterData");
        Test1(args);
        print("Test: No nodes");
        Test2(args);
        print("Test: Tree");
        Test3(args);
        print("Test: Simple");
        Test4(args);
    }

    private static void Test1(String[] args) {
        StateGraph<CharacterData> sg = buildWorkflowTest1();

        System.out.println(sg);

        CharacterData input = new CharacterData('a');
        System.out.println("input = " + input);
        CharacterData output = sg.run(input, true); // ejecución con debug
        System.out.println("result = " + output);
    }

    private static void Test2(String[] args) {
        StateGraph<CharacterData> sg = buildWorkflowTest2();

        System.out.println(sg);

        CharacterData input = new CharacterData('a');
        System.out.println("input = " + input);
        CharacterData output = sg.run(input, true); // ejecución con debug
        System.out.println("result = " + output);
    }

    private static void Test3(String[] args) {
        StateGraph<CharacterData> sg = new StateGraph<>("tree", "");

        sg.addNode("1", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 1);}))
          .addNode("2", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 1);}))
          .addNode("3", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 1);}))
          .addNode("4", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 1);}))
          .addNode("5", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 1);}))
          .addNode("6", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 1);}))
          .addNode("7", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 1);}))
          .addNode("8", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 1);}))
          .addNode("9", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 1);}))
          .addNode("10", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 1);}));

        sg.addEdge("1", "2");
        sg.addEdge("2", "3");
        sg.addEdge("2", "4");
        sg.addEdge("2", "5");
        sg.addEdge("3", "6");
        sg.addEdge("4", "7");
        sg.addEdge("5", "8");
        sg.addEdge("6", "9");
        sg.addEdge("7", "10");
        sg.addEdge("8", "10");
        sg.addEdge("9", "10");

        sg.setInitial("1");

        System.out.println(sg);

        CharacterData input = new CharacterData('a');
        System.out.println("input = " + input);
        CharacterData output = sg.run(input, true); // ejecución con debug
        System.out.println("result = " + output);
    }

    private static void Test4(String[] args) {
        StateGraph<CharacterData> sg = new StateGraph<>("simple", "");

        sg.addNode("start", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 1);}))
          .addNode("middle", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 2);}))
          .addNode("end", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {return (char) (c + 3);}));

        sg.addEdge("start", "middle");
        sg.addEdge("middle", "end");

        sg.setInitial("start");
        sg.setFinal("middle");

        System.out.println(sg);

        CharacterData input = new CharacterData('b');
        System.out.println("input = " + input);
        CharacterData output = sg.run(input, true); // ejecución con debug
        System.out.println("result = " + output);
    }

    private static StateGraph<CharacterData> buildWorkflowTest1() {
        StateGraph<CharacterData> sg = new StateGraph<>("coder", "");

        sg.addNode("encrypt easy", (CharacterData mo) -> mo.encryptChar(c -> {
            char encrypted = (char) (c + 1);
            if (encrypted > 'z' || encrypted < 'a') encrypted = (char) ((encrypted % 26) + 97);
            return encrypted;
        }))
        .addNode("replace", (CharacterData mo) -> mo.encryptAndReplaceChar(c -> {
            return mo.getEncrypted();
        }))
        .addNode("encrypt hard", (CharacterData mo) -> mo.encryptChar(c -> {
            char encrypted = (char) (Math.pow(c, 4) % 256);
            if (encrypted > 'z' || encrypted < 'a') encrypted = (char) ((encrypted % 26) + 97);
            return encrypted;
        }));

        sg.addEdge("encrypt easy", "replace");
        sg.addEdge("replace", "encrypt hard");

        sg.setInitial("encrypt easy");
        sg.setFinal("encrypt hard");

        return sg;
    }

    

    private static StateGraph<CharacterData> buildWorkflowTest2() {
        StateGraph<CharacterData> sg = new StateGraph<>("coder", "");
        
        return sg;
    }

    private static void print(String message) {
        System.out.println("-------------------------------------------------");
        System.out.println(message);
        System.out.println("-------------------------------------------------");
    }
}