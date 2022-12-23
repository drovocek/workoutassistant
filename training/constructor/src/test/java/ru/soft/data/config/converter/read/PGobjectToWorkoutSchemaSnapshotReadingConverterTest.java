package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.soft.common.data.snapshot.WorkoutSchemaSnapshot;

import static ru.soft.testdata.snapshot.TestSnapshotStore.workoutSchemaSnapshot;

class PGobjectToWorkoutSchemaSnapshotReadingConverterTest
        extends BasePGobjectToEntityReadingConverterTest<WorkoutSchemaSnapshot, PGobjectToWorkoutSchemaSnapshotReadingConverter> {

    @Override
    protected PGobjectToWorkoutSchemaSnapshotReadingConverter readingConverter(ObjectMapper mapper) {
        return new PGobjectToWorkoutSchemaSnapshotReadingConverter(mapper);
    }

    @Override
    protected WorkoutSchemaSnapshot expected() {
        return workoutSchemaSnapshot();
    }
}