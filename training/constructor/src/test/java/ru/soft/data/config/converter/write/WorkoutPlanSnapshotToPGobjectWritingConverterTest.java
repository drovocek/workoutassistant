package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.soft.common.data.snapshot.WorkoutPlanSnapshot;

import static ru.soft.testdata.snapshot.TestSnapshotStore.workoutPlanSnapshot;

class WorkoutPlanSnapshotToPGobjectWritingConverterTest
        extends BaseEntityToPGobjectWritingConverterTest<WorkoutPlanSnapshot, WorkoutPlanSnapshotToPGobjectWritingConverter> {

    @Override
    protected WorkoutPlanSnapshotToPGobjectWritingConverter writingConverter(ObjectMapper mapper) {
        return new WorkoutPlanSnapshotToPGobjectWritingConverter(mapper);
    }

    @Override
    protected WorkoutPlanSnapshot forWriting() {
        return workoutPlanSnapshot();
    }
}