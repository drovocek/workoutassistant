package ru.soft.web.mapper;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutPlanToTestDataStore;
import ru.soft.common.to.WorkoutPlanTo;
import ru.soft.data.model.WorkoutPlan;
import ru.soft.data.test.WorkoutPlanTestDataStore;

class WorkoutPlanTOMapperTest extends AbstractTOMapperTest<WorkoutPlan, WorkoutPlanTo> {

    @Override
    TOMapper<WorkoutPlan, WorkoutPlanTo> mapper() {
        return new WorkoutPlanTOMapper();
    }

    @Override
    TestDataStore<WorkoutPlan> entityStore() {
        return new WorkoutPlanTestDataStore();
    }

    @Override
    TestDataStore<WorkoutPlanTo> toStore() {
        return new WorkoutPlanToTestDataStore();
    }
}