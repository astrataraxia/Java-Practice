package tdd.practice.tddpractice.gradeadvancement.data;

import java.util.function.Function;

public class Either<L,R> {

    private L leftValue;
    private R rightValue;

    public Either(L leftValue, R rightValue) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public boolean isLeft() {
        return leftValue != null;
    }

    public boolean isRight() {
        return !isLeft();
    }

    public <R2> Either<L, R2> map(Function<R, R2> func) {
        if (isLeft()) return Either.left(leftValue);
        else return Either.right(func.apply(rightValue));
    }


    public <R2> Either<L, R2> flatMap(Function<R, Either<L, R2>> func) {
        if (isLeft()) return Either.left(leftValue);
        else return func.apply(rightValue);
    }

    public static <L, R> Either<L, R> left(L leftValue) {
        return new Either<>(leftValue, null);
    }

    public static <L, R> Either<L, R> right(R rightValue) {
        return new Either<>(null, rightValue);
    }


    public L getLeft() {
        return leftValue;
    }

    public R getRight() {
        return rightValue;
    }
}
