package ru.soft.data.repository;

import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.WorkoutSession;
import ru.soft.testdata.model.WorkoutSessionTestDataStore;
import ru.soft.utils.MatcherFactory;

class WorkoutSessionRepositoryTest extends BaseRepositoryTest<WorkoutSession> {

    @Override
    protected TestDataStore<WorkoutSession> entityStore() {
        return new WorkoutSessionTestDataStore();
    }

    @Override
    protected MatcherFactory.Matcher<WorkoutSession> matcher() {
        return MatcherFactory.usingIgnoringFieldsComparator(WorkoutSession.class, "id");
    }
}