package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.soft.data.model.snapshot.WorkoutPlanSnapshot;

import static ru.soft.utils.JsonTestUtils.createWorkoutPlanPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutPlanSnapshot;

class WorkoutPlanSnapshotToPGobjectWritingConverterTest
        extends BaseEntityToPGobjectWritingConverterTest<WorkoutPlanSnapshot, WorkoutPlanSnapshotToPGobjectWritingConverter> {

    @Override
    protected WorkoutPlanSnapshotToPGobjectWritingConverter writingConverter(ObjectMapper mapper) {
        return new WorkoutPlanSnapshotToPGobjectWritingConverter(mapper);
    }

    @Override
    protected WorkoutPlanSnapshot forWriting() {
        return createWorkoutPlanSnapshot();
    }

    @Override
    protected PGobject expected() {
        return createWorkoutPlanPGobject();
    }
}