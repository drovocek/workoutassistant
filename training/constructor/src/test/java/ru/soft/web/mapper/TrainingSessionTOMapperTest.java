package ru.soft.web.mapper;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.TrainingSessionToTestDataStore;
import ru.soft.common.to.TrainingSessionTo;
import ru.soft.data.model.TrainingSession;
import ru.soft.data.test.TrainingSessionTestDataStore;

class TrainingSessionTOMapperTest extends AbstractTOMapperTest<TrainingSession, TrainingSessionTo> {

    @Override
    TOMapper<TrainingSession, TrainingSessionTo> mapper() {
        return new TrainingSessionTOMapper();
    }

    @Override
    TestDataStore<TrainingSession> entityStore() {
        return new TrainingSessionTestDataStore();
    }

    @Override
    TestDataStore<TrainingSessionTo> toStore() {
        return new TrainingSessionToTestDataStore();
    }
}