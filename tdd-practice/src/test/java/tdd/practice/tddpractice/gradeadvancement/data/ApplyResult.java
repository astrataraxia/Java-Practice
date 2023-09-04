package tdd.practice.tddpractice.gradeadvancement.data;

import java.util.Collection;
import java.util.Map;

public class ApplyResult {
    private Map<Integer, GradeCount> gradeCntMap;

    public ApplyResult(Map<Integer, GradeCount> gradeCntMap) {

        this.gradeCntMap = gradeCntMap;
    }

    public Collection<GradeCount> getGradeCounts() {
        return gradeCntMap.values();
    }
}
