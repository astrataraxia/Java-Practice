package ex.gradeadvancement.temp;

import ex.gradeadvancement.data.User;
import org.junit.jupiter.api.Test;

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
    }
}
