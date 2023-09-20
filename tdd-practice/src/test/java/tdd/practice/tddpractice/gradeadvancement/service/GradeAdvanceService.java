package tdd.practice.tddpractice.gradeadvancement.service;

import org.springframework.stereotype.Service;
import tdd.practice.tddpractice.gradeadvancement.data.AdvanceResult;
import tdd.practice.tddpractice.gradeadvancement.data.AdvanceState;
import tdd.practice.tddpractice.gradeadvancement.data.ApplyResult;
import tdd.practice.tddpractice.gradeadvancement.data.Either;
import tdd.practice.tddpractice.gradeadvancement.data.Targets;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GradeAdvanceService {

    public static final Path DEFAULT_TARGETS_FILE = Paths.get(".", "targets");
    private Path targetsFilePath = DEFAULT_TARGETS_FILE;
    private States states;
    private TargetsGen targetsGen;
    private TargetsExporter targetsExporter;
    private AdvanceApplier advanceApplier;
    private TargetsImporter targetsImporter;

    public GradeAdvanceService(States states,
                               TargetsGen targetsGen,
                               TargetsExporter targetsExporter,
                               AdvanceApplier advanceApplier,
                               TargetsImporter targetsImporter) {
        this.states = states;
        this.targetsGen = targetsGen;
        this.targetsExporter = targetsExporter;
        this.advanceApplier = advanceApplier;
        this.targetsImporter = targetsImporter;
    }

    public void setTargetsFilePath(Path targetsFilePath) {
        this.targetsFilePath = targetsFilePath;
    }

    //refactoring 필요 try-catch 구문이 엄청 많음
    public AdvanceResult advance() {
        //state 구해서 확인
        AdvanceState state = states.get();
        if (state == AdvanceState.COMPLETED) {
            return AdvanceResult.ALREADY_COMPLETED;
        }
        Either<AdvanceResult, Targets> targetsEither = state == AdvanceState.APPLY_FAILED ?
                importTargets() : getExportTargets();

        //읽어온 타겟 목록에 승급을 적용
        Either<AdvanceResult, ApplyResult> applyEither =
                targetsEither.flatMap(ts -> applyAdvanceTargets(ts));
        if (applyEither.isLeft()) {
            return applyEither.getLeft();
        } else {
            return AdvanceResult.SUCCESS;
        }
    }

    private Either<AdvanceResult, Targets> importTargets() {
        try {
            return Either.right(targetsImporter.imporTargets(targetsFilePath));
        } catch (Exception e) {
            return Either.left(AdvanceResult.TARGET_IMPORT_FAILED);
        }
    }

    private Either<AdvanceResult, Targets> getExportTargets() {
        return genTargets().flatMap(ts -> exportTargets(ts));
    }

    private Either<AdvanceResult, Targets> genTargets() {
        try {
            // 처음 읽어오는 거면 target 생성
            return Either.right(targetsGen.gen());
        } catch (Exception e) {
            return Either.left(AdvanceResult.TARGET_GET_FAILED);
        }
    }

    private Either<AdvanceResult, Targets> exportTargets(Targets ts) {
        try {
            // 생성한 target을 export해서 File로 저장
            targetsExporter.export(targetsFilePath, ts);
            return Either.right(ts);
        } catch (Exception e) {
            return Either.left(AdvanceResult.TARGET_EXPORT_FAILED);
        }
    }

    private Either<AdvanceResult, ApplyResult> applyAdvanceTargets(Targets ts) {
        try {
            // 구한 target을 가지고 승급을 시켜줌
            return Either.right(advanceApplier.apply(ts));
        } catch (Exception e) {
            states.set(AdvanceState.APPLY_FAILED);
            return Either.left(AdvanceResult.TARGET_APPLY_FAILED);
        }
    }
}
