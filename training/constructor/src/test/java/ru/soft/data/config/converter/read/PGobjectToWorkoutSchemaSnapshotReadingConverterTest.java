package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.soft.common.data.snapshot.WorkoutSchemaSnapshot;
import ru.soft.testdata.WorkoutPlanTestDataStore;

class PGobjectToWorkoutSchemaSnapshotReadingConverterTest
        extends BasePGobjectToEntityReadingConverterTest<WorkoutSchemaSnapshot, PGobjectToWorkoutSchemaSnapshotReadingConverter> {

    @Override
    protected PGobjectToWorkoutSchemaSnapshotReadingConverter readingConverter(ObjectMapper mapper) {
        return new PGobjectToWorkoutSchemaSnapshotReadingConverter(mapper);
    }

    @Override
    protected PGobject forReading() {
        return WorkoutPlanTestDataStore.workoutSchemaPGobject();
    }

    @Override
    protected WorkoutSchemaSnapshot expected() {
        return WorkoutPlanTestDataStore.workoutSchemaSnapshot();
    }
}