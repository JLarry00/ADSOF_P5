package src.decorate;

import java.util.function.*;
import src.graph.*;
import java.util.*;
import java.io.*;

public class StateGraphLogger<T> extends BaseDecorator<T> {
    private String fileName;
    private List<String> registers;

    public StateGraphLogger(InterfaceStateGraph<T> wrappee, String fileName) {
        super(wrappee);
        this.fileName = fileName;
        this.registers = new ArrayList<String>();
    }

    public List<String> getRegisters() {
        return Collections.unmodifiableList(registers);
    }

    public void addRegister(String register) {
        registers.add(register);
    }

    @Override
    public T run(T input, boolean debug) {
        T result = super.run(input, debug);
        Collections.reverse(registers);
        for (String register : registers) {
            guardar(register);
        }
        return result;
    }

    private void guardar(String mensaje) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InterfaceStateGraph<T> addNode(String name, Consumer<T> action) {
        super.addNode(name, action);
        Node<T, Object> node = super.getNode(name);
        node = new NodeLogger<>(node, this);
        super.replaceNode(node);
        return this;
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
