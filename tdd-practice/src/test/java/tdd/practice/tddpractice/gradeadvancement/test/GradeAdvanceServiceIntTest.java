package tdd.practice.tddpractice.gradeadvancement.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tdd.practice.tddpractice.gradeadvancement.data.AdvanceResult;
import tdd.practice.tddpractice.gradeadvancement.data.GivenAssertHelper;
import tdd.practice.tddpractice.gradeadvancement.service.GradeAdvanceService;

import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest
@ActiveProfiles("test")
class GradeAdvanceServiceIntTest {

    @Autowired
    private GivenAssertHelper helper;

    @Autowired
    GradeAdvanceService service;

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(GradeAdvanceService.DEFAULT_TARGETS_FILE);
    }

    @Test
    void applySuccess() {
        helper.clearStu();
        helper.givenStd(501, 1);
        helper.givenStd(502, 2);

        AdvanceResult result = service.advance();
        Assertions.assertThat(result).isEqualTo(AdvanceResult.SUCCESS);

        helper.assertStuGrade(501, 2);
        helper.assertStuGrade(502, 3);
    }

}