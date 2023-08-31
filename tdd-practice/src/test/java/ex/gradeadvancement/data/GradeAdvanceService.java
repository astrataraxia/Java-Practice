package ex.gradeadvancement.data;

import ex.gradeadvancement.AdvanceResult;

public class GradeAdvanceService {

    private States states;

    public GradeAdvanceService(States states) {
        this.states = states;
    }

    public AdvanceResult advance() {
        AdvanceState state = states.get();
        if (state == AdvanceState.COMPLETED) {
            return AdvanceResult.ALREADY_COMPLETED;
        }
        return null;
    }
}
