package tdd.practice.tddpractice.gradeadvancement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tdd.practice.tddpractice.gradeadvancement.data.AdvanceApplier;
import tdd.practice.tddpractice.gradeadvancement.data.AdvanceResult;
import tdd.practice.tddpractice.gradeadvancement.data.AdvanceState;
import tdd.practice.tddpractice.gradeadvancement.data.ApplyResult;
import tdd.practice.tddpractice.gradeadvancement.service.GradeAdvanceService;
import tdd.practice.tddpractice.gradeadvancement.data.States;
import tdd.practice.tddpractice.gradeadvancement.data.Targets;
import tdd.practice.tddpractice.gradeadvancement.data.TargetsExporter;
import tdd.practice.tddpractice.gradeadvancement.data.TargetsGen;
import tdd.practice.tddpractice.gradeadvancement.data.TargetsImporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

public class GradeAdvanceServiceTest {

    private final States states = new States(Paths.get("target/state"));
    TargetsGen mockGen = mock(TargetsGen.class);
    private TargetsExporter mockExporter = mock(TargetsExporter.class);
    private TargetsImporter mockImporter = mock(TargetsImporter.class);
    private AdvanceApplier mockApplier = mock(AdvanceApplier.class);
    GradeAdvanceService service = new GradeAdvanceService(states, mockGen, mockExporter, mockApplier, mockImporter);

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Paths.get("target/state"));
    }

    @Test
    void alreadyCompleted() {
        states.set(AdvanceState.COMPLETED);
        AdvanceResult result = service.advance();
        assertThat(result).isEqualTo(AdvanceResult.ALREADY_COMPLETED);
    }
    
    @Test
    void targetsGenFail() {
        given(mockGen.gen()).willThrow(new RuntimeException("!"));

        AdvanceResult result = service.advance();
        assertThat(result).isEqualTo(AdvanceResult.TARGET_GET_FAILED);
    }

    @Test
    void targetExportFail() {
        given(mockGen.gen()).willReturn(mock(Targets.class));
        willThrow(new RuntimeException("!"))
                .given(mockExporter).export(any(Path.class), any(Targets.class));

        AdvanceResult result = service.advance();
        assertThat(result).isEqualTo(AdvanceResult.TARGET_EXPORT_FAILED);
    }

    @Test
    void applyFail() {
        // AdvanceApplier
        given(mockGen.gen()).willReturn(mock(Targets.class));
        given(mockApplier.apply(any(Targets.class)))
                .willThrow(new RuntimeException("!"));

        AdvanceResult result = service.advance();
        assertThat(result).isEqualTo(AdvanceResult.TARGET_APPLY_FAILED);
    }

    @Test
    void applyFail_Then_State_ApplyFailed() {
        given(mockGen.gen()).willReturn(mock(Targets.class));
        given(mockApplier.apply(any(Targets.class)))
                .willThrow(new RuntimeException("!"));

        service.advance();
        assertThat(states.get()).isEqualTo(AdvanceState.APPLY_FAILED);
    }

    @Test
    void applySuccess() {
        given(mockGen.gen()).willReturn(mock(Targets.class));
        given(mockApplier.apply(any(Targets.class)))
                .willReturn(mock(ApplyResult.class));

        AdvanceResult result = service.advance();
        assertThat(result).isEqualTo(AdvanceResult.SUCCESS);
    }

    @Test
    void state_ApplyFailed_When_Advance() {
        states.set(AdvanceState.APPLY_FAILED);
        Targets targets = new Targets(null);
        given(mockImporter.imporTargets(any(Path.class)))
                .willReturn(targets);
        service.advance();

        then(mockGen).shouldHaveNoMoreInteractions();
        then(mockExporter).shouldHaveNoInteractions();
        then(mockApplier).should().apply(Mockito.eq(targets));
    }
}
