package tdd.practice.tddpractice.gradeadvancement.service;

import tdd.practice.tddpractice.gradeadvancement.data.AdvanceApplier;
import tdd.practice.tddpractice.gradeadvancement.data.AdvanceResult;
import tdd.practice.tddpractice.gradeadvancement.data.AdvanceState;
import tdd.practice.tddpractice.gradeadvancement.data.States;
import tdd.practice.tddpractice.gradeadvancement.data.Targets;
import tdd.practice.tddpractice.gradeadvancement.data.TargetsExporter;
import tdd.practice.tddpractice.gradeadvancement.data.TargetsGen;
import tdd.practice.tddpractice.gradeadvancement.data.TargetsImporter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GradeAdvanceService {

    private States states;
    private TargetsGen targetsGet;
    private TargetsExporter targetsExporter;
    private AdvanceApplier advanceApplier;
    private TargetsImporter targetsImporter;
    private final Path targetsFilePath = Paths.get("target/targets");
    public GradeAdvanceService(States states,
                               TargetsGen targetsGet,
                               TargetsExporter targetsExporter,
                               AdvanceApplier advanceApplier, TargetsImporter targetsImporter) {
        this.states = states;
        this.targetsGet = targetsGet;
        this.targetsExporter = targetsExporter;
        this.advanceApplier = advanceApplier;
        this.targetsImporter = targetsImporter;
    }

    public AdvanceResult advance() {
        //state 구해서 확인
        AdvanceState state = states.get();
        if (state == AdvanceState.COMPLETED) {
            return AdvanceResult.ALREADY_COMPLETED;
        }
        Targets targets;
        if (state == AdvanceState.APPLY_FAILED) {
            // TODO : targetsImporter.importTargets가 실패하는 경우에 대한 구현 필요
            // state가 error로 끝낫으면, 저장해 둔 대상 다시 읽어오기.
            targets = targetsImporter.imporTargets(targetsFilePath);
        } else {
            try {
                // 처음 읽어오는 거면 target 생성
                targets = targetsGet.gen();
            } catch (Exception e) {
                return AdvanceResult.TARGET_GET_FAILED;
            }
            try {
                // 생성한 target을 export해서 File로 저장
                targetsExporter.export(targetsFilePath,targets);
            } catch (Exception e) {
                return AdvanceResult.TARGET_EXPORT_FAILED;
            }
        }
        try {
            // 구한 target을 가지고 승급을 시켜줌
            advanceApplier.apply(targets);
        } catch (Exception e) {
            states.set(AdvanceState.APPLY_FAILED);
            return AdvanceResult.TARGET_APPLY_FAILED;
        }
        return AdvanceResult.SUCCESS;
    }
}
