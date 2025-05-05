package src.decorate;

import src.graph.*;

public class NodeLogger<T, R> extends NodeDecorator<T, R> {
    private StateGraphLogger<T> stateGraphLogger;

    public NodeLogger(Node<T, R> wrappee, StateGraphLogger<T> stateGraphLogger) {
        super(wrappee);
        this.stateGraphLogger = stateGraphLogger;
    }

    @Override
    public boolean run(T input, boolean debug, int i) {
        String inputString = input.toString();
        String str = "node " + getName() + " executed,";
        String timestamp = new java.text.SimpleDateFormat("[dd/MM/yyyy - HH:mm:ss]").format(new java.util.Date());
        str = timestamp + " " + str;
        str = str + " with output: " + inputString;
        boolean result = super.run(input, debug, i);
        if (i > 2) stateGraphLogger.addRegister(str);

        return result;
    }

    @Override
    public String toString(){
        return super.toString() + " [logged]";
    }
}