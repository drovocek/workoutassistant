package ru.soft.web.mapper;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutSessionToTestDataStore;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.data.model.WorkoutSession;
import ru.soft.testdata.model.WorkoutSessionTestDataStore;

class WorkoutSessionTOMapperTest extends AbstractTOMapperTest<WorkoutSession, WorkoutSessionTo> {

    @Override
    TOMapper<WorkoutSession, WorkoutSessionTo> mapper() {
        return new WorkoutSessionTOMapper();
    }

    @Override
    TestDataStore<WorkoutSession> entityStore() {
        return new WorkoutSessionTestDataStore();
    }

    @Override
    TestDataStore<WorkoutSessionTo> toStore() {
        return new WorkoutSessionToTestDataStore();
    }
}