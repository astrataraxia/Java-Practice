package statm.switch_statm;

public class Client {

    public int someMethod(CalculateCommand calculateCommand) {
        CalculateType calculateType = calculateCommand.calculateType();
        int num1 = calculateCommand.num1();
        int num2 = calculateCommand.num2();

        int result = 0;

        switch (calculateType) {
            case ADD -> result = num1 + num2;
            case MINUS -> result = num1 - num2;
            case MULTIPLY -> {
                if (num2 == 0) throw new IllegalArgumentException();
                result = num1 * num2;
            }
            case DIVIDE -> result = num1 / num2;
            default -> {
                return result;
            }
        }
        return result;
    }
}
