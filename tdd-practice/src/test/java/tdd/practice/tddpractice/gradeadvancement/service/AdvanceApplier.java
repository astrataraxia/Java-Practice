package tdd.practice.tddpractice.gradeadvancement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import tdd.practice.tddpractice.gradeadvancement.data.ApplyResult;
import tdd.practice.tddpractice.gradeadvancement.data.GradeCount;
import tdd.practice.tddpractice.gradeadvancement.data.Targets;
import tdd.practice.tddpractice.gradeadvancement.data.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

@Service
public class AdvanceApplier {

    private Logger log = LoggerFactory.getLogger(AdvanceApplier.class);
    private final JdbcTemplate jdbcTemplate;

    public AdvanceApplier(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ApplyResult apply(Targets targets) {
        LongAdder counter = new LongAdder();
        //자바의 람다는 외부 변수를 바꿀 수 없다...
        Flux.fromStream(targets.getUsers().stream().limit(1_0000))
                .buffer(1000)
                .subscribe(users -> {
                    jdbcTemplate.batchUpdate(
                            "update student set std_grade = ? where std_id=?",
                            new BatchPreparedStatementSetter() {
                                @Override
                                public void setValues(PreparedStatement ps, int i) throws SQLException {
                                    User user = users.get(i);
                                    ps.setInt(1, user.getGrade() + 1);
                                    ps.setInt(2, user.getId());
                                }
                                @Override
                                public int getBatchSize() {
                                    return users.size();
                                }
                            }
                    );
                    counter.add(users.size());
                    log.info("processing... {}", counter.longValue());
                });

        Map<Integer, GradeCount> gradeCntMap = new HashMap<>();
//        targets.getUsers()
//                .forEach(user -> {
//                    int nextGrade = user.getGrade() + 1;
//                    jdbcTemplate.update("update student set std_grade = ? where std_id=?",
//                            nextGrade, user.getId());
//                    GradeCount gradeCount = gradeCntMap.get(nextGrade);
//                    if (gradeCount == null) {
//                        gradeCount = new GradeCount(nextGrade, 0);
//                        gradeCntMap.put(nextGrade, gradeCount);
//                    }
//                    gradeCount.inc();
//                });
        return new ApplyResult(gradeCntMap);
    }
}
