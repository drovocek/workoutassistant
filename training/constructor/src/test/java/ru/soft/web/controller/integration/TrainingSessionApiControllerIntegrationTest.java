package ru.soft.web.controller.integration;

import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.TrainingSessionToTestDataStore;
import ru.soft.common.to.TrainingSessionTo;
import ru.soft.data.model.TrainingSession;
import ru.soft.data.test.TrainingSessionTestDataStore;
import ru.soft.utils.MatcherFactory;

class TrainingSessionApiControllerIntegrationTest extends AbstractApiControllerIntegrationTest<TrainingSession, TrainingSessionTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.TrainingSessions.URL;
    }

    @Override
    protected MatcherFactory.Matcher<TrainingSessionTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(TrainingSessionTo.class,"id");
    }

    @Override
    protected TestDataStore<TrainingSession> entityStore() {
        return new TrainingSessionTestDataStore();
    }

    @Override
    protected TestDataStore<TrainingSessionTo> toStore() {
        return new TrainingSessionToTestDataStore();
    }
}