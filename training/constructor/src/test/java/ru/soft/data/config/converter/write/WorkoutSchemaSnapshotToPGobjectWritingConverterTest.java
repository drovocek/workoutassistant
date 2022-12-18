package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.soft.common.data.snapshot.WorkoutSchemaSnapshot;

import static ru.soft.utils.JsonTestUtils.createWorkoutSchemaPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutSchemaSnapshot;

class WorkoutSchemaSnapshotToPGobjectWritingConverterTest
        extends BaseEntityToPGobjectWritingConverterTest<WorkoutSchemaSnapshot, WorkoutSchemaSnapshotToPGobjectWritingConverter> {

    @Override
    protected WorkoutSchemaSnapshotToPGobjectWritingConverter writingConverter(ObjectMapper mapper) {
        return new WorkoutSchemaSnapshotToPGobjectWritingConverter(mapper);
    }

    @Override
    protected WorkoutSchemaSnapshot forWriting() {
        return createWorkoutSchemaSnapshot();
    }

    @Override
    protected PGobject expected() {
        return createWorkoutSchemaPGobject();
    }
}