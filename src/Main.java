package src;

import src.data.*;
import src.graph.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
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
    }
}