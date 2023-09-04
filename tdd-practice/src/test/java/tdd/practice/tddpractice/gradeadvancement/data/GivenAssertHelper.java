package tdd.practice.tddpractice.gradeadvancement.data;

import org.assertj.core.api.Assertions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class GivenAssertHelper {

    private final JdbcTemplate jdbcTemplate;

    public GivenAssertHelper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void clearStu() {
        jdbcTemplate.update("truncate table stdinfo");
    }

    public void givenStd(int id, int grade) {
        jdbcTemplate.update("insert into stdinfo values (?,?)", id, grade);
    }

    public void assertStuGrade(int id, int expGrade) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select std_id, std_grade from stdinfo where std_id =?", id);
        rs.next();
        Assertions.assertThat(rs.getInt("std_grade")).isEqualTo(expGrade);
    }
}

