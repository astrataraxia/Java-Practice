package statm.construct;

public record CalculateCommand(
        CalculateType calculateType,
        int num1,
        int num2
) {
    public CalculateCommand(CalculateType calculateType, int num1, int num2) {
        if (calculateType == null) {
            throw new IllegalArgumentException();
        }
        if (calculateType == CalculateType.DIVIDE && num2 == 0) {
            throw new IllegalArgumentException();
        }
        this.calculateType = calculateType;
        this.num1 = num1;
        this.num2 = num2;
    }
}
