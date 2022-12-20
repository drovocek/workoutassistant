package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.soft.common.data.snapshot.WorkoutRoundSchemaSnapshot;
import ru.soft.testdata.WorkoutRoundTestDataStore;

class PGobjectToWorkoutRoundSchemaSnapshotReadingConverterTest
        extends BasePGobjectToEntityReadingConverterTest<WorkoutRoundSchemaSnapshot, PGobjectToWorkoutRoundSchemaSnapshotReadingConverter> {

    @Override
    protected PGobjectToWorkoutRoundSchemaSnapshotReadingConverter readingConverter(ObjectMapper mapper) {
        return new PGobjectToWorkoutRoundSchemaSnapshotReadingConverter(mapper);
    }

    @Override
    protected PGobject forReading() {
        return WorkoutRoundTestDataStore.workoutRoundSchemaPGobject();
    }

    @Override
    protected WorkoutRoundSchemaSnapshot expected() {
        return WorkoutRoundTestDataStore.workoutRoundSchemaSnapshot();
    }
}