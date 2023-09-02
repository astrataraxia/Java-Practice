package tdd.practice.tddpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TddPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TddPracticeApplication.class, args);
	}
}
