package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.soft.common.data.snapshot.WorkoutPlanSnapshot;
import ru.soft.testdata.WorkoutPlanTestDataStore;

class WorkoutPlanSnapshotToPGobjectWritingConverterTest
        extends BaseEntityToPGobjectWritingConverterTest<WorkoutPlanSnapshot, WorkoutPlanSnapshotToPGobjectWritingConverter> {

    @Override
    protected WorkoutPlanSnapshotToPGobjectWritingConverter writingConverter(ObjectMapper mapper) {
        return new WorkoutPlanSnapshotToPGobjectWritingConverter(mapper);
    }

    @Override
    protected WorkoutPlanSnapshot forWriting() {
        return WorkoutPlanTestDataStore.workoutPlanSnapshot();
    }

    @Override
    protected PGobject expected() {
        return WorkoutPlanTestDataStore.workoutPlanPGobject();
    }
}