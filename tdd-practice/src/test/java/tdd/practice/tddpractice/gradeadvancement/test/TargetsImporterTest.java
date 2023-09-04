package tdd.practice.tddpractice.gradeadvancement.test;

import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;
import tdd.practice.tddpractice.gradeadvancement.data.Targets;
import tdd.practice.tddpractice.gradeadvancement.service.TargetsImporter;
import tdd.practice.tddpractice.gradeadvancement.data.User;
import tdd.practice.tddpractice.gradeadvancement.exception.NoTargetsFileException;
import tdd.practice.tddpractice.gradeadvancement.exception.TargetsFileBadFormatException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TargetsImporterTest {

    private TargetsImporter importer = new TargetsImporter();

    @Test
    void importTargets_given_noFile() {
        assertThatThrownBy(() -> importer.imporTargets(Paths.get("target/asdfghkl")))
                .isInstanceOf(NoTargetsFileException.class);
    }

    @Test
    void importTargets_given_badFormatFile() throws IOException {
        Path path = Paths.get("target/badformat");

        FileCopyUtils.copy("105=5\n1066",
                new FileWriter(path.toFile()));

        assertThatThrownBy(() -> importer.imporTargets(path))
                .isInstanceOf(TargetsFileBadFormatException.class);
    }

    @Test
    void importTargets_given_fileExists() throws IOException {
        Path path = Paths.get("target/tfile");

        FileCopyUtils.copy("105=5\n106=6",
                new FileWriter(path.toFile()));

        Targets targets = importer.imporTargets(path);
        List<User> users = targets.getUsers();

        assertThat(users).hasSize(2);
        assertThat(users.get(0)).isEqualTo(new User(105, 5));
        assertThat(users.get(1)).isEqualTo(new User(106, 6));
    }

    @Test
    void importTargets_given_fileIsEmpty() throws IOException {
        Path path = Paths.get("target/emptyfile");

        FileCopyUtils.copy("",
                new FileWriter(path.toFile()));

        Targets targets = importer.imporTargets(path);
        List<User> users = targets.getUsers();

        assertThat(users).hasSize(0);
        assertThat(users).isEmpty();
    }
}