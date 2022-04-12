package io.cucumber.skeleton;

public class Calculator {

    private String calculation = "";

    public void push(Object obj) {
        if (obj instanceof Integer) {
            if (calculation.length()>0) {
                calculation += " ";
            }
            calculation += String.valueOf(obj);
        }
        else {
            String numbers[] = calculation.split(" ");
            calculation = numbers[0] + obj + numbers[1];
        }
    }

    public double value() {
        String operators[] = calculation.split("[0-9]+");
        String operands[] = calculation.split("[+-]");

        int aggregate = Integer.parseInt(operands[0]);
        for (int i = 1; i < operands.length; i++) {
            if (operators[i].equals("+"))
                aggregate += Integer.parseInt(operands[i]);
            else
                aggregate -= Integer.parseInt(operands[i]);
        }
        calculation = "";
        return aggregate;
    }

}
