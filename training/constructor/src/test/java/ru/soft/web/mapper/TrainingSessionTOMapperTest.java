package ru.soft.web.mapper;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutSessionToTestDataStore;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.data.model.TrainingSession;
import ru.soft.data.test.WorkoutSessionTestDataStore;

class TrainingSessionTOMapperTest extends AbstractTOMapperTest<TrainingSession, WorkoutSessionTo> {

    @Override
    TOMapper<TrainingSession, WorkoutSessionTo> mapper() {
        return new WorkoutSessionTOMapper();
    }

    @Override
    TestDataStore<TrainingSession> entityStore() {
        return new WorkoutSessionTestDataStore();
    }

    @Override
    TestDataStore<WorkoutSessionTo> toStore() {
        return new WorkoutSessionToTestDataStore();
    }
}