package tdd.practice.tddpractice.gradeadvancement.data;

import java.util.Objects;

public class User {
    int id;
    int grade;

    public User(int id, int grade) {
        this.id = id;
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id && grade == user.grade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grade);
    }

    public int getId() {
        return id;
    }

    public int getGrade() {
        return grade;
    }
}
