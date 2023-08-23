package parser.cal.eval;

import java.math.BigDecimal;

public class EvalResult {

    private BigDecimal value;

    public EvalResult(BigDecimal bigDecimal) {
        this.value = bigDecimal;
    }

    public static EvalResult of(String value) {
        return new EvalResult(new BigDecimal(value));
    }

    public BigDecimal getValue() {
        return value;
    }

    public double getValueAsDouble() {
        return value.doubleValue();
    }

}
