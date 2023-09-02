package tdd.practice.tddpractice.gradeadvancement.data;

import tdd.practice.tddpractice.gradeadvancement.exception.NoTargetsFileException;
import tdd.practice.tddpractice.gradeadvancement.exception.TargetsFileBadFormatException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TargetsImporter {

    public Targets imporTargets(Path path) {
        if (!Files.exists(path)) {
            throw new NoTargetsFileException();
        }
        try {
            List<String> lines = Files.readAllLines(path);
            List<User> users = lines.stream().map(line -> {
                String[] values = line.split("=");
                if(values.length % 2 !=0  ) throw new TargetsFileBadFormatException();
                return new User(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            }).toList();
            return new Targets(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
