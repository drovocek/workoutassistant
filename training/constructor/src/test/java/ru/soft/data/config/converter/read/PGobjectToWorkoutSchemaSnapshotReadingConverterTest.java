package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import static ru.soft.utils.JsonTestUtils.createWorkoutSchemaPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutSchemaSnapshot;

class PGobjectToWorkoutSchemaSnapshotReadingConverterTest
        extends BasePGobjectToEntityReadingConverterTest<WorkoutSchemaSnapshot, PGobjectToWorkoutSchemaSnapshotReadingConverter> {

    @Override
    protected PGobjectToWorkoutSchemaSnapshotReadingConverter readingConverter(ObjectMapper mapper) {
        return new PGobjectToWorkoutSchemaSnapshotReadingConverter(mapper);
    }

    @Override
    protected PGobject forReading() {
        return createWorkoutSchemaPGobject();
    }

    @Override
    protected WorkoutSchemaSnapshot expected() {
        return createWorkoutSchemaSnapshot();
    }
}