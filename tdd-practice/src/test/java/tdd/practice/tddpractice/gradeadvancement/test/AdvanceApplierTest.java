package tdd.practice.tddpractice.gradeadvancement.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ActiveProfiles;
import tdd.practice.tddpractice.gradeadvancement.data.GivenAssertHelper;
import tdd.practice.tddpractice.gradeadvancement.service.AdvanceApplier;
import tdd.practice.tddpractice.gradeadvancement.data.ApplyResult;
import tdd.practice.tddpractice.gradeadvancement.data.GradeCount;
import tdd.practice.tddpractice.gradeadvancement.data.Targets;
import tdd.practice.tddpractice.gradeadvancement.data.User;

import java.util.Arrays;
import java.util.Collection;

@SpringBootTest
@ActiveProfiles("test")
class AdvanceApplierTest {

    @Autowired
    GivenAssertHelper helper;

    @Autowired
    AdvanceApplier applier;

    @Test
    void applyTest() {
        helper.clearStu();
        helper.givenStd(101, 1);
        helper.givenStd(102, 2);
        helper.givenStd(103, 3);

        Targets targets = new Targets(Arrays.asList(
                new User(101, 1),
                new User(102, 2),
                new User(103, 3)
        ));
        applier.apply(targets);

        helper.assertStuGrade(101, 2);
        helper.assertStuGrade(102, 3);
        helper.assertStuGrade(103, 4);
    }

    @Test
    void applyResult() {
        helper.clearStu();
        helper.givenStd(101, 1);
        helper.givenStd(102, 2);

        Targets targets = new Targets(Arrays.asList(
                new User(101, 1),
                new User(102, 2),
                new User(103, 3)
        ));

        ApplyResult applyResult = applier.apply(targets);
        Collection<GradeCount> cnts = applyResult.getGradeCounts();
        Assertions.assertThat(cnts).contains(
                new GradeCount(2, 1),
                new GradeCount(3, 1));
    }
}