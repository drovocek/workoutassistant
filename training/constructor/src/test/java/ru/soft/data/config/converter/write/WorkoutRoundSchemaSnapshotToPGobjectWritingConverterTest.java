package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.soft.common.data.snapshot.WorkoutRoundSchemaSnapshot;
import ru.soft.testdata.WorkoutRoundTestDataStore;

class WorkoutRoundSchemaSnapshotToPGobjectWritingConverterTest
        extends BaseEntityToPGobjectWritingConverterTest<WorkoutRoundSchemaSnapshot, WorkoutRoundSchemaSnapshotToPGobjectWritingConverter> {

    @Override
    protected WorkoutRoundSchemaSnapshotToPGobjectWritingConverter writingConverter(ObjectMapper mapper) {
        return new WorkoutRoundSchemaSnapshotToPGobjectWritingConverter(mapper);
    }

    @Override
    protected WorkoutRoundSchemaSnapshot forWriting() {
        return WorkoutRoundTestDataStore.workoutRoundSchemaSnapshot();
    }

    @Override
    protected PGobject expected() {
        return WorkoutRoundTestDataStore.workoutRoundSchemaPGobject();
    }
}