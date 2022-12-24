package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.soft.common.data.snapshot.WorkoutSchemaSnapshot;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.workoutSchemaSnapshot;

class WorkoutSchemaSnapshotToPGobjectWritingConverterTest
        extends BaseEntityToPGobjectWritingConverterTest<WorkoutSchemaSnapshot, WorkoutSchemaSnapshotToPGobjectWritingConverter> {

    @Override
    protected WorkoutSchemaSnapshotToPGobjectWritingConverter writingConverter(ObjectMapper mapper) {
        return new WorkoutSchemaSnapshotToPGobjectWritingConverter(mapper);
    }

    @Override
    protected WorkoutSchemaSnapshot forWriting() {
        return workoutSchemaSnapshot();
    }
}