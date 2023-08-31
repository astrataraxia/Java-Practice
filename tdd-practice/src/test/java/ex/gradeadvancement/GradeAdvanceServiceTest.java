package ex.gradeadvancement;

import ex.gradeadvancement.data.AdvanceState;
import ex.gradeadvancement.data.GradeAdvanceService;
import ex.gradeadvancement.data.States;
import ex.gradeadvancement.data.TargetGen;
import ex.gradeadvancement.exception.AlreadyCompletedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class GradeAdvanceServiceTest {

    private final States state = new States(Paths.get("target/state"));
    GradeAdvanceService service = new GradeAdvanceService(state);

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Paths.get("target/state"));
    }

    @Test
    void alreadyCompleted() {
        state.set(AdvanceState.COMPLETED);
        AdvanceResult result = service.advance();
        assertThat(result).isEqualTo(AdvanceResult.ALREADY_COMPLETED);
    }
    
    @Test
    void targetsGenFail() {
        TargetGen mockGen = mock(TargetGen.class);
        given(mockGen.gen()).willThrow(new RuntimeException("!"));

        AdvanceResult result = service.advance();
    }
}
