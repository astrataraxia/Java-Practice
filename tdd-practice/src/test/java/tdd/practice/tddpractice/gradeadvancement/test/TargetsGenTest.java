package tdd.practice.tddpractice.gradeadvancement.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tdd.practice.tddpractice.gradeadvancement.data.GivenAssertHelper;
import tdd.practice.tddpractice.gradeadvancement.data.Targets;
import tdd.practice.tddpractice.gradeadvancement.service.TargetsGen;
import tdd.practice.tddpractice.gradeadvancement.data.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class TargetsGenTest {

    @Autowired
    private TargetsGen targetsGen;
    @Autowired
    private GivenAssertHelper givenAssertHelper;

    @Test
    void gen() {
        // given
        givenAssertHelper.clearStu();

        givenAssertHelper.givenStd(101, 1);
        givenAssertHelper.givenStd(102, 2);
        givenAssertHelper.givenStd(103, 3);

        Targets targets = targetsGen.gen();
        assertThat(targets.getUsers()).hasSize(3);
        assertThat(targets.getUsers()).contains(
                new User(101, 1), new User(102, 2), new User(103, 3)
        );
    }
}
