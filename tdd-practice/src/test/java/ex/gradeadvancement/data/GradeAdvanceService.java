package ex.gradeadvancement.data;

import ex.gradeadvancement.AdvanceResult;

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
        AdvanceState state = states.get();
        if (state == AdvanceState.COMPLETED) {
            return AdvanceResult.ALREADY_COMPLETED;
        }
        Targets targets;
        if (state == AdvanceState.APPLY_FAILED) {
            targets = targetsImporter.imporTargets(targetsFilePath);
        } else {
            try {
                targets = targetsGet.gen();
            } catch (Exception e) {
                return AdvanceResult.TARGET_GET_FAILED;
            }
            try {
                targetsExporter.export(targetsFilePath,targets);
            } catch (Exception e) {
                return AdvanceResult.TARGET_EXPORT_FAILED;
            }
        }

        try {
            advanceApplier.apply(targets);
        } catch (Exception e) {
            states.set(AdvanceState.APPLY_FAILED);
            return AdvanceResult.TARGET_APPLY_FAILED;
        }
        return AdvanceResult.SUCCESS;
    }
}
