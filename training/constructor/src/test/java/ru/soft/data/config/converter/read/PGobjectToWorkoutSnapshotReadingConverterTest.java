package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.soft.common.data.snapshot.WorkoutSnapshot;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.planSnapshot;

class PGobjectToWorkoutSnapshotReadingConverterTest
        extends BasePGobjectToEntityReadingConverterTest<WorkoutSnapshot, PGobjectToWorkoutPlanSnapshotReadingConverter> {

    @Override
    protected PGobjectToWorkoutPlanSnapshotReadingConverter readingConverter(ObjectMapper mapper) {
        return new PGobjectToWorkoutPlanSnapshotReadingConverter(mapper);
    }

    @Override
    protected WorkoutSnapshot expected() {
        return planSnapshot();
    }
}