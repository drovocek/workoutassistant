package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.soft.common.data.snapshot.WorkoutPlanSnapshot;

import static ru.soft.utils.JsonTestUtils.createWorkoutPlanPGobject;
import static ru.soft.utils.JsonTestUtils.createWorkoutPlanSnapshot;

class PGobjectToWorkoutPlanSnapshotReadingConverterTest
        extends BasePGobjectToEntityReadingConverterTest<WorkoutPlanSnapshot, PGobjectToWorkoutPlanSnapshotReadingConverter> {

    @Override
    protected PGobjectToWorkoutPlanSnapshotReadingConverter readingConverter(ObjectMapper mapper) {
        return new PGobjectToWorkoutPlanSnapshotReadingConverter(mapper);
    }

    @Override
    protected PGobject forReading() {
        return createWorkoutPlanPGobject();
    }

    @Override
    protected WorkoutPlanSnapshot expected() {
        return createWorkoutPlanSnapshot();
    }
}