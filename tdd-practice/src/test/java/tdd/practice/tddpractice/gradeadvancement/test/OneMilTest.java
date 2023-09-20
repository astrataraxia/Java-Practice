package tdd.practice.tddpractice.gradeadvancement.test;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tdd.practice.tddpractice.gradeadvancement.data.Targets;
import tdd.practice.tddpractice.gradeadvancement.service.AdvanceApplier;
import tdd.practice.tddpractice.gradeadvancement.service.TargetsExporter;
import tdd.practice.tddpractice.gradeadvancement.service.TargetsGen;
import tdd.practice.tddpractice.gradeadvancement.service.TargetsImporter;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class OneMilTest {

    private Logger log = LoggerFactory.getLogger(OneMilTest.class);

    @Autowired
    TargetsGen gen;
    @Autowired
    TargetsExporter exporter;
    @Autowired
    TargetsImporter importer;
    @Autowired
    AdvanceApplier applier;

    @Disabled
    @Test
    void name() {
        log.info("start gen");
        Targets targets = this.gen.gen();
        assertThat(targets.getUsers()).hasSize(1_500_000);
        log.info("end gen");

        Path target = Paths.get("target", "1m_targets");

        log.info("start exporter");
        exporter.export(target, targets);
        log.info("end exporter");

        log.info("start importer");
        Targets impTargets = importer.imporTargets(target);
        assertThat(impTargets.getUsers()).hasSize(1_500_000);
        log.info("end importer");
    }

    @Test
    void applyTest() {
        Targets impTargets = importer.imporTargets(Paths.get("target", "1m_targets"));
        applier.apply(impTargets);
    }
}
