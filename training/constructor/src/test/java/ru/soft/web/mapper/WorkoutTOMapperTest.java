package ru.soft.web.mapper;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutPlanToTestDataStore;
import ru.soft.common.to.WorkoutTo;
import ru.soft.data.model.Workout;
import ru.soft.data.test.WorkoutPlanTestDataStore;

class WorkoutTOMapperTest extends AbstractTOMapperTest<Workout, WorkoutTo> {

    @Override
    TOMapper<Workout, WorkoutTo> mapper() {
        return new WorkoutPlanTOMapper();
    }

    @Override
    TestDataStore<Workout> entityStore() {
        return new WorkoutPlanTestDataStore();
    }

    @Override
    TestDataStore<WorkoutTo> toStore() {
        return new WorkoutPlanToTestDataStore();
    }
}