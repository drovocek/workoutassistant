package ru.soft.web.controller.unit;

import ru.soft.common.AppApi;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.testdata.to.TrainingSessionToTestDataStore;
import ru.soft.common.to.TrainingSessionTo;
import ru.soft.utils.MatcherFactory;

class TrainingSessionApiControllerUnitTest extends AbstractApiControllerUnitTest<TrainingSessionTo> {

    @Override
    protected String getApiUrl() {
        return AppApi.TrainingSessions.URL;
    }

    @Override
    protected MatcherFactory.Matcher<TrainingSessionTo> matcher() {
        return MatcherFactory.usingEqualsComparator(TrainingSessionTo.class);
    }

    @Override
    protected TestDataStore<TrainingSessionTo> toStore() {
        return new TrainingSessionToTestDataStore();
    }
}