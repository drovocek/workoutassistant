package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.soft.common.data.snapshot.WorkoutRoundSchemaSnapshot;

import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaSnapshot;

class PGobjectToWorkoutRoundSchemaSnapshotReadingConverterTest
        extends BasePGobjectToEntityReadingConverterTest<WorkoutRoundSchemaSnapshot, PGobjectToWorkoutRoundSchemaSnapshotReadingConverter> {

    @Override
    protected PGobjectToWorkoutRoundSchemaSnapshotReadingConverter readingConverter(ObjectMapper mapper) {
        return new PGobjectToWorkoutRoundSchemaSnapshotReadingConverter(mapper);
    }

    @Override
    protected PGobject forReading() {
        return createWorkoutRoundSchemaPGobject();
    }

    @Override
    protected WorkoutRoundSchemaSnapshot expected() {
        return createWorkoutRoundSchemaSnapshot();
    }
}