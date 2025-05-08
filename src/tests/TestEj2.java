package src.tests;

import src.data.*;
import src.graph.*;

import java.util.Random;
import java.util.Scanner;

public class TestEj2 {

    public static void main(String[] args) {
        print("Test: Decrement");
        Test1(args);
        print("Test: Riddle Solver");
        Test2(args);
    }

    private static void Test1(String[] args) {
        StateGraph<NumericData> sg = buildWorkflowTest1();

        System.out.println(sg);

        NumericData input = new NumericData(0, 0);
        System.out.println("input = " + input);
        NumericData output = sg.run(input, true); // ejecución con debug
        System.out.println("result = " + output);
    }

    private static StateGraph<NumericData> buildWorkflowTest1() {
        StateGraph<NumericData> sg = new StateGraph<>("randomReducer", "A workflow to reduce a random number, in steps of 1, to zero.");

        sg.addNode("generate random", (NumericData mo) -> {
            Random random = new Random();
            mo.put("result", random.nextInt(100)); // Generates a random integer between 0 and 99
        })
        .addNode("reduce to zero", (NumericData mo) -> {
            mo.put("result", mo.get("result") - 1); // Reduces the value by 1
        });

        sg.addEdge("generate random", "reduce to zero");
        sg.addConditionalEdge("reduce to zero", "reduce to zero", (NumericData mo) -> mo.get("result") > 0);

        sg.setInitial("generate random");

        return sg;
    }
    
    private static void Test2(String[] args) {
        StateGraph<PasswordData> sg = new StateGraph<>("riddleSolver", "A workflow to solve a riddle by checking user input against a predefined answer.");

        sg.addNode("ask riddle", (PasswordData mo) -> {
            System.out.println("What has keys but can't open locks?");
        })
        .addNode("check answer", (PasswordData mo) -> {
            // Simulate user input for the answer
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your answer: ");
            String userAnswer = scanner.nextLine();
            if (mo.get("answer").equalsIgnoreCase(userAnswer))
                mo.setCorrect(true);
            else
                mo.setCorrect(false);
            
            scanner.close();
        })
        .addNode("success message", (PasswordData mo) -> {
            System.out.println("WELL DONE!");
        })
        .addNode("failure message", (PasswordData mo) -> {
            System.out.println("WRONG!");
        })
        .addNode("end", (PasswordData mo) -> {
        System.out.println("Accessing protected information...");
        System.out.println("Protected Information: The treasure is buried under the old oak tree.");
        });

        sg.addEdge("ask riddle", "check answer");
        sg.addConditionalEdge("check answer", "success message", (PasswordData mo) -> mo.isCorrect());
        sg.addConditionalEdge("check answer", "failure message", (PasswordData mo) -> !mo.isCorrect());
        sg.addEdge("success message", "end");

        sg.setInitial("ask riddle");

        System.out.println(sg);

        PasswordData input = new PasswordData("piano");
        System.out.println("input = " + input);
        PasswordData output = sg.run(input, true); // ejecución con debug
        System.out.println("result = " + output);
    }

    private static void print(String message) {
        System.out.println("-------------------------------------------------");
        System.out.println(message);
        System.out.println("-------------------------------------------------");
    }
}