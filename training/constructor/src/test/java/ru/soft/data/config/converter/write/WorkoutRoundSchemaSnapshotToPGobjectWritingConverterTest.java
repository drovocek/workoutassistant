package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.soft.data.model.snapshot.WorkoutRoundSchemaSnapshot;

import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutRoundSchemaSnapshot;

class WorkoutRoundSchemaSnapshotToPGobjectWritingConverterTest
        extends BaseEntityToPGobjectWritingConverterTest<WorkoutRoundSchemaSnapshot, WorkoutRoundSchemaSnapshotToPGobjectWritingConverter> {

    @Override
    protected WorkoutRoundSchemaSnapshotToPGobjectWritingConverter writingConverter(ObjectMapper mapper) {
        return new WorkoutRoundSchemaSnapshotToPGobjectWritingConverter(mapper);
    }

    @Override
    protected WorkoutRoundSchemaSnapshot forWriting() {
        return createWorkoutRoundSchemaSnapshot();
    }

    @Override
    protected PGobject expected() {
        return createWorkoutRoundSchemaPGobject();
    }
}