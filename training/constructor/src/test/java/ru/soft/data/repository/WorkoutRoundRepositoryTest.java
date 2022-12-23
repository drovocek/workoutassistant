package ru.soft.data.repository;

import ru.soft.data.model.WorkoutRound;
import ru.soft.testdata.TestDataStore;
import ru.soft.testdata.model.WorkoutRoundTestDataStore;

class WorkoutRoundRepositoryTest extends BaseRepositoryTest<WorkoutRound> {

    @Override
    protected TestDataStore<WorkoutRound> entityStore() {
        return new WorkoutRoundTestDataStore();
    }
}