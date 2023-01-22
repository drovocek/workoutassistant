package ru.soft.web.mapper;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutToTestDataStore;
import ru.soft.common.to.WorkoutTo;
import ru.soft.data.model.Workout;
import ru.soft.data.test.WorkoutTestDataStore;

class WorkoutTOMapperTest extends AbstractTOMapperTest<Workout, WorkoutTo> {

    @Override
    TOMapper<Workout, WorkoutTo> mapper() {
        return new TrainingSessionTOMapper();
    }

    @Override
    TestDataStore<Workout> entityStore() {
        return new WorkoutTestDataStore();
    }

    @Override
    TestDataStore<WorkoutTo> toStore() {
        return new WorkoutToTestDataStore();
    }
}