package statm.construct;

public class Client {

    public int someMethod(CalculateCommand calculateCommand) {
        CalculateType calculateType = calculateCommand.calculateType();
        int num1 = calculateCommand.num1();
        int num2 = calculateCommand.num2();

        //1. CalculateType이 null이라면 연산이 되지 않음
        //2. DIVIDE 시에 num2가 0이라면?
        if (calculateType == null) {
            return 0;
        }

        if (calculateType.equals(CalculateType.ADD)) {
            return num1 + num2;
        } else if (calculateType.equals(CalculateType.MINUS)) {
            return num1 - num2;
        } else if (calculateType.equals(CalculateType.MULTIPLY)) {
            return num1 * num2;
        } else if (calculateType.equals(CalculateType.DIVIDE)) {
            if (num2 == 0) {
                throw new IllegalArgumentException();
            }
            return num1 / num2;
        }
        return 0;
    }
}
