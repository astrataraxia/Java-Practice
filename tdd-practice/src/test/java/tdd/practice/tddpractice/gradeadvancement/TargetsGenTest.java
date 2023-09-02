package tdd.practice.tddpractice.gradeadvancement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import tdd.practice.tddpractice.gradeadvancement.data.Targets;
import tdd.practice.tddpractice.gradeadvancement.data.TargetsGen;
import tdd.practice.tddpractice.gradeadvancement.data.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class TargetsGenTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void gen() {
        // given
        clearStu();
        givenStd(101, 1);
        givenStd(102, 2);
        givenStd(103, 3);

        TargetsGen targetsGen = new TargetsGen(jdbcTemplate);
        Targets targets = targetsGen.gen();
        assertThat(targets.getUsers()).hasSize(3);
        assertThat(targets.getUsers()).contains(
                new User(101, 1), new User(102, 2), new User(103, 3)
        );
    }

    private void clearStu() {
        jdbcTemplate.update("truncate table stdinfo");
    }

    private void givenStd(int id, int grade) {
        jdbcTemplate.update("insert into stdinfo values (?,?)", id, grade);
    }
}
