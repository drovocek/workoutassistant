package ru.soft.data.repository;

import ru.soft.data.model.WorkoutSession;
import ru.soft.testdata.TestDataStore;
import ru.soft.testdata.model.WorkoutSessionTestDataStore;

class WorkoutSessionRepositoryTest extends BaseRepositoryTest<WorkoutSession> {

    @Override
    protected TestDataStore<WorkoutSession> entityStore() {
        return new WorkoutSessionTestDataStore();
    }
}