package statm.if_statm;

public record CalculateCommand(
        CalculateType calculateType,
        int num1,
        int num2
) {
}
