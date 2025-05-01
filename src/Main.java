package src;

import src.data.*;
import src.graph.*;

public class Main {
    public static void main(String[] args) {
        StateGraph<NumericData> sg = buildWorkflow();

        System.out.println(sg);

        NumericData input = new NumericData(2, 3);
        System.out.println("input = " + input);
        NumericData output = sg.run(input, true); // ejecución con debug
        System.out.println("result = " + output);
    }

    private static StateGraph<NumericData> buildWorkflow() {
        StateGraph<NumericData> sg = new StateGraph<>("math2", "Add two numbers, and then square");

        sg.addNode("sum", (NumericData mo) -> mo.put("result", mo.get("op1") + mo.get("op2")))
          .addNode("square", (NumericData mo) -> mo.put("result", mo.get("result") * mo.get("result")));
          
        sg.addEdge("sum", "square");

        sg.setInitial("sum");
        sg.setFinal("square");

        return sg;
    }
}