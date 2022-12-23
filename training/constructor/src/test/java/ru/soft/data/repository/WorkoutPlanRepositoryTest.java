package ru.soft.data.repository;

import ru.soft.data.model.WorkoutPlan;
import ru.soft.testdata.TestDataStore;
import ru.soft.testdata.model.WorkoutPlanTestDataStore;

class WorkoutPlanRepositoryTest extends BaseRepositoryTest<WorkoutPlan> {

    @Override
    protected TestDataStore<WorkoutPlan> entityStore() {
        return new WorkoutPlanTestDataStore();
    }
}