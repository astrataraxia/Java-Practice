package tdd.practice.tddpractice.gradeadvancement.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tdd.practice.tddpractice.gradeadvancement.data.Targets;
import tdd.practice.tddpractice.gradeadvancement.data.User;

import java.util.List;

@Service
public class TargetsGen {

    private JdbcTemplate jdbcTemplate;

    public TargetsGen(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Targets gen() {
        List<User> users = jdbcTemplate.query("select * from stdinfo",
                (rs, rowNum) -> new User(rs.getInt("std_id"), rs.getInt("std_grade")));
        return new Targets(users);
    }
}
