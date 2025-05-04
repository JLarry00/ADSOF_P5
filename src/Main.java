package src;

import src.data.*;
import src.decorate.*;
import src.graph.*;

public class Main {

    public static void main(String[] args) {
        StateGraph<NumericData> g = new StateGraph<>("loop-down", "Get a number, and decrease if positive");
        StateGraphLogger<NumericData> lg = new StateGraphLogger<>(g, "traces.txt");
        StateGraphProfiler<NumericData> sg = new StateGraphProfiler<>(lg);

        sg.addNode("decrease", (NumericData mo) -> mo.setOp1(mo.getOp1() - 1));
        sg.addConditionalEdge("decrease", "decrease", (NumericData mo) -> mo.getOp1() > 0);
        sg.setInitial("decrease");

        NumericData input = new NumericData(3, 0);
        System.out.println(sg + "\ninput = " + input);
        NumericData output = sg.run(input, true);
        System.out.println("result = " + output);
        System.out.println("history = " + sg.history());
    }









    
    /*public static void main(String[] args) {
        StreamingStateGraph<DoubleData> sg = buildWorkflow(); // el método construye el workflow
        System.out.println(sg);
        List
          .of(1, 5, 2, 4)
          .forEach( d->{ DoubleData wfInput = new DoubleData(d, 0);
            System.out.println("Workflow input = "+wfInput);
            sg.run(wfInput, true);
          });
        System.out.println("History="+sg.history());
    }

    private static StreamingStateGraph<DoubleData> buildWorkflow() {
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
    }*/
}