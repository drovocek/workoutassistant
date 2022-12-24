package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.soft.common.data.snapshot.WorkoutRoundSchemaSnapshot;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.workoutRoundSchemaSnapshot;

class PGobjectToWorkoutRoundSchemaSnapshotReadingConverterTest
        extends BasePGobjectToEntityReadingConverterTest<WorkoutRoundSchemaSnapshot, PGobjectToWorkoutRoundSchemaSnapshotReadingConverter> {

    @Override
    protected PGobjectToWorkoutRoundSchemaSnapshotReadingConverter readingConverter(ObjectMapper mapper) {
        return new PGobjectToWorkoutRoundSchemaSnapshotReadingConverter(mapper);
    }

    @Override
    protected WorkoutRoundSchemaSnapshot expected() {
        return workoutRoundSchemaSnapshot();
    }
}