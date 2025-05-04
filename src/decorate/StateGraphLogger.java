package src.decorate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StateGraphLogger<T> extends BaseDecorator<T> {
    private String fileName;

    public StateGraphLogger(InterfaceStateGraph<T> wrappee, String fileName) {
        super(wrappee);
        this.fileName = fileName;
    }

    @Override
    public T run(T input, boolean debug) {
        guardar("node decrease executed,");
        T result = super.run(input, debug);
        guardar(" with output: " + input.toString());
        return result;
    }

    private void guardar(String mensaje) {
        String timestamp = new java.text.SimpleDateFormat("[dd/MM/yyyy - HH:mm:ss]").format(new java.util.Date());
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(timestamp + " " + mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getSuffixDecorators() {
        return "[logged]";
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
