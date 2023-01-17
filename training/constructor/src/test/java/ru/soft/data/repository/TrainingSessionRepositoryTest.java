package ru.soft.data.repository;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.TrainingSession;
import ru.soft.data.test.TrainingSessionTestDataStore;
import ru.soft.utils.MatcherFactory;

class TrainingSessionRepositoryTest extends BaseRepositoryTest<TrainingSession> {

    @Override
    protected TestDataStore<TrainingSession> entityStore() {
        return new TrainingSessionTestDataStore();
    }

    @Override
    protected MatcherFactory.Matcher<TrainingSession> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(TrainingSession.class, "id");
    }
}