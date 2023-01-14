package ru.soft.web.controller.integration;

import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.WorkoutSessionToTestDataStore;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.data.model.TrainingSession;
import ru.soft.data.test.WorkoutSessionTestDataStore;
import ru.soft.utils.MatcherFactory;

class TrainingSessionApiControllerIntegrationTest extends AbstractApiControllerIntegrationTest<TrainingSession, WorkoutSessionTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.WorkoutSessions.URL;
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutSessionTo> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutSessionTo.class,"id");
    }

    @Override
    protected TestDataStore<TrainingSession> entityStore() {
        return new WorkoutSessionTestDataStore();
    }

    @Override
    protected TestDataStore<WorkoutSessionTo> toStore() {
        return new WorkoutSessionToTestDataStore();
    }
}