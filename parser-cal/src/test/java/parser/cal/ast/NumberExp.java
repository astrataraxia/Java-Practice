package parser.cal.ast;

public class NumberExp implements Exp {

    private String value;

    public NumberExp(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public double valueAsDouble() {
        return Double.parseDouble(value);
    }

    @Override
    public String debugString() {
        return value;
    }
}
