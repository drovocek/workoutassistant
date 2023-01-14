package ru.soft.data.repository;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.TrainingSession;
import ru.soft.data.test.WorkoutSessionTestDataStore;
import ru.soft.utils.MatcherFactory;

class TrainingSessionRepositoryTest extends BaseRepositoryTest<TrainingSession> {

    @Override
    protected TestDataStore<TrainingSession> entityStore() {
        return new WorkoutSessionTestDataStore();
    }

    @Override
    protected MatcherFactory.Matcher<TrainingSession> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(TrainingSession.class, "id");
    }
}