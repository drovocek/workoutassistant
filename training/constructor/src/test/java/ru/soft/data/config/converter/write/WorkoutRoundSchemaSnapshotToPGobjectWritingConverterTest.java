package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.soft.common.data.snapshot.WorkoutRoundSchemaSnapshot;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.workoutRoundSchemaSnapshot;

class WorkoutRoundSchemaSnapshotToPGobjectWritingConverterTest
        extends BaseEntityToPGobjectWritingConverterTest<WorkoutRoundSchemaSnapshot, WorkoutRoundSchemaSnapshotToPGobjectWritingConverter> {

    @Override
    protected WorkoutRoundSchemaSnapshotToPGobjectWritingConverter writingConverter(ObjectMapper mapper) {
        return new WorkoutRoundSchemaSnapshotToPGobjectWritingConverter(mapper);
    }

    @Override
    protected WorkoutRoundSchemaSnapshot forWriting() {
        return workoutRoundSchemaSnapshot();
    }
}