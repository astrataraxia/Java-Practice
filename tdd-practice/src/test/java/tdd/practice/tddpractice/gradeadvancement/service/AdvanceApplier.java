package tdd.practice.tddpractice.gradeadvancement.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tdd.practice.tddpractice.gradeadvancement.data.ApplyResult;
import tdd.practice.tddpractice.gradeadvancement.data.GradeCount;
import tdd.practice.tddpractice.gradeadvancement.data.Targets;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdvanceApplier {

    private final JdbcTemplate jdbcTemplate;

    public AdvanceApplier(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ApplyResult apply(Targets targets) {
        Map<Integer, GradeCount> gradeCntMap = new HashMap<>();
        targets.getUsers()
                .forEach(user -> {
                    int nextGrade = user.getGrade();
                    jdbcTemplate.update("update stdinfo set std_grade = ? where std_id=?",
                            user.getGrade() + 1, user.getId());
                    GradeCount gradeCount = gradeCntMap.get(nextGrade);
                    if (gradeCount == null) {
                        gradeCount = new GradeCount(nextGrade, 0);
                        gradeCntMap.put(nextGrade, gradeCount);
                    }
                    gradeCount.inc();
                });
        return new ApplyResult(gradeCntMap);
    }
}
