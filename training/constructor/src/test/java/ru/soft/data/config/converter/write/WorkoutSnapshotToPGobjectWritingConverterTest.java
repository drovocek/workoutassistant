package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.soft.common.data.snapshot.WorkoutSnapshot;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.planSnapshot;

class WorkoutSnapshotToPGobjectWritingConverterTest
        extends BaseEntityToPGobjectWritingConverterTest<WorkoutSnapshot, WorkoutPlanSnapshotToPGobjectWritingConverter> {

    @Override
    protected WorkoutPlanSnapshotToPGobjectWritingConverter writingConverter(ObjectMapper mapper) {
        return new WorkoutPlanSnapshotToPGobjectWritingConverter(mapper);
    }

    @Override
    protected WorkoutSnapshot forWriting() {
        return planSnapshot();
    }
}