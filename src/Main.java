package src;

import src.data.*;
import src.graph.*;

public class Main {
    public static void main(String[] args) {
        StateGraph<NumericData> sg = buildWorkflow();
        StateGraph<StringData> sg2 = buildWorkflow(sg);

        System.out.println(sg2);

        StringData input = new StringData("jamon", 2);
        System.out.println("input = " + input);
        StringData output = sg2.run(input, true); // ejecución con debug
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

    public static StateGraph<StringData> buildWorkflow(StateGraph<NumericData> wfNumeric) {
        StateGraph<StringData> sg = new StateGraph<>("replicate", "Replicates a given word");
    
        sg.addWfNode("calculate", wfNumeric)
          .withInjector((StringData sd) -> sd.toNumericData())
          .withExtractor((NumericData nd, StringData sd) -> sd.setTimes(nd.get("result")));
    
        sg.addNode("replicate", sd -> sd.replicate());
    
        sg.addEdge("calculate", "replicate")
          .addConditionalEdge("replicate", "replicate", sd -> sd.times() > 0);
    
        sg.setInitial("calculate");
    
        return sg;
    }
}