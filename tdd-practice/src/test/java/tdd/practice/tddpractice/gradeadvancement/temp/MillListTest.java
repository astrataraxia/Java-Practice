package tdd.practice.tddpractice.gradeadvancement.temp;

import org.junit.jupiter.api.Test;
import tdd.practice.tddpractice.gradeadvancement.data.User;

import java.util.LinkedList;
import java.util.List;

public class MillListTest {

    @Test
    void MillionUserTest() {
        System.out.println("before -> " + Runtime.getRuntime().freeMemory() / 1024 / 1024);
        List<User> users = new LinkedList<>();
        for (int i = 0; i < 150_0000; i++){
            users.add(new User(i, 0));
        }
        System.out.println("after -> " + Runtime.getRuntime().freeMemory() / 1024 / 1024);
        // before -> 240, after -> 176 .. 64 150만개 List 담는네 사용. (LinkedList)
        // before -> 240, after -> 187 .. 53 150만개 List 담는네 사용. (ArrayList)
    }

}
