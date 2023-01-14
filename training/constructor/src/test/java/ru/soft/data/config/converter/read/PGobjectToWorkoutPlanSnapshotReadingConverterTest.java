package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.soft.common.data.snapshot.WorkoutPlanSnapshot;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.planSnapshot;

class PGobjectToWorkoutPlanSnapshotReadingConverterTest
        extends BasePGobjectToEntityReadingConverterTest<WorkoutPlanSnapshot, PGobjectToWorkoutPlanSnapshotReadingConverter> {

    @Override
    protected PGobjectToWorkoutPlanSnapshotReadingConverter readingConverter(ObjectMapper mapper) {
        return new PGobjectToWorkoutPlanSnapshotReadingConverter(mapper);
    }

    @Override
    protected WorkoutPlanSnapshot expected() {
        return planSnapshot();
    }
}