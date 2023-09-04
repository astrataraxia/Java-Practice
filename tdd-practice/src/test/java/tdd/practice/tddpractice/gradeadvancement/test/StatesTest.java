package tdd.practice.tddpractice.gradeadvancement.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;
import tdd.practice.tddpractice.gradeadvancement.data.AdvanceState;
import tdd.practice.tddpractice.gradeadvancement.service.States;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StatesTest {

    private final Path path = Paths.get("target/state");
    States states = new States(path);

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(path);
    }

    @Test
    void noStateFile() {
        AdvanceState state = states.get();
        assertThat(state).isNull();
    }

    @Test
    void set() throws IOException {
        states.set(AdvanceState.GENERATING);
        List<String> lines = Files.readAllLines(Paths.get("target/state"));
        assertThat(lines.get(0)).isEqualTo(AdvanceState.GENERATING.name());
    }

    @Test
    void get() throws IOException {
        FileCopyUtils.copy(AdvanceState.GENERATING.name(), new FileWriter(path.toFile()));
        AdvanceState state = states.get();
        assertThat(state).isEqualTo(AdvanceState.GENERATING);
    }
}
